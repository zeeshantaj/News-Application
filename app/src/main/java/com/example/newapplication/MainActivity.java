package com.example.newapplication;

import static com.example.newapplication.R.color.red;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newapplication.Models.NewsApiResponse;
import com.example.newapplication.Models.NewsHeadlines;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SelectListener, View.OnClickListener {

    RecyclerView recyclerView;
    CustomAdapter adapter;
    ProgressDialog dialog;

    Button b1,b2,b3,b4,b5,b6,b7;
    SearchView searchView;

    Spinner spinner;
    NewsHeadlines headlines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        searchView = findViewById(R.id.search_view);
        spinner = findViewById(R.id.country_set_spinner);
        spinner.setSelection(49, true);
        View v = spinner.getSelectedView();
        ((TextView)v).setTextColor(getResources().getColor(R.color.black));


        getSupportActionBar().setTitle("News");
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_background));

        String countryName = spinner.getSelectedItem().toString();

        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadlines(listener, "general",countryName,null);




        dialog = new ProgressDialog(this);
        dialog.setTitle("Fetching News Articles");
        dialog.setCancelable(false);
        dialog.show();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                dialog.setTitle("Fetching News Articles "+ query);
                dialog.setCancelable(false);
                dialog.show();
                RequestManager manager = new RequestManager(MainActivity.this);
                manager.getNewsHeadlines(listener,"general",query,countryName);


                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        b1 = findViewById(R.id.btn_1);
        b1.setOnClickListener(this);
        b2 = findViewById(R.id.btn_2);
        b2.setOnClickListener(this);
        b3 = findViewById(R.id.btn_3);
        b3.setOnClickListener(this);
        b4 = findViewById(R.id.btn_4);
        b4.setOnClickListener(this);
        b5 = findViewById(R.id.btn_5);
        b5.setOnClickListener(this);
        b6 = findViewById(R.id.btn_6);
        b6.setOnClickListener(this);
        b7 = findViewById(R.id.btn_7);
        b7.setOnClickListener(this);

        // todo country selection

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) view).setTextColor(getResources().getColor(R.color.blue1));


                String countryName = spinner.getSelectedItem().toString();


                dialog = new ProgressDialog(MainActivity.this);
                dialog.setTitle("Fetching News Articles" + countryName);
                dialog.setCancelable(false);
                dialog.show();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        dialog.dismiss();
                    }}, 3000);



                RequestManager manager = new RequestManager(MainActivity.this);
                manager.getNewsHeadlines(listener,"general",countryName,null);



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });

    }
    private final OnFetchDataListener<NewsApiResponse> listener = new OnFetchDataListener<NewsApiResponse>() {
        @Override
        public void onFetchData(List<NewsHeadlines> list, String message) {

            if (list.isEmpty()){
                Toast.makeText(MainActivity.this, "No Data Found11", Toast.LENGTH_SHORT).show();

            }
            else {
                ShowNews(list);

            }
            dialog.dismiss();
        }

        @Override
        public void onError(String message) {

            Toast.makeText(MainActivity.this, "An Error occur", Toast.LENGTH_SHORT).show();
        }
    };

    private void ShowNews(List<NewsHeadlines> list) {
        recyclerView = findViewById(R.id.recycler_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        adapter = new CustomAdapter(this, list,this);
        recyclerView.setAdapter(adapter);
        ViewCompat.setNestedScrollingEnabled(recyclerView, false);
    }

    @Override
    public void OnNewsClicked(NewsHeadlines headlines) {
            Intent intent = new Intent(MainActivity.this, DetailsActivity.class)
            .putExtra("data",headlines);
            startActivity(intent);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onClick(View view) {
            Button button = (Button) view;
            String category = button.getText().toString();
            dialog.setTitle("Fetching News Article for " + category);
            dialog.setCancelable(false);
            dialog.show();
            RequestManager manager = new RequestManager(this);
            manager.getNewsHeadlines(listener, category, null, null);

            //button.setText(getResources().getColor(R.color.black));


    }






}