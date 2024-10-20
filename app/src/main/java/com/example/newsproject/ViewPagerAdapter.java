package com.example.newsproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.net.URL;
import java.util.List;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder> {
    private final List<NewsResponse.Article> articles;

    public ViewPagerAdapter(List<NewsResponse.Article> articles) {
        this.articles = articles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewsResponse.Article article = articles.get(position);
        holder.bind(article);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTextView;
        private final TextView descriptionTextView;
        private final ImageView articleImageView;
        private final TextView link;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.article_title);
            descriptionTextView = itemView.findViewById(R.id.article_description);
            articleImageView = itemView.findViewById(R.id.article_image);
            link = itemView.findViewById(R.id.link);
        }

        public void bind(NewsResponse.Article article) {
            titleTextView.setText(article.getTitle());
            descriptionTextView.setText(article.getDescription());
            link.setText(article.getUrl());

            // Use Glide or any other image loading library to load the image from the URL
            Glide.with(articleImageView.getContext())
                    .load(article.getImage())
                    .into(articleImageView);
        }
    }
}
