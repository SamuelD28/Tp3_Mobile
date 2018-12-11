package com.samdube.tp3_mobile.Database;

public class LocationDbSchema {
    public static final class LocationTable{
        public static final String NAME = "locations";

        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String NAME = "name";
            public static final String DESCRIPTION = "description";
            public static final String CATEGORY = "category";
            public static final String LATITUDE = "latitude";
            public static final String LONGITUDE = "longitude";
        }
    }
}
