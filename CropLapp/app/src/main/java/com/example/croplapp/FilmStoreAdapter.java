/*
* FilmImagesAdapter.java
* Author: Nguyen Duc Tien 16020175
* Purpose: Adapter 
* Include: Contructor, ViewHolder, onCreateViewHolder, setOnClickListener
*/
package com.example.croplapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FilmStoreAdapter extends RecyclerView.Adapter<FilmStoreAdapter.ViewHolder>{
    private List<FilmDetails> filmList;
    public Context context;
    boolean switched = false;

    //Constructor
    public FilmStoreAdapter(Context context, List<FilmDetails> filmList){
        this.context = context;
        this.filmList = filmList;
    }

    //Get id
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_store_item, parent,false);
        return new ViewHolder(view);
    }

    //Set value
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        final FilmDetails filmDetails = filmList.get(position);

        viewHolder.filmname.setText(filmDetails.getFilmname());
        viewHolder.filmprice.setText(filmDetails.getFilmprice());
        viewHolder.filmstatus.setText(filmDetails.getFilmstatus());
        Glide.with(context).load(filmDetails.getFilmimage()).placeholder(R.mipmap.loading).error(R.mipmap.file).into(viewHolder.filmimage);

        //When seclected, call FilmItem, kill current acticity
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, FilmItem.class);
                    Bundle bundle = new Bundle();

                    bundle.putString("name", filmDetails.getFilmname());
                    bundle.putString("price", filmDetails.getFilmprice());
                    bundle.putString("iso", filmDetails.getIso());
                    bundle.putString("shot", filmDetails.getShot());
                    bundle.putInt("nPic", filmDetails.getnPic());
                    bundle.putString("link", filmDetails.getLink());
                    intent.putExtras(bundle);

                    context.startActivity(intent);
                    ((Activity)context).finish();
                }
            });
        }
        @Override
        public int getItemCount() {
            return filmList.size();
        }

}


