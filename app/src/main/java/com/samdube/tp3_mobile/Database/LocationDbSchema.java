package com.samdube.tp3_mobile.Database;

/**
 * Class used for regrouping all the information about a database schema
 */
public class LocationDbSchema {

    /**
     * Inner class that defines the location table inside the database
     */
    public static final class LocationTable {
        public static final String NAME = "locations"; //Name of the table

        /**
         * Name definitions of all the columns present in the location table
         */
        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String NAME = "name";
            public static final String DESCRIPTION = "description";
            public static final String CATEGORY = "category";
            public static final String LATITUDE = "latitude";
            public static final String LONGITUDE = "longitude";
        }
    }
}
