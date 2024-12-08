package com.example.letswatch;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Episoderecyclerview extends AppCompatActivity {
RecyclerView recyclerView;
ArrayList<Model> list;
Adapter adapter;
FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_episoderecyclerview);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        String va = getIntent().getStringExtra("Name");
        Log.d("show", "onCreate: "+va);
        db = FirebaseFirestore.getInstance();
        list = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview);
        adapter = new Adapter(list,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        db.collection(va).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null){
                    Log.d("show", "onEvent: "+error.getMessage());
                    return;
                }
                for (DocumentChange dc :value.getDocumentChanges()){
                    if (dc.getType()==DocumentChange.Type.ADDED){
                        list.add(dc.getDocument().toObject(Model.class));
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });


    }
}