package com.example.greenplants_pak;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddReminder  extends AppCompatActivity {
    EditText Name , SDAte , Dose , DoesUnit, RDate , Note ;
    Button Save ;
    DatabaseReference databaseReference ;
    FirebaseAuth mAuth ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.add_reminder);

       mAuth = FirebaseAuth.getInstance();
       databaseReference= FirebaseDatabase.getInstance().getReference("Add_Remider").child(mAuth.getUid());
       Name = findViewById(R.id.name);
        SDAte = findViewById(R.id.sdate);
        Dose = findViewById(R.id.dosage);
        DoesUnit = findViewById(R.id.unit);
        RDate = findViewById(R.id.rtime);
        Note = findViewById(R.id.note);
        Save = findViewById(R.id.save);
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Name.getText().toString().isEmpty()) {
                    Name.setError("Field is empty");
                    return;
                }
                if (SDAte.getText().toString().isEmpty()) {
                    SDAte.setError("Field is empty");
                    return;
                }
                if (Dose.getText().toString().isEmpty()) {
                    Dose.setError("Field is empty");
                    return;
                }

                if (RDate.getText().toString().isEmpty()) {
                    RDate.setError("Field is empty");
                    return;
                }

                if (Note.getText().toString().isEmpty()) {
                    Note.setError("Field is empty");
                    return;
                }

                if (DoesUnit.getText().toString().isEmpty()) {
                    DoesUnit.setError("Field is empty");
                    return;
                }
                String Key = databaseReference.push().getKey();
                 AddremClass addItem = new
                         AddremClass(Name.getText().toString() ,
                        SDAte.getText().toString() , Dose.getText().toString() , RDate.getText().toString() ,
                        Note.getText().toString() , DoesUnit.getText().toString() , Key);
                databaseReference.child(Key).setValue(addItem);
                Toast.makeText( AddReminder.this, "reminder added", Toast.LENGTH_SHORT).show();

                Name.setText("");
                SDAte.setText("");
                Dose.setText("");
                RDate.setText("");
                DoesUnit.setText("");
                Note.setText("");

            }



        });
    }
}


