package com.example.newsproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsproject.R;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {
    private List<PostModel> articleList; // Use PostModel instead of NewsResponse.Article
    private Context context;

    public ArticleAdapter(List<PostModel> articleList, Context context) {
        this.articleList = articleList;
        this.context = context;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item (your layout file)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.demo_breaking_news, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        // Bind data to each item
        PostModel article = articleList.get(position);
        holder.articleTitle.setText(article.getName());
        holder.dateView.setText(article.getDate());
        holder.timeView.setText(article.getTime());
        holder.linkView.setText(article.getUrl());

        // Use Glide to load images
        Glide.with(context)
                .load(article.getImageUrl())
                .placeholder(R.drawable.profile) // Add your placeholder image here
                .into(holder.articleImage);
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    // ViewHolder class to hold the view references
    public static class ArticleViewHolder extends RecyclerView.ViewHolder {
        ImageView articleImage;
        TextView articleTitle, dateView, timeView, linkView;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            articleImage = itemView.findViewById(R.id.article_image);
            articleTitle = itemView.findViewById(R.id.article_title);
            dateView = itemView.findViewById(R.id.date_view);
            timeView = itemView.findViewById(R.id.time_view);
            linkView = itemView.findViewById(R.id.link);
        }
    }
}
