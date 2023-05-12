package com.hvdevs.dailynews.DB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "TBNews")
public class SavedNewsModel {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "imageURL")
    private String imageURL;
    @ColumnInfo(name = "url")
    private String url;

    SavedNewsModel(int id, String title, String description, String imageURL, String url)
    {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageURL = imageURL;
        this.url = url;
    }

    @Ignore
    public SavedNewsModel(String title, String description, String imageURL, String url)
    {
        this.title = title;
        this.description = description;
        this.imageURL = imageURL;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
