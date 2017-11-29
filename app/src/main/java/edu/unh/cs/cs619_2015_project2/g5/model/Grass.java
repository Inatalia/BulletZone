package edu.unh.cs.cs619_2015_project2.g5.model;

/**
 * Grass class represents a grass object in the grid.
 *
 * @author Rabbit
 */
public class Grass {
    private int position;

    /**
     * Constructor
     */
    public Grass() {
        position = 0;
    }

    /**
     * Constructor creates grass object as specified position.
     * @param pos position of the grass
     */
    public Grass(int pos) {
        position = pos;
    }

    /**
     * Returns the position of the grass.
     *
     * @return
     */
    public int getPosition() {
        return position;
    }

    /**
     * Sets the position of the grass.
     *
     * @param pos
     * @return
     */
    public void setPosition(int pos) {
        position = pos;
    }
}
