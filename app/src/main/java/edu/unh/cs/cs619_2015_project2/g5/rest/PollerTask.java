package edu.unh.cs.cs619_2015_project2.g5.rest;

/**
 * Created by rabbit on 10/21/15.
 */

import android.os.SystemClock;

import edu.unh.cs.cs619_2015_project2.g5.event.BusProvider;
import edu.unh.cs.cs619_2015_project2.g5.event.GridUpdateEvent;
import edu.unh.cs.cs619_2015_project2.g5.model.FieldEntity;
import edu.unh.cs.cs619_2015_project2.g5.util.GridWrapper;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;

/**
 * PollerTask.
 */
@EBean
public class PollerTask {
    private static final String TAG = "GridPollerTask";

    @Bean
    BusProvider mBusProvider;

    @RestService
    BulletZoneRestClient restClient;

    /**
     * Do poll. Updating every 100ms.
     */
    @Background(id = "grid_poller_task")
    public void doPoll() {
        while (true) {
            onGridUpdate(restClient.grid());
            // poll server every 100ms
            SystemClock.sleep(100);
        }
    }

    /**
     * Grid updates.
     * @param gw GridWrapper
     */
    @UiThread
    public void onGridUpdate(GridWrapper gw) {
        //Log.d(TAG, "grid at timestamp: " + gw.getTimeStamp());
        //busProvider.getEventBus().post(new GridUpdateEvent(gw));
        FieldEntity[][] fieldEntities = new FieldEntity[16][16];
        for(int r = 0; r < 16; r++){
            for(int c = 0; c < 16; c++) {
                fieldEntities [r][c] = new FieldEntity(r, c, gw.getGrid()[r][c]);
            }
        }
        mBusProvider.getInstance().post(new GridUpdateEvent(fieldEntities, gw.getGrid(), gw.getTimeStamp()));
    }
}
