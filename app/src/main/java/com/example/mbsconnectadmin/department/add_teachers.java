package com.example.mbsconnectadmin.department;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.mbsconnectadmin.NoticeData;
import com.example.mbsconnectadmin.R;
import com.example.mbsconnectadmin.upload_event;
import com.example.mbsconnectadmin.upload_notice;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.github.muddz.styleabletoast.StyleableToast;

public class add_teachers extends AppCompatActivity {

    String item = "";
    String[] items = {"BCA", "BBA", "BCOM", "MCOM", "BSc", "MSc"};
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;
    int REQ = 1;
    ImageView teacher_image;
    EditText teacher_mail, teacher_name, teacher_post;
    CardView button;
    private Bitmap bitmap;
    String name,mail,post,downloadUrl="";
    ProgressBar progressBar;
    private DatabaseReference reference,dbef;
    private  StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teachers);

        autoCompleteTxt = findViewById(R.id.auto_complete_txt);
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_items, items);
        autoCompleteTxt.setAdapter(adapterItems);

        teacher_image = findViewById(R.id.addtechaerimage);
        teacher_mail = findViewById(R.id.teachermail);
        teacher_name = findViewById(R.id.teachername1);
        teacher_post = findViewById(R.id.teacherpost);
        button = findViewById(R.id.upload);
        progressBar=findViewById(R.id.progress);


        reference= FirebaseDatabase.getInstance().getReference().child("teacher");
        storageReference= FirebaseStorage.getInstance().getReference().child("teacher");

        teacher_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item = parent.getItemAtPosition(position).toString();

            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });
    }

    private void validation() {
        name=teacher_name.getText().toString();
        mail=teacher_mail.getText().toString();
        post=teacher_post.getText().toString();

        if(name.isEmpty())
        {
            teacher_name.setError("Name cannot be empty");
            teacher_name.requestFocus();
        }
        else if(mail.isEmpty())
        {
            teacher_mail.setError("Email cannot be empty");
            teacher_mail.requestFocus();
        }
        else if(post.isEmpty())
        {
            teacher_post.setError("Post cannot be empty");
            teacher_post.requestFocus();
        }
        else if(item=="")
        {
            StyleableToast.makeText(add_teachers.this, "Department cannot be empty", R.style.failure).show();
        }
        else if(bitmap==null)
        {
           StyleableToast.makeText(add_teachers.this,"Please choose a photo",R.style.failure).show();
        }
        else
        {

            insertImage();
        }
    }

    private void insertImage() {
        progressBar.setVisibility(View.VISIBLE);
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalImage=baos.toByteArray();
        final StorageReference filePath;
        filePath=storageReference.child(finalImage+"jpg");
        final UploadTask uploadTask=filePath.putBytes(finalImage);
        uploadTask.addOnCompleteListener(add_teachers.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful())
                {
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl=String.valueOf(uri);
                                    insertData();
                                }
                            });
                        }
                    });
                }
                else
                {
                    StyleableToast.makeText(add_teachers.this,"Error! Something Went Wrong!!",R.style.failure).show();
                }
            }
        });
    }


    private void insertData() {
        dbef=reference.child(item);


        final String uniquekey=dbef.push().getKey();




        teacher_Data teacherData=new teacher_Data(name,mail,post,downloadUrl,uniquekey);
        dbef.child(uniquekey).setValue(teacherData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                progressBar.setVisibility(View.GONE);
                StyleableToast.makeText(add_teachers.this,"Teacher added successfully",R.style.success).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);
                StyleableToast.makeText(add_teachers.this,"Error!!"+e,R.style.failure).show();
            }
        });




    }

    private void openGallery() {


        Intent pickimage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickimage, REQ);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            teacher_image.setImageBitmap(bitmap);
        }
    }
}
