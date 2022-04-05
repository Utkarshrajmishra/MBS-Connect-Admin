package com.example.mbsconnectadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;



import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

import io.github.muddz.styleabletoast.StyleableToast;

public class createclass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createclass);
        getSupportActionBar().hide();

        try {
            JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(new URL(""))
                    .setFeatureFlag("welcomepage.enabled", false)
                    .setFeatureFlag("invite.enabled",false)
                    .setFeatureFlag("help.enabled",false)
                    .build();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    public void onButtonClick(View v) {
        EditText editText = findViewById(R.id.codeBox);
        String text = editText.getText().toString();
        if (text.length() > 0) {
            JitsiMeetConferenceOptions options
                    = new JitsiMeetConferenceOptions.Builder()
                    .setRoom(text)
                    .setFeatureFlag("invite.enabled",false)
                    .setFeatureFlag("help.enabled",false)
                    .build();
            JitsiMeetActivity.launch(this, options);
        }




    }

    public void share(View view) {
        PackageManager pm=getPackageManager();
        EditText editText = findViewById(R.id.codeBox);
        if(editText.getText().toString().isEmpty()){
            StyleableToast.makeText(createclass.this, "Enter the Room Code",R.style.failure).show();
        }
        else {
            try {

                Intent waIntent = new Intent(Intent.ACTION_SEND);
                waIntent.setType("text/plain");
                String text = "Room Code to join class: ";
                String code=editText.getText().toString();
                text+=code;


                PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                //Check if package exists or not. If not then code
                //in catch block will be called
                waIntent.setPackage("com.whatsapp");

                waIntent.putExtra(Intent.EXTRA_TEXT, text);
                startActivity(Intent.createChooser(waIntent, "Share with"));

            } catch (PackageManager.NameNotFoundException e) {
                StyleableToast.makeText(createclass.this,"Whatsapp not installed",R.style.failure).show();
            }
        }

    }
}