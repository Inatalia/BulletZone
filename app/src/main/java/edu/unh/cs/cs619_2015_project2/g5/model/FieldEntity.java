package edu.unh.cs.cs619_2015_project2.g5.model;

import android.content.Context;
import android.widget.ImageView;

import edu.unh.cs.cs619_2015_project2.g5.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

/**
 * Represents the Game field. Creates tank, wall, bullet, and grass.
 * Created by rabbit on 11/9/15.
 */
public class FieldEntity {
    private int gridValue;
    private int r, c;
    private HashMap<Long,Tank> collectionOfTanks = new HashMap<Long,Tank>();

    private Boolean isInit = true;

    /**
     * Constructor
     */
    public FieldEntity(){}

    /**
     * Constructor with grid value and [x,y]
     * @param r row
     * @param c col
     * @param grid grid value
     */
    public FieldEntity(int r, int c, int grid){
        this.r = r;
        this.c = c;
        this.gridValue = grid;
    }

    /**
     * Creates tank object
     * @param tankid tank's id
     * @param life tank's life
     * @param curPos current position of tank
     * @param degree degree to rotate position of tank
     * @return tank object
     */
    public Tank createTank(long tankid, int life, int curPos, float degree){
        return new Tank(tankid, life, curPos, degree);
    }

    /**
     * Creates indestructible wall object
     * @param mContext context of grid adapter
     * @param gridItem imageview of wall
     * @return wall object
     */
    private Wall createIndestructibleWall(Context mContext, ImageView gridItem){
        Picasso.with(mContext)
                .load(R.drawable.wall)
                .noFade()
                .into(gridItem);
        return new Wall();
    }

    /**
     * Creates destructible wall object
     * @param mContext context of grid adapter
     * @param gridItem imageview of wall
     * @param life wall's life
     * @return wall object
     */
    private Wall createDestructibleWall(Context mContext, ImageView gridItem, int life){
        Picasso.with(mContext)
                .load(R.drawable.destoyedwall)
                .noFade()
                .into(gridItem);
        return new Wall(life);
    }

    /**
     * Creates bullet object
     * @param mContext context of grid adapter
     * @param gridItem imageview of bullet
     * @param damage damage of the bullet
     * @return
     */
    public Bullet createBullet(Context mContext, ImageView gridItem, int damage){
        Picasso.with(mContext)
                .load(R.drawable.bullet)
                .noFade()
                .into(gridItem);
        return new Bullet(damage);
    }

    /**
     * Creates grass object
     * @param mContext context of grid adapter
     * @param gridItem imageview of grass
     * @return grass object
     */
    public Grass createGrass(Context mContext, ImageView gridItem){
        Picasso.with(mContext)
                .load(R.drawable.grass)
                .noFade()
                .into(gridItem);
        return new Grass();
    }

    /**
     * Updates the image of the field entity
     * @param mContext context of grid adapter
     * @param gridItem imageview of whole object including tanks
     * @param tankId tank's id
     */
    public void updateFieldEntity(Context mContext, ImageView gridItem, long tankId){
        if (gridValue == 1000) {
            createIndestructibleWall(mContext, gridItem);
        }
        else if(gridValue >= 1000 && gridValue <= 2000){
            int life = gridValue - 1000;
            createDestructibleWall(mContext, gridItem, life);
        }
        else if (gridValue >= 2000000 && gridValue <= 3000000) {
            int damageOfBullet =Integer.parseInt(String.valueOf(gridValue).substring(4));
            createBullet(mContext, gridItem, damageOfBullet);
        }
        else if (gridValue >= 10000000 && gridValue <= 20000000) {
            //Updates Tanks' position
            int direction =Integer.parseInt(String.valueOf(gridValue).substring(7));
            long id =Integer.parseInt(String.valueOf(gridValue).substring(1, 4));
            int life =Integer.parseInt(String.valueOf(gridValue).substring(4, 7));

            //Log.d("DIRECTION", String.valueOf(direction));

            Tank mTank;
            if(collectionOfTanks.containsKey(id)) {
                mTank = collectionOfTanks.get(id);

                // 4 - DOWN or 0 - UP
                if ((mTank.getDirection() == 0) || (mTank.getDirection() == 4)) {
                    if (direction == 2)
                        mTank.setDegree(2);
                    else if (direction == 6)
                        mTank.setDegree(6);
                    mTank.setDirection((int) direction);
                }
                // 2 - RIGHT or 6 - LEFT
                else if ((mTank.getDirection() == 2) || (mTank.getDirection() == 6)) {
                    if (direction == 4)
                        mTank.setDegree(4);
                    else if (direction == 0)
                        mTank.setDegree(0);
                    mTank.setDirection((int) direction);
                }
            }
            else {
                mTank = createTank(id, life, direction, direction);
                collectionOfTanks.put(id, mTank);
            }

            if(mTank.getID() == tankId) {
                Picasso.with(mContext)
                        .load(R.drawable.mytank)
                        .noFade()
                        .rotate(mTank.getDegree())
                        .into(gridItem);
            }
            else{
                Picasso.with(mContext)
                        .load(R.drawable.tank)
                        .noFade()
                        .rotate(mTank.getDegree())
                        .into(gridItem);
            }
        }
        else createGrass(mContext, gridItem);
    }

    /**
     * return value of the grid
     * @return returned value of the field
     */
    public int getValue(){
        return gridValue;
    }
}
