package com.example.carparkapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    private Button button,btn_Signup;
    private EditText nameTxt,emailTxt,phoneNumberTxt,passwordTxt,cPaswwordTxt;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        nameTxt=findViewById(R.id.editTextUserName);
        emailTxt=findViewById(R.id.editTextEmailAddress);
        phoneNumberTxt=findViewById(R.id.editTextPhoneNumber);
        passwordTxt=findViewById(R.id.textSgnUpPassword);
        cPaswwordTxt=findViewById(R.id.textSgnUpCfrm_Password);
        progressDialog=new ProgressDialog(this);
        button=findViewById(R.id.blockLogin);
        btn_Signup=findViewById(R.id.btnSignUp);

        mAuth=FirebaseAuth.getInstance();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this,MainActivity.class));
                finish();
            }
        });
        btn_Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String txt_name=nameTxt.getText().toString();
                final String txt_email=emailTxt.getText().toString();
                final String txt_phoneNumber=phoneNumberTxt.getText().toString();
                String txt_password=passwordTxt.getText().toString();
                String txt_cPassword=cPaswwordTxt.getText().toString();



//                (validateEmail() || validateName(txt_name) || validatePhoneNumber(txt_phoneNumber) || validatePassword(txt_password,txt_cPassword))
if(!(validateName(txt_name))&& !(validateEmail(txt_email))  && !(validatePhoneNumber(txt_phoneNumber)) && !(validatePassword(txt_password,txt_cPassword)))
{
if(!(txt_name.isEmpty() )&& !(txt_email.isEmpty()) &&!(txt_phoneNumber.isEmpty()) && !(txt_password.isEmpty()) && !(txt_cPassword.isEmpty())) {
    signUp(txt_name, txt_email, txt_phoneNumber, txt_password, txt_cPassword);
}

}

            }
            private boolean validateName(String txt_name){


                if(txt_name.isEmpty()){
                    nameTxt.setError("Please provide name");
                    nameTxt.requestFocus();
                }
                if(txt_name.length()>=15){
                    nameTxt.setError("Name is too long");
                    nameTxt.requestFocus();
                }
                return false;
            }
            private boolean validateEmail(String txt_email){
            String EMAIL_pattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                if(txt_email.isEmpty()){
                   emailTxt.setError("Please provide email");
                   emailTxt.requestFocus();

                }
                else if(!txt_email.matches(EMAIL_pattern)) {
                    emailTxt.setError("Invalid Email Address Format");
                    emailTxt.requestFocus();
                }
              return false;
            }
           private boolean validatePhoneNumber(String txt_phoneNumber) {
               if(txt_phoneNumber.isEmpty()){
                   phoneNumberTxt.setError("Please provide phone number");
                   phoneNumberTxt.requestFocus();
               }
               if(!Pattern.matches("[a-zA-Z]+", txt_phoneNumber)){
                   if (txt_phoneNumber.length() < 9 || txt_phoneNumber.length() > 13) {
                       if(txt_phoneNumber.length() != 10) {
                            phoneNumberTxt.setError("Not Valid Phone Number");
                            return false;
                       }
                   }
               }

             return false;
           }
            private boolean validatePassword(String txt_password,String txt_cPassword){
                if(txt_password.isEmpty()){
                    passwordTxt.setError("Password Cannot be Empty");
                    passwordTxt.requestFocus();
                }
                if(txt_cPassword.isEmpty()){
                    cPaswwordTxt.setError("Confirm Password Cannot be Empty");
                    cPaswwordTxt.requestFocus();
                }
                if(txt_password.length() < 8){
                    passwordTxt.setError("Password must be more than 8 character");
                    passwordTxt.requestFocus();
                }
                if(txt_cPassword.length()<8 || !(txt_cPassword.equals(txt_password))){
                    cPaswwordTxt.setError("Password Does Not Matches");
                    cPaswwordTxt.requestFocus();
                }

                return false;

            }


        });


    }

    private void signUp(final String nameTxt, final String emailTxt, final String phoneNumberText, String passwordTxt, String cPasswordTxt){
        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();
//        if(!(emailTxt.isEmpty() && !(nameTxt.isEmpty()) &&!(phoneNumberText.isEmpty()) && passwordTxt.isEmpty())){
//        if(cPasswordTxt.length()<8 || !(cPasswordTxt.equals(passwordTxt))){
//            cPaswwordTxt.setError("Password Does Not Matches");
//            cPaswwordTxt.requestFocus();
//        }
            mAuth.createUserWithEmailAndPassword(emailTxt,passwordTxt).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser ruser = mAuth.getCurrentUser();
                    String userId = ruser.getUid();
                    databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("Name", nameTxt);
                    hashMap.put("userID", userId);
                    hashMap.put("Email", emailTxt);
                    hashMap.put("Phone Number", phoneNumberText);
                    databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                startActivity(new Intent(SignUp.this, MainActivity.class));
                                progressDialog.dismiss();
                                Toast.makeText(SignUp.this, "Account Created Successful", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(SignUp.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(SignUp.this, "Account Register Unsuccessful ", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();

                }
            }
});
    }

}