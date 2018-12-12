package com.samdube.tp3_mobile.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.samdube.tp3_mobile.Database.LocationBaseHelper;
import com.samdube.tp3_mobile.Database.LocationCursorWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.samdube.tp3_mobile.Database.LocationDbSchema.*;
import static com.samdube.tp3_mobile.Model.Location.*;

/**
 * Singleton Class that holds the logic for interacting with the sqlite database
 */
public class LocationLog {

    //Logic Variable
    private static LocationLog INSTANCE = null; //Instance of the class used for the singleton pattern
    private SQLiteDatabase mDatabase;           //Database for making query

    /**
     * Function that return the current instance if there is one or creates it
     *
     * @param context Context of the application
     * @return location log instance
     */
    public static LocationLog GetInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = new LocationLog(context);

        return INSTANCE;
    }

    /**
     * Private Constructor that create a new location log
     *
     * @param context
     */
    private LocationLog(Context context) {
        //Retrieve a database from the database helper class
        mDatabase = new LocationBaseHelper(context.getApplicationContext()).getWritableDatabase();
    }

    /**
     * Function that return a location from the database based on the database
     *
     * @param id Id of the location wanted
     * @return foudn location
     */
    public Location getLocation(UUID id) {
        //Create a cursor wrapper and try to the first element to
        // see if the query found something
        LocationCursorWrapper cursor = new LocationCursorWrapper(
                mDatabase.query(LocationTable.NAME,
                        null,
                        LocationTable.Cols.UUID + "=?",
                        new String[]{id.toString()},
                        null,
                        null,
                        null)
        );
        try {
            if (cursor.getCount() == 0)
                return null;

            cursor.moveToFirst();
            return cursor.getLocation();

        } finally {
            cursor.close();
        }
    }

    /**
     * Function that retrieve a list of all the locations inside the database
     *
     * @return
     */
    public List<Location> getLocations() {

        List<Location> listLocation = new ArrayList<>();

        //Create a cursor and reads it. Adding every location to the list
        LocationCursorWrapper cursor = new LocationCursorWrapper(
                mDatabase.query(LocationTable.NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null)
        );

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                listLocation.add(cursor.getLocation());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return listLocation;
    }

    /**
     * Function that update a location inside the database
     *
     * @param location location to update
     */
    public void UpdateLocation(Location location) {
        ContentValues cv = getContentValues(location);
        mDatabase.update(LocationTable.NAME,
                cv,
                LocationTable.Cols.UUID + "=?",
                new String[]{location.getId().toString()}
        );
    }

    /**
     * Function that delete a location inside the databases
     *
     * @param id id of the location to delete
     */
    public void DeleteTask(UUID id) {
        mDatabase.delete(LocationTable.NAME,
                LocationTable.Cols.UUID + "=?",
                new String[]{id.toString()});
    }

    /**
     * Function that adds a new location inside the database
     *
     * @param location new location to add inside the database
     */
    public void AddLocation(Location location) {
        ContentValues cv = getContentValues(location);
        mDatabase.insert(LocationTable.NAME, null, cv);
    }

    /**
     * Function that create a content values based on a location argument
     *
     * @param location location to create content values from
     * @return new content values
     */
    private ContentValues getContentValues(Location location) {

        ContentValues cv = new ContentValues();
        cv.put(LocationTable.Cols.UUID, location.getId().toString());
        cv.put(LocationTable.Cols.NAME, location.getName());
        cv.put(LocationTable.Cols.DESCRIPTION, location.getDescription());
        cv.put(LocationTable.Cols.CATEGORY, location.getCategory().toString());
        cv.put(LocationTable.Cols.LATITUDE, location.getLat());
        cv.put(LocationTable.Cols.LONGITUDE, location.getLng());
        return cv;
    }

    /**
     * Helper function for feeding sample data to the database. Place the function call
     * after the first line in the constructor, run the app once, comeback and remove
     * the function call. If you don't remove it, data keeps being added
     */
    private void DataSeed() {

        String tempDescription = "Le Lorem Ipsum est simplement du faux texte employé dans la composition et la mise en page avant impression.";

        Location data1 = new Location(0, 0, "Vietnam", tempDescription, Category.Restaurant);
        Location data2 = new Location(1, 1, "Chine", tempDescription, Category.Entertainment);
        Location data3 = new Location(2, 2, "Canada", tempDescription, Category.Hotel);
        Location data4 = new Location(3, 3, "Japon", tempDescription, Category.Touristic);
        Location data5 = new Location(4, 4, "USA", tempDescription, Category.Restaurant);
        Location data6 = new Location(5, 5, "Irlande", tempDescription, Category.Restaurant);

        AddLocation(data1);
        AddLocation(data2);
        AddLocation(data3);
        AddLocation(data4);
        AddLocation(data5);
        AddLocation(data6);
    }
}
