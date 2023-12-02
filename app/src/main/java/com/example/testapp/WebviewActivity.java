package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class WebviewActivity extends AppCompatActivity {

    private WebView webView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        webView = findViewById(R.id.webview);

        // Enable JavaScript
        webView.getSettings().setJavaScriptEnabled(true);

        // Set a WebViewClient to open links within the app
        webView.setWebViewClient(new WebViewClient());

        // Load a URL
        webView.loadUrl("http://192.168.19.205:8081/");

        Button userOkButton = (Button) findViewById(R.id.userOkButton);
        userOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserOk();
            }
        });

    }

    public void sendUserOk(){
        RequestQueue queue = Volley.newRequestQueue(this);

//        String output = "not set yet";

        //this URL only works at my (nicks) apartemnt/laptop

        String url = "http://192.168.19.205:8080/set_userok";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("NicksAppFixer", "sendUserOk response: "+response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("NicksAppFixer", "error: "+error);

            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}