package com.example.firebasesample;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private Button upload_Btn;
    private Uri imagsUri;
    private Bitmap Images;
    private ImageView imageView;
    private EditText filename;
    private TextView upload;
    private ProgressBar progressBar;
    private StorageReference storageReference;
    private DatabaseReference mDatabasereferance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn=findViewById(R.id.choice_image);
        imageView=findViewById(R.id.image_view);
        filename=findViewById(R.id.edittext);
        progressBar=findViewById(R.id.Progres_Bar);
        upload=findViewById(R.id.upload_textview);
        upload_Btn=findViewById(R.id.upload_button);
        storageReference= FirebaseStorage.getInstance().getReference("Uploads");
        mDatabasereferance= FirebaseDatabase.getInstance().getReference("uploads");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
//                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
//                }else{
//                    Intent intent=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI);
//                    startActivityForResult(intent,2);
//                }
                fileChooser();
            }
        });
    }
    public void fileChooser(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        System.out.println("Calisti");
        if (requestCode==1){

            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                System.out.println("Calisti");
                if (grantResults.length>0){
                    Intent intent=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    startActivityForResult(intent,2);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1){
            if (data!=null){
                if (resultCode==RESULT_OK){
                    imagsUri=data.getData();
                    Picasso.with(MainActivity.this).load(imagsUri).into(imageView);
                }
            }
        }
    }
    private String getFileextension(Uri uri){
        ContentResolver cR=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void UploadFile(){
        if (imagsUri!=null){
            StorageReference fileReferance=storageReference.child("Uploads/"+System.currentTimeMillis()+" "+getFileextension(imagsUri));
        }else{
            Toast.makeText(this, "No file selected", Toast.LENGTH_LONG).show();
        }
    }
}