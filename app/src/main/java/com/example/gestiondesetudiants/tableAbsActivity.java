package com.example.gestiondesetudiants;


import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class tableAbsActivity extends AppCompatActivity {
    ArrayList<Note> mNoteList = new ArrayList<>();

    FloatingActionButton add_new_note;
    AlertDialog.Builder  alertbuilder ;
    AlertDialog alertDialog;
    View view;
    FirebaseDatabase database;
    DatabaseReference mRef ;
    Button btnSaveNote;
    EditText title_edit_text ;
    EditText note_edit_text ;
    String title;
    String note;
    Note myNote; String id;
    ListView note_list_view;
    TextView titileTextView;


    private DatabaseReference databaseReference;
    private ArrayList<String> Datelist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_abs);
        note_list_view=findViewById(R.id.note_list_view);
        database =FirebaseDatabase.getInstance();






        // Initialiser la référence à la base de données
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Liste d'absence");

        // Référence à votre ListView dans le layout
        note_list_view = findViewById(R.id.note_list_view);

        // Créer un adaptateur pour lier la liste des enfants au ListView
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Datelist);
        note_list_view.setAdapter(adapter);

        // Écouter les modifications dans la base de données
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                // Ajouter les données de l'enfant à la liste
                String date = dataSnapshot.getKey();
                Datelist.add(date);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                // Réagir à une modification d'un enfant existant
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                // Réagir à la suppression d'un enfant
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                // Réagir au déplacement d'un enfant
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Gérer les erreurs ici
            }
        });





        note_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle item click, get the selected date
                String selectedDate = Datelist.get(position);

                // Call a method to retrieve and display the list of absent students for the selected date
                displayAbsentStudents(selectedDate);
            }
        });

        // ChildEventListener remains the same...
    }

    private void displayAbsentStudents(String selectedDate) {
        DatabaseReference dateReference = databaseReference.child(selectedDate);

        dateReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> absentStudentsList = new ArrayList<>();

                for (DataSnapshot studentSnapshot : dataSnapshot.getChildren()) {
                    String studentName = studentSnapshot.getKey();
                    absentStudentsList.add(studentName);
                }

                // Display the list of absent students, for example, in an AlertDialog
                showAbsentStudentsDialog(absentStudentsList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });
    }

    private void showAbsentStudentsDialog(ArrayList<String> absentStudentsList) {
        // Create a StringBuilder to build the message for the dialog
        StringBuilder message = new StringBuilder("Absent Students:\n");

        // Append each absent student's name to the message
        for (String student : absentStudentsList) {
            message.append("- ").append(student).append("\n");
        }

        // Create an AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Absent Students")
                .setMessage(message.toString())
                .setPositiveButton("OK", null);

        // Show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}


