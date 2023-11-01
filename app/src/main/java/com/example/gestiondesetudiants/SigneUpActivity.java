package com.example.gestiondesetudiants;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SigneUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signe_up);
        Button inscrire =findViewById(R.id.inscrire);
        EditText edt_nom_user =findViewById(R.id.edt_nom_user);
        EditText edt_email =findViewById(R.id.edt_email);
        EditText edt_password =findViewById(R.id.edt_password);
        // ...
        FirebaseAuth mAuth;
// Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        ProgressBar progressBar =findViewById(R.id.progressBar);
        inscrire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth mAuth;
                String email = edt_email.getText().toString();
                String password = edt_password.getText().toString();
                String name = edt_nom_user.getText().toString();
//                if (!email.isEmpty() && !password.isEmpty()){
//                    progressBar.setVisibility(View.VISIBLE);
                //                progressBar.setVisibility(View.VISIBLE);

//                String email, password,name;
//                email = editTextEmail.getText().toString();
//                password = editTextPassword.getText().toString();
//                name = editTextFullname.getText().toString();
//                number = editTextNumber.getText().toString();
                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    edt_nom_user.setError("Please enter your Full Name");
                    edt_email.setError("Please enter your Email");
                    edt_password.setError("Please enter your Password");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    edt_email.setError("Invalid Email");
                } else if (name.length() > 20) {
                    edt_email.setError("Invalid Name");
                } else if (password.length() < 8) {
                    edt_password.setError("Invalid Password");
                } else {
                    mAuth = FirebaseAuth.getInstance();

                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SigneUpActivity.this, "createUserWithEmail:success.",
                                                Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);

                                        Intent intent = new Intent(SigneUpActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        //  Sign in success, update UI with the signed-in user's information
                                        //FirebaseUser user = mAuth.getCurrentUser();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(SigneUpActivity.this, task.getException().toString(),
                                                Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                }
//                else{
//                    Toast.makeText(SigneUpActivity.this, "SVP Entrer email et le mot de passe",
//                            Toast.LENGTH_LONG).show();
//
//                }


            }

        });
    }

}
