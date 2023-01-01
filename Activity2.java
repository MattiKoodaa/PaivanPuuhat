package com.example.sptest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Activity2 extends AppCompatActivity implements View.OnClickListener {

    private int notificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);


        EditText editText = findViewById(R.id.eTNotify);
        findViewById(R.id.setBtn).setOnClickListener(this);
        findViewById(R.id.cancelBtn).setOnClickListener(this);

        String s = getIntent().getStringExtra("keynotify");
        editText.setText(s);
    }

    @Override
    public void onClick(View view) {
        EditText editText = findViewById(R.id.eTNotify);
        TimePicker timePicker = findViewById(R.id.timePicker);
        DatePicker datePicker = findViewById(R.id.datePicker);

        notificationId =(int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

        System.out.println(notificationId+" alarm");


        Intent intent = new Intent(Activity2.this, AlarmReceiver.class);
        intent.putExtra("notificationId", notificationId);
        intent.putExtra("message", editText.getText().toString());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                Activity2.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT
        );

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        switch (view.getId()) {
            case R.id.setBtn:
                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();

                Calendar startTime = Calendar.getInstance();
                startTime.set(Calendar.YEAR, year);
                startTime.set(Calendar.MONTH, month);
                startTime.set(Calendar.DAY_OF_MONTH, day);
                startTime.set(Calendar.HOUR_OF_DAY, hour);
                startTime.set(Calendar.MINUTE, minute);
                startTime.set(Calendar.SECOND, 0);
                long alarmStartTime = startTime.getTimeInMillis();

                alarmManager.set(AlarmManager.RTC_WAKEUP, alarmStartTime, pendingIntent);

                openMainActivity();
                Toast.makeText(this, "Valmis!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cancelBtn:
                openMainActivity();
                break;
        }
    }
    private void openMainActivity () {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}