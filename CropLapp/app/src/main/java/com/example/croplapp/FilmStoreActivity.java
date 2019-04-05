package com.example.croplapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

public class FilmStoreActivity extends AppCompatActivity {

        ArrayList <Film> listfilm = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_store);



        RecyclerView recyclerView = findViewById(R.id.rv_listfilm);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        ListfilmAdapter listfilmAdapter = new ListfilmAdapter();
        recyclerView.setAdapter(listfilmAdapter);


        for (int i=0 ; i < 10000; i++){
            Film film = new Film();
            film.filmname= " Kodak Colorplus";
            film.filmprice= " 75.000";
            film.filmstatus = "Còn hàng";
            listfilm.add(film);
        }

    }

    class ListfilmAdapter extends RecyclerView.Adapter<ListfilmAdapter.ViewHolder>{



        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.list_store_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        }

        @Override
        public int getItemCount() {
            return listfilm.size();
        }

        class ViewHolder extends  RecyclerView.ViewHolder{

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }
    }


}
