package edu.unh.cs.cs619_2015_project2.g5.model;

/**
 * Tank class represents tank object in the grid.
 *
 * @author Rabbit
 */
public class Tank {
    private long tankID;
    private int curTankDirection;
    private boolean alive = false;
    private int life;

    private float degree;

    private static final int FORWARD = 0;
    private static final int LEFT = 6;
    private static final int RIGHT = 2;
    private static final int BACKWARD = 4;

    /**
     * Constructor of tank
     * @param tankID tank's id
     * @param life tank's life
     * @param curTankDirection current direction of tank
     * @param degree current position of tank
     */
    public Tank(long tankID, int life, int curTankDirection, float degree) {
        //Log.d("TANK", tankID + " " + life + " " + curTankDirection);
        this.tankID = tankID;
        this.life = life;
        //this.degree = degree;
        setDegree(curTankDirection);
        alive = this.life >= 0;
        this.curTankDirection = curTankDirection;
    }

    /**
     * Returns the tank ID value.
     *
     * @return
     */
    public long getID() {
        return tankID;
    }

    /**
     * Returns the current direction the tank is facing.
     *
     * @return
     */
    public int getDirection() {
        return curTankDirection;
    }

    /**
     * Returns a boolean value representing whether the tank is still alive or not.
     *
     * @return
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Sets the id of the tank.
     *
     * @param id
     */
    public void setID(long id) {
        tankID = id;
    }

    /**
     * Sets the direction the tank is facing.
     *
     * @param direction
     */
    public void setDirection(int direction) {
        curTankDirection = direction;
    }

    /**
     * Sets the life of the tank, whether it is alive or not.
     *
     * @param isAlive
     */
    public void setIsAlive(boolean isAlive) {
        alive = isAlive;
    }

    /**
     * Return tank's position
     * @return degree
     */
    public float getDegree(){
        return degree;
    }

    /**
     * Set the degree for the rotation according to tank's direction
     * @param direction turn or move to this direction
     */
    public void setDegree(int direction){
        if (direction == RIGHT)
            degree = 90;
        else if (direction == BACKWARD)
            degree = 180;
        else if (direction == LEFT)
            degree = 270;
        else if (direction == FORWARD)
            degree = 0;
    }
}
