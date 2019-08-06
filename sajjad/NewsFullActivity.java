package com.example.ssoheyli.ticketing_newdesign.News;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ssoheyli.ticketing_newdesign.Helpfull.Config;
import com.example.ssoheyli.ticketing_newdesign.Helpfull.Constans;
import com.example.ssoheyli.ticketing_newdesign.Images.NewsImageActivity;
import com.example.ssoheyli.ticketing_newdesign.Model.NewsItemsModel;
import com.example.ssoheyli.ticketing_newdesign.R;

import org.jsoup.Jsoup;

import java.io.ByteArrayOutputStream;

public class NewsFullActivity extends AppCompatActivity {

    private NewsItemsModel model = new NewsItemsModel(0, "", "", "", null);

    ImageView newsImage, jumpToButton;
    TextView tv_description;
    TextView tv_moreinfo;
    TextView tv_date;
    NestedScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Constans.theme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_full);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        newsImage = findViewById(R.id.newsImage);
        tv_description = findViewById(R.id.tv_description);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);

        tv_moreinfo = findViewById(R.id.more_info);
        tv_date = findViewById(R.id.tv_date);
        jumpToButton = findViewById(R.id.jumpToTopButton);
        scrollView = findViewById(R.id.scrollView);
        tv_moreinfo.setTextColor(ContextCompat.getColor(this, Constans.colorprimary));
        fab.setBackgroundColor(ContextCompat.getColor(this, Constans.colorprimary));
        fab2.setBackgroundColor(ContextCompat.getColor(this, Constans.colorprimary));

        if (getIntent().getExtras() != null) {

            byte[] byteArray = getIntent().getByteArrayExtra("bitmap");
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

            model.setNewsId(getIntent().getIntExtra("id", 0));
            model.setTitle(getIntent().getStringExtra("title"));
            model.setDate(getIntent().getStringExtra("date"));
            model.setBitmap(bmp);
            model.setDescription(getIntent().getStringExtra("description"));

        }

        Glide.with(this)
                .load(model.getBitmap())
                .into(newsImage);

        toolbar.setTitle(model.getTitle());

        String htmlText = model.getDescription();
        String description_text = Jsoup.parse(htmlText).text();
        tv_description.setText(description_text);
        tv_date.setText(model.getDate());

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = Config.getBaseUrl() + "weblog/detail/" + model.getNewsId();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        tv_moreinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = Config.getBaseUrl() + "weblog/detail/" + model.getNewsId();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        newsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                model.getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                Intent intent = new Intent(NewsFullActivity.this, NewsImageActivity.class);
                intent.putExtra("bitmap", byteArray);
                startActivity(intent);
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.smoothScrollTo(0, 0);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                    int x = scrollY - oldScrollY;
                    if (x > 0) {
                        //scroll up
                        fab2.show();
                    } else if (x < 0) {
                        //scroll down
                        fab2.hide();
                    } else {

                    }

                }
            });

        } else {
            jumpToButton.setVisibility(View.VISIBLE);
            jumpToButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    scrollView.smoothScrollTo(0, 0);
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
