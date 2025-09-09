package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    private EditText user_name, pass_word;
    private TextView b1;
    FirebaseAuth mAuth1;

    private String emergencyPhoneNumber = null;
    private Button b, sosButton; // Changed button name



    private FusedLocationProviderClient fusedLocationClient;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        sosButton = findViewById(R.id.sosButton1);
 // Use the new button ID
        user_name = findViewById(R.id.email);
        pass_word = findViewById(R.id.password);
        b = findViewById(R.id.l1ogin_button);
        b1 = findViewById(R.id.register_button);
        mAuth1 = FirebaseAuth.getInstance();

        // SOS Call
        sosButton.setOnClickListener(v -> makeEmergencyCall());

        // Combined action: Select contact and share location


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // MOVE LOGIN PAGE TO REGISTER PAGE
                Intent intent000 = new Intent(MainActivity.this, MainActivity3.class);
                startActivity(intent000);
                finish();
            }
        });

        // Login
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email1 = user_name.getText().toString().trim();
                String password1 = pass_word.getText().toString().trim();
                mAuth1.signInWithEmailAndPassword(email1, password1)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    startActivity(new Intent(MainActivity.this, MainActivity2.class));
                                } else {
                                    Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    // Method for making SOS Call
    private void makeEmergencyCall() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:112")); // Change to your emergency number
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startActivity(callIntent);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        }
    }

}