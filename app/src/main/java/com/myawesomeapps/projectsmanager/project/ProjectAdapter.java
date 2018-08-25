package com.myawesomeapps.projectsmanager.project;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectHolder> {

    private static final String LOG_TAG = ProjectAdapter.class.getSimpleName();

    private Context mContext;
    private List<Project> mListProjects;
    private ProjectCursorAdapter mCursorAdapter;

    public ProjectAdapter(@NonNull Context context, List<Project> projects) {
        mContext = context;
        if(projects != null) {
            this.mListProjects = new ArrayList<>(projects);
        } else {
            mListProjects = new ArrayList<>();
        }
        if(mCursorAdapter == null) {
            mCursorAdapter = new ProjectCursorAdapter(mContext, null);
        }

        Log.d(LOG_TAG, "ProjectAdapter " + mListProjects.size());
    }


    @NonNull
    @Override
    public ProjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(LOG_TAG, "onCreateViewHolder");
        View v = mCursorAdapter.newView(mContext, mCursorAdapter.getCursor(), parent);
        return new ProjectHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectHolder holder, int position) {
        Log.d(LOG_TAG, "onBindViewHolder");

        mCursorAdapter.getCursor().moveToPosition(position);
        mCursorAdapter.bindView(holder.itemView, mContext, mCursorAdapter.getCursor());
    }

    @Override
    public int getItemCount() {
        Log.d(LOG_TAG, "getItemCount: " + mCursorAdapter.getCount());

        return mCursorAdapter.getCount();
    }

    public void changeCursor(Cursor cursor) {
        Log.v(LOG_TAG, "changeCursor");
        mCursorAdapter.changeCursor(cursor);
    }
}
