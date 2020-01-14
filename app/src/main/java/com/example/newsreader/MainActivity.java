package com.example.newsreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static ArrayList<String> noted = new ArrayList<String>();
    static ArrayAdapter arrayAdapter;
    ListView listView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        noted.add("Add a new place");
        noted.add("second web");
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, noted);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, noted.toString(), Toast.LENGTH_SHORT).show(); // to see list array yang di klik
                Intent intent = new Intent (getApplicationContext(), webView.class);
                //intent.putExtra("position",noted.get(position)); menampilkan yang pertama
                intent.putExtra("position",position); // menampilkan kedua
                startActivity(intent);
            }
        });


    }
}
