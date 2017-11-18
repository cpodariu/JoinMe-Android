package com.example.alexandru.joinme_android;


import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alexandru.joinme_android.Helpers.NetworkUtils;
import com.example.alexandru.joinme_android.Helpers.SharedPreferencesHelper;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText mEmailEdit;
    EditText mPasswordEdit;
    Button logInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmailEdit = (EditText) findViewById(R.id.email_text);
        mPasswordEdit = (EditText) findViewById(R.id.password_text);
        logInButton = (Button) findViewById(R.id.log_in_button);

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.this.logIn();
            }
        });
    }

    void logIn()
    {
        final String email = mEmailEdit.getText().toString();
        final String password = mPasswordEdit.getText().toString();
        if (email == null || password == null)
            Toast.makeText(this, "Credentials can't be empty", Toast.LENGTH_SHORT).show();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(com.android.volley.Request.Method.GET, NetworkUtils.buildLogInUrl(email, password), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                SharedPreferencesHelper.logIn(email, password, LoginActivity.this);
                //LoginActivity.this.startNavigationDrawer();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(sr);
    }

    void startNavigationDrawer()
    {
        Intent intent = new Intent(this, NavigationDrawer.class);
        startActivity(intent);
    }
}
