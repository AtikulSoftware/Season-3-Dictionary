package com.example.season3dictionary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    SearchView searchView;
    DatabaseHelper dbHelper;

    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    HashMap<String, String> hashMap;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // পরিচয় করিয়ে দেওয়া হয়েছে ।
        listView = findViewById(R.id.listView);
        searchView = findViewById(R.id.searchView);
        textView = findViewById(R.id.textView);

        // db helper কে পরিচয় করিয়ে দেওয়া হয়েছে ।
        dbHelper = new DatabaseHelper(MainActivity.this);

        boolean result = dbHelper.insertData("dj hello", "ভালো", "aagh", "agagh", 0 );
        if (result){
            Toast.makeText(this, "Data Insert", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Faild", Toast.LENGTH_SHORT).show();
        }
 
        // Local Database কে call করা হয়েছে ।
        LoadData(dbHelper.getAllData());



        // Adapter কে call করা হয়েছে এবং ListView তে set করে দেওয়া হয়েছে ।
        MyAdapter myAdapter = new MyAdapter();
        listView.setAdapter(myAdapter);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return true;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                arrayList.clear();
                LoadData(dbHelper.searchData(newText));
                myAdapter.notifyDataSetChanged();
                return false;
            }
        });


    } // onCreate method end here =============

    public void LoadData(Cursor cursor) {
        /// cursor = dbHelper.getAllData();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String word = cursor.getString(1);
                String meaning = cursor.getString(2);
                String partsOfSpeech = cursor.getString(3);
                String example = cursor.getString(4);

                // hashmap এর মধ্যে data put করা হয়েছে ।
                hashMap = new HashMap<>();
                hashMap.put("id", "" + id);
                hashMap.put("word", word);
                hashMap.put("meaning", meaning);
                hashMap.put("partsOfSpeech", partsOfSpeech);
                hashMap.put("example", example);
                arrayList.add(hashMap);

            }
        }

    } // LoadData end here ============

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            @SuppressLint("ViewHolder") View myView = layoutInflater.inflate(R.layout.list_item_layout, parent, false);

            // পরিচয় করিয়ে দেওয়া হয়েছে ।
            TextView tvWord = myView.findViewById(R.id.tvWord);
            TextView tvMeaning = myView.findViewById(R.id.tvMeaning);
            TextView tvPartsOfSpeech = myView.findViewById(R.id.tvPartsOfSpeech);
            TextView tvExample = myView.findViewById(R.id.tvExample);

            // list থেকে data get করা হয়েছে ।
            HashMap<String, String> myHashMap = arrayList.get(position);
            String word = myHashMap.get("word");
            String meaning = myHashMap.get("meaning");
            String partsOfSpeech = myHashMap.get("partsOfSpeech");
            String example = myHashMap.get("example");

            // textview এর মধ্যে setText করা হয়েছে ।
            tvWord.setText(word);
            tvMeaning.setText(meaning);
            tvPartsOfSpeech.setText(partsOfSpeech);
            tvExample.setText(example);

            

            return myView;
        }

    } // MyAdapter end here =============


} // public class end here ====================