package com.example.novela;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class BookAdapter extends RecyclerView.Adapter<com.example.novela.BookAdapter.ViewHolder>{

    private ArrayList<String> mBooks;
    private String mGenre;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    // Create a storage reference from our app
    StorageReference storageRef;


    private Context mContext;


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView bookCover;

        public ViewHolder(View itemView) {
            super(itemView);
            bookCover = (ImageView) itemView.findViewById(R.id.bookButton);

        }

    }

    public BookAdapter(ArrayList<String> books, String genre, Context context) {
        mBooks = books;
        mContext = context;
        mGenre = genre;
        storageRef = FirebaseStorage.getInstance().getReference();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final com.example.novela.BookAdapter.ViewHolder holder, final int position) {
        // This method gets data based on the item selected from the recycler view
        Log.d(TAG, "onBindViewHolder: " + mBooks.get(position)+ " added.");
        String path = "Library/BookCover/" + mGenre + "/" + mBooks.get(position);


        StorageReference pathReference = storageRef.child(path);

        GlideApp.with(mContext /* context */)
                .load(pathReference)
                .into(holder.bookCover);



        holder.bookCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Book " + mBooks.get(position));
                Intent intent = new Intent(mContext, BookActivity.class);
                intent.putExtra("Book", mBooks.get(position));
                intent.putExtra("Genre", mGenre);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }

}
