package com.myawesomeapps.projectsmanager.database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class ProjectContract {

    public static final String CONTENT_AUTHORITY = "com.myawesomeapps.projectsmanager";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_PROJECTS = "projects";

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private ProjectContract() {}

    public static final class ProjectEntry implements BaseColumns {

        public  static  final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PROJECTS);
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE +
                "/" + CONTENT_AUTHORITY + "/" + PATH_PROJECTS;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE +
                "/" + CONTENT_AUTHORITY + "/" + PATH_PROJECTS;

        /** Name of database table for projects */
        public final static String TABLE_NAME = "projects";

        /**
         * Unique ID number for the project (only for use in the database table).
         *
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Name of the project.
         *
         * Type: TEXT
         */
        public final static String COLUMN_PROJECT_NAME ="name";
    }
}