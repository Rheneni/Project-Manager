package com.myawesomeapps.projectsmanager;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.myawesomeapps.projectsmanager.project.Project;
import com.myawesomeapps.projectsmanager.project.ProjectAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

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
        TextView titleView = findViewById(R.id.projects_menu_title);
        titleView.setText("My Projects");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_project);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProjectorEditorActivity.class);
                startActivity(intent);
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.projects_recycler_view);
        setupRecycler();
    }

    private void setupRecycler () {
        Log.d(LOG_TAG, "setupRecycler");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        for(int i = 0; i < 10; i++) {
            mListProjects.add(new Project("TestProject", i+1));
        }

        mAdapter = new ProjectAdapter(this, mListProjects);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();
    }
}
