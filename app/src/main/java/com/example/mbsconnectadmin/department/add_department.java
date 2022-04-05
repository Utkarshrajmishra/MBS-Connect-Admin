package com.example.mbsconnectadmin.department;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.mbsconnectadmin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.github.muddz.styleabletoast.StyleableToast;

public class add_department extends AppCompatActivity {

    private RecyclerView bca,bba,bcom,mcom,bsc,msc;
    private LinearLayout bcanodata,bbanodata,bcomnodata,mcomnodata,mscnodata,bscnodata;
    private List<teacher_Data> list1,list2,list3,list4,list5,list6;

     private teacher_adaopter adaopter;

    private DatabaseReference reference, dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_department);


        bca=findViewById(R.id.bca);
        bba=findViewById(R.id.bba);
        bcom=findViewById(R.id.bcom);
        mcom=findViewById(R.id.mcom);
        bsc=findViewById(R.id.bsc);
        msc=findViewById(R.id.msc);

        bscnodata=findViewById(R.id.bscanodata);
        bcanodata=findViewById(R.id.bcanodata);
        bbanodata=findViewById(R.id.bbanodata);
        bcomnodata=findViewById(R.id.bcomnodata);
        mcomnodata=findViewById(R.id.mcomnodata);
        mscnodata=findViewById(R.id.mscnodata);
        reference= FirebaseDatabase.getInstance().getReference().child("teacher");

        bca();

        bbaa();

        mcom();

        bcom();

        bsc();

        msc();


    }


    private void msc() {
        dbRef=reference.child("MSc");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list6= new ArrayList<>();
                if(!snapshot.exists()){
                    mscnodata.setVisibility(View.VISIBLE);
                    msc.setVisibility(View.GONE);
                }
                else
                {
                    mscnodata.setVisibility(View.GONE);
                    msc.setVisibility(View.VISIBLE);
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        teacher_Data data=dataSnapshot.getValue(teacher_Data.class);
                        list6.add(data);
                        msc.setHasFixedSize(true);
                        msc.setLayoutManager(new LinearLayoutManager(add_department.this));
                        adaopter=new teacher_adaopter(list6, add_department.this,"MSc");
                        msc.setAdapter(adaopter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                StyleableToast.makeText(add_department.this, error.getMessage(),R.style.failure).show();

            }
        });
    }

    private void bsc() {
        dbRef=reference.child("BSc");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list5= new ArrayList<>();
                if(!snapshot.exists()){
                    bscnodata.setVisibility(View.VISIBLE);
                    bsc.setVisibility(View.GONE);
                }
                else
                {
                    bscnodata.setVisibility(View.GONE);
                    bsc.setVisibility(View.VISIBLE);
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        teacher_Data data=dataSnapshot.getValue(teacher_Data.class);
                        list5.add(data);
                        bsc.setHasFixedSize(true);
                        bsc.setLayoutManager(new LinearLayoutManager(add_department.this));
                        adaopter=new teacher_adaopter(list5, add_department.this,"BSc");
                        bsc.setAdapter(adaopter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                StyleableToast.makeText(add_department.this, error.getMessage(),R.style.failure).show();

            }
        });
    }

    private void bcom() {
        dbRef=reference.child("BCOM");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list4= new ArrayList<>();
                if(!snapshot.exists()){
                    bcomnodata.setVisibility(View.VISIBLE);
                    bcom.setVisibility(View.GONE);
                }
                else
                {
                    bcomnodata.setVisibility(View.GONE);
                    bcom.setVisibility(View.VISIBLE);
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        teacher_Data data=dataSnapshot.getValue(teacher_Data.class);
                        list4.add(data);
                        bcom.setHasFixedSize(true);
                        bcom.setLayoutManager(new LinearLayoutManager(add_department.this));
                        adaopter=new teacher_adaopter(list4, add_department.this,"BCOM");
                        bcom.setAdapter(adaopter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                StyleableToast.makeText(add_department.this, error.getMessage(),R.style.failure).show();

            }
        });
    }

    private void mcom() {
        dbRef=reference.child("MCOM");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list3= new ArrayList<>();
                if(!snapshot.exists()){
                    mcomnodata.setVisibility(View.VISIBLE);
                    mcom.setVisibility(View.GONE);
                }
                else
                {
                    mcomnodata.setVisibility(View.GONE);
                    mcom.setVisibility(View.VISIBLE);
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        teacher_Data data=dataSnapshot.getValue(teacher_Data.class);
                        list3.add(data);
                        mcom.setHasFixedSize(true);
                        mcom.setLayoutManager(new LinearLayoutManager(add_department.this));
                        adaopter=new teacher_adaopter(list3, add_department.this, "MCOM");
                        mcom.setAdapter(adaopter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                StyleableToast.makeText(add_department.this, error.getMessage(),R.style.failure).show();

            }
        });


            }

    private void bbaa() {
        dbRef=reference.child("BBA");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list2= new ArrayList<>();
                if(!snapshot.exists()){
                    bbanodata.setVisibility(View.VISIBLE);
                    bba.setVisibility(View.GONE);
                }
                else
                {
                    bbanodata.setVisibility(View.GONE);
                    bba.setVisibility(View.VISIBLE);
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        teacher_Data data=dataSnapshot.getValue(teacher_Data.class);
                        list2.add(data);
                        bba.setHasFixedSize(true);
                        bba.setLayoutManager(new LinearLayoutManager(add_department.this));
                        adaopter=new teacher_adaopter(list2, add_department.this,"BBA");
                        bba.setAdapter(adaopter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                StyleableToast.makeText(add_department.this, error.getMessage(),R.style.failure).show();

            }
        });
    }

    private void bca() {


        dbRef=reference.child("BCA");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list1= new ArrayList<>();
                if(!snapshot.exists()){
                    bcanodata.setVisibility(View.VISIBLE);
                    bca.setVisibility(View.GONE);
                }
                else
                {
                    bcanodata.setVisibility(View.GONE);
                    bca.setVisibility(View.VISIBLE);
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        teacher_Data data=dataSnapshot.getValue(teacher_Data.class);
                        list1.add(data);
                        bca.setHasFixedSize(true);
                        bca.setLayoutManager(new LinearLayoutManager(add_department.this));
                        adaopter=new teacher_adaopter(list1, add_department.this,"BCA");
                        bca.setAdapter(adaopter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                StyleableToast.makeText(add_department.this, error.getMessage(),R.style.failure).show();

            }
        });
    }

    public void floatingactionbutton(View view) {
        Intent intent=new Intent(add_department.this, add_teachers.class);
        startActivity(intent);
    }
}