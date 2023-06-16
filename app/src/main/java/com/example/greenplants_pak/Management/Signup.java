package com.example.greenplants_pak.Management;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.greenplants_pak.Model.UserModelClass;
import com.example.greenplants_pak.R;
import com.example.greenplants_pak.MapPackage.SetLocation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;

public class Signup extends AppCompatActivity {

    ProgressDialog pd ;
    EditText Location ;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    TextView Register,TextLogin;
    static EditText D_UserName , D_Password , D_Cnic  , D_Contact  , Emails;
    Spinner UserType ;

    static Uri picUri ;
    public static final int PICK_IMAGE_REQUEST = 0 ;
    CircularImageView ChooseImage ;
    private String Lattitude = "";
    private String Longitude = "";
    DatabaseReference databaseReference ;
    public static final String TAG = "ErrorCheck" ;
    StorageReference storageReference ;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);
        TextLogin=findViewById(R.id.txtlogin);
        init_method();
        set_Listenner();
        storageReference = FirebaseStorage.getInstance().getReference("Uploads");
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        TextLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Signup.this,Login.class);
                startActivity(intent);
            }
        });



    }




    private void init_method() {
        ChooseImage = findViewById(R.id.simchooseimg) ;
        Location = findViewById(R.id.location) ;
        D_UserName = findViewById(R.id.name);
        D_Password = findViewById(R.id.pass);

        UserType = findViewById(R.id.usertype);
        D_Cnic = findViewById(R.id.cnic);
        Emails = findViewById(R.id.email);
        D_Contact = findViewById(R.id.phone);
        Register = findViewById(R.id.reg) ;

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                } else {
                    Toast.makeText(Signup.this, "user sign out", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }



    private void createAccount(final String email, final String password) {

        mAuth = FirebaseAuth.getInstance();


        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String UserName = D_UserName.getText().toString();
                    String Password = D_Password.getText().toString();
                    String  Cnic = D_Cnic.getText().toString();
                    String Address = Emails.getText().toString();
                    String Contact = D_Contact.getText().toString();

//                    sendVerification(email , password);



                        uploadData( mAuth.getUid() , UserName ,Address , Password , Cnic  , picUri.toString() , Contact ,  UserType.getSelectedItem().toString()
                        ) ;










                    pd.dismiss();
                } else {
                    Toast.makeText(Signup.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    pd.dismiss();
                }

            }
        });
    }
    private void uploadData(final String uid, final String userName, final String address, final String password, final String cnic, final String picUri1, final String contact, final String toString) {

        try {


            storageReference.child(System.currentTimeMillis() + "." + getExtension(picUri)).putFile(picUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Handler handler = new Handler() ;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                        }
                    },500);
                    Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                    task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            try {
                                UserModelClass userModelClass = new UserModelClass(
                                        uid, userName, address, password, cnic, uri.toString(), contact, toString
                                        , Lattitude , Longitude  , "true"  , "false" , "0"     );
                                databaseReference.child(uid).setValue(userModelClass);
                                Toast.makeText(Signup.this, "Created Successfull sent for Approve", Toast.LENGTH_SHORT).show();

                                D_UserName.setText("");
                                D_Password.setText("");
                                D_Cnic.setText("");
                                D_Contact.setText("");
                                Emails.setText("");

                                D_UserName.setText("");

                                ImageView im = findViewById(R.id.simchooseimg) ;

                                im.setImageResource(R.drawable.camera2);
                            }catch (Exception ex){
                                Log.d(TAG , ex.toString()) ;
                            }
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(Signup.this, ""+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());

                }
            });

        } catch (Exception e) {
            Log.d(e.toString(), "Plz Select Pic");
            Toast.makeText(getApplicationContext() ,"Plz Select Pic " + e.toString() , Toast.LENGTH_LONG).show();

        }








    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if(requestCode == PICK_IMAGE_REQUEST){
                picUri = data.getData();

            }

            if (null != picUri) {
                ImageView im = findViewById(R.id.simchooseimg) ;
                String path = getPathFromURI(picUri);
                im.setImageURI(picUri);
            }
            if (requestCode == 2)
            {
                Lattitude = data.getStringExtra("Lattitude");
                Longitude = data.getStringExtra("Longitude");
                Toast.makeText(this, data.getStringExtra("Longitude")+"", Toast.LENGTH_SHORT).show();
            }
        }


    }


    private void ChooseImageFromeGallery() {
        Intent intent = new Intent() ;
        intent.setAction(Intent.ACTION_GET_CONTENT) ;
        intent.setType("image/*") ;
        startActivityForResult(intent  , PICK_IMAGE_REQUEST);

    }


    public   String getExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver() ;
        MimeTypeMap mime = MimeTypeMap.getSingleton() ;
        return  mime.getExtensionFromMimeType(contentResolver.getType(uri));

    }

    private String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    private void set_Listenner() {
        ChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseImageFromeGallery() ;
            }
        });

        Location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signup.this, SetLocation.class);
                startActivityForResult(intent,2);

            }
        });
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(picUri == null){
                    Toast.makeText(Signup.this, "Select Picture ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Lattitude.isEmpty()){
                    Toast.makeText(Signup.this, "Select Lat", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(Longitude.isEmpty()){
                    Toast.makeText(Signup.this, "Select Long", Toast.LENGTH_SHORT).show();
                    return;
                }


                if(Emails.getText().toString().isEmpty()){
                    Emails.setError("Field is Empty");
                    return ;
                }


                if(D_Cnic.getText().toString().isEmpty()){
                    D_Cnic.setError("Field is Empty");
                    return;
                }
                if(D_Password.getText().toString().isEmpty()){
                    D_Password.setError("Field is Empty");
                    return;
                }

                if(D_Contact.getText().toString().isEmpty()){
                    D_Contact.setError("Field is Empty");
                    return;

                }

                if(D_Contact.getText().toString().length() !=11){
                    D_Contact.setError("Phone number must be 11 digit");
                    return;
                }

                if(D_Cnic.getText().toString().length() !=13){
                    D_Cnic.setError("Phone number must be 11 digit");
                    return;
                }







                mAuth = FirebaseAuth.getInstance();

                pd = new ProgressDialog(Signup.this);
                pd.setMessage("loading");
                pd.show();

                createAccount(Emails.getText().toString() , D_Password.getText().toString());

            }
        });
    }
    @SuppressLint("ClickableViewAccessibility")
    private void sendVerification(final String email , final String password) {
        final FirebaseUser firebaseUser = mAuth.getCurrentUser();
        firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Signup.this, "Verification email sent to " + firebaseUser.getEmail(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext() , VerificationActivity.class);
                    intent.putExtra("email",email);
                    intent.putExtra("pass",password);
                    startActivity(intent);
                    mAuth.signOut();
                } else {
                    Toast.makeText(Signup.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    databaseReference.child(mAuth.getUid()).removeValue();
                    return;
                }
            }
        });




    }






}