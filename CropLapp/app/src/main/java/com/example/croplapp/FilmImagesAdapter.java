/*
* FilmImagesAdapter.java
* Author: Nguyen Duc Tien 16020175
* Purpose: Film object
* Include: Contructor, instantiateItem, destroyItem
*/
package com.example.croplapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FilmImagesAdapter extends PagerAdapter {
    public List<FilmDetails> models;
    public LayoutInflater layoutInflater;
    public Context context;

    //Constructor
    public FilmImagesAdapter(List<FilmDetails> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    //Set adapter
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        ImageView imageView;
        TextView name, price, iso, shot;

        View view;
        view = layoutInflater .inflate(R.layout.film_images_item, container, false);

        name = view.findViewById(R.id.film_item_name);
        price = view.findViewById(R.id.film_item_price);
        iso = view.findViewById(R.id.film_item_iso);
        shot = view.findViewById(R.id.film_item_shot);
        imageView = view.findViewById(R.id.film_item_image);

        name.setText(models.get(position).getFilmname());
        price.setText(models.get(position).getFilmprice());
        iso.setText(models.get(position).getIso());
        shot.setText(models.get(position).getShot());

        Glide.with(context).load(models.get(position).getLink()).placeholder(R.mipmap.loading).error(R.mipmap.file).into(imageView);
        //When seclected
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, DetailActivity.class);
//                intent.putExtra("param", models.get(position).getTitle());
//                context.startActivity(intent);
                // finish();
                Log.d("print", "click");

            }
        });
        container.addView(view, 0);
        return view;
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
