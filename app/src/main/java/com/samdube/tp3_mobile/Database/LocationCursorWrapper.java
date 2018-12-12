package com.samdube.tp3_mobile.Database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.samdube.tp3_mobile.Model.Location;

import java.util.UUID;

import static com.samdube.tp3_mobile.Database.LocationDbSchema.*;
import static com.samdube.tp3_mobile.Model.Location.*;

/**
 * Cursor wrapper used for extracting new location from the database
 */
public class LocationCursorWrapper extends CursorWrapper {

    public LocationCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    /**
     * Function that individually extracts all the column from a table row and parse them in the proper type
     *
     * @return a new location based on the table colums
     */
    public Location getLocation() {
        String uuidString = getString(getColumnIndex(LocationTable.Cols.UUID));
        String locationName = getString(getColumnIndex(LocationTable.Cols.NAME));
        String locationDescription = getString(getColumnIndex(LocationTable.Cols.DESCRIPTION));
        Category locationCategory = Category.valueOf(getString(getColumnIndex(LocationTable.Cols.CATEGORY)));
        double locationLatitude = getDouble(getColumnIndex(LocationTable.Cols.LATITUDE));
        double locationLongitude = getDouble(getColumnIndex(LocationTable.Cols.LONGITUDE));
        return new Location(locationLatitude, locationLongitude, locationName, locationDescription, locationCategory, UUID.fromString(uuidString));
    }
}
