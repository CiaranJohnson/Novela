package com.example.novela;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FirebaseFirestore mFirestore;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private ImageButton profileBtn, notificationBtn;

    private ArrayList<String> genres;
    private HashMap<String, ArrayList<String>> genreAndBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirestore = FirebaseFirestore.getInstance();

        profileBtn = (ImageButton) findViewById(R.id.profileBtn);
        notificationBtn = (ImageButton) findViewById(R.id.notificationBtn);

        recyclerView = (RecyclerView) findViewById(R.id.genreRecyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        genres = new ArrayList<>();
        genreAndBooks = new HashMap<>();
        getGenres();


        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        notificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getGenres(){
        mFirestore.collection("Library").document("genre").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    Map <String, Object> map = task.getResult().getData();
                    for(Object obj: map.values()){
                        genres.add(obj.toString());
                    }
                    Log.d(TAG, "onComplete: " + map.toString());
                    Log.d(TAG, "onComplete: List of added genres: " + genres.toString());

                    getBooks();
//                    createAdapter();

                }

            }
        });
    }

    private void getBooks(){
        int count = 0;
        for (final String genre: genres) {
            Log.d(TAG, "getBooks: " + genre);
            count++;

            final ArrayList<String> books = new ArrayList<>();
            mFirestore.collection("Library").document("genre").collection(genre).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                            Log.d(TAG, "onComplete: " + genre);
                            Map<String, Object> bookDetails = documentSnapshot.getData();
                            books.add(bookDetails.get("cover_pic").toString());
                            Log.d(TAG, "onComplete: " + bookDetails.get("cover_pic").toString());
                        }
                        genreAndBooks.put(genre, books);

                        Log.d(TAG, "getBooks: " + genreAndBooks.size());
                        if(genreAndBooks.size() == genres.size()){
                            Log.d(TAG, "getBooks: " + genreAndBooks.size());
                            createAdapter();
                        }
                    }
                }
            });

        }
    }

    private void createAdapter(){
        Log.d(TAG, "createAdapter: " + genres.toString());
        Log.d(TAG, "createAdapter: " + genreAndBooks.toString());
        mAdapter = new GenreAdapter(genres, genreAndBooks, this);
        recyclerView.setAdapter(mAdapter);
    }


}
