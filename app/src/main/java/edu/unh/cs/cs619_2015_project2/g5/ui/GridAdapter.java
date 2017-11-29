package edu.unh.cs.cs619_2015_project2.g5.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import edu.unh.cs.cs619_2015_project2.g5.R;
import edu.unh.cs.cs619_2015_project2.g5.model.FieldEntity;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.ViewById;

/**
 * Adapter class used to populate GridView.
 */
@EBean
public class GridAdapter extends BaseAdapter {

    @SystemService
    protected LayoutInflater inflater;

    @ViewById
    ImageView gridItem;

    private final Object monitor = new Object();

    //private int[][] mEntities = new int[16][16];
    private FieldEntity[][] mEntities = new FieldEntity[16][16];

    private Context mContext;

    private long tankID = -1;

    /**
     * Constructor.
     * @param context Context
     */
    public GridAdapter(Context context) {
        for(int r = 0; r < 16; r++){
            for(int c = 0; c < 16; c++) {
                mEntities [r][c] = new FieldEntity(r, c, 0);
            }
        }
        mContext = context;
    }

    /**
     * Update GridView with current events occuring on battlefield.
     * @param entities FieldEntities
     */
    public void updateList(FieldEntity[][] entities) {
        synchronized (monitor) {
            this.mEntities = entities;
            this.notifyDataSetChanged();
        }
    }

    /**
     * Sets the tank id
     * @param tankID tank's id
     */
    public void setTankId(long tankID){
        this.tankID = tankID;
    }

    /**
     * Total number of objects contained within the GridView.
     *
     * @return
     */
    @Override
    public int getCount() {
        return 16 * 16;
    }

    /**
     * Returns a specific object within the GridView.
     *
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        return mEntities[(int) position / 16][position % 16];
    }

    /**
     * Returns a specific object id within the GridView.
     *
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set.
     * @param position specified position
     * @param convertView View
     * @param parent Viewgroup
     * @return View of data set
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.field_item, null);
        }

        int row = position / 16;
        int col = position % 16;

        //int val = mEntities[row][col];
        FieldEntity eachField = mEntities[row][col];

        gridItem = (ImageView) convertView.findViewById(R.id.gridItem);

        Picasso.with(mContext)
                .load(R.drawable.grass)
                .noFade()
                .into(gridItem);

        synchronized (monitor) {
            eachField.updateFieldEntity(mContext, gridItem, tankID);
        }
        return convertView;
    }
}


