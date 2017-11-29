package edu.unh.cs.cs619_2015_project2.g5;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import com.google.common.eventbus.Subscribe;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.sql.SQLException;

import edu.unh.cs.cs619_2015_project2.g5.database.ReplaySource;
import edu.unh.cs.cs619_2015_project2.g5.event.BusProvider;
import edu.unh.cs.cs619_2015_project2.g5.event.GridUpdateEvent;
import edu.unh.cs.cs619_2015_project2.g5.model.FieldEntity;
import edu.unh.cs.cs619_2015_project2.g5.rest.BattlefieldFactory;
import edu.unh.cs.cs619_2015_project2.g5.rest.MyErrorHandler;
import edu.unh.cs.cs619_2015_project2.g5.rest.PollerTask;
import edu.unh.cs.cs619_2015_project2.g5.rest.ShakeListener;
import edu.unh.cs.cs619_2015_project2.g5.ui.GridAdapter;

@EActivity(edu.unh.cs.cs619_2015_project2.g5.R.layout.activity_main)
public class TankClientActivity extends AppCompatActivity {

    //TAG for log
    private static final String TAG = "TankClientActivity";

    @Bean
    BusProvider mBusProvider;

    @Bean
    MyErrorHandler myErrorHandler;

    @ViewById
    protected GridView gridView;

    @Bean
    BattlefieldFactory mBattlefieldFactory;

    @Bean
    PollerTask gridPollTask;

    //global tankId
    private long tankId = -1;

    //global context
    private Context mContext = TankClientActivity.this;

    private ShakeListener mShaker;

    @Bean
    protected GridAdapter mGridAdapter = new GridAdapter(mContext);

    private ReplaySource mDatabase;

    /**
     * OnCreate. Initialize TankClient Activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(edu.unh.cs.cs619_2015_project2.g5.R.layout.activity_main);

        mDatabase = new ReplaySource(this);

        try {
            mDatabase.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        mShaker = new ShakeListener(this);
        mBattlefieldFactory.shake(mShaker);
    }

    /**
     * onCreateOptionsMenu.
     * @param menu Menu
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(edu.unh.cs.cs619_2015_project2.g5.R.menu.menu_main, menu);
        return true;
    }

    /**
     * onOptionsItemSelected.
     * @param item MenuItem
     * @return true
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == edu.unh.cs.cs619_2015_project2.g5.R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * After View Injection.
     */
    @AfterViews
    protected void afterViewInjection() {
        joinAsync();
        SystemClock.sleep(500);
        gridView.setAdapter(mGridAdapter);
    }

    /**
     * After Inject.
     */
    @AfterInject
    protected void afterInject(){
        mBattlefieldFactory.setRestErrorHandler(myErrorHandler);
        mBusProvider.getInstance().register(eventHandler);
    }

    /**
     * Join the game asynchronously
     */
    @Background
    void joinAsync() {
        try {
            //tankId = restClient.join().getResult();
            tankId = mBattlefieldFactory.joinGame();
            Log.d(TAG, "tankId is " + tankId);
            mGridAdapter.setTankId(tankId);
            gridPollTask.doPoll(); // start polling the server
        } catch (Exception e) {

        }
    }

    /**
     * Object eventHandler
     */
    private Object eventHandler = new Object()
    {
        /**
         * This method will be notified on GridUpdateEvent events that are posted to the bus.
         * the main activity subscribe to the bus to receive GridUpdateEvents
         * @param event
         */
        @Subscribe
        public void onUpdateGrid(GridUpdateEvent event) {
            updateGrid(event);
        }
    };

    /**
     * Updates the grid display in main thread and inserts the grid into the database.
     *
     * @param event
     */
    @UiThread
    void updateGrid(GridUpdateEvent event){
        //int [][] grid = event.getGrid();
        FieldEntity[][] grid = event.getGrid();
        mGridAdapter.updateList(grid);
        mDatabase.insert(event);
    }

    /**
     * Register onResume onPause to/from the bus.
     */
    @Override
    protected void onResume() {
        super.onResume();
        mBusProvider.getInstance().register(eventHandler);
        mShaker.resume();
    }

    /**
     * Unregister onPause to/from the bus.
     */
    @Override
    protected void onPause() {
        super.onPause();
        mBusProvider.getInstance().unregister(eventHandler);
        mShaker.pause();
    }

    /**
     * Called when activity is destroyed. Close database here.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabase.close();
    }


    /**
     * Show the replay of the recording game.
     */
    @Click(edu.unh.cs.cs619_2015_project2.g5.R.id.replayButton)
    void replayButton() {
        //opens up new dialog activity to show the replay
        try {
            Toast.makeText(TankClientActivity.this, "Replay",
                    Toast.LENGTH_SHORT).show();
            if (getResources().getConfiguration().orientation ==
                    getResources().getConfiguration().ORIENTATION_LANDSCAPE) {
                DialogFragment newDialog = new ReplayActivityFragment();
                newDialog.show(TankClientActivity.this.getFragmentManager(),
                        "ReplayActivity");
            } else {
                Intent newIntent = new Intent(TankClientActivity.this,
                        edu.unh.cs.cs619_2015_project2.g5.ReplayActivity.class);
                //newIntent.putExtra("student_Id", tankId);
                startActivity(newIntent);
            }
        } catch (Exception e) {

        }
    }

    /**
     * Sets fire Button.
     */
    @Click(edu.unh.cs.cs619_2015_project2.g5.R.id.buttonFire)
    void buttonFire() {
        mBattlefieldFactory.fire(tankId);
    }

    /**
     * Leaves the game
     */
    void leave() {
        mBattlefieldFactory.leave(tankId);
    }

    /**
     * Move Buttons
     * Tank. If the value is 1ABCDEFG, then the ID of the tank is ABC,
     * it has DEF life and its direction is G.
     * (E.g., value = 12220071, tankId = 222, life = 007, direction = 2).
     * Directions: {0 - UP, 2 - RIGHT, 4 - DOWN, 6 - LEFT}
     * @param view
     */
    @Click({edu.unh.cs.cs619_2015_project2.g5.R.id.buttonUp, edu.unh.cs.cs619_2015_project2.g5.R.id.buttonDown, edu.unh.cs.cs619_2015_project2.g5.R.id.buttonLeft, edu.unh.cs.cs619_2015_project2.g5.R.id.buttonRight})
    void buttonOnMove(View view) {
        switch (view.getId()) {
            case edu.unh.cs.cs619_2015_project2.g5.R.id.buttonUp:
                mBattlefieldFactory.move(tankId, 0, TankClientActivity.this);
                break;
            case edu.unh.cs.cs619_2015_project2.g5.R.id.buttonDown:
                mBattlefieldFactory.move(tankId, 4, TankClientActivity.this);
                break;
            case edu.unh.cs.cs619_2015_project2.g5.R.id.buttonLeft:
                mBattlefieldFactory.move(tankId, 6, TankClientActivity.this);
                break;
            case edu.unh.cs.cs619_2015_project2.g5.R.id.buttonRight:
                mBattlefieldFactory.move(tankId, 2, TankClientActivity.this);
                break;
        }
    }
}