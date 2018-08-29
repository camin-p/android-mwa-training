package com.maxile.lesson.myapplication2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        String title = this.getIntent().getStringExtra("title");
        String cover_picture = this.getIntent().getStringExtra("cover_picture");
        String news = this.getIntent().getStringExtra("news");

        ((TextView)findViewById(R.id.title)).setText(title);
        ImageView iv = (ImageView)findViewById(R.id.image);
        Picasso.get()
                .load(cover_picture)
                .into(iv);
    }
}
