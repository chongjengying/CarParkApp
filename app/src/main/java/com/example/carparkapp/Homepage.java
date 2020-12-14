package com.example.carparkapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Homepage extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private String email,phoneNumber;
    String nameId="";
    private FirebaseAuth mAuth;
    FrameLayout frameLayout;
    private  FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference,databaseReference1;
    private TextView nameTextView,vehiclePLateTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Welcome To CarParkApp");

//        Intent intent=getIntent();
//        email=intent.getStringExtra("Email");
        //Firebase
        mAuth=FirebaseAuth.getInstance();
        String id=mAuth.getCurrentUser().getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        databaseReference1 = firebaseDatabase.getReference().child("Vehicle");

//        firebaseAuth=FirebaseAuth.getInstance();
//        FirebaseUser user=firebaseAuth.getCurrentUser();
        nameTextView = findViewById(R.id.txtPersonName);
        vehiclePLateTxt= findViewById(R.id.txtVehiclePlateNo);
        //Initialize And assign valuable
        bottomNavigationView=findViewById(R.id.bottomNav);
        //Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.myHome);




        //perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.myHome:
                       startActivity(new Intent(getApplicationContext(),Homepage.class));
                       overridePendingTransition(0,0);
                       return true;

                    case R.id.myProfile:
                        startActivity(new Intent(getApplicationContext(),UserProfile.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.myBooking:
                        startActivity(new Intent(getApplicationContext(),MyBooking.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.addReservation:
                        startActivity(new Intent(getApplicationContext(),MainActivity2.class));
                        overridePendingTransition(0,0);
                        return true;

//
                    case R.id.logOut:
                        startActivity(new Intent(getApplicationContext(),SignUp.class));
                        Toast.makeText(Homepage.this,"Sign Out Successful",Toast.LENGTH_LONG).show();
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        //navigate to addVehicle
        vehiclePLateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Homepage.this,addVehicle.class);
                startActivity(myIntent);
            }
        });

        getUser(nameId);

//        //Get User Id from Intent
//        if (getIntent() != null) {
//            nameId = getIntent().getStringExtra("Name");
//            if (!nameId.isEmpty()) {
//
//            }
//        }




//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String username=dataSnapshot.child("Name").getValue().toString();
//                String phoneNumber=dataSnapshot.child("PhoneNumber").getValue().toString();
////                nameTextView.setText(username);
////                phoneTextView.setText(phoneNumber);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser ruser= firebaseAuth.getCurrentUser();
//                if(ruser!=null)
//                {
//                    if(ruser.getDisplayName()!=null)
//                    {
//                        nameTextView.setText(ruser.getDisplayName());
//                    }
//                }
//                String userId=ruser.getUid();
//
////                String name=sharedPreferences.getString("Name","");
////                nameTextView.setText(name);
//            }
//
//
//        };

    }
//    private void  signOut(){
//        //alert dialog to confirm
//        AlertDialog confirmDialog= new AlertDialog.Builder(this)
//                .setTitle("Sign Out")
//                .setMessage("Do you really want to sign out")
//                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Common.currentUser= null;
//                        AccountKit.logOut();
//                        Intent intent= new Intent(Homepage.this,MainActivity.class);
//                        //clear all previous activities
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        startActivity(intent);
//                        finish();
//
//                    }
//                }).create();
//
//    }
   public void getUser(String nameId) {
        String id=mAuth.getCurrentUser().getUid();

        final DatabaseReference username = databaseReference.child(id).child("Name");
        final  DatabaseReference db1 = databaseReference1.child(id);
       db1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()) {

                  Vehicle vehicle= ds.getValue(Vehicle.class);

                    vehiclePLateTxt.setText("Plate Number:" +vehicle.getPlateNumber());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        username.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String username = snapshot.getValue().toString();
                    nameTextView.setText("Driver Name :"+username);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}