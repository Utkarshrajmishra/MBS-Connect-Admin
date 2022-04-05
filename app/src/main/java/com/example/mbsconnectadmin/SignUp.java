package com.example.mbsconnectadmin;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.github.muddz.styleabletoast.StyleableToast;

public class SignUp extends AppCompatActivity {

    CardView signbtn;
    EditText email,password;
    ProgressBar progressBar;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        signbtn=findViewById(R.id.signin_button);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        progressBar=findViewById(R.id.progress);
        fAuth=FirebaseAuth.getInstance();


        signbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail=email.getText().toString();
                String pass=password.getText().toString();

                if(TextUtils.isEmpty(mail))
                {
                    email.setError("Email is required!!!");
                    return;
                }
                if(TextUtils.isEmpty(pass))
                {
                    password.setError("Password is required");
                    return;
                }

                if(pass.length()<6)
                {
                    password.setError("Password must be > 6");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                fAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {

                            StyleableToast.makeText(SignUp.this, "User Registered",R.style.success ).show();
                            Intent intent=new Intent(SignUp.this, menu_acitivirty.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            startActivity(intent);
                            progressBar.setVisibility(View.GONE);
                        }

                        else
                        {
                            progressBar.setVisibility(View.GONE);
                            StyleableToast.makeText(SignUp.this,"Error!! "+task.getException().getMessage(),R.style.failure).show();
                        }



                    }
                });


            }
        });

    }




}