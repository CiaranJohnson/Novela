package com.example.novela;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignInActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private static final String TAG = "SignInActivity";


    Button signInBtn, backBtn;
    MaterialEditText metEmail, metPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Log.d(TAG, "onCreate: Sign In Activity");

        signInBtn = (Button) findViewById(R.id.signInButton);
        backBtn = (Button) findViewById(R.id.backButton);

        metEmail = (MaterialEditText) findViewById(R.id.editEmail);
        metPassword = (MaterialEditText) findViewById(R.id.editPassword);

        mAuth = FirebaseAuth.getInstance();

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signInWithEmailAndPassword(metEmail.getText().toString(), metPassword.getText().toString()).addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "Sign In: successful");
                            Toast.makeText(SignInActivity.this, "Authentication Successful", Toast.LENGTH_LONG).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else{
                            Log.d(TAG, "Sign In: unsuccessful"+task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication failed: "+ task.getException(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    public void updateUI(FirebaseUser currentUser){
        if(currentUser != null){
            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
