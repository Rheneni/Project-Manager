package com.myawesomeapps.projectsmanager;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.myawesomeapps.projectsmanager.database.ProjectContract.ProjectEntry;

public class ProjectEditorActivity  extends AppCompatActivity {

    public static final String LOG_TAG = ProjectEditorActivity.class.getSimpleName();

    private TextView mNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_editor);

        mNameEditText = (EditText) findViewById(R.id.project_name_input);

        TextView confirmProjectView = (TextView) findViewById(R.id.confirm_project);
        confirmProjectView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProject();
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        return;
    }

    private void saveProject() {
        String nameString = mNameEditText.getText().toString().trim();

        ContentValues contentValues = new ContentValues();

        contentValues.put(ProjectEntry.COLUMN_PROJECT_NAME, nameString);

        String toastText = "";

        boolean wasInserted = false;
        long newRowId = 0;

        try {
            Uri uri = getContentResolver().insert(ProjectEntry.CONTENT_URI, contentValues);
            newRowId = ContentUris.parseId(uri);
            if (newRowId >= 0) {
                wasInserted = true;
            }
            if(wasInserted) {
                toastText = getString(R.string.project_created);
            } else {
                toastText = getString(R.string.project_not_created);
            }

            Toast toast = Toast.makeText(this, toastText, Toast.LENGTH_LONG);
            toast.show();
        } catch (IllegalArgumentException e ) {
            Log.e(LOG_TAG, "saveProject - " + e.getMessage());

            Toast toast = Toast.makeText(this, getString(R.string.unexpected_behaviour), Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void finish() {
        Intent intent = new Intent(ProjectEditorActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
