package com.myawesomeapps.projectsmanager.project;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myawesomeapps.projectsmanager.R;

import java.util.ArrayList;
import java.util.List;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectHolder> {

    private static final String LOG_TAG = ProjectAdapter.class.getSimpleName();
    private Context mContext;
    private List<Project> mListProjects;

    public ProjectAdapter(@NonNull Context context, List<Project> projects) {
        mContext = context;
        if(projects != null) {
            this.mListProjects = new ArrayList<>(projects);
        } else {
            mListProjects = new ArrayList<>();
        }
        Log.d(LOG_TAG, "ProjectAdapter " + mListProjects.size());
    }


    @NonNull
    @Override
    public ProjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(LOG_TAG, "onCreateViewHolder");

        return new ProjectHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.project_item, parent, false));    }

    @Override
    public void onBindViewHolder(@NonNull ProjectHolder holder, int position) {
        Log.d(LOG_TAG, "onBindViewHolder");
        String name = mListProjects.get(position).getName();
        long id = mListProjects.get(position).getId();
        holder.projectNameView.setText(name);
        holder.editProjectView.setText(Long.toString(id));
        Log.d(LOG_TAG, "onBindViewHolder " + holder.projectNameView.getText());
        Log.d(LOG_TAG, "onBindViewHolder " + holder.editProjectView.getText());
    }

    @Override
    public int getItemCount() {
        Log.d(LOG_TAG, "getItemCount");

        return mListProjects != null ? mListProjects.size() : 0;
    }
}
