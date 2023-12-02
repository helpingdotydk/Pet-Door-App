package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class SettingsViewActivity extends AppCompatActivity {



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        Button durationUpdater = (Button) findViewById(R.id.DurationUpdateButton);
        durationUpdater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText number = (EditText) findViewById(R.id.DoorDurationEditText);
                sendHTTPRequest("set_doorDuration",number.getText().toString());
            }
        });

        ImageButton addTagButton = (ImageButton) findViewById(R.id.add_tag_button);
        addTagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAddTagTrue();
            }
        });

    }

    public void durationUpdater(){
        EditText number = (EditText) findViewById(R.id.DoorDurationEditText);
        sendHTTPRequest("set_doorDuration",number.getText().toString());
    }

    public String sendHTTPRequest (String method, String input){
        RequestQueue queue = Volley.newRequestQueue(this);

//        String output = "not set yet";

        //this URL only works at my (nicks) apartemnt/laptop

        String url = "http://192.168.19.205:8080/"+method+"/"+input;

        TextView textView = (TextView) findViewById(R.id.devTextView);

        textView.setText("not set yet");

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        textView.setText(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("That didn't work!");
                Log.i("NicksAppFixer", "error: "+error);

            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
        return textView.getText().toString();
    }


    public void setAddTagTrue(){
        RequestQueue queue = Volley.newRequestQueue(this);

//        String output = "not set yet";

        //this URL only works at my (nicks) apartemnt/laptop

        String url = "http://192.168.19.205:8080/add_tag";

        TextView textView = (TextView) findViewById(R.id.devTextView);

        textView.setText("not set yet");

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        textView.setText(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("That didn't work!");
                Log.i("NicksAppFixer", "error: "+error);

            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}