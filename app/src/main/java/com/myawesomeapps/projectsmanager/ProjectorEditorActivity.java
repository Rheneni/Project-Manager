package com.myawesomeapps.projectsmanager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ProjectorEditorActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_editor);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        return;
    }
}
