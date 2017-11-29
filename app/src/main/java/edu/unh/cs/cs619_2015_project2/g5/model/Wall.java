package edu.unh.cs.cs619_2015_project2.g5.model;

/**
 * Wall class represents wall object in the grid.
 *
 * @author Rabbit
 */
public class Wall {
    private boolean isThisDestructibleWall;
    private int life;

    /**
     * Constructor for indestructible wall
     */
    public Wall() {
        isThisDestructibleWall = false;
    }

    /**
     * Constructor for Destructible wall
     */
    public Wall(int life) {
        this.life = life;
        isThisDestructibleWall = true;
    }

    /**
     * Returns whether the wall has been destroyed or not.
     *
     * @return
     */
    public boolean isDestroyed() {
        if(!isThisDestructibleWall) return false;
        else return life == 0;
    }

    /**
     * Returns wall's life
     * @return life
     */
    public int getLife() {return life;}
}
