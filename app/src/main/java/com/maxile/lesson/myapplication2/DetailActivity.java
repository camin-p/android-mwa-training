package com.maxile.lesson.myapplication2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String title = this.getIntent().getStringExtra("title");
        String cover_picture = this.getIntent().getStringExtra("cover_picture");
        String news = this.getIntent().getStringExtra("news");

        ((TextView)findViewById(R.id.title)).setText(title);
        ImageView iv = (ImageView)findViewById(R.id.image);
        Picasso.get()
                .load(cover_picture)
                .into(iv);
        WebView webView = (WebView)findViewById(R.id.webview);
        webView.loadDataWithBaseURL
                ("file:///android_asset/",news,
                        "text/html", "utf-8",
                        null);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
