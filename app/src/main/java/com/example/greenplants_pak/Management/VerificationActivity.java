package com.example.greenplants_pak.Management;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.greenplants_pak.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

public class VerificationActivity extends AppCompatActivity {
Button btnVerify;
FirebaseAuth mAuth;
int counter = 0;
DatabaseReference UserDatabaseRef ;
Boolean isFirebaseDestroy = false;
    StorageReference UserStorageDatabase  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        UserDatabaseRef = FirebaseDatabase.getInstance().getReference("users") ;
        mAuth = FirebaseAuth.getInstance();

        btnVerify = findViewById(R.id.verify_button);

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignInAndVerify(getIntent().getStringExtra("email") , getIntent().getStringExtra("pass"));
            }
        });
    }
    private void SignInAndVerify(String email, String password) {
        counter = counter+1;
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if (checkEmailIsVerified()) {

                        isFirebaseDestroy = true ;

//                        uploadData( getIntent().getStringExtra("email") , getIntent().getStringExtra("pass") ,
//                                SignUpPage.D_Cnic.getText().toString() , SignUpPage.D_Contact.getText().toString()
//                                , SignUpPage.picUri , SignUpPage.D_UserName) ;

                        startActivity(new Intent(getApplicationContext(), Login.class));

                    } else {
                        Toast.makeText(VerificationActivity.this, "Error", Toast.LENGTH_LONG).show();
                        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        AuthCredential credential = EmailAuthProvider.getCredential(email, password);
                        firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(VerificationActivity.this, "Email not verified...", Toast.LENGTH_LONG).show();
                                            finish();
                                        }
                                    }
                                });
                            }
                        });
                    }
                }
            }
        });
    }

    public   String getExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver() ;
        MimeTypeMap mime = MimeTypeMap.getSingleton() ;
        return  mime.getExtensionFromMimeType(contentResolver.getType(uri));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(isFirebaseDestroy == false){
        Toast.makeText(VerificationActivity.this, "UserDestroy", Toast.LENGTH_LONG).show();
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider.getCredential(getIntent().getStringExtra("email") , getIntent().getStringExtra("pass"));
        firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(VerificationActivity.this, "Email not verified...", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                });
            }
        });
        }
    }

    private boolean checkEmailIsVerified() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user.isEmailVerified()){
            return true;
        }else {
            return false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
