package com.example.tictactoe.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseCrud extends SQLiteOpenHelper {
    public static  final String DATABASE_NAME = "tictactoe.db";
    public static final int DATABASE_VERSION = 1;
    public  static  final String TABLE_NAME = "player";
    public DatabaseCrud(@Nullable Context context) {
        super(context, DATABASE_NAME, null , DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlStatement = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, score INTEGER)";
        sqLiteDatabase.execSQL(sqlStatement);
    }
    public void  updateScore(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        //check if player exist in database if exists update score else insert player
        String sqlStatement = "SELECT * FROM "+TABLE_NAME+" WHERE name = '"+name+"'";
        try{
            if(db.rawQuery(sqlStatement,null).getCount() > 0){
                sqlStatement = "UPDATE "+TABLE_NAME+" SET score = score + 1 WHERE name = '"+name+"'";
                this.getWritableDatabase().execSQL(sqlStatement);
            }else{
                insertPlayer(name);
            }
            db.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void insertPlayer(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlStatement = "INSERT INTO "+TABLE_NAME+" (name, score) VALUES ('"+name+"', 1)";
        db.execSQL(sqlStatement);
        db.close();
    }
    public void deleteAllPlayer(){
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlStatement = "DELETE FROM "+TABLE_NAME;
        db.execSQL(sqlStatement);
        db.close();
    }

    public String getPlayerWithScore(){
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlStatement = "SELECT * FROM "+TABLE_NAME+" ORDER BY score DESC";
        Cursor cursor = db.rawQuery(sqlStatement,null);
        String result = "";
        while (cursor.moveToNext()){
            result += cursor.getString(1)+" "+cursor.getInt(2)+"\n";
        }
        db.close();
        return result;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sqlStatement = "DROP TABLE IF EXISTS "+TABLE_NAME;
        sqLiteDatabase.execSQL(sqlStatement);
        onCreate(sqLiteDatabase);
    }
}