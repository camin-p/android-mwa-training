package com.maxile.lesson.myapplication2;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        findViewById(R.id.main_btn).setOnClickListener(this);
        requestPermission();
    }

    @Override
    public void onClick(View view) {
        Locale current = getResources().getConfiguration().locale;
        String currentL = current.getLanguage();
        String lang;
        if (current.getLanguage() == "th")
            lang = "en";
        else
            lang = "th";
        MainActivity.this.setLanguage(lang);
    }

    @Override
    protected void onResume() {
        super.onResume();
        int i = 0;
    }

    @Override
    protected void onPause() {
        super.onPause();
        int i = 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        int i = 0;
    }

    public void setLanguage(String language) {
        Resources res = MainActivity.this.getResources();

        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(language));

        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(MainActivity.this, MainActivity.class);
        refresh.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(refresh);
        finish();
    }

    public final String[] listPermission = new String[]{
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.INTERNET,
            android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.WAKE_LOCK,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.ACCESS_NETWORK_STATE,
            android.Manifest.permission.VIBRATE,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
    };
    private final static int MY_PERMISSIONS_REQUEST_PHONE_STATE = 999;//any number
    public List<String> listRequest;

    private void requestPermission() {
        listRequest = new ArrayList<String>();
        for (String permission : listPermission) {
            if (ContextCompat.checkSelfPermission(this,
                    permission)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    listRequest.add(permission);
                }
            }
        }
        Log.d("request permission", listRequest.size() + "");
        if (listRequest.size() > 0) {
            ActivityCompat.requestPermissions(this,
                    listRequest.toArray(new String[0]),
                    MY_PERMISSIONS_REQUEST_PHONE_STATE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_PHONE_STATE: {
                boolean isPass = true;
                if (grantResults.length == permissions.length) {
                    for (int g : grantResults) {
                        if (g != PackageManager.PERMISSION_GRANTED) {
                            isPass = false;
                        }
                    }
                }
                if (!isPass) {
                    //permission not pass
                } else {
                    //permission pass
                }
                break;
            }
        }
    }
}
