package com.example.mbsconnectadmin.department;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.mbsconnectadmin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import io.github.muddz.styleabletoast.StyleableToast;

public class update_teacher extends AppCompatActivity {

    private ImageView teacher_image;
     EditText teacher_name, teacher_mail,teacher_post;
     CardView update,delete;
    String tname,tmail, tpost;
     int REQ=1;
     Bitmap bitmap;
     String downloadUrl="";
     String name,email,post,image;
     ProgressBar progressBar;
    private DatabaseReference reference,dbef;
    private  StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_teacher2);

        teacher_image=findViewById(R.id.update_teacher_image);
        teacher_name=findViewById(R.id.updateteachername);
        teacher_mail=findViewById(R.id.updateteachermail);
        teacher_post=findViewById(R.id.updateteacherpost);

        delete=findViewById(R.id.deleteteacher);
        progressBar=findViewById(R.id.progress);

        name=getIntent().getStringExtra("name");
        email=getIntent().getStringExtra("mail");
        post=getIntent().getStringExtra("post");
        image=getIntent().getStringExtra("image");

        reference= FirebaseDatabase.getInstance().getReference().child("teacher");
        storageReference= FirebaseStorage.getInstance().getReference().child("teacher");

        try {
            Picasso.get().load(image).into(teacher_image);
        } catch (Exception e) {
            e.printStackTrace();
        }

        teacher_name.setText(name);
        teacher_mail.setText(email);
        teacher_post.setText(post);

      delete.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              deleteData();
          }
      });

    }

    private void deleteData() {
        String key=getIntent().getStringExtra("key");
        String category=getIntent().getStringExtra("category");
        reference.child(category).child(key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                StyleableToast.makeText(update_teacher.this, "Teacher deleted", R.style.success).show();
                Intent intent=new Intent(update_teacher.this, add_department.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
              StyleableToast.makeText(update_teacher.this, "Error", R.style.failure).show();
            }
        });
    }


}


