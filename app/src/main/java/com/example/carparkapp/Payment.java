package com.example.carparkapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Payment extends AppCompatActivity {


    String[] MONTH= {"January","February"};
    String[] YEAR={"2021","2021","2022","2023","2024"};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        ArrayAdapter<String> arrayAdapterMonth= new ArrayAdapter<String> (Payment.this, android.R.layout.simple_dropdown_item_1line,MONTH);
        Spinner BetterSpinnerMonth=findViewById(R.id.spinnerMonth);
        BetterSpinnerMonth.setAdapter(arrayAdapterMonth);

        ArrayAdapter<String> arrayAdapterYear= new ArrayAdapter<String> (Payment.this, android.R.layout.simple_dropdown_item_1line,YEAR);
        Spinner BetterSpinnerYear=findViewById(R.id.spinnerMonth);
        BetterSpinnerYear.setAdapter(arrayAdapterYear);


    }
}