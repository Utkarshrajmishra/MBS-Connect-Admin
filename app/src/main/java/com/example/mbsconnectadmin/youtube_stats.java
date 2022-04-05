package com.example.mbsconnectadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;


public class youtube_stats extends AppCompatActivity {

    TextView title;
    TextView totalview,totalsubs,totalvideo;
    private RequestQueue mQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_stats);

        title=findViewById(R.id.title);
        totalview=findViewById(R.id.view2);
        totalsubs=findViewById(R.id.subs);
        totalvideo=findViewById(R.id.video);

        mQueue = Volley.newRequestQueue(this);

        jasonParse();
    }

    private void jasonParse() {

        String url="https://www.googleapis.com/youtube/v3/channels?part=statistics&id=UCKIufDX0ghxP00ycsSdZ8YQ&key=AIzaSyDri0qWLm-R54YhMnfstVKe2tD9DbQ-JeE";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("items");


//                                JSONObject items = jsonArray.getJSONObject(0).getJSONObject("snippet");
//
//                                String view1 = items.getString("title");
//                                String subs2 = items.getString("description");
                            JSONObject items = jsonArray.getJSONObject(0).getJSONObject("statistics");
                            String video3 = items.getString("videoCount");
                            String view1 = items.getString("subscriberCount");
                            String subs2 = items.getString("viewCount");

                            totalview.setText(subs2);
                            totalsubs.setText(view1);
                            totalvideo.setText(video3);

                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }


    }
