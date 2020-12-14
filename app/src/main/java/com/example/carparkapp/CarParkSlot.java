package com.example.carparkapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CarParkSlot extends AppCompatActivity {
    RelativeLayout relativeLayout;
    boolean isPressed = false;

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    // array to store id of all seat buttons
//    int[][] buttonId = {{R.id.btn1},{ R.id.btn2},{ R.id.btn3}, {R.id.btn4},{R.id.btn5},{R.id.btn6},{R.id.btn7},{R.id.btn8}};
        String[][] buttonId=new String[8][3];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_park_slot);
        final Button buttonSave = findViewById(R.id.btnConfirmSeats);
        final Button btn1=findViewById(R.id.btn1);
        final Button btn2=findViewById(R.id.btn2);
        final Button btn3=findViewById(R.id.btn3);
        final Button btn4=findViewById(R.id.btn4);
        final Button btn5=findViewById(R.id.btn5);
        final Button btn6=findViewById(R.id.btn6);
        final Button btn7=findViewById(R.id.btn7);
        final Button btn8=findViewById(R.id.btn8);


        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();


        FirebaseUser user=firebaseAuth.getCurrentUser();
        final String userId=user.getUid();
        final ArrayList<String> bookedSeats = new ArrayList<>();
        databaseReference = firebaseDatabase.getReference().child("Slot");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i=0;

                for(DataSnapshot ds:snapshot.getChildren())
                {
                    ReserveSlot reserveSlot=ds.getValue(ReserveSlot.class);
                    buttonId[i][0]= "R.id.btn"+(i+1);
                    buttonId[i][1]=ds.child("slotId").getValue(String.class);

                    buttonId[i][2]=ds.child("status").getValue(String.class);
//                    Log.d("buttonId[i][1]",buttonId[i][1]);
//                    Log.d("buttonId[i][2]",buttonId[i][2]);
                    Log.d("buttonId",buttonId[i][2]);
                    if(buttonId[i][2].equals("booked")){
                        bookedSeats.add(buttonId[i][0]);
                        Log.d("buttonId",buttonId[i][0]);
                    }
                    i++;
                }
              for(int j = 0; j< bookedSeats.size(); j++)
              {
                  Log.d("bookedSeats",bookedSeats.get(j));
                  switch (bookedSeats.get(j))
                  {
                      case "R.id.btn1":
                          btn1.setBackground(getResources().getDrawable(R.drawable.booked));
                          btn1.setEnabled(false);
                          break;
                      case "R.id.btn2":
                          btn2.setBackground(getResources().getDrawable(R.drawable.booked));
                          btn2.setEnabled(false);
                          break;
                      case "R.id.btn3":
                          btn3.setBackground(getResources().getDrawable(R.drawable.booked));
                          btn3.setEnabled(false);
                          break;
                      case "R.id.btn4":
                          btn4.setBackground(getResources().getDrawable(R.drawable.booked));
                          btn4.setEnabled(false);
                          break;
                      case "R.id.btn5":
                          btn5.setBackground(getResources().getDrawable(R.drawable.booked));
                          btn5.setEnabled(false);
                          break;
                      case "R.id.btn6":
                          btn6.setBackground(getResources().getDrawable(R.drawable.booked));
                          btn6.setEnabled(false);
                          break;
                      case "R.id.btn7":
                          btn7.setBackground(getResources().getDrawable(R.drawable.booked));
                          btn7.setEnabled(false);
                          break;
                      case "R.id.btn8":
                          btn8.setBackground(getResources().getDrawable(R.drawable.booked));
                          btn8.setEnabled(false);
                          break;


                  }
              }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        final ArrayList<Integer> selectedSlot = new ArrayList<>();
        View.OnClickListener onClickListener=new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Button btn=(Button) v;

                if (!btn.isSelected()) {
                    selectedSlot.add(v.getId());
                    btn.setBackground(getResources().getDrawable(R.drawable.selected));
                    btn.setSelected(true);

                    Toast.makeText(CarParkSlot.this, "You Selected Seat Number :" + (btn.getText().toString()), Toast.LENGTH_SHORT).show();

                } else {
                    btn.setBackground(getResources().getDrawable(R.drawable.available));
                    btn.setSelected(false);
                    Toast.makeText(CarParkSlot.this, "You Unselected Seat Number :" + (btn.getText().toString()), Toast.LENGTH_SHORT).show();
                }

            }

        };
        btn1.setOnClickListener(onClickListener);
        btn2.setOnClickListener(onClickListener);
        btn3.setOnClickListener(onClickListener);
        btn4.setOnClickListener(onClickListener);
        btn5.setOnClickListener(onClickListener);
        btn6.setOnClickListener(onClickListener);
        btn7.setOnClickListener(onClickListener);
        btn8.setOnClickListener(onClickListener);


        relativeLayout = findViewById(R.id.relativeLayout2);


        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!buttonSave.isSelected()) {

//                    buttonSave.setBackground(getResources().getDrawable(R.drawable.selected));
//                    buttonSave.setSelected(true);
                    for(int i=0; i<selectedSlot.size();i++)
                    {
                        switch (selectedSlot.get(i)) {
                            case R.id.btn1:
                                databaseReference.child("branch1").child("status").setValue("booked");
                                Toast.makeText(CarParkSlot.this, "Added Car Park Slot Successfully", Toast.LENGTH_SHORT).show();
                                Intent mypayment1= new Intent(CarParkSlot.this,Payment.class);
                                startActivity(mypayment1);
                                break;

                            case R.id.btn2:
                                databaseReference.child("branch2").child("status").setValue("booked");
                                Toast.makeText(CarParkSlot.this, "Added Car Park Slot Successfully", Toast.LENGTH_SHORT).show();
                                Intent mypayment2= new Intent(CarParkSlot.this,Payment.class);
                                startActivity(mypayment2);
                                break;
                            case R.id.btn3:
                                databaseReference.child("branch3").child("status").setValue("booked");
                                Toast.makeText(CarParkSlot.this, "Added Car Park Slot Successfully", Toast.LENGTH_SHORT).show();
                                Intent mypayment3= new Intent(CarParkSlot.this,Payment.class);
                                startActivity(mypayment3);
                                break;
                            case R.id.btn4:
                                databaseReference.child("branch4").child("status").setValue("booked");
                                Toast.makeText(CarParkSlot.this, "Added Car Park Slot Successfully", Toast.LENGTH_SHORT).show();
                                Intent mypayment4= new Intent(CarParkSlot.this,Payment.class);
                                startActivity(mypayment4);
                                break;
                            case R.id.btn5:
                                databaseReference.child("branch5").child("status").setValue("booked");
                                Toast.makeText(CarParkSlot.this, "Added Car Park Slot Successfully", Toast.LENGTH_SHORT).show();
                                Intent mypayment5= new Intent(CarParkSlot.this,Payment.class);
                                startActivity(mypayment5);
                                break;
                            case R.id.btn6:
                                databaseReference.child("branch6").child("status").setValue("booked");
                                Toast.makeText(CarParkSlot.this, "Added Car Park Slot Successfully", Toast.LENGTH_SHORT).show();
                                Intent mypayment6= new Intent(CarParkSlot.this,Payment.class);
                                startActivity(mypayment6);
                                break;
                            case R.id.btn7:
                                databaseReference.child("branch7").child("status").setValue("booked");
                                Toast.makeText(CarParkSlot.this, "Added Car Park Slot Successfully", Toast.LENGTH_SHORT).show();
                                Intent mypayment7= new Intent(CarParkSlot.this,Payment.class);
                                startActivity(mypayment7);
                                break;
                            case R.id.btn8:
                                databaseReference.child("branch8").child("status").setValue("booked");
                                Toast.makeText(CarParkSlot.this, "Added Car Park Slot Successfully", Toast.LENGTH_SHORT).show();
                                Intent mypayment8= new Intent(CarParkSlot.this,Payment.class);
                                startActivity(mypayment8);
                                break;
                        }

                    }


                } else {
                    buttonSave.setBackground(getResources().getDrawable(R.drawable.available));
                    buttonSave.setSelected(false);
                }
            if(buttonSave.isSelected())
            {



            }


            }
        });




//    public List<String> getAllBookedSeat(int reserveId)
//    {
//
//        List<String> bookedSeatsList = new ArrayList<String>();
//        String movieNum=Integer.toString(reserveId);
//
//    }
    }



}