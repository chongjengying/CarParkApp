package com.example.carparkapp;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class DateTime extends AppCompatActivity {
    CalendarView calendarView;
    TextView dateTextView,timeView;
    Button btnSearch;
    int t1Hour;
    int t1Minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_time);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Select Time & Duration");

        //Assign Variable
        calendarView=findViewById(R.id.calendarView);
        dateTextView=findViewById(R.id.textViewDate);
        timeView=findViewById(R.id.textViewTime);
        btnSearch=findViewById(R.id.btnSearch);

        timeView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              //Initialize time picker dialog
              TimePickerDialog timePickerDialog= new TimePickerDialog(DateTime.this, new TimePickerDialog.OnTimeSetListener() {
                  @Override
                  public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                      //Initialize hour and minutes
                       t1Hour=hourOfDay;
                       t1Minute=minute;
                      //Initialize Calendar
                      Calendar calendar=Calendar.getInstance();
                      //Set hour and minutes
                      calendar.set(0,0,0,t1Hour,t1Minute);
                      //Set selected time on textView
                      timeView.setText(DateFormat.format("hh:mm aa",calendar));
                  }
              },12,0,false );
              //Displayed pervious selected time
              timePickerDialog.updateTime(t1Hour,t1Minute);
              //show Dialog
              timePickerDialog.show();
          }
      });
                calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                        String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                        dateTextView.setText(date);
                    }
                });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mySearch = new Intent(DateTime.this,CarParkSlot.class);
                startActivity(mySearch );
            }
        });





    }
}