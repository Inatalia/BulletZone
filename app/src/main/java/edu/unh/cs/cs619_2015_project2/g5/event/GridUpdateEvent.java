package edu.unh.cs.cs619_2015_project2.g5.event;

import edu.unh.cs.cs619_2015_project2.g5.model.FieldEntity;

/**
 * Created by rabbit on 11/4/15.
 * It is a class wrapper that wraps a message (a grid and a timestamp)
 * that needs to be passed from Publisher (grid poller) to subscriber (main activity's onUpdateGrid)
 */
public class GridUpdateEvent {
    int[][] gridArray;
    FieldEntity[][] grid;
    long timestamp;

    /**
     * Grid Updates Event
     * @param grid field representation
     * @param timestamp timestamp
     */
    public GridUpdateEvent(FieldEntity[][] grid, int[][] gridArray, long timestamp) {
        this.grid = grid;
        this.gridArray = gridArray;
        this.timestamp = timestamp;
    }

    /**
     * Returns grid of field representation
     * @return grid
     */
    public FieldEntity[][] getGrid() {
        return grid;
    }

    /**
     * Returns the grid in a 2D array.
     *
     * @return
     */
    public int[][] getGridArray() {
        return gridArray;
    }

    /**
     * Returns timestamp
     * @return long timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }
}