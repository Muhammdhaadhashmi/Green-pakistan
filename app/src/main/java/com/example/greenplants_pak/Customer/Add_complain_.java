package com.example.greenplants_pak.Customer;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.greenplants_pak.Model.Botonical_infomation;
import com.example.greenplants_pak.Model.Complain;
import com.example.greenplants_pak.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Add_complain_ extends AppCompatActivity {

    EditText Subject ;
    EditText Description ;
    Button Submit;
    DatabaseReference databaseReference ;
    FirebaseAuth mAuth  ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_botnoicalinformation_complain);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("add_complain");

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

                String C_key = databaseReference.push().getKey();
                Complain complain = new Complain(C_key , getIntent().getStringExtra("sid") , getIntent().getStringExtra("pid") ,
                mAuth.getUid() ,Subject.getText().toString() , Description.getText().toString() );
                databaseReference.child(C_key).setValue(complain);

                Toast.makeText(Add_complain_.this, "complain added", Toast.LENGTH_SHORT).show();
                Subject.setText("");
                Description.setText("");

            }
        });


    }
}