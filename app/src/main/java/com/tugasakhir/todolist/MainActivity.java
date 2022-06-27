package com.tugasakhir.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    ImageView btn_add, not_found;
    TextView not_found_txt;
    RecyclerView recyclerView;
    Adapter todoAdapter;
    ArrayList<Model> todolist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_add = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recycler_view);
        not_found = findViewById(R.id.not_found);
        not_found_txt = findViewById(R.id.txt_not_found);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddActivity.class));
            }
        });
    }

    public void setRecyclerView(ArrayList<Model> list){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        todoAdapter = new Adapter(this, list);
        recyclerView.setAdapter(todoAdapter);
    }

    public void LoadQuery(){
        todolist = new ArrayList<>();
        todolist.clear();
        DatabaseManager dbManager = new DatabaseManager(this);
        String[] projections = {"id","title","description"};
        String[] selectionArgs = {""};
        Cursor cursor = dbManager.Query(projections, "", null, null);

        if(cursor.getCount() == 0){
            //No data..
            not_found.setVisibility(View.VISIBLE);
            not_found_txt.setVisibility(View.VISIBLE);
        }
        else{
            if(cursor.moveToFirst()){
                do {
                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    String description = cursor.getString(cursor.getColumnIndex("description"));

                    todolist.add(new Model(id, title, description));
                }while (cursor.moveToNext());
            }
            not_found.setVisibility(View.GONE);
            not_found_txt.setVisibility(View.GONE);
        }
        if(todolist != null){
            Collections.reverse(todolist);
            setRecyclerView(todolist);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        LoadQuery();
    }

}