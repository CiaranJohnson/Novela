package com.example.novela;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class BookActivity extends AppCompatActivity {

    private static final String TAG = "BookActivity";
    TextView bookName;
    ImageView bookCover;
    StorageReference storageRef;

    String book, genre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        storageRef = FirebaseStorage.getInstance().getReference();

//        bookName = (TextView) findViewById(R.id.bookTitle);
        bookCover = (ImageView) findViewById(R.id.bookCover);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Log.d(TAG, "Found extras");
            book = extras.getString("Book");
            genre = extras.getString("Genre");
            Log.d(TAG, "onCreate: " + book);
//            bookName.setText(String.valueOf(book));
        }

        String path = "Library/BookCover/" + genre + "/" + book;


        StorageReference pathReference = storageRef.child(path);

        GlideApp.with(this /* context */)
                .load(pathReference)
                .into(bookCover);
    }
}
