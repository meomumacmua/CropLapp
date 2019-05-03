package com.example.croplapp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static android.app.ProgressDialog.show;


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
//            viewHolder.filmname.setText(filmdetails.get(position).getFilmname());
//            viewHolder.filmprice.setText(filmdetails.get(position).getFilmprice());

//            viewHolder.filmimage.setImageBitmap(filmdetails.get(position).getFilmimage());
//            Picasso.with(context).load(filmdetails.get(position).getFilmimage()).into(viewHolder.filmimage);
//            viewHolder.filmstatus.setText(filmdetails.get(position).getFilmstatus());

//            Log.e("print", filmdetails.get(position).getFilmname());
//            Log.e("print", filmdetails.get(position).getFilmprice());
//            Log.e("print", filmdetails.get(position).getFilmimage());
//            Log.e("print", filmdetails.get(position).getFilmstatus());
            final FilmDetails filmDetails = filmList.get(position);

            viewHolder.filmname.setText(filmDetails.getFilmname());
            viewHolder.filmprice.setText(filmDetails.getFilmprice());
            viewHolder.filmstatus.setText(filmDetails.getFilmstatus());
            Picasso.get().load(filmDetails.getFilmimage()).into(viewHolder.filmimage);

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(activity, filmDetails.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return filmList.size();
        }
    }


