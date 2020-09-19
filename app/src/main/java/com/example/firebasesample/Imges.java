package com.example.firebasesample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.encoders.ObjectEncoder;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Imges extends AppCompatActivity implements ItemAdapter.OnItemClickListener {
    private List<Uploads> uploads;
    private ItemAdapter adapter;
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference ;
    private FirebaseStorage storage;
    private ValueEventListener mDBlistener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imges);
        uploads=new ArrayList<>();

        adapter=new ItemAdapter(Imges.this,uploads);

        adapter.setOnItemClickListener(Imges.this);
        storage=FirebaseStorage.getInstance();
        recyclerView=findViewById(R.id.recyler_virw);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference= FirebaseDatabase.getInstance().getReference("uploads");
        mDBlistener=databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                uploads.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
//                    System.out.println();
                    HashMap<String,String> userData=(HashMap<String,String>)dataSnapshot.getValue();
                    Uploads uploads1= new Uploads(userData.get("name"),userData.get("url"));
                    uploads1.setKey(dataSnapshot.getKey());

                    uploads.add(uploads1);
                }
                adapter.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Imges.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, "Item clıck "+ position, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onWhatEverClick(int position) {

    }

    @Override
    public void onDelereClick(final int position) {
        Uploads selectesItem=uploads.get(position);
        final String key=selectesItem.getKey();
        StorageReference imageReferance=storage.getReferenceFromUrl(selectesItem.getImaUrl());
        imageReferance.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                databaseReference.child(key).removeValue();
                Toast.makeText(Imges.this, "Deleted success", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Imges.this, "error happened during deleted object", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        databaseReference.removeEventListener(mDBlistener);
    }
}