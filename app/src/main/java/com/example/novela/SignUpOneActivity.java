package com.example.novela;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpOneActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText nameEdt, emailEdt, passwordEdt, confirmPasswordEdt;
    Button nextBtn, backBtn;

    private static final String TAG = SignUpOneActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_one);

        mAuth = FirebaseAuth.getInstance();

        nameEdt = findViewById(R.id.nameEdt);
        emailEdt = findViewById(R.id.emailEdt);
        passwordEdt = findViewById(R.id.passwordEdt);
        confirmPasswordEdt = findViewById(R.id.confirmPasswordEdt);

        backBtn = findViewById(R.id.backBtn);
        nextBtn = findViewById(R.id.nextBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpOneActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // Change once I have figured out the information for SignUpTwoActivity.class
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEdt.getText().toString();
                String password = passwordEdt.getText().toString();
                String confPassword = confirmPasswordEdt.getText().toString();
                Log.d(TAG, "onClick: Next Button with email: " + email +
                        " Password: " + password);
                if(!password.equals(confPassword)) {
                    Toast.makeText(SignUpOneActivity.this, "Your password's dont match.", Toast.LENGTH_LONG).show();
                } else if(email.equals("")) {
                    Toast.makeText(SignUpOneActivity.this, "Your Email is empty", Toast.LENGTH_LONG).show();
                } else if(password.equals("")) {
                    Toast.makeText(SignUpOneActivity.this, "Your Password is empty", Toast.LENGTH_LONG).show();
                } else {
                    createAccount(email, password);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    protected void updateUI(FirebaseUser currentUser){
        if(currentUser != null){
            Intent intent = new Intent(SignUpOneActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void createAccount(String email, String password){
        Log.d(TAG, "createAccount: " + email + password);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpOneActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }
}
