package com.example.newsapp.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsapp.Api.Helper;
import com.example.newsapp.WebViewActivity;
import com.example.newsapp.LoadMoreRecyclerViewListener;
import com.example.newsapp.R;
import com.example.newsapp.RVAdapter;
import com.example.newsapp.models.ArticlesList;
import com.example.newsapp.models.News;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment implements RVAdapter.OnItemListener {

    private static final String API_KEY = "c7c30a5c2e824179ab4cc3b84ab0a235";
    private static final String COUNTRY = "eg";
    private static final String BASE_URL = "https://newsapi.org/v2/";

    private View mView;
    private String mFragmentStatus;

    private RecyclerView recyclerView;
    private TextView no_list;
    private ProgressBar progressBar;
    private RVAdapter rvAdapter;
    private List<ArticlesList> articlesLists = new ArrayList<>();

    public NewsFragment(String mStatus) {
        this.mFragmentStatus = mStatus;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        articlesLists = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
        mView = inflater.inflate(R.layout.fragment_news, container, false);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        load();
    }

    private void load() {
        if (articlesLists == null || articlesLists.size() == 0) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            Helper helper = retrofit.create(Helper.class);

            Call<News> call = helper.getArticles(COUNTRY, mFragmentStatus, 1, API_KEY);

            call.enqueue(new Callback<News>() {
                @Override
                public void onResponse(Call<News> call, Response<News> response) {
                    if (response.isSuccessful()) {
                        progressBar.setVisibility(View.GONE);
                        assert response.body() != null;
                        articlesLists = response.body().getArticles();
                        setAdapter(articlesLists);
                    }
                }

                @Override
                public void onFailure(Call<News> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    no_list.setVisibility(View.VISIBLE);
                }
            });

        } else {
            progressBar.setVisibility(View.GONE);
            setAdapter(articlesLists);
        }
    }

    private void initViews() {
        recyclerView = mView.findViewById(R.id.recyclerview);
        progressBar = mView.findViewById(R.id.progress);
        no_list = mView.findViewById(R.id.no_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnScrollListener(new LoadMoreRecyclerViewListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {

            }
        });

    }

    private void setAdapter(List<ArticlesList> articlesLists) {
        rvAdapter = new RVAdapter(articlesLists, this);
        recyclerView.setAdapter(rvAdapter);
    }

    @Override
    public void onItemClicked(int position) {
        try {
            showDialog(position);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void showDialog(int position) throws MalformedURLException {
        Context context = mView.getContext();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        ViewGroup viewGroup = mView.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(context)
                .inflate(R.layout.dialog_layout, viewGroup, false);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        TextView txt_description = dialogView.findViewById(R.id.description_Txt_View);
        ImageView imageView = dialogView.findViewById(R.id.description_image_view);
        Button button = dialogView.findViewById(R.id.openWebView);

        final String url = articlesLists.get(position).getUrl();
        String imageUrl = articlesLists.get(position).getThumbnail();
        String description = articlesLists.get(position).getDescription();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WebViewActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });

        txt_description.setMovementMethod(new ScrollingMovementMethod());
        txt_description.setText(description);

        Glide.with(dialogView)
                .load(imageUrl)
                .placeholder(R.drawable.no_preview)
                .error(R.drawable.no_preview)
                .into(imageView);


    }

}
