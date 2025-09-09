package com.example.myapplication;
import static android.content.ContentValues.TAG;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity3 extends AppCompatActivity {
    private EditText emailEditText1;
    private EditText passwordEditText1;
    private EditText ED;
    private Button Confirm;
    private FirebaseAuth mAuth;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        emailEditText1 = (EditText) findViewById(R.id.email1);
        passwordEditText1 = (EditText) findViewById(R.id.password1);

        mAuth = FirebaseAuth.getInstance();


        Confirm = (Button) findViewById(R.id.confirmButton);
        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(MainActivity3.this, MainActivity2.class);
                startActivity(intent3);
                String email = emailEditText1.getText().toString().trim();
                String password = passwordEditText1.getText().toString().trim();

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity3.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Registration success
                                    // You can navigate to the main activity or perform other actions here
                                    Intent intent = new Intent(MainActivity3.this, MainActivity2.class);
                                    startActivity(intent);
                                    finish(); // Optional: Close the current activity
                                } else {
                                    // Registration failed
                                    // Display an error message or handle the error appropriately
                                }
                            }
                        });









            }
        });
    }
}












































