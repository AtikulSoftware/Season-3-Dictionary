package com.example.season3dictionary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseHelper extends SQLiteAssetHelper {
    Context context;
    public DatabaseHelper(Context context) {
        super(context, "dictionary.db", null, 1);
        this.context = context;
    }

    public boolean insertData (String word, String meaning, String partsOfSpeech, String example, int isFavorite){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("word", word);
        contentValues.put("meaning", meaning);
        contentValues.put("partsOfSpeech", partsOfSpeech);
        contentValues.put("example", example);
        contentValues.put("isFavorite", isFavorite);

        // data insert করা হয়েছে ।
        long result = database.insert("Dictionary", null, contentValues);

        if (result <= 0) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Dictionary",null);
        return cursor;
    }

    public Cursor searchData(String word){
        SQLiteDatabase db = this.getReadableDatabase();
       //  Cursor cursor = db.rawQuery("SELECT * FROM Dictionary WHERE word LIKE '%"+word+"%' OR meaning LIKE '%"+word+"%'",null);
        Cursor cursor = db.rawQuery("SELECT * FROM Dictionary WHERE word LIKE '"+word+"%'",null);
        return cursor;
    }
}
