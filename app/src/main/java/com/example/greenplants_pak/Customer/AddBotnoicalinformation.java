package com.example.greenplants_pak.Customer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.greenplants_pak.Model.Botonical_infomation;
import com.example.greenplants_pak.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddBotnoicalinformation extends AppCompatActivity {

    EditText Subject ;
    EditText Description ;
    Button Submit;
    DatabaseReference databaseReference ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_botnoicalinformation);

        databaseReference = FirebaseDatabase.getInstance().getReference("botonical_info");

        Subject = findViewById(R.id.subject);
        Description = findViewById(R.id.complatin);

        Submit = findViewById(R.id.submitpost);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Subject.getText().toString().isEmpty()){
                    Subject.setError("Field is empty");
                    return;
                }
                if(Description.getText().toString().isEmpty()){
                    Description.setError("Field is empty");
                    return;
                }

                String Key = databaseReference.push().getKey();
                Botonical_infomation botonical_infomation
                         = new Botonical_infomation(Key , Subject.getText().toString() , Description.getText().toString());
                databaseReference.child(Key).setValue(botonical_infomation);
                Toast.makeText(AddBotnoicalinformation.this, "botonical information added", Toast.LENGTH_SHORT).show();
                Subject.setText("");
                Description.setText("");
            }
        });


    }
}