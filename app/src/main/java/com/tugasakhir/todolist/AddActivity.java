package com.tugasakhir.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    Model item = null;
    EditText todoTitle, todoDescription;
    ImageView btn_save, btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        todoTitle = findViewById(R.id.et_kegiatan);
        todoDescription = findViewById(R.id.et_description);
        btn_save = findViewById(R.id.save_btn);
        btn_back = findViewById(R.id.back_btn);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if(getIntent().getExtras()!=null){
            item = new Model(getIntent().getExtras().getInt("id"),
                    getIntent().getExtras().getString("title"),
                    getIntent().getExtras().getString("description"));

            todoTitle.setText(item.getTitle());
            todoDescription.setText(item.getDescription());

        }

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (todoTitle.getText().toString().length() > 0
                        && todoDescription.getText().toString().length() > 0) {
                    if(item!=null){
                        updateTodo();
                    }
                    else if(item == null){
                        addTodo();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Isi dulu sebelum simpan", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

        public void addTodo() {
            DatabaseManager dbManager = new DatabaseManager(this);
            ContentValues contentValues = new ContentValues();
            contentValues.put("title", todoTitle.getText().toString().trim());
            contentValues.put("description", todoDescription.getText().toString().trim());

            long id = dbManager.Insert(contentValues);
            if (id > 0) {
                Toast.makeText(this, "Aktifitas ditambahkan", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Terjadi masalah!", Toast.LENGTH_SHORT).show();
            }

            finish();
        }

        public void updateTodo() {
            DatabaseManager dbManager = new DatabaseManager(this);
            ContentValues contentValues = new ContentValues();
            contentValues.put("title", todoTitle.getText().toString().trim());
            contentValues.put("description", todoDescription.getText().toString().trim());
            String[] selectionArgs = {item.getTodoId()+""};

            long id = dbManager.Update(contentValues,"id=?", selectionArgs);

            if (id > 0) {
                Toast.makeText(this, "Aktifitas di ubah", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Terjadi masalah!", Toast.LENGTH_SHORT).show();
            }

            finish();
        }
}