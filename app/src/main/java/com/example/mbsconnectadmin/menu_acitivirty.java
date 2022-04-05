package com.example.mbsconnectadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.mbsconnectadmin.department.add_department;

public class menu_acitivirty extends AppCompatActivity {

    String url="https://forms.gle/N7ZyPEu2RYxwytMB9";

    ImageView classes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_acitivirty);

        classes=findViewById(R.id.lievclass);
        classes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(menu_acitivirty.this, createclass.class);
                startActivity(intent);
            }
        });
    }

    public void useroptions(View view) {
        Intent intent=new Intent(menu_acitivirty.this, user_info.class);
        startActivity(intent);
    }

    public void notice(View view) {
        Intent intent=new Intent(menu_acitivirty.this, upload_notice.class);
        startActivity(intent);
    }

    public void uploadbook(View view) {
        Intent intent=new Intent(menu_acitivirty.this, upload_book.class);
        startActivity(intent);
    }

    public void event(View view) {
        Intent intent=new Intent(menu_acitivirty.this, upload_event.class);
        startActivity(intent);
    }

    public void bug(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        String[] strTo = { "utkarshrajmishra811545@gmail.com" };
        intent.putExtra(Intent.EXTRA_EMAIL, strTo);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Bug in MBS Admin");
        intent.putExtra(Intent.EXTRA_TEXT, "Explain the bug here!");
        intent.setType("message/rfc822");
        intent.setPackage("com.google.android.gm");
        startActivity(intent);
    }

    public void contact(View view) {
        String phone = "7521859385";
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        startActivity(intent);
    }

    public void rateus(View view) {

        CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();

        customIntent.setToolbarColor(ContextCompat.getColor(menu_acitivirty.this, R.color.buttoncolor));

        openCustomTab(menu_acitivirty.this, customIntent.build(), Uri.parse(url));


    }



    public static void openCustomTab(Activity activity, CustomTabsIntent customTabsIntent, Uri uri) {

        String packageName = "com.android.chrome";
        if (packageName != null) {


            customTabsIntent.intent.setPackage(packageName);


            customTabsIntent.launchUrl(activity, uri);
        } else
            activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }

    public void marksheet(View view) {
        Intent intent=new Intent(menu_acitivirty.this, upload_marksheet.class);
        startActivity(intent);
    }

    public void pratical(View view) {
        Intent intent=new Intent(menu_acitivirty.this, upload_labwork.class);
        startActivity(intent);
    }




    public void youtubestats(View view) {

        Intent intent=new Intent(menu_acitivirty.this, youtube_stats.class);
        startActivity(intent);

    }

    public void uploadassigement(View view) {
        Intent intent=new Intent(menu_acitivirty.this, upload_Assigment.class);
        startActivity(intent);
    }

    public void uploadtimetable(View view) {
        Intent intent=new Intent(menu_acitivirty.this,upload_timetable.class);
        startActivity(intent);
    }

    public void uploadschecdule(View view) {
    }

    public void addfaculty(View view) {
        Intent intent=new Intent(menu_acitivirty.this, add_department.class);
        startActivity(intent);
    }

    public void paper(View view) {
        Intent intent=new Intent(menu_acitivirty.this, upload_paper.class);
        startActivity(intent);
    }

    public void aboutus(View view) {
        Intent intent=new Intent(menu_acitivirty.this, about_us.class);
        startActivity(intent);
    }
}
