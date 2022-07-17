package com.tugasakhir.todolist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{
    Context context;
    ArrayList<Model> todoList;

    Adapter(Context context, ArrayList<Model> list) {
        this.todoList = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Returning view
        View view = LayoutInflater.from(context).inflate(R.layout.item_model, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Setting text
        holder.title.setText(todoList.get(position).getTitle());
        holder.description.setText(todoList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView description;
        ImageView edit, done;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            edit = itemView.findViewById(R.id.btn_edit);
            done = itemView.findViewById(R.id.btn_done);

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AddActivity.class);
                    intent.putExtra("title", todoList.get(getAdapterPosition()).getTitle());
                    intent.putExtra("description", todoList.get(getAdapterPosition()).getDescription());
                    intent.putExtra("id",todoList.get(getAdapterPosition()).getTodoId());
                    context.startActivity(intent);
                }
            });
            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteTodo(todoList.get(getAdapterPosition()));
                    ((MainActivity)context).LoadQuery();
                }
            });
        }

        public void deleteTodo(Model item) {
            DatabaseManager dbManager = new DatabaseManager(context);
            String[] selectionArgs = {item.getTodoId() + ""};
            dbManager.Delete("id=?", selectionArgs);
            Toast.makeText(context, "Aktivitas Selesai", Toast.LENGTH_SHORT).show();

        }

    }
}
