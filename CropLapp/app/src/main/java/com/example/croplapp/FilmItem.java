/*
* FilmItem.java
* Author: Nguyen Duc Tien 16020175
* Purpose: Film extend activity
* Include: Contructor, instantiateItem, destroyItem
*/
package com.example.croplapp;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class FilmItem extends AppCompatActivity {

    ViewPager viewPager;
    FilmImagesAdapter adapter;
    List<FilmDetails> models;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_item);

        //Get data from FilmStoreAdapter
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String name = bundle.getString("name");
        String price = bundle.getString("price");
        String iso = bundle.getString("iso");
        String shot = bundle.getString("shot");
        int nPic = bundle.getInt("nPic");
        String link = bundle.getString("link");
        String[] split = link.split(";");

        // Add item
        models = new ArrayList<>();
        models.clear();
        for (int i = 0; i < nPic; i++) {
            models.add(new FilmDetails(name, price,iso, shot,nPic,split[i]));
        }

        adapter = new FilmImagesAdapter(models, this);

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(0, 0, 0, 0);

        // Set Background color
        Integer[] colors_temp = {
                getResources().getColor(R.color.trans),
                getResources().getColor(R.color.trans),
                getResources().getColor(R.color.trans),
                getResources().getColor(R.color.trans)
        };

        colors = colors_temp;

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

//                if (position < (adapter.getCount() -1) && position < (colors.length - 1)) {
//                    viewPager.setBackgroundColor(
//
//                            (Integer) argbEvaluator.evaluate(
//                                    positionOffset,
//                                    colors[position],
//                                    colors[position + 1]
//                            )
//                    );
//                }
//
//                else {
//                    viewPager.setBackgroundColor(colors[colors.length - 1]);
//                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    // Call again FilmstoreActivity, kill current activity
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(FilmItem.this, FilmStoreActivity.class);
        startActivity(intent);
        finish();
    }
}
