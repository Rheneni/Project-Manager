package com.myawesomeapps.projectsmanager.project;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.myawesomeapps.projectsmanager.MainActivity;
import com.myawesomeapps.projectsmanager.ProjectEditorActivity;
import com.myawesomeapps.projectsmanager.R;
import com.myawesomeapps.projectsmanager.database.ProjectContract.ProjectEntry;

public class ProjectCursorAdapter extends CursorAdapter {

    public ProjectCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.project_item, parent, false);
    }

    /**
     * This method binds the pet data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current pet can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, Context context, final Cursor cursor) {
        TextView nameView = (TextView) view.findViewById(R.id.project_name);
        TextView idView = (TextView)view.findViewById(R.id.edit_project);
        Log.v(  "bindView", "Position" + cursor.getPosition());
        Log.v("bindView", "ColumnIndex" + cursor.getColumnIndexOrThrow(ProjectEntry.COLUMN_PROJECT_NAME));
        String nameText = cursor.getString(cursor.getColumnIndexOrThrow(ProjectEntry.COLUMN_PROJECT_NAME));
        nameView.setText(nameText);
        String idText = cursor.getString(cursor.getColumnIndexOrThrow(ProjectEntry._ID));
        idView.setText(idText);
        idView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ProjectEditorActivity.class);
                long id = cursor.getPosition();
                Uri uri = ContentUris.withAppendedId(ProjectEntry.CONTENT_URI, id);
                intent.setData(uri);
                v.getContext().startActivity(intent);
            }
        });
//        String name = mListProjects.get(position).getName();
//        long id = mListProjects.get(position).getId();
//        holder.projectNameView.setText(name);
//        holder.editProjectView.setText(Long.toString(id));
//        Log.d(LOG_TAG, "onBindViewHolder " + holder.projectNameView.getText());
//        Log.d(LOG_TAG, "onBindViewHolder " + holder.editProjectView.getText());
    }
}