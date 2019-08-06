package com.example.novela;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.ViewHolder>{


    public GenreAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // This method gets data based on the item selected from the recycler view
    }

    @Override
    public int getItemCount() {
        return 0;
    }


        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView genre;

            public ViewHolder(View itemView) {
                super(itemView);
                genre = (TextView) itemView.findViewById(R.id.genreTxt);
            }

        }
}
