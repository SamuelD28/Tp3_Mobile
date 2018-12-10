package com.samdube.tp3_mobile.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import static com.samdube.tp3_mobile.Model.Location.*;

public class LocationLog {

    private ArrayList<Location> mLocations;
    private static LocationLog INSTANCE = null;

    public static LocationLog GetInstance()
    {
        if(INSTANCE == null)
            INSTANCE =  new LocationLog();

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

    public boolean AddLocation(Location location){
        return mLocations.add(location);
    }

    private void DataSeed(){

        String tempDescription = "Le Lorem Ipsum est simplement du faux texte employé dans la composition et la mise en page avant impression.";

        Location data1 = new Location(0,0,"Vietnam", tempDescription, Category.Restaurant);
        Location data2 = new Location(1,1,"Chine", tempDescription, Category.Entertainment);
        Location data3 = new Location(2,2,"Canada",tempDescription, Category.Hotel);
        Location data4 = new Location(3,3,"Japon",tempDescription, Category.Touristic);
        Location data5 = new Location(4,4,"USA",tempDescription, Category.Restaurant);
        Location data6 = new Location(5,5,"Irlande",tempDescription, Category.Restaurant);

        mLocations.addAll(Arrays.asList(data1, data2,data3,data4, data5, data6));
    }
}
