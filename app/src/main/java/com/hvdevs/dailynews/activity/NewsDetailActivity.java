package com.hvdevs.dailynews.activity;

import static com.hvdevs.dailynews.R.id.bookMark;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hvdevs.dailynews.DB.DatabaseHelper;
import com.hvdevs.dailynews.DB.SavedNewsModel;
import com.hvdevs.dailynews.R;
import com.squareup.picasso.Picasso;

public class NewsDetailActivity extends AppCompatActivity {

    String title,description,content,imageURL,url;

    private TextView titleTV,subDescriptionTV,contentTV;
    private ImageView newsIV,bookMark;
    private Button readNewsBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        title = getIntent().getStringExtra("title");
        description = getIntent().getStringExtra("description");
        content = getIntent().getStringExtra("content");
        imageURL = getIntent().getStringExtra("image");
        url = getIntent().getStringExtra("url");

        titleTV = findViewById(R.id.idTVTitle);
        subDescriptionTV = findViewById(R.id.idTVSubDescription);
        contentTV = findViewById(R.id.idTVContent);

        newsIV = findViewById(R.id.idIVNews);
        bookMark = findViewById(R.id.bookMark);

        readNewsBtn = findViewById(R.id.idBtnReadNews);
        titleTV.setText(title);
        subDescriptionTV.setText(description);
        contentTV.setText(content);
        Picasso.get().load(imageURL).into(newsIV);
        readNewsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        DatabaseHelper databaseHelper = DatabaseHelper.getDB(this);

        bookMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHelper.tbNewsDao().addNews(
                        new SavedNewsModel(title,description,imageURL,url)
                );
                Toast toast = Toast.makeText(getApplicationContext(),"News saved scussesfully",Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }


}