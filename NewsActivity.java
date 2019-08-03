package com.example.ssoheyli.ticketing_newdesign.News;

import android.os.Build;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ssoheyli.ticketing_newdesign.Helpfull.API;
import com.example.ssoheyli.ticketing_newdesign.Helpfull.Config;
import com.example.ssoheyli.ticketing_newdesign.Helpfull.Constans;
import com.example.ssoheyli.ticketing_newdesign.Helpfull.FakeDataGenerator;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_News_Groups;
import com.example.ssoheyli.ticketing_newdesign.Model.News_Post_Category;
import com.example.ssoheyli.ticketing_newdesign.R;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private ProgressBar loader;
    private ConstraintLayout LL1;
    private SwipeRefreshLayout refresher;
    private RecyclerView newsGroupList;
    private NewsGroupAdapter adapter;
    private ImageView im1;
    private TextView tv1;
    private List<Model_News_Groups> modelNewsGroups = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Constans.theme);
        setContentView(R.layout.activity_news);

        initViews();

        //Progressbar color
        Sprite circle = new Circle();
        circle.setColor(ContextCompat.getColor(this, Constans.colorprimary));
        loader.setIndeterminateDrawable(circle);
        loader.setVisibility(View.VISIBLE);

        //toolbar color
        LL1.setBackgroundColor(ContextCompat.getColor(this, Constans.colorprimary));

        loadData();
    }

    private void initViews() {
        loader = findViewById(R.id.loader);
        LL1 = findViewById(R.id.LL1);
        refresher = findViewById(R.id.refresher);
        refresher.setOnRefreshListener(this);
        newsGroupList = findViewById(R.id.news_group_list);
        im1 = findViewById(R.id.im1);
        tv1 = findViewById(R.id.tv1);
    }

    private void loadData() {
        //TODO load images with bitmap

        Retrofit loadCategories = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        API api = loadCategories.create(API.class);
        News_Post_Category postCategory = new News_Post_Category();
        postCategory.setLangID(1065);
        Call<List<Model_News_Groups>> models = api.getNewsGroups(postCategory);
        models.enqueue(new Callback<List<Model_News_Groups>>() {
            @Override
            public void onResponse(Call<List<Model_News_Groups>> call, Response<List<Model_News_Groups>> response) {
                if (response.isSuccessful()) {
                    modelNewsGroups = response.body();
                }
            }

            @Override
            public void onFailure(Call<List<Model_News_Groups>> call, Throwable t) {

            }
        });


        //modelNewsGroups = FakeDataGenerator.getNewsGroups();
        if (modelNewsGroups.size() == 0) {
            tv1.setTextColor(ContextCompat.getColor(this, Constans.colorprimary));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                im1.setImageTintList(ContextCompat.getColorStateList(this, Constans.colorprimary));
            }
            im1.setImageResource(R.drawable.ti1);
            tv1.setText(getResources().getString(R.string.nothing_exist));
        }

        adapter = new NewsGroupAdapter(this, modelNewsGroups);

        // LINEAR
        LinearLayoutManager linearVertical = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        // GRID
        GridLayoutManager gridHorizontal = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);

        newsGroupList.setLayoutManager(linearVertical);
        newsGroupList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        loader.setVisibility(View.INVISIBLE);
    }

    public void goBack(View view) {
        finish();
    }

    @Override
    public void onRefresh() {
        refresher.setColorSchemeColors(ContextCompat.getColor(this, Constans.colorprimary));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadData();
                refresher.setRefreshing(false);
            }
        }, 2000);
    }
}
