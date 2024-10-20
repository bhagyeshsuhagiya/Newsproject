package com.example.newsproject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.List;

public class ReelFragment extends Fragment {
    private ViewPager2 viewPager;
    private RequestQueue requestQueue;

    private static final String TAG = "ReelFragment";
    private List<NewsResponse.Article> articles;

    // Category, language, and country can be set dynamically
    private String category = "general";
    private String language = "en";
    private String country = "in"; // 

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestQueue = Volley.newRequestQueue(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reel, container, false);
        viewPager = view.findViewById(R.id.viewpager);


        // Fetch data with the specified category
        fetchNewsData(category, language, country);

        return view;
    }

    private void fetchNewsData(String category, String language, String country) {
        String url = NewsApi.buildUrl(category, language, country);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        NewsResponse newsResponse = gson.fromJson(response, NewsResponse.class);
                        articles = newsResponse.getArticles();
                        setupViewPager();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error fetching data: " + error.getMessage());

                    }
                });

        requestQueue.add(stringRequest);
    }

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(articles);
        viewPager.setAdapter(adapter);
    }
}
