package com.example.newapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.newapplication.Models.NewsHeadlines;

public class WebActivity extends AppCompatActivity {

    WebView webView;
    NewsHeadlines headlines;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        dialog = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);
        dialog.setTitle("Wait While Web Page is Loading...");
        dialog.setCancelable(false);
        dialog.show();
        dialog.setProgress(100);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                dialog.dismiss();
            }}, 3000);


        getSupportActionBar().setTitle("News Browser");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_background));

        webView = (WebView) findViewById(R.id.web_view);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);


        String url = getIntent().getStringExtra("url");
        webView.loadUrl(url);
        //dialog.dismiss();

        //webView.loadUrl("https://www.google.com/");
        //webView.loadUrl(headlines.getUrl());

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}