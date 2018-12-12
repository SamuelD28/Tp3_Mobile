package com.samdube.tp3_mobile.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.samdube.tp3_mobile.Database.LocationDbSchema.*;

/**
 * Class used to simplify the interaction with the sqlite database
 */
public class LocationBaseHelper extends SQLiteOpenHelper {

    //Logic Constants
    private static final String DATABASE_NAME = "crimeBase.db"; //Name of the sqlite database
    private static final int VERSION = 2;                       //Version of the database

    /**
     * Constructor for a new Location database helper
     *
     * @param context Context of the activity
     */
    public LocationBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    /**
     * Function that creates a new sqlite database
     *
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //Creates a new tables with all the columsn defined in the LocationDbSchema
        String Create_Table = "CREATE TABLE " + LocationTable.NAME +
                "("
                + LocationTable.Cols.UUID + " TEXT PRIMARY KEY,"
                + LocationTable.Cols.NAME + " TEXT NOT NULL ,"
                + LocationTable.Cols.DESCRIPTION + " TEXT NOT NULL ,"
                + LocationTable.Cols.CATEGORY + " TEXT NOT NULL ,"
                + LocationTable.Cols.LATITUDE + " DOUBLE NOT NULL ,"
                + LocationTable.Cols.LONGITUDE + " DOUBLE NOT NULL " +
                ");";

        //Execute the sql query string
        sqLiteDatabase.execSQL(Create_Table);
    }

    /**
     * Function that upgrades the sqlite database when the database version is changed
     *
     * @param db         Current database
     * @param oldVersion Old database version
     * @param newVersion New Database version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            //Drops all the table if the version changed
            db.execSQL("DROP TABLE IF EXISTS " + LocationTable.NAME);
            onCreate(db);
        }
    }
}
