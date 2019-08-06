package com.example.novela;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.ViewHolder>{

    private String[] mGenres;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView genre;

        public ViewHolder(View itemView) {
            super(itemView);
            genre = (TextView) itemView.findViewById(R.id.genreTxt);


            recyclerView = (RecyclerView) itemView.findViewById(R.id.bookRecyclerView);
            recyclerView.setHasFixedSize(true);



        }

    }

    public GenreAdapter(String[] genres) {
        mGenres = genres;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.genre_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);

        layoutManager = new LinearLayoutManager(parent.getContext(), RecyclerView.HORIZONTAL, false);

        recyclerView.setLayoutManager(layoutManager);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // This method gets data based on the item selected from the recycler view
        holder.genre.setText(mGenres[position]);
        Log.d(TAG, "onBindViewHolder: " + mGenres[position] + " added.");

        String[] books = {"A", "B", "C", "D", "E", "F"};
        mAdapter = new BookAdapter(books);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public int getItemCount() {
        return mGenres.length;
    }



}
