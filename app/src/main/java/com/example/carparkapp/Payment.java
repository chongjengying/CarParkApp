package com.example.carparkapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Payment extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    String[] MONTH= {"January","February"};
    String[] YEAR={"2021","2021","2022","2023","2024"};
    Button btnCheck;
    EditText editTextNameOfCard,editTextCardNmber,editTextSecuirtyCode;
    Spinner spinnerMonth,spinnerYear;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        editTextNameOfCard=findViewById(R.id.editTextNameOfCard);
        editTextCardNmber=findViewById(R.id.editCardNumber);
        editTextSecuirtyCode=findViewById(R.id.editTextSecuirtyCode);

        btnCheck=findViewById(R.id.btnCheckOut);
        spinnerMonth=findViewById(R.id.spinnerMonth);
        spinnerYear = findViewById(R.id.spinnerYear);
        spinnerMonth.setOnItemSelectedListener(this);
        spinnerYear.setOnItemSelectedListener(this);

        //Month
        ArrayAdapter arrayAdapterMonth=new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,MONTH);
        arrayAdapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonth.setAdapter(arrayAdapterMonth);
        //Color
        ArrayAdapter arrayAdapterYear=new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,YEAR);
        arrayAdapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(arrayAdapterYear);


        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameOfCard=editTextNameOfCard.getText().toString();
                String cardNumber=editTextCardNmber.getText().toString();
                String secureCode=editTextSecuirtyCode.getText().toString();
                TextView errorText = (TextView)spinnerMonth.getSelectedView();
                TextView errorText1 = (TextView)spinnerYear.getSelectedView();



              if(nameOfCard.isEmpty())
                {
                   editTextNameOfCard.setError("Please provide Name");
                   editTextNameOfCard.requestFocus();
                }
            if(cardNumber.isEmpty())
                {
                    editTextCardNmber.setError("Please provide Card Number");
                    editTextCardNmber.requestFocus();
                }
            if(cardNumber.length() != 12)
                {
                    editTextCardNmber.setError("Please provide 12 digit card Number");
                    editTextCardNmber.requestFocus();
                }
               if(secureCode.length()>=4)
                {
                    editTextSecuirtyCode.setError("The secure code is 3 digit only");
                    editTextSecuirtyCode.requestFocus();
                }
            if(secureCode.isEmpty())
                {
                    editTextSecuirtyCode.setError("Please provide Secure Code");
                    editTextSecuirtyCode.requestFocus();
                }

            if (! (nameOfCard.isEmpty() || cardNumber.isEmpty() || secureCode.isEmpty() || secureCode.length()>=4 ||
                    cardNumber.length()!=12))
                {
                    AlertDialog.Builder builder=new AlertDialog.Builder(Payment.this);

                    builder.setCancelable(true);
                    builder.setTitle("Successful Payment ");
                    builder.setMessage("Have A Nice Day Thank You");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent mySearch = new Intent(Payment.this,Homepage.class);
                            startActivity(mySearch );
                        }
                    });
                    builder.show();
                }

            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        spinnerMonth.getSelectedItem().toString();
        spinnerYear.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}