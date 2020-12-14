package com.example.carparkapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgetPassword extends AppCompatActivity {

    EditText fgt_Email;
    Button rst_Btn;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Forget Password");

        fgt_Email=findViewById(R.id.forgetEmail);
        rst_Btn=findViewById(R.id.resetBtn);
        mAuth=FirebaseAuth.getInstance();

        rst_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String EMAIL_pattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                String fgtEmail = fgt_Email.getText().toString();
                if (fgtEmail.isEmpty()) {
                    Toast.makeText(forgetPassword.this, "Please Fill-up the email", Toast.LENGTH_SHORT).show();
                } else if (!fgt_Email.equals(EMAIL_pattern)) {
                    Toast.makeText(forgetPassword.this, "Please Fill-up the email format", Toast.LENGTH_SHORT).show();
                }
               else{ mAuth.sendPasswordResetEmail(fgt_Email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(forgetPassword.this, "Password send to your email", Toast.LENGTH_LONG).show();
                            ;
                        }

                    }
                });
            }
            }
        });

    }

}