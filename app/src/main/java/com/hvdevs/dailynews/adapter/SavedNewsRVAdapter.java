package com.hvdevs.dailynews.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hvdevs.dailynews.DB.SavedNewsModel;
import com.hvdevs.dailynews.R;
import com.hvdevs.dailynews.activity.NewsDetailActivity;
import com.hvdevs.dailynews.viewModel.Articles;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SavedNewsRVAdapter extends RecyclerView.Adapter<SavedNewsRVAdapter.ViewHolder> {

    private ArrayList<SavedNewsModel> articlesArrayList;
    private Context context;

    public SavedNewsRVAdapter(ArrayList<SavedNewsModel> articlesArrayList, Context context) {
        this.articlesArrayList = articlesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public SavedNewsRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_rv_item,parent,false);
        return new SavedNewsRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedNewsRVAdapter.ViewHolder holder, int position) {

        SavedNewsModel articles = articlesArrayList.get(position);
        holder.subTitleTV.setText(articles.getDescription());
        holder.titleTV.setText(articles.getTitle());
        Picasso.get().load(articles.getImageURL()).into(holder.newsIV);

        holder.itemView.setOnClickListener(view -> {

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(articles.getUrl()));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return articlesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView titleTV;
        private final TextView subTitleTV;
        private final ImageView newsIV;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            titleTV = itemView.findViewById(R.id.idTVNewsHeading);
            subTitleTV = itemView.findViewById(R.id.idTVSubTitle);
            newsIV = itemView.findViewById(R.id.idIVNews);
        }
    }
}
