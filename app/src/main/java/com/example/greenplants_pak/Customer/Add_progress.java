package com.example.greenplants_pak.Customer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.greenplants_pak.Management.Signup;
import com.example.greenplants_pak.Model.Complain;
import com.example.greenplants_pak.Model.Progress;
import com.example.greenplants_pak.Model.UserModelClass;
import com.example.greenplants_pak.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Add_progress extends AppCompatActivity {
    FirebaseAuth getmAuth ;
    EditText Subject ;
    EditText Description ;
    Button Submit;
    Uri picUri;
    Button Select;
    DatabaseReference databaseReference ;
    FirebaseAuth mAuth  ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_progress);
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("add_progress");


        Subject = findViewById(R.id.subject);
        Description = findViewById(R.id.complatin);


        Submit = findViewById(R.id.submitpost);
        Select = findViewById(R.id.select);




        Select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ChooseImageFromeGallery();
            }
        });



        getmAuth = FirebaseAuth.getInstance();

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

                if(picUri == null){
                    Toast.makeText(Add_progress.this, "select an image", Toast.LENGTH_SHORT).show();
                 return;
                }


                StorageReference storageReference = FirebaseStorage.getInstance().getReference("progrss");






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

                                    String Key  = databaseReference.push().getKey();
                                    Progress progress = new Progress(getmAuth.getUid() , uri.toString() ,
                                            Subject.getText().toString() , Description.getText()
                                    .toString() ,Key );
                                    databaseReference.child(Key).setValue(progress);

                                }catch (Exception ex){

                                }
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(Add_progress.this, ""+e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());

                        ProgressBar progressBar = findViewById(R.id.prog);
                        progressBar.setProgress((int) progress);
                    }
                });

            }
        });



    }

    private void ChooseImageFromeGallery() {
        Intent intent = new Intent() ;
        intent.setAction(Intent.ACTION_GET_CONTENT) ;
        intent.setType("image/*") ;
        startActivityForResult(intent  , 0);

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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if(requestCode == 0){
                picUri = data.getData();

            }

            if (null != picUri) {
                ImageView im = findViewById(R.id.select_image) ;
                im.setImageURI(picUri);
            }
        }
    }
}