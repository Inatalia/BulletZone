package edu.unh.cs.cs619_2015_project2.g5.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.unh.cs.cs619_2015_project2.g5.event.GridUpdateEvent;
import edu.unh.cs.cs619_2015_project2.g5.model.FieldEntity;

/**
 * Class represents our Data Abstract Object (DAO). It maintains the database connection.
 *
 * @author Rabbit
 */
public class ReplaySource {

    private static final String TAG = "ReplaySource";

    private SQLiteDatabase database;
    private ReplayDB dbHelper;
    private String[] allColumns = { ReplayDB.COLUMN_ID, ReplayDB.COLUMN_GRID };

    /**
     * Constructor
     *
     * @param context
     */
    public ReplaySource(Context context) {
        dbHelper = new ReplayDB(context);
    }

    /**
     * Creates connection with replay database.
     *
     * @throws SQLException
     */
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    /**
     * Disconnects the connection to the database.
     */
    public void close() {
        dbHelper.close();
    }

    /**
     * Deletes everything in the database.
     */
    public void clearDatabase() {
        database.delete(dbHelper.TABLE_GRID, null, null);
    }

    /**
     * Inserts 2D array on ints, representing the grid, into the database.
     *
     * @param event
     */
    public void insert(GridUpdateEvent event) {
        int[][] grid = event.getGridArray();

        try {
            // Serialize the input array
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ObjectOutputStream objectStream = new ObjectOutputStream(stream);
            objectStream.writeObject(grid);
            byte[] blob = stream.toByteArray();

            // Set up database for insert
            String sql = "INSERT INTO " + dbHelper.TABLE_GRID + " VAlUES(?,?);";
            SQLiteStatement statement = database.compileStatement(sql);

            // Insert into database
            database.beginTransaction();
            statement.clearBindings();
            statement.bindLong(1, event.getTimestamp());
            statement.bindBlob(2, blob);
            statement.execute();
            database.setTransactionSuccessful();
            database.endTransaction();

            Log.i(TAG, "Inserted into the database.");

        } catch(Exception e) {
            Log.e(TAG, "IO EXCEPTION WRITING TO DB");
        }
    }

    /**
     * Returns every snapshot taken of the grid that was stored in the database.
     *
     * @return
     */
    public ArrayList<int[][]> getAllGrids() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<int[][]> grids = new ArrayList<int[][]>();
        Cursor cursor = database.query(ReplayDB.TABLE_GRID, allColumns, null, null, null, null, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                try {
                    byte[] blob = cursor.getBlob(1);
                    ByteArrayInputStream bStream = new ByteArrayInputStream(blob);
                    ObjectInputStream oStream = new ObjectInputStream(bStream);
                    int[][] dSerialize = (int[][]) oStream.readObject();
                    grids.add(dSerialize);
                }
                catch (Exception e){
                    Log.e(TAG, "IO EXCEPTION READING DB");
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return grids;
    }
}
