package com.samdube.tp3_mobile.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class LocationLog {

    private ArrayList<Location> mLocations;
    private static LocationLog INSTANCE = null;

    public static LocationLog GetInstance()
    {
        if(INSTANCE == null)
            return new LocationLog();

        return INSTANCE;
    }

    private LocationLog()
    {
        mLocations = new ArrayList<Location>();
        DataSeed();
    }


    public ArrayList<Location> getmLocations() {
        return mLocations;
    }

    public boolean UpdateLocation(UUID oldTaskID, Location newTask)
    {
        //Lamda expression to retrieve the old task from the list
        Location oldTask = mLocations.stream().filter(t -> t.getId() == oldTaskID).findFirst().get();

        if(oldTask == null)
            return false;

//        oldTask.UpdateTask(newTask);
        return true;
    }

    public boolean DeleteTask(UUID id){
        Location taskToDelete = mLocations.stream().filter(t -> t.getId() == id).findFirst().get();

        if(taskToDelete == null)
            return false;

        mLocations.remove(mLocations.indexOf(taskToDelete));
        return true;
    }

    public boolean AddTask(Location location){
        return mLocations.add(location);
    }

    private void DataSeed(){
        Location data1 = new Location(0,0,"Vietnam");
        Location data2 = new Location(1,1,"Chine");
        Location data3 = new Location(2,2,"Canada");
        Location data4 = new Location(3,3,"Japon");
        Location data5 = new Location(4,4,"USA");
        Location data6 = new Location(5,5,"Irlande");

        mLocations.addAll(Arrays.asList(data1, data2,data3,data4, data5, data6));
    }
}
