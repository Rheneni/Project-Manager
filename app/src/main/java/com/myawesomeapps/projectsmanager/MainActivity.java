package com.myawesomeapps.projectsmanager;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.myawesomeapps.projectsmanager.database.ProjectContract.ProjectEntry;
import com.myawesomeapps.projectsmanager.project.Project;
import com.myawesomeapps.projectsmanager.project.ProjectAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    private static final int PROJECT_LOADER = 0;

    private RecyclerView mRecyclerView;
    private List<Project> mListProjects = new ArrayList<>();
    private ProjectAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        FloatingActionButton editProject = (FloatingActionButton) findViewById(R.id.add_project);
        editProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProjectEditorActivity.class);
                startActivity(intent);
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.projects_recycler_view);

        getSupportLoaderManager().initLoader(PROJECT_LOADER, null, this);

        setupRecycler();
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        String[] projection = {
//                ProjectEntry._ID,
//                ProjectEntry.COLUMN_PROJECT_NAME
//        };
////        Cursor cursor = getContentResolver().query(ProjectEntry.CONTENT_URI, projection, null, null, null);
//
////        Log.v(LOG_TAG, "cursor count " + cursor.getCount());
//
////        mAdapter.notifyDataSetChanged();
//    }

    private void setupRecycler () {
        Log.d(LOG_TAG, "setupRecycler");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);

//        mListProjects.add(new Project("TestProject", 1));

        mAdapter = new ProjectAdapter(this, mListProjects);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.v(LOG_TAG, "onCreateLoader - id: " + id);

        switch (id) {
            case PROJECT_LOADER:
                String[] projection = {
                        ProjectEntry._ID,
                        ProjectEntry.COLUMN_PROJECT_NAME
                };

                return new CursorLoader(this, ProjectEntry.CONTENT_URI, projection, null, null, null);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        Log.v(LOG_TAG, "onLoaderFinished");
        mAdapter.changeCursor(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        Log.v(LOG_TAG, "onLoaderReset");
        mAdapter.changeCursor(null);

    }

}
