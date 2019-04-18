package com.example.croplapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.list_store);
//
//
//
//        RecyclerView recyclerView = findViewById(R.id.rv_listfilm);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        ListfilmAdapter listfilmAdapter = new ListfilmAdapter();
//        recyclerView.setAdapter(listfilmAdapter);
//
//
//        for (int i=0 ; i < 10000; i++){
//            Film film = new Film();
//            film.filmname= " Kodak Colorplus";
//            film.filmprice= " 75.000";
//            film.filmstatus = "Còn hàng";
//            listfilm.add(film);
//        }
//
//    }



    class FilmStoreAdapter extends RecyclerView.Adapter<FilmStoreAdapter.ViewHolder>{

        Context context;
        ArrayList <FilmDetails> filmdetails;

        public FilmStoreAdapter(Context c, ArrayList<FilmDetails> fd){

            context = c;
            filmdetails = fd;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_store_item,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
            viewHolder.filmname.setText(filmdetails.get(position).getFilmname());
            viewHolder.filmprice.setText(filmdetails.get(position).getFilmprice());

//            viewHolder.filmimage.setImageBitmap(filmdetails.get(position).getFilmimage());
            Picasso.with(context).load(filmdetails.get(position).getFilmimage()).into(viewHolder.filmimage);
            viewHolder.filmstatus.setText(filmdetails.get(position).getFilmstatus());
        }

        @Override
        public int getItemCount() {

            return filmdetails.size();
        }

        class ViewHolder extends  RecyclerView.ViewHolder{

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
    }


