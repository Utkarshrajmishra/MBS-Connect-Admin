package com.example.mbsconnectadmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.HashMap;

import io.github.muddz.styleabletoast.StyleableToast;

public class exam_timetable extends AppCompatActivity {

    ProgressBar progressBar;
    CardView button;
    Uri pdfdata;
    EditText title;
    CardView choosepdf;
    TextView pdfname;
    private final int REQ=99;
    String pdfName="", pdftitle;

    private DatabaseReference databaseReference;
    private StorageReference storageReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_timetable);

        progressBar=findViewById(R.id.progress);
        button=findViewById(R.id.upload);
        title=findViewById(R.id.title);
        choosepdf=findViewById(R.id.choosepdf);
        pdfname=findViewById(R.id.filename);


        databaseReference= FirebaseDatabase.getInstance().getReference();
        storageReference= FirebaseStorage.getInstance().getReference();

        choosepdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pdftitle=title.getText().toString();
                if(pdftitle.isEmpty())
                {
                    title.setError("This cannot be null");
                    title.requestFocus();
                }
                else if(pdfdata==null)
                {

                    StyleableToast.makeText(exam_timetable.this,"Please choose a pdf!!!",R.style.failure).show();
                }
                else
                {
                    progressBar.setVisibility(View.VISIBLE);
                    uploadpdf();
                }
            }
        });


    }

    private void uploadpdf() {
        StorageReference reference=storageReference.child("exam/"+pdfName+"-"+System.currentTimeMillis()+".pdf");
        reference.putFile(pdfdata).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                progressBar.setVisibility(View.GONE);
                Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isComplete());
                Uri uri=uriTask.getResult();
                uploadData(String.valueOf(uri));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);

                StyleableToast.makeText(exam_timetable.this, "Error! "+e+"!!",R.style.failure).show();
            }
        });
    }



    private void uploadData(String downloadUrl) {
        String unqiuekey=databaseReference.child("exam").push().getKey();

        HashMap data=new HashMap();
        data.put("pdfTitle", pdftitle);
        data.put("pdfUrl",downloadUrl);

        databaseReference.child("exam").child(unqiuekey).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressBar.setVisibility(View.GONE);
                StyleableToast.makeText(exam_timetable.this, "Schedule Uploaded!!", R.style.success).show();
                title.setText("");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);
                StyleableToast.makeText(exam_timetable.this, "Error!!"+e+"!!", R.style.failure).show();
            }
        });
    }


    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ && resultCode==RESULT_OK)
        {
            pdfdata=data.getData();
            if(pdfdata.toString().startsWith("content://"))
            {
                Cursor cursor=null;
                try {
                    cursor=exam_timetable.this.getContentResolver().query(pdfdata, null, null, null, null);
                    if(cursor!=null && cursor.moveToFirst())
                    {
                        pdfName=cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if(pdfdata.toString().startsWith("file://"))
            {
                pdfName=new File(pdfName.toString()).getName();

            }
            pdfname.setText(pdfName);
            StyleableToast.makeText(exam_timetable.this,""+pdfName,R.style.info).show();
        }
    }

    private void openGallery() {

        Intent pickpdf=new Intent();
        pickpdf.setType("application/pdf");
        pickpdf.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(pickpdf, "Select PDF"), REQ);
    }


}