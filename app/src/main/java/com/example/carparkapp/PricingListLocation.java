package com.example.carparkapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PricingListLocation extends AppCompatActivity {
        //Intialize Variable
        TextView firstHour,thirdHour,maxHour;
        Button btnSelectDuration;
private String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pricing_list_location);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Pricing List");


       firstHour=findViewById(R.id.firstHourTxt);
       thirdHour=findViewById(R.id.thirdHoursTxt);
       maxHour=findViewById(R.id.maxDayTxt );
       btnSelectDuration=findViewById(R.id.btnSelectDuration);
       Intent i=getIntent();
       String price=i.getStringExtra("Location");
        Intent it=getIntent();
        String price1=it.getStringExtra("Location1");
        Intent its=getIntent();
        String max=its.getStringExtra("Location2");
        firstHour.setText("RM "+ price);
        thirdHour.setText("RM "+ price1);
        maxHour.setText("RM "+ max);

        btnSelectDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent select = new Intent(PricingListLocation.this, DateTime.class);
                startActivity(select);
            }
        });


//        //Assign Variable
//        abc=findViewById(R.id.test);
//
//        //Create bundle
//        Bundle bundle=getIntent().getExtras();
//        String locationName=bundle.getString("name");
//
//        //Set lang name to textView
//        abc.setText(locationName);
    }
}