package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity5 extends AppCompatActivity {

    Button btnSelect21, btnUpload21, btnViewReport21, btnLogout45;
    TextView title21;
    private FirebaseAuth firebaseAuth1;
    private DatabaseHelper databaseHelper;
    private byte[] fileData;
    private String fileName, fileType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        databaseHelper = new DatabaseHelper(this);

        title21 = findViewById(R.id.ti);
        btnSelect21 = findViewById(R.id.SelectDocument);
        btnUpload21 = findViewById(R.id.Selectupload);
        btnViewReport21 = findViewById(R.id.ViewReports);
        btnLogout45 = findViewById(R.id.Logout454);

        btnSelect21.setOnClickListener(view -> selectFile());

        btnUpload21.setOnClickListener(view -> {
            if (fileData != null && fileName != null && fileType != null) {
                boolean inserted = databaseHelper.insertFile(fileName, fileType, fileData);
                if (inserted) {
                    Toast.makeText(this, "File Uploaded Successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Upload Failed!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please select a file first!", Toast.LENGTH_SHORT).show();
            }
        });

        btnViewReport21.setOnClickListener(view -> startActivity(new Intent(MainActivity5.this, ViewFilesActivity.class)));

        btnLogout45.setOnClickListener(view -> {
            firebaseAuth1.signOut();
            Toast.makeText(MainActivity5.this, "Logged out", Toast.LENGTH_SHORT).show();
        });
    }

    private void selectFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            try {
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                fileData = getBytes(inputStream);
                fileName = getFileName(data);
                fileType = getContentResolver().getType(data.getData());
                Toast.makeText(this, "File Selected: " + fileName, Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "File Selection Failed!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    private String getFileName(Intent data) {
        Cursor cursor = getContentResolver().query(data.getData(), null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            String name = cursor.getString(index);
            cursor.close();
            return name;
        }
        return "unknown";
    }
}
