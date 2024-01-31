package com.example.gestiondesetudiants;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

public class Home_activity extends AppCompatActivity {

    private EditText editTextNews;
    private ListView listViewNews;
    private Button addButton;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        editTextNews = findViewById(R.id.editTextNews);
        listViewNews = findViewById(R.id.listViewNews);
        addButton = findViewById(R.id.addButton);


        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user.getEmail().toString().equals("admin@admin.ma")) {
            editTextNews.setVisibility(View.VISIBLE);
            addButton.setVisibility(View.VISIBLE);
        }


        // Récupérer les références des vues


        myRef = FirebaseDatabase.getInstance().getReference("Remarques");


        // Gérer le clic sur le bouton
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ajouter le texte saisi à la liste des actualités
                String newsText = editTextNews.getText().toString();
                UUID randomId = UUID.randomUUID();
                String idString = randomId.toString();



                myRef.child(idString).setValue(newsText).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        if (task.isSuccessful()) {
                            envoyerNotification(newsText);
                            Toast.makeText(Home_activity.this, "successful", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Home_activity.this, "unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                        ;
                    }
                });

            }
        });


        final ArrayList<String> newsList = new ArrayList<>();
        final ArrayAdapter<String> newsAdapter = new ArrayAdapter<String>(this, R.layout.list_ithem, newsList);
        listViewNews.setAdapter(newsAdapter);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                newsList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    newsList.add(data.getValue().toString());
                }
                newsAdapter.notifyDataSetChanged();

            }


            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(Home_activity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void envoyerNotification(String message) {
        // Obtenez le FCM token de l'utilisateur depuis votre backend ou depuis FirebaseInstanceId si vous l'avez stocké localement

        // Créez un objet de notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id")
                .setSmallIcon(R.drawable.icon_notes)
                .setContentTitle("Nouveau Message")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Envoyez la notification en utilisant le NotificationManager
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WAKE_LOCK) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(1, builder.build());
    }
}


