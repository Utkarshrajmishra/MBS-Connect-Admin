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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

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

import io.github.muddz.styleabletoast.StyleableToast;

public class upload_event extends AppCompatActivity {


    String item="";
    String [] items={"Fariwell","Annual Function","Independence Day","Republic Day", "NSS"};
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;
    ImageView showimage;
    int REQ=1;
    CardView image,button;
    private Bitmap bitmap;
    ProgressBar progressBar;
    String downloadUrl="";
    private DatabaseReference reference;
    private  StorageReference storageReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_event);

        showimage=findViewById(R.id.image);
        button=findViewById(R.id.upload);
        image=findViewById(R.id.chooseimage);
        autoCompleteTxt = findViewById(R.id.auto_complete_txt);
        progressBar=findViewById(R.id.progress);


        reference= FirebaseDatabase.getInstance().getReference().child("eventimage");
        storageReference= FirebaseStorage.getInstance().getReference().child("eventimage");

        adapterItems = new ArrayAdapter<String>(this,R.layout.list_items,items);
        autoCompleteTxt.setAdapter(adapterItems);

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item = parent.getItemAtPosition(position).toString();
                StyleableToast.makeText(upload_event.this, "Event: "+item, R.style.info).show();
            }
        });


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(bitmap==null)
                {
                    StyleableToast.makeText(upload_event.this,"Please choose an image!!",R.style.failure).show();

                }
                else if(item=="")
                {
                    StyleableToast.makeText(upload_event.this,"Please select a category",R.style.failure).show();
                }
                else
                {
                    uploadimage();
                }
            }
        });

    }

    private void uploadimage() {
        progressBar.setVisibility(View.VISIBLE);
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalImage=baos.toByteArray();
        final StorageReference filePath;
        filePath=storageReference.child(finalImage+"jpg");
        final UploadTask uploadTask=filePath.putBytes(finalImage);
        uploadTask.addOnCompleteListener(upload_event.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                    StyleableToast.makeText(upload_event.this,"Error! Something Went Wrong!!",R.style.failure).show();
                }
            }
        });
    }

    private void uploadData() {
        reference=reference.child(item);
        final String uniquekey=reference.push().getKey();
        reference.child(uniquekey).setValue(downloadUrl).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                progressBar.setVisibility(View.GONE);
                StyleableToast.makeText(upload_event.this , "Image Uploaded", R.style.success).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);
             StyleableToast.makeText(upload_event.this , "Error!!"+e, R.style.failure).show();
            }
        });
    }

    private void openGallery() {



            Intent pickimage=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickimage, REQ);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ && resultCode==RESULT_OK)
        {
            Uri uri=data.getData();
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            showimage.setImageBitmap(bitmap);
        }
    }

}