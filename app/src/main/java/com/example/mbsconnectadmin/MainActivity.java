package com.example.mbsconnectadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import io.github.muddz.styleabletoast.StyleableToast;

public class MainActivity extends AppCompatActivity {

    CardView signbtn;
    EditText email,password;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    TextView forget;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        signbtn=findViewById(R.id.signin_button);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        progressBar=findViewById(R.id.progress);
        fAuth=FirebaseAuth.getInstance();
        forget=findViewById(R.id.forgetpassword);




        signbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



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
                fAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            StyleableToast.makeText(MainActivity.this, "Login Successful",R.style.success ).show();
                            Intent intent=new Intent(MainActivity.this, menu_acitivirty.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                            startActivity(intent);
                            progressBar.setVisibility(View.GONE);
                        }

                        else
                        {
                            progressBar.setVisibility(View.GONE);
                            StyleableToast.makeText(MainActivity.this,"Error!! "+task.getException().getMessage(),R.style.failure).show();
                        }

                    }
                });

            }
        });


        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText resetmail=new EditText(view.getContext());

                AlertDialog.Builder resetPassword= new AlertDialog.Builder(view.getContext());
                resetPassword.setTitle("Reset Password");
                resetPassword.setMessage("Enter your email to receive reset link");
                resetPassword.setView(resetmail);

                resetPassword.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String mail=resetmail.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                StyleableToast.makeText(MainActivity.this,"Reset link sent on your mail", R.style.success).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                StyleableToast.makeText(MainActivity.this, "Error!!"+e.getMessage(), R.style.success);
                            }
                        });

                    }
                });

                resetPassword.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                resetPassword.create().show();
            }
        });





           }


    }


