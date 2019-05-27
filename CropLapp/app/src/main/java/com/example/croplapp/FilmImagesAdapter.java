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

import com.squareup.picasso.Picasso;

import java.util.List;

public class FilmImagesAdapter extends PagerAdapter {
    private List<FilmDetails> models;
    private LayoutInflater layoutInflater;
    private Context context;

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

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.film_images_item, container, false);

        ImageView imageView;
        TextView iso, shot;
        int nPic = models.get(position).getnPic();

        iso = view.findViewById(R.id.film_item_iso);
        shot = view.findViewById(R.id.film_item_shot);
        imageView = view.findViewById(R.id.film_item_image);

        for (int i = 0; i < nPic; i++) {
            iso.setText(models.get(position).getIso());
            shot.setText(models.get(position).getShot());
            Picasso.get().load(models.get(position).getLink()).placeholder(R.mipmap.loading).error(R.mipmap.file).into(imageView);
        }

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
