package com.example.myapplication;

import android.database.Cursor;
import android.os.Bundle;

import android.widget.Button;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

public class ViewFilesActivity extends AppCompatActivity {

    TextView textViewFiles;
    Button btnBack;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_files);

        textViewFiles = findViewById(R.id.textViewFiles);
        btnBack = findViewById(R.id.btnBack);
        databaseHelper = new DatabaseHelper(this);

        loadFiles();

        btnBack.setOnClickListener(view -> finish());
    }

    private void loadFiles() {
        Cursor cursor = databaseHelper.getAllFiles();
        if (cursor.getCount() == 0) {
            textViewFiles.setText(
                    "No files found.");
            return;
        }

        StringBuilder builder = new StringBuilder();
        while (cursor.moveToNext()) {
            builder.append("ID: ").append(cursor.getInt(0))
                    .append("\nName: ").append(cursor.getString(1))
                    .append("\nType: ").append(cursor.getString(2))
                    .append("\n\n");
        }
        textViewFiles.setText(builder.toString());
    }
}
