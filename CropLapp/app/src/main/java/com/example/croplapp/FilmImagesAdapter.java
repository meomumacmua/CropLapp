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
        TextView iso, shot;

        View view;
        view = layoutInflater .inflate(R.layout.film_images_item, container, false);

        iso = view.findViewById(R.id.film_item_iso);
        shot = view.findViewById(R.id.film_item_shot);
        imageView = view.findViewById(R.id.film_item_image);

        iso.setText(models.get(position).getIso());
        shot.setText(models.get(position).getShot());
        int nPic = models.get(position).getnPic();
//        Log.d("print", "link " + models.get(position).getLink());

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
