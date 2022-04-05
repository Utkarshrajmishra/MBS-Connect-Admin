package com.example.mbsconnectadmin;

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

public class upload_notice extends AppCompatActivity {


    ProgressBar progressBar;
    CardView button;
    ImageView showimage;
    EditText title;
    CardView chooseimage;
    private final int REQ=1;
    private Bitmap bitmap;
    String downloadUrl="";

    private DatabaseReference reference;
    private  StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_notice);

        progressBar=findViewById(R.id.progress);
        button=findViewById(R.id.upload);
        title=findViewById(R.id.title);
        chooseimage=findViewById(R.id.chooseimage);
        showimage=findViewById(R.id.image);

        reference= FirebaseDatabase.getInstance().getReference();
        storageReference= FirebaseStorage.getInstance().getReference();



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(title.getText().toString().isEmpty())
                {
                    title.setError("Title cannot be empty");
                    title.requestFocus();
                }
                else  if(bitmap==null)
                {
                    uploadData();
                }
                else
                {
                    uploadImage();
                }
            }
        });





        chooseimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }






    private void uploadData() {
        reference=reference.child("Notice");
        String ntitle=title.getText().toString();

        final String uniquekey=reference.push().getKey();


        Calendar cal=Calendar.getInstance();
        SimpleDateFormat currentData=new SimpleDateFormat("dd-MM-yy");
        String date=currentData.format(cal.getTime());


        Calendar cal2=Calendar.getInstance();
        SimpleDateFormat currenttime=new SimpleDateFormat("hh:mm a");
        String time=currenttime.format(cal.getTime());


        NoticeData noticeData=new NoticeData(ntitle, downloadUrl, date, time, uniquekey);
        reference.child(uniquekey).setValue(noticeData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                progressBar.setVisibility(View.GONE);
                StyleableToast.makeText(upload_notice.this,"Notice Uploaded",R.style.success).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);
                StyleableToast.makeText(upload_notice.this,"Error!!"+e,R.style.failure).show();
            }
        });





    }








    private void uploadImage() {
        progressBar.setVisibility(View.VISIBLE);
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalImage=baos.toByteArray();
        final StorageReference filePath;
        filePath=storageReference.child("Notice").child(finalImage+"jpg");
        final UploadTask uploadTask=filePath.putBytes(finalImage);
        uploadTask.addOnCompleteListener(upload_notice.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                                    uploadData();
                                }
                            });
                        }
                    });
                }
                else
                {
                    StyleableToast.makeText(upload_notice.this,"Error! Something Went Wrong!!",R.style.failure).show();
                }
            }
        });
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ && resultCode==RESULT_OK)
        {
            Uri uri=data.getData();
            try {
                bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            showimage.setImageBitmap(bitmap);
        }
    }

    private void openGallery() {

        Intent pickimage=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickimage, REQ);
    }
}