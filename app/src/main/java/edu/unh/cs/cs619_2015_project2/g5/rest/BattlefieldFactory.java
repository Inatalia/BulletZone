package edu.unh.cs.cs619_2015_project2.g5.rest;

import android.content.Context;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.rest.RestService;

/**
 * A Factory class that controls the game.
 * Created by rabbit on 11/9/15.
 */

@EBean
public class BattlefieldFactory {

    @RestService
    BulletZoneRestClient restClient; //Inject it

    @Bean
    MyErrorHandler myErrorHandler;

    private int savedState, counterFire, init = 0;

    private long tankId = -1;

    /**
     * Joins the game.
     * @return true if the tank succeeds joining the game, otherwise false
     */
    public long joinGame() {
        return restClient.join().getResult();
    }

    /**
     * Sets the Rest Error handler
     * @param myErrorHandler - ErrorHandler class that handles the error
     */
    public void setRestErrorHandler(MyErrorHandler myErrorHandler){
        restClient.setRestErrorHandler(myErrorHandler);
    }

    public void shake(ShakeListener mShaker){
        //ShakeListener mShaker = new ShakeListener(ctx);
        mShaker.setOnShakeListener(new ShakeListener.OnShakeListener() {
            //@UiThread
            public void onShake() {
                fire(tankId);
                //Toast.makeText(TankClientActivity.this, "Fire!!! ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Tank can only move forward or back relative to its current direction. No sideway movements are allowed.
     * Tank. If the value is 1ABCDEFG, then the ID of the tank is ABC,
     * it has DEF life and its direction is G.
     * (E.g., value = 12220071, tankId = 222, life = 007, direction = 2).
     * Directions: {0 - UP, 2 - RIGHT, 4 - DOWN, 6 - LEFT}
     * @param tankId tank's id
     * @param direction direction to move or turn
     */
    @Background
    public void move(long tankId, int  direction, Context context) {
        //public void move(long tankId, View view) {
        this.tankId = tankId;
        if(init == 0) {
            updateInitPosition();
            init++;
        }
        try{
            if(getPosition() == direction || (Math.abs(getPosition() - direction) == 4)) {
                restClient.move(tankId, (byte) direction).isResult();
                //init++;
            }
            else /*if(getPosition() != direction)*/
                turn(tankId, direction);
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Returns the saved state
     * @return savedState position of the tank
     */
    private int getPosition(){
        return savedState;
    }

    /**
     * Updates the initial position of tank
     */
    public void updateInitPosition() {
        int[][] grid = restClient.grid().getGrid();
        for(int r = 0; r < 16; r++){
            for(int c = 0; c < 16; c++) {
                int val = grid[r][c];
                if (val >= 10000000 && val <= 20000000) {
                    //Updates Tanks' position
                    int direction = Integer.parseInt(String.valueOf(val).substring(7));
                    long id = Integer.parseInt(String.valueOf(val).substring(1, 4));
                    if (id == tankId){
                        savedState = direction;
                    }

                }
            }
        }
    }

    /**
     * Tank can take only one turn per step.
     * (E.g., Correct turn: UP -> LEFT Incorrect turn: UP -> DOWN);
     * Directions: {0 - UP, 2 - RIGHT, 4 - DOWN, 6 - LEFT}
     * @param tankId tank's id
     * @param direction direction to go
     */
    @Background
    public void turn(long tankId, int direction) {
        this.tankId = tankId;
        try {
            switch (savedState) {
                //UP
                case 0:
                    if(direction == 2) {
                        savedState = 2;
                        restClient.turn(tankId, (byte) 2);
                    }
                    else if(direction == 6) {
                        savedState = 6;
                        restClient.turn(tankId, (byte) 6);
                    }
                    break;
                //Down
                case 4:
                    if(direction == 2) {
                        savedState = 2;
                        restClient.turn(tankId, (byte) 2);
                    }
                    else if(direction == 6) {
                        savedState = 6;
                        restClient.turn(tankId, (byte) 6);
                    }
                    break;
                //Left
                case 6:
                    if(direction == 0) {
                        savedState = 0;
                        restClient.turn(tankId, (byte) 0);
                    }
                    else if(direction == 4) {
                        savedState = 4;
                        restClient.turn(tankId, (byte) 4);
                    }
                    break;
                //Right
                case 2:
                    if(direction == 0) {
                        savedState = 0;
                        restClient.turn(tankId, (byte) 0);
                    }
                    else if(direction == 4) {
                        savedState = 4;
                        restClient.turn(tankId, (byte) 4);
                    }
                    break;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Tank to shoot a bullet.
     * @param tankId tank's id
     */
    @Background
    public void fire(long tankId) {
        this.tankId = tankId;
        try {
            if(counterFire < 2) {
                restClient.fire(tankId).isResult();
                counterFire++;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Leaves the game
     * @param tankId tank's id
     */
    @Background
    public void leave(long tankId) {
        this.tankId = tankId;
        try {
            restClient.leave(tankId).isResult();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}

