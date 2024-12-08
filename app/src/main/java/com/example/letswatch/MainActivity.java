package com.example.letswatch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.letswatch.adapter.MainAdapter;
import com.example.letswatch.model.Profile;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
RecyclerView recyclerView;
MainAdapter adapter;
ArrayList<Profile> list;
FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
       recyclerView = findViewById(R.id.recyclerview_main);
       list = new ArrayList<>();
       db = FirebaseFirestore.getInstance();
       adapter = new MainAdapter(MainActivity.this,list);
       db.collection("Categories").addSnapshotListener(new EventListener<QuerySnapshot>() {
           @Override
           public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
               if (error!=null){
                   Log.d("show", "onEvent: "+error.getMessage());
               }
               else {
                   for (DocumentChange dc : value.getDocumentChanges()){
                       if (dc.getType()==DocumentChange.Type.ADDED){
                           list.add(dc.getDocument().toObject(Profile.class));
                       }
                   }
                   adapter.notifyDataSetChanged();
               }
           }
       });
       recyclerView.setLayoutManager(new GridLayoutManager(this,2));
       recyclerView.setAdapter(adapter);
    }
}