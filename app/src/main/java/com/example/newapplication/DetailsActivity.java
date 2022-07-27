package com.example.newapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newapplication.Models.NewsHeadlines;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    NewsHeadlines headlines;

    TextView title, author,time,detail,content,url;
    ImageView img_news;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        getSupportActionBar().setTitle("News Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_background));

        time = findViewById(R.id.text_details_time);
        title = findViewById(R.id.text_details_title);
        author = findViewById(R.id.text_details_author);
        detail = findViewById(R.id.text_details_detail);
        content = findViewById(R.id.text_details_content);
        url = findViewById(R.id.text_details_url);
        img_news = findViewById(R.id.img_details_news);



        headlines = (NewsHeadlines) getIntent().getSerializableExtra("data");


        title.setText(headlines.getTitle());
        author.setText(headlines.getAuthor());
        time.setText(headlines.getPublishedAt());
        detail.setText(headlines.getDescription());
        content.setText(headlines.getContent());
        url.setText(headlines.getUrl());
        Picasso.get().load(headlines.getUrlToImage()).into(img_news);




        url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailsActivity.this,WebActivity.class);
                String URL = headlines.getUrl();
                intent.putExtra("url",URL);
                startActivity(intent);


    //            webView.loadUrl(String.valueOf(url));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
/*
        if (item.getItemId() == R.id.share){
            Toast.makeText(DetailsActivity.this, "share clicked", Toast.LENGTH_SHORT).show();

        }
*/

        if (item.getItemId() == R.id.whatsapp){
            Toast.makeText(getApplicationContext(), "clicked", Toast.LENGTH_SHORT).show();
/*
            String url = headlines.getUrl();

            boolean installed = appInstallOrNot("com.whatsapp");

            if (installed){
                Intent intent = new Intent(Intent.ACTION_VIEW);
               // intent.setType("text/plain");
                intent.setData(Uri.parse(Intent.EXTRA_TEXT + url));
                startActivity(intent);
            }
            else {
                Toast.makeText(getApplicationContext(), "whatsapp not installed  ", Toast.LENGTH_SHORT).show();
            }


*/
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean appInstallOrNot(String s) {
        PackageManager packageManager = getPackageManager();
        boolean app_install;
        try {

            packageManager.getPackageInfo(s,PackageManager.GET_ACTIVITIES);
            app_install = true;
        }

        catch (PackageManager.NameNotFoundException e){
            app_install = false;
        }
        return app_install;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}