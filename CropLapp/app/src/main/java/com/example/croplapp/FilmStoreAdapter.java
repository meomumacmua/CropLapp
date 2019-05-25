package com.example.croplapp;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class FilmStoreAdapter extends RecyclerView.Adapter<FilmStoreAdapter.ViewHolder>{
    private List<FilmDetails> filmList;
    private Activity activity;
    public FilmStoreAdapter(Activity activity, List<FilmDetails> filmList){
        this.activity = activity;
        this.filmList = filmList;
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        TextView filmprice, filmname,filmstatus;
        ImageView filmimage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            filmname = (TextView) itemView.findViewById(R.id.film_name);
            filmprice = (TextView) itemView.findViewById(R.id.film_price);
            filmstatus = (TextView) itemView.findViewById(R.id.film_status);
            filmimage = (ImageView) itemView.findViewById(R.id.film_image);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_store_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final FilmDetails filmDetails = filmList.get(position);

        viewHolder.filmname.setText(filmDetails.getFilmname());
        viewHolder.filmprice.setText(filmDetails.getFilmprice());
        viewHolder.filmstatus.setText(filmDetails.getFilmstatus());
        Picasso.get().load(filmDetails.getFilmimage()).placeholder(R.mipmap.loading).error(R.mipmap.file).into(viewHolder.filmimage);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(activity, "Coming soon!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(activity, FilmItem.class);
                    activity.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return filmList.size();
        }
    }


