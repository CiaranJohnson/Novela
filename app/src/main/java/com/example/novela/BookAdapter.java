package com.example.novela;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class BookAdapter extends RecyclerView.Adapter<com.example.novela.BookAdapter.ViewHolder>{

    private String[] mBooks;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageButton book;

        public ViewHolder(View itemView) {
            super(itemView);
            book = (ImageButton) itemView.findViewById(R.id.bookButton);

        }

    }

    public BookAdapter(String[] books) {
        mBooks = books;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.novela.BookAdapter.ViewHolder holder, int position) {
        // This method gets data based on the item selected from the recycler view
        Log.d(TAG, "onBindViewHolder: " + mBooks[position] + " added.");

    }

    @Override
    public int getItemCount() {
        return mBooks.length;
    }

}
