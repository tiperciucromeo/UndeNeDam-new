package com.romeotutorial.undenedam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by romeotiperciuc on 09/07/2017.
 */


public class ListaPartii extends AppCompatActivity {
    ArrayList<String> partii;
    ListView listView;
    ArrayAdapter<String> mAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listapartii);
        partii = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listView);

        Log.w("DEBUGING", "onCreate ListaPartii");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference partiiRef = database.getReference("Partii");

        Log.w("DEBUGING", "database reference OK");

        partiiRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("DEBUGING", "on data change cbk");
                partii.clear();
                Map<String, Object> mapPartii = (Map<String, Object>) dataSnapshot.getValue();

                for (Map.Entry<String, Object> entry : mapPartii.entrySet()) {
                    Log.w("DEBUGING", "Adding partie: " + entry.getKey());
                    partii.add(entry.getKey());
                }

                reloadListaPartii();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("nu nu nu", "Failed to read value.", error.toException());
            }
        });

        Log.w("DEBUGING", "callbacks added to database reference");
    }

    public void reloadListaPartii() {
       //        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, partii));

        //        }

        mAdapter = new ArrayAdapter<String>(ListaPartii.this, android.R.layout.simple_list_item_1, partii);

         listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                   Log.d("Blala",listView.getItemAtPosition(i).toString());
                   Log.d("Blala",Integer.toString(i));

                   Intent intent = new Intent(ListaPartii.this, ListaPartiiDeschis.class);
                 intent.putExtra("NUME_PARTIE", listView.getItemAtPosition(i).toString());
                   startActivity(intent);
             }
          });

        listView.setAdapter(mAdapter);
    }

}

