package com.myawesomeapps.projectsmanager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.myawesomeapps.projectsmanager.database.ProjectContract.ProjectEntry;

public class ProjectDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = ProjectDbHelper.class.getSimpleName();

    /** Name of the database file */
    private static final String DATABASE_NAME = "projects.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    public ProjectDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_PROJECTS_TABLE =  "CREATE TABLE " + ProjectEntry.TABLE_NAME + " ("
                + ProjectEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ProjectEntry.COLUMN_PROJECT_NAME + " TEXT NOT NULL);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_PROJECTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
