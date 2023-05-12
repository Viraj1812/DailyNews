package com.hvdevs.dailynews.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.hvdevs.dailynews.DB.DatabaseHelper;
import com.hvdevs.dailynews.DB.SavedNewsModel;
import com.hvdevs.dailynews.R;
import com.hvdevs.dailynews.adapter.CategoryRVAdapter;
import com.hvdevs.dailynews.adapter.NewsRVAdapter;
import com.hvdevs.dailynews.adapter.SavedNewsRVAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class SavedNewsActivity extends AppCompatActivity {

    private SavedNewsRVAdapter newsRVAdapter;
    private RecyclerView newsRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_news);

        newsRV = findViewById(R.id.idRVNews);
        DatabaseHelper databaseHelper = DatabaseHelper.getDB(this);

        ArrayList<SavedNewsModel> arrNews = (ArrayList<SavedNewsModel>)  databaseHelper.tbNewsDao().getAllSavedNews();

        Collections.reverse(arrNews);

        newsRVAdapter = new SavedNewsRVAdapter(arrNews,this);
        newsRV.setLayoutManager(new LinearLayoutManager(this));
        newsRV.setAdapter(newsRVAdapter);
    }
}