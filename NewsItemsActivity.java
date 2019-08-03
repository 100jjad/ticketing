package com.example.ssoheyli.ticketing_newdesign.News;

import android.os.Build;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ssoheyli.ticketing_newdesign.Helpfull.Constans;
import com.example.ssoheyli.ticketing_newdesign.Helpfull.FakeDataGenerator;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_News_NewsItems;
import com.example.ssoheyli.ticketing_newdesign.R;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;

import java.util.List;

public class NewsItemsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private ProgressBar loader;
    private ConstraintLayout LL1;
    private SwipeRefreshLayout refresher;
    private TextView title;
    private ImageView im1;
    private TextView tv1;
    private RecyclerView itemsList;
    private NewsItemsAdapter adapter;
    private List<Model_News_NewsItems> model_news_newsItems;
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Constans.theme);
        setContentView(R.layout.activity_news_items);

        initViews();

        //Progressbar color
        Sprite circle = new Circle();
        circle.setColor(ContextCompat.getColor(this, Constans.colorprimary));
        loader.setIndeterminateDrawable(circle);
        loader.setVisibility(View.VISIBLE);

        //toolbar color
        LL1.setBackgroundColor(ContextCompat.getColor(this, Constans.colorprimary));

        //TODO Load data from server
        loadData();
    }

    private void initViews() {
        title = findViewById(R.id.category);

        // get category
        if (getIntent().getExtras() != null) {
            category = getIntent().getStringExtra("category");
            title.setText(category);
        }

        loader = findViewById(R.id.loader);
        LL1 = findViewById(R.id.LL1);
        refresher = findViewById(R.id.refresher);
        refresher.setOnRefreshListener(this);
        im1 = findViewById(R.id.im1);
        tv1 = findViewById(R.id.tv1);
        itemsList = findViewById(R.id.news_item_list);
    }

    private void loadData() {

        model_news_newsItems = FakeDataGenerator.getNewsItems();
        if (model_news_newsItems.size() == 0) {
            tv1.setTextColor(ContextCompat.getColor(this, Constans.colorprimary));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                im1.setImageTintList(ContextCompat.getColorStateList(this, Constans.colorprimary));
            }
            im1.setImageResource(R.drawable.ti1);
            tv1.setText(getResources().getString(R.string.nothing_exist));
        }

        adapter = new NewsItemsAdapter(this, model_news_newsItems);

        // LINEAR
        LinearLayoutManager linearVertical = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        itemsList.setLayoutManager(linearVertical);
        itemsList.setAdapter(adapter);
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
