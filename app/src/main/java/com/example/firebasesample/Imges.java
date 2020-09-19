package com.example.firebasesample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.encoders.ObjectEncoder;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imges);
        uploads=new ArrayList<>();
        recyclerView=findViewById(R.id.recyler_virw);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference= FirebaseDatabase.getInstance().getReference("uploads");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
//                    System.out.println();
                    HashMap<String,String> userData=(HashMap<String,String>)dataSnapshot.getValue();
                    Uploads uploads1= new Uploads(userData.get("name"),userData.get("url"));
                    uploads.add(uploads1);
                }
                adapter=new ItemAdapter(Imges.this,uploads);
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(Imges.this);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Imges.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, "Item clıck "+ position, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onWhatEverClick(int position) {

    }

    @Override
    public void onDelereClick(int position) {

    }
}