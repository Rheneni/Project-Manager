package com.myawesomeapps.projectsmanager.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.myawesomeapps.projectsmanager.database.ProjectContract.ProjectEntry;

public class ProjectProvider extends ContentProvider {


    private ProjectDbHelper mDbHelper;
    /** Tag for the log messages */
    public static final String LOG_TAG = ProjectProvider.class.getSimpleName();

    private static final int PROJECTS = 100;
    private static final int PROJECT_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(ProjectContract.CONTENT_AUTHORITY, ProjectContract.PATH_PROJECTS, PROJECTS);
        sUriMatcher.addURI(ProjectContract.CONTENT_AUTHORITY, ProjectContract.PATH_PROJECTS + "/#", PROJECT_ID);
    }

    /**
     * Initialize the provider and the database helper object.
     */
    @Override
    public boolean onCreate() {
        mDbHelper = new ProjectDbHelper(getContext());

        return true;
    }

    /**
     * Perform the query for the given URI. Use the given projection, selection, selection arguments, and sort order.
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor = null;
        int match = sUriMatcher.match(uri);
        switch (match) {
            case PROJECTS:
                cursor = db.query(ProjectEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case PROJECT_ID:
                selection = ProjectEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(ProjectEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    /**
     * Insert new data into the provider with the given ContentValues.
     */
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PROJECTS:
                return insertProject(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertProject(Uri uri, ContentValues contentValues) {
        try {
            insertProjectSanityCheck(contentValues);

            SQLiteDatabase db = mDbHelper.getWritableDatabase();

            long id = db.insert(ProjectEntry.TABLE_NAME, null, contentValues);
            if (id == -1) {
                Log.e(LOG_TAG, "Failed to insert row for " + uri);
                return null;
            }
            getContext().getContentResolver().notifyChange(uri, null);
            return ContentUris.withAppendedId(uri, id);
        } catch (IllegalArgumentException e) {
            Log.e(LOG_TAG + "::insertProject", "IllegalArgumentException " + e.getMessage());
            throw e;
        }
    }

    /**
     * Updates the data at the given selection and selection arguments, with the new ContentValues.
     */
    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PROJECTS:
                return updateProject(uri, contentValues, selection, selectionArgs);
            case PROJECT_ID:
                selection = ProjectEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return updateProject(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supporder for " + uri);
        }
    }

    private int updateProject(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        Log.e(LOG_TAG, "updateProject");
        try {
            updateProjectSanityCheck(contentValues);

            if (contentValues.size() == 0) {
                return 0;
            }
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            int rowsUpdated = db.update(ProjectEntry.TABLE_NAME, contentValues, selection, selectionArgs);
            if(rowsUpdated > 0) {
                getContext().getContentResolver().notifyChange(uri, null);
            }
            return rowsUpdated;
        } catch (IllegalArgumentException e) {
            Log.e(LOG_TAG, "updateProject - IllegalArgumentException " + e.getMessage());
            throw e;
        }
    }
    /**
     * Delete the data at the given selection and selection arguments.
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.e(LOG_TAG, "delete");

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted = 0;
        switch (match) {
            case PROJECTS:
                rowsDeleted = db.delete(ProjectEntry.TABLE_NAME,selection, selectionArgs);
                break;
            case PROJECT_ID:
                selection = ProjectEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};

                rowsDeleted = db.delete(ProjectEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
        if(rowsDeleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    /**
     * Returns the MIME type of data for the content URI.
     */
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PROJECTS:
                return ProjectEntry.CONTENT_LIST_TYPE;
            case PROJECT_ID:
                return ProjectEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    private void insertProjectSanityCheck(ContentValues contentValues) {
        String name = contentValues.getAsString(ProjectEntry.COLUMN_PROJECT_NAME);
        if(name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Project requires a name");
        }
    }

    private void updateProjectSanityCheck(ContentValues contentValues) {
        if(contentValues.containsKey(ProjectEntry.COLUMN_PROJECT_NAME)) {
            String name = contentValues.getAsString(ProjectEntry.COLUMN_PROJECT_NAME);
            if(name == null || name.isEmpty()) {
                throw new IllegalArgumentException("Project requires a name");
            }
        }
    }
}
