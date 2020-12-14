package com.example.carparkapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private EditText emailTxt,passwordTxt;
    private Button btn_Login;
    private TextView forgetPassword;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=findViewById(R.id.blockSignUpButton);
        emailTxt=findViewById(R.id.editTextEmailAddress);
        passwordTxt=findViewById(R.id.editTextPassword);
        forgetPassword=findViewById(R.id.textForgetPassword);
        forgetPassword.setPaintFlags(forgetPassword.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        btn_Login=findViewById(R.id.btnLogin);
        progressDialog=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SignUp.class));
                finish();
            }
        });
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,forgetPassword.class));
            }
        });
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email=emailTxt.getText().toString();
                String txt_password=passwordTxt.getText().toString();

                if(txt_email.isEmpty()){
                    emailTxt.setError("Please provide email");
                    emailTxt.requestFocus();
                }
                else if(txt_password.isEmpty()){
                    passwordTxt.setError("Please provide password");
                    passwordTxt.requestFocus();
                }
                else if(txt_password.length() < 8){
                    passwordTxt.setError("Password must be more than 8 character");
                    passwordTxt.requestFocus();
                }
                else if(!(txt_email.isEmpty()&& txt_password.isEmpty())){
                    checkCredentials(txt_email,txt_password);
                }
            }
        });
    }
    private void checkCredentials(String emailTxt, String passwordTxt){
        progressDialog.setMessage("Logging Please Wait...");
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(emailTxt,passwordTxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MainActivity.this,Homepage.class));
                }
                else{
                    Toast.makeText(MainActivity.this,"Login Failed",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }

        });}

}