package com.hvdevs.dailynews.DB;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TBNewsDao {

    @Query("select * from TBNews")
    List<SavedNewsModel> getAllSavedNews();

    @Insert
    void addNews(SavedNewsModel news);

}
