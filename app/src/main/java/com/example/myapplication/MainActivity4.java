package com.example.myapplication;


import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;


import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.auth.FirebaseAuth;

import android.widget.EditText;
import android.widget.TextView;


import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Calendar;


import android.content.pm.PackageManager;
import android.provider.Settings;
import android.content.Intent;
import android.net.Uri;


public class MainActivity4 extends AppCompatActivity {
    private Button document11, btnSelectDate, btnSelectTime, btnSetReminder;
    private FirebaseAuth firebaseAuth1;
    TextView tvDate, tvTime;
    Calendar calendar;
    int selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute;
    EditText etTitle, etDescription;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);


        // Find views by their IDs

        document11 = findViewById(R.id.Docoument1);
        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        btnSelectDate = findViewById(R.id.btnSelectDate);
        btnSelectTime = findViewById(R.id.btnSelectTime);
        btnSetReminder = findViewById(R.id.btnSetReminder);
        tvDate = findViewById(R.id.tvDate);
        tvTime = findViewById(R.id.tvTime);

        calendar = Calendar.getInstance();

        document11.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {


                                                 // Redirect the user to the login screen (assuming MainActivity is your login activity)
                                                 Intent intent9 = new Intent(MainActivity4.this, MainActivity5.class);
                                                 startActivity(intent9);
                                                 finish();
                                             }
                                         });








        btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        btnSelectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });

        btnSetReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setReminder();
            }
        });
    }

    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    selectedYear = year;
                    selectedMonth = month;
                    selectedDay = dayOfMonth;
                    tvDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                },
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void showTimePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minute) -> {
                    selectedHour = hourOfDay;
                    selectedMinute = minute;
                    tvTime.setText(hourOfDay + ":" + (minute < 10 ? "0" + minute : minute));
                },
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);

        timePickerDialog.show();
    }

    @SuppressLint("ScheduleExactAlarm")
    private void setReminder() {
        String title = etTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        if (title.isEmpty()) {
            Toast.makeText(this, "Please enter a title", Toast.LENGTH_SHORT).show();
            return;
        }

        if (tvDate.getText().toString().equals("No date selected") ||
                tvTime.getText().toString().equals("No time selected")) {
            Toast.makeText(this, "Please select date and time", Toast.LENGTH_SHORT).show();
            return;
        }
        // Set the reminder time
        Calendar reminderTime = Calendar.getInstance();
        reminderTime.set(selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute, 0);
        long triggerTime = reminderTime.getTimeInMillis();













        String reminderDetails = "Reminder Set: " + title + "\n" +
                "Description: " + description + "\n" +
                "Date: " + tvDate.getText().toString() + "\n" +
                "Time: " + tvTime.getText().toString();

        Toast.makeText(this, reminderDetails, Toast.LENGTH_LONG).show();

        TextView tvReminderDetails = findViewById(R.id.tvReminderDetails);
        tvReminderDetails.setText(reminderDetails);
        tvReminderDetails.setVisibility(View.VISIBLE);

        // Create notification intent
        Intent intent = new Intent(this, ReminderBroadcastReceiver.class);
        intent.putExtra("title", title);
        intent.putExtra("description", description);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Set alarm
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
            }
        }










        }
    }





