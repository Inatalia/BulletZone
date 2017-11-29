package edu.unh.cs.cs619_2015_project2.g5.model;

/**
 * Bullet class represents bullet object in the grid.
 *
 * @author Rabbit
 */
public class Bullet {
    private int damageOfBullet;

    /**
     * Constructor of the bullet
     */
    public Bullet(int damageOfBullet) {
        this.damageOfBullet = damageOfBullet;
    }

    /**
     * Returns damage of the bullet.
     * @return damageOfBullet
     */
    public int getDamageOfBullet(){ return damageOfBullet; }
}
