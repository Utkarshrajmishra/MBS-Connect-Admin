package com.example.mbsconnectadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.github.muddz.styleabletoast.StyleableToast;

public class user_info extends AppCompatActivity {

    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        fAuth=FirebaseAuth.getInstance();
    }

    public void back(View view) {
        Intent intent=new Intent(user_info.this, menu_acitivirty.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void addaccount(View view) {
        Intent intent=new Intent(user_info.this,SignUp.class);
        startActivity(intent);
    }

    public void SignOut(View view) {
        FirebaseAuth.getInstance().signOut();
        StyleableToast.makeText(user_info.this, "SignOut Successful!!", R.style.success);
        Intent intent=new Intent(user_info.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);
    }

    public void reset(View view) {
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
                        StyleableToast.makeText(user_info.this,"Reset link sent on your mail", R.style.success).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        StyleableToast.makeText(user_info.this, "Error!!"+e.getMessage(), R.style.success);
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

    public void delete(View view) {
        AlertDialog.Builder resetPassword= new AlertDialog.Builder(view.getContext());
        resetPassword.setTitle("Delete Account");
        resetPassword.setMessage("Deleting your account will delete all the data related and you won't be allowed to access the app again");
        resetPassword.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                user.delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                   StyleableToast.makeText(user_info.this,"Account Deleted Successfully", R.style.success).show();
                                }
                                else
                                {
                                    StyleableToast.makeText(user_info.this,"Error!!",R.style.failure).show();

                                }
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
}