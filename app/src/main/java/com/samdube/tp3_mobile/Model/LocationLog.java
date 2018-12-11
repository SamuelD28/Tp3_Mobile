package com.samdube.tp3_mobile.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.samdube.tp3_mobile.Database.LocationBaseHelper;
import com.samdube.tp3_mobile.Database.LocationCursorWrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.samdube.tp3_mobile.Database.LocationDbSchema.*;
import static com.samdube.tp3_mobile.Model.Location.*;

public class LocationLog {

    private static LocationLog INSTANCE = null;
    private SQLiteDatabase mDatabase;

    public static LocationLog GetInstance(Context context)
    {
        if(INSTANCE == null)
            INSTANCE =  new LocationLog(context);

        return INSTANCE;
    }

    private LocationLog(Context context)
    {
        mDatabase = new LocationBaseHelper(context.getApplicationContext()).getWritableDatabase();
    }

    public Location getLocation(UUID id){
        LocationCursorWrapper cursor = new LocationCursorWrapper(
                mDatabase.query(LocationTable.NAME,
                        null,
                        LocationTable.Cols.UUID + "=?",
                        new String[]{id.toString()},
                        null,
                        null,
                        null)
        );
        try{
            if(cursor.getCount() == 0)
                return null;

            cursor.moveToFirst();
            return cursor.getLocation();

        }finally {
            cursor.close();
        }
    }

    public List<Location> getLocations() {

        List<Location> listLocatiosn = new ArrayList<>();

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
                listLocatiosn.add(cursor.getLocation());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return listLocatiosn;
    }

    public boolean UpdateLocation(Location location)
    {
        //TODO Modifier un crime dans la base de données
        ContentValues cv = getContentValues(location);
        mDatabase.update(LocationTable.NAME,
                cv,
                LocationTable.Cols.UUID + "=?",
                new String[]{location.getId().toString()}
        );
    }

    public boolean DeleteTask(UUID id){
//        Location taskToDelete = mLocations.stream().filter(t -> t.getId() == id).findFirst().get();
//
//        if(taskToDelete == null)
//            return false;
//
//        mLocations.remove(mLocations.indexOf(taskToDelete));
        return true;
    }

    public void AddLocation(Location location){
        ContentValues cv = getContentValues(location);
        mDatabase.insert(LocationTable.NAME,null,cv);
    }

    private ContentValues getContentValues(Location location){

        ContentValues cv = new ContentValues();
        cv.put(LocationTable.Cols.UUID, location.getId().toString());
        cv.put(LocationTable.Cols.NAME, location.getName());
        cv.put(LocationTable.Cols.DESCRIPTION, location.getDescription());
        cv.put(LocationTable.Cols.CATEGORY, location.getCategory().toString());
        cv.put(LocationTable.Cols.LATITUDE, location.getLat());
        cv.put(LocationTable.Cols.LONGITUDE, location.getLng());
        return cv;
    }

    private void DataSeed(){

        String tempDescription = "Le Lorem Ipsum est simplement du faux texte employé dans la composition et la mise en page avant impression.";

        Location data1 = new Location(0,0,"Vietnam", tempDescription, Category.Restaurant);
        Location data2 = new Location(1,1,"Chine", tempDescription, Category.Entertainment);
        Location data3 = new Location(2,2,"Canada",tempDescription, Category.Hotel);
        Location data4 = new Location(3,3,"Japon",tempDescription, Category.Touristic);
        Location data5 = new Location(4,4,"USA",tempDescription, Category.Restaurant);
        Location data6 = new Location(5,5,"Irlande",tempDescription, Category.Restaurant);

//        mLocations.addAll(Arrays.asList(data1, data2,data3,data4, data5, data6));
    }
}
