package com.myawesomeapps.projectsmanager.project;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;

import android.widget.TextView;

import com.myawesomeapps.projectsmanager.R;

public class ProjectHolder  extends ViewHolder{

    protected TextView projectNameView;
    protected TextView editProjectView;
    public ProjectHolder(View itemView) {
        super(itemView);
        projectNameView = (TextView) itemView.findViewById(R.id.project_name);
        editProjectView = (TextView) itemView.findViewById(R.id.edit_project);
    }
}
