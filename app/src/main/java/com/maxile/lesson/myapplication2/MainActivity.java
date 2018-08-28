package com.maxile.lesson.myapplication2;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

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
    }
    @Override
    public void onClick(View view) {
        Locale current = getResources().getConfiguration().locale;
        String currentL = current.getLanguage();
        String lang;
        if (current.getLanguage()=="th")
            lang = "en";
        else
            lang = "th";
        MainActivity.this.setLanguage(lang);
    }
    @Override
    protected void onResume() {
        super.onResume();
        int i =0 ;
    }

    @Override
    protected void onPause() {
        super.onPause();
        int i =0 ;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        int i =0 ;
    }
    public void setLanguage(String language){
        Resources res = MainActivity.this.getResources();

        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(language));

        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(MainActivity.this,MainActivity.class);
        refresh.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(refresh);
        finish();
    }
}
