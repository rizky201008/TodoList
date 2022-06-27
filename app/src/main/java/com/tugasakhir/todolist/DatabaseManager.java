package com.tugasakhir.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import androidx.annotation.Nullable;

public class DatabaseManager {
    String db = "TodoList";
    String table = "List";
    String id = "id";
    String title = "title";
    String description = "description";
    int versiDB = 1;

    //Membuat tabel di SQLite
    String queryCreate = "CREATE TABLE IF NOT EXISTS "+table+" ("+ id +" INTEGER PRIMARY KEY, "+
            title + " TEXT, "+ description +" TEXT);";

    SQLiteDatabase sqlite =null;

    public DatabaseManager(Context context){
        DbHelper db = new DbHelper(context);
        sqlite = db.getWritableDatabase();
    }

    public class DbHelper extends SQLiteOpenHelper{

        public DbHelper(@Nullable Context context) {
            super(context, db, null, versiDB);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(queryCreate);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            String query = "DROP TABLE IF EXISTS";
            db.execSQL(query+ table);
        }
    }
    public long Insert(ContentValues contentValues){
        long id = sqlite.insert(table, "", contentValues);
        return id;
    }
    public Cursor Query(String[] projection, String selection, String[] selectionArgs, String sorOrder){
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(table);
        Cursor cursor = qb.query(sqlite, projection, selection, selectionArgs, null, null, sorOrder);
        return cursor;
    }
    public int Delete(String selection, String[] selectioArgs){
        int count = sqlite.delete(table, selection, selectioArgs);
        return count;
    }

    public int Update(ContentValues values, String selection, String[] selectionArgs){
        int count = sqlite.update(table, values, selection, selectionArgs);
        return count;
    }
}
