package com.example.newsproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder> {

    private List<NewsResponse.Article> articles;

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
        holder.titleTextView.setText(article.getTitle());
        holder.descriptionTextView.setText(article.getDescription());
        holder.link.setText(article.getUrl());
        Glide.with(holder.itemView.getContext())
                .load(article.getImage())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView,link;
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.article_title);
            descriptionTextView = itemView.findViewById(R.id.article_description);
            imageView = itemView.findViewById(R.id.article_image);
            link = itemView.findViewById(R.id.link);
        }
    }
}
