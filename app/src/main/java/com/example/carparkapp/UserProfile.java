package com.example.carparkapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private TextView txtEmail, txtName,txtPhoneNumber, txtCarPlate;
    private Button btnSubmit;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference profileUserRef,databaseReference1;
    private FirebaseAuth mAuth;
    private String userID;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Profile");



        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference1 = firebaseDatabase.getReference().child("Vehicle");

         mAuth=FirebaseAuth.getInstance();
         userID=mAuth.getCurrentUser().getUid();
         profileUserRef=FirebaseDatabase.getInstance().getReference("Users").child(userID);

        txtEmail=findViewById(R.id.editProfileEmail);
        txtName=findViewById(R.id.editProfileName);
        txtPhoneNumber=findViewById(R.id.editProfilePhoneNumber);
        txtCarPlate=findViewById(R.id.editCarPlate);




        loadUserInformation();



        //Initialize And assign valuable
        bottomNavigationView=findViewById(R.id.bottomNav);
        //Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.myProfile);

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

                    case R.id.addReservation:
                        startActivity(new Intent(getApplicationContext(),MainActivity2.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.myBooking:
                        startActivity(new Intent(getApplicationContext(),MyBooking.class));
                        overridePendingTransition(0,0);
                        return true;
//
                    case R.id.logOut:
                        startActivity(new Intent(getApplicationContext(),SignUp.class));
                        overridePendingTransition(0,0);
                        Toast.makeText(UserProfile.this,"Sign Out Successful",Toast.LENGTH_LONG).show();
                        return true;
                }
                return false;
            }
        });
    }

    private void loadUserInformation() {
         DatabaseReference db1 = databaseReference1.child(userID);
        db1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()) {

                    Vehicle vehicle= ds.getValue(Vehicle.class);
                        Log.d("vehicle",vehicle.getPlateNumber());
                    txtCarPlate.setText(vehicle.getPlateNumber());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

       profileUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String email = snapshot.child("Email").getValue().toString();
                    String name = snapshot.child("Name").getValue().toString();
                    String phoneNumber = snapshot.child("Phone Number").getValue().toString();
                    txtEmail.setText(email);
                    txtName.setText(name);
                    txtPhoneNumber.setText(phoneNumber);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserProfile.this,"Something wrong happened",Toast.LENGTH_LONG).show();
            }
        });

//       btnSubmit.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//               updateProfile(txtName.getText().toString());
//
//           }
//
//           private void updateProfile(String txtName) {
//               profileUserRef=FirebaseDatabase.getInstance().getReference("Users");
//               HashMap<String, Object>hashMap=new HashMap<>();
//               hashMap.put("Name",txtName);
////               profileUserRef.updateChildren(hashMap);
//               Toast.makeText(UserProfile.this,"Updated Success",Toast.LENGTH_LONG).show();
//           }
//       });
//

    }


}