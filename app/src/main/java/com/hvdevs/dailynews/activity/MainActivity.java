package com.hvdevs.dailynews.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hvdevs.dailynews.R;
import com.hvdevs.dailynews.RetrofitAPI;
import com.hvdevs.dailynews.adapter.CategoryRVAdapter;
import com.hvdevs.dailynews.adapter.NewsRVAdapter;
import com.hvdevs.dailynews.viewModel.Articles;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CategoryRVAdapter.CategoryClickInterface {

    private RecyclerView newsRV,categoryRV;
    private ProgressBar loadingPB;
    private ImageView iconSave;
    private ArrayList<com.hvdevs.dailynews.viewModel.Articles> articlesArrayList;
    private ArrayList<com.hvdevs.dailynews.viewModel.CategoryRVModel> categoryRVModelArrayList;
    private CategoryRVAdapter categoryRVAdapter;
    private NewsRVAdapter newsRVAdapter;


    @SuppressLint({"NotifyDataSetChanged", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsRV = findViewById(R.id.idRVNews);
        iconSave = findViewById(R.id.iconSave);
        categoryRV = findViewById(R.id.idRVCategories);
        loadingPB = findViewById(R.id.idPBLoading);
        articlesArrayList = new ArrayList<>();
        categoryRVModelArrayList = new ArrayList<>();
        newsRVAdapter = new NewsRVAdapter(articlesArrayList,this);
        categoryRVAdapter = new CategoryRVAdapter(categoryRVModelArrayList,this,this::onCategoryClick);
        newsRV.setLayoutManager(new LinearLayoutManager(this));
        newsRV.setAdapter(newsRVAdapter);
        categoryRV.setAdapter(categoryRVAdapter);

        iconSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SavedNewsActivity.class);
                startActivity(i);
            }
        });

        getCategories();
        getNews("All");
        newsRVAdapter.notifyDataSetChanged();

    }

    private void getCategories(){
        categoryRVModelArrayList.add(new com.hvdevs.dailynews.viewModel.CategoryRVModel("All",
                "https://media.istockphoto.com/photos/daily-papers-with-news-on-the-computer-picture-id1301656823?b=1&k=20&m=1301656823&s=170667a&w=0&h=s9IXcVfB151qb7Vb_9uJbl-XDGr2179rHA4ikgpdTUM="));
        categoryRVModelArrayList.add(new com.hvdevs.dailynews.viewModel.CategoryRVModel("Technology",
                "https://media.istockphoto.com/photos/data-scientists-male-programmer-using-laptop-analyzing-and-developing-picture-id1295900106?k=20&m=1295900106&s=612x612&w=0&h=hDkQP1a9dUo4Esv8iMyVlEnP4nfN2mwM5LdtPW9M8zo="));
        categoryRVModelArrayList.add(new com.hvdevs.dailynews.viewModel.CategoryRVModel("Science",
                "https://images.unsplash.com/photo-1507668077129-56e32842fceb?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NXx8c2NpZW5jZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelArrayList.add(new com.hvdevs.dailynews.viewModel.CategoryRVModel("Sports",
                "https://media.istockphoto.com/photos/full-stadium-and-neoned-colorful-flashlights-background-picture-id1276444914?b=1&k=20&m=1276444914&s=170667a&w=0&h=FKHO_16rIS7zdUYBJ0yWMa5MkcAGvgnhDiKOztsbgzg="));
        categoryRVModelArrayList.add(new com.hvdevs.dailynews.viewModel.CategoryRVModel("General",
                "https://images.unsplash.com/photo-1432821596592-e2c18b78144f?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTd8fGdlbmVyYWx8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelArrayList.add(new com.hvdevs.dailynews.viewModel.CategoryRVModel("Business",
                "https://media.istockphoto.com/photos/business-persons-walking-and-working-around-the-office-building-picture-id1282663908?b=1&k=20&m=1282663908&s=170667a&w=0&h=2s04Z6VCDte2PwJoWijU9kQuj_S36HtgPY1-v9EdRR4="));
        categoryRVModelArrayList.add(new com.hvdevs.dailynews.viewModel.CategoryRVModel("Entertainment",
                "https://media.istockphoto.com/photos/communication-network-concept-young-asian-woman-in-the-office-social-picture-id1271698373?b=1&k=20&m=1271698373&s=170667a&w=0&h=hTb74VRaSw8cJycsgvvVMcOuFW4LuHGsCFx3zCQ0bao="));
        categoryRVModelArrayList.add(new com.hvdevs.dailynews.viewModel.CategoryRVModel("Health",
                "https://images.unsplash.com/photo-1506126613408-eca07ce68773?ixid=MnwxMjA3fDB8MHxzZWFyY2h8M3x8aGVhbHRofGVufDB8fDB8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));

        categoryRVAdapter.notifyDataSetChanged();
    }

    private void getNews(String category) {
        loadingPB.setVisibility(View.VISIBLE);
        articlesArrayList.clear();
        String categoryURL = "https://newsapi.org/v2/top-headlines?country=in&category=" + category + "&apikey=ae3a258817ba43c6a82c31550aaf1020";
        String url = "https://newsapi.org/v2/top-headlines?country=in&excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apikey=ae3a258817ba43c6a82c31550aaf1020";
        String Base_URL = "https://newsapi.org/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<com.hvdevs.dailynews.viewModel.NewsModel> call;
        if(category.equals("All")){
            call = retrofitAPI.getAllNews(url);
        }
        else{
            call = retrofitAPI.getNewsByCategory(categoryURL);
        }

        call.enqueue(new Callback<com.hvdevs.dailynews.viewModel.NewsModel>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<com.hvdevs.dailynews.viewModel.NewsModel> call, @NonNull Response<com.hvdevs.dailynews.viewModel.NewsModel> response) {
                com.hvdevs.dailynews.viewModel.NewsModel newsModel = response.body();
                loadingPB.setVisibility(View.GONE);
                ArrayList<com.hvdevs.dailynews.viewModel.Articles> articles = newsModel.getArticles();
                for (int i = 0 ; i<articles.size();i++){
                    articlesArrayList.add(new Articles(articles.get(i).getTitle(),articles.get(i).getDescription(),articles.get(i).getUrlToImage(),articles.get(i).getUrl(),articles.get(i).getContent()));
                }
                newsRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<com.hvdevs.dailynews.viewModel.NewsModel> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Fail to get News",Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void onCategoryClick(int position) {
        String category = categoryRVModelArrayList.get(position).getCategory();
        getNews(category);
    }




}