package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.ktx.Firebase;

public class MainActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;

    final String CHANNEL_ID = String.valueOf(R.string.default_notification_channel_id);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        createNotificationChannel();

        Switch lockSwitch = (Switch) findViewById(R.id.lockSwitch);
        Switch requestSwitch = (Switch) findViewById(R.id.requestSwitch);

        //put switches in the state the pi has them in
        updateLockSwitch();
        updateSecureSwitch();


        lockSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    lockSwitch.setText("Locked");
                    sendHTTPRequest("set_lock", "true");

                }
                else{
                    lockSwitch.setText("Unlocked");
                    sendHTTPRequest("set_lock", "false");
                }

                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, null);
            }
        });

        requestSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    sendHTTPRequest("set_secure", "true");
                }
                else{
                    sendHTTPRequest("set_secure", "false");
                }
            }
        });

        Button refreshButton = (Button) findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLockSwitch();
                updateSecureSwitch();
            }
        });

        ImageButton settingsBtn = (ImageButton) findViewById(R.id.settingsButton);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, SettingsViewActivity.class));
            }
        });

        ImageButton cameraButton = (ImageButton) findViewById(R.id.imageButton2);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, WebviewActivity.class));
            }
        });

        Button thatsMyPetButton = (Button) findViewById(R.id.thatsMyDogButton);

        thatsMyPetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserOk();
            }
        });
    }


    //method for handling http requests
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

    // these methods are for making sure the mode switches are accurate
    public void set_secure_switch(boolean state){

        Switch requestSwitch = (Switch) findViewById(R.id.requestSwitch);

        if(state){
            requestSwitch.setChecked(true);
        }
        else{
            requestSwitch.setChecked(false);
        }
        Log.i("NicksAppFixer", "secure set to: "+state);
    }

    public void set_lock_switch(boolean state){
        Switch lockSwitch = (Switch) findViewById(R.id.lockSwitch);
        if(state){
            lockSwitch.setChecked(true);
        }
        else{
            lockSwitch.setChecked(false);
        }
        Log.i("NicksAppFixer", "lock set to: "+state);

    }

    public void updateSecureSwitch(){
        RequestQueue queue = Volley.newRequestQueue(this);

//        String output = "not set yet";

        //this URL only works at my (nicks) apartemnt/laptop

        String url = "http://192.168.19.205:8080/get_secure";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.toString().toLowerCase().equals("true")){
                            set_secure_switch(true);
                        }
                        else{
                            set_secure_switch(false);
                        }
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

    public void updateLockSwitch(){
        RequestQueue queue = Volley.newRequestQueue(this);

//        String output = "not set yet";

        //this URL only works at my (nicks) apartemnt/laptop

        String url = "http://192.168.19.205:8080/get_lock";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.toString().toLowerCase().equals("true")){
                            set_lock_switch(true);
                        }
                        else{
                            set_lock_switch(false);
                        }
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



    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "default_notification_channel_id",
                    "Channel name",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Channel description");

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }


}