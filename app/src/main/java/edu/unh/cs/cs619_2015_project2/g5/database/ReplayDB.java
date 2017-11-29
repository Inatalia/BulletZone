package edu.unh.cs.cs619_2015_project2.g5.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Database stores snapshots of the grid which will be queried during the replay.
 *
 * @author Rabbit
 */
public class ReplayDB extends SQLiteOpenHelper {
    public static final String TABLE_GRID = "grid";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_GRID = "grid";

    private static final String DATABASE_NAME = "replay.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_GRID + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_GRID
            + " text not null);";

    /**
     * Constructor
     *
     * @param context
     */
    public ReplayDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Creation of database.
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    /**
     * Updating values in database.
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GRID);
        onCreate(db);
    }
}
