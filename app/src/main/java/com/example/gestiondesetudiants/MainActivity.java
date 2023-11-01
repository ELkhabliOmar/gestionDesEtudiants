package com.example.gestiondesetudiants;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    com.google.android.material.button.MaterialButton button;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button demarer=findViewById(R.id.demarer);

        demarer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
            });

        FirebaseAuth mAuth;
// Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseAuth mAuth;
//// Initialize Firebase Auth
//        mAuth = FirebaseAuth.getInstance();
//        if(mAuth.getCurrentUser() != null){
//            Intent intentToLogin =new Intent(MainActivity.this, LoginActivity.class);
//            startActivity(intentToLogin);
//
//    }
//}
//public void logout(View view){
//    FirebaseAuth.getInstance().signOut();
//    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//    startActivity(intent);
//    finish();}


}