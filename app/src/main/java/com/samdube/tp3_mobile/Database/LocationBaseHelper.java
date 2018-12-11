package com.samdube.tp3_mobile.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import static com.samdube.tp3_mobile.Database.LocationDbSchema.*;


public class LocationBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "crimeBase.db";
    private static final int VERSION = 1;

    public LocationBaseHelper(Context context) {
        super(context,DATABASE_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String Create_Table = "CREATE TABLE " + LocationTable.NAME +
                "("
                +    LocationTable.Cols.UUID + " TEXT PRIMARY KEY,"
                +    LocationTable.Cols.NAME + " TEXT NOT NULL ,"
                +    LocationTable.Cols.DESCRIPTION + " TEXT NOT NULL ,"
                +    LocationTable.Cols.CATEGORY + " TEXT NOT NULL ,"
                +    LocationTable.Cols.LATITUDE + " DOUBLE NOT NULL ,"
                +    LocationTable.Cols.LONGITUDE + " DOUBLE NOT NULL "+
                ");";

        sqLiteDatabase.execSQL(Create_Table);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        if(oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + LocationTable.NAME);
            onCreate(db);
        }
    }
}