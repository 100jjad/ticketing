package com.example.ssoheyli.ticketing_newdesign.Financial;

import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ssoheyli.ticketing_newdesign.Helpfull.API;
import com.example.ssoheyli.ticketing_newdesign.Helpfull.Config;
import com.example.ssoheyli.ticketing_newdesign.Helpfull.ConnectionReceiver;
import com.example.ssoheyli.ticketing_newdesign.Helpfull.Constans;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Get_Turnover;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Token;
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

public class TurnOverTestActivity extends AppCompatActivity implements ConnectionReceiver.ConnectionReceiverListener {

    // UI
    private Toolbar toolbar;
    private ProgressBar loader;
    private TextView nothingFound;
    private ImageView nothingImage;
    private LinearLayout titles;
    private LinearLayout finalTurnOver;
    private LinearLayout finalAmount;
    private TextView finalPrice;
    private TextView finalCreditor;
    private TextView finalDebator;
    private TextView finalDebatorLayout;
    private TextView finalCreditorLayout;
    private TextView amount_remain;
    private RecyclerView turnOverList;
    private TurnOverListAdapter adapter;
    private CoordinatorLayout coordinatorLayout;
    private Animation animation;
    private boolean visibility_flag = false;

    //Values
    List<Model_Get_Turnover> turnoverList = new ArrayList<>();
    ConnectionReceiver receiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Constans.theme);
        setContentView(R.layout.activity_test_turnover);
        initViews();
        setupToolbar();

        //registering receiver
        receiver = new ConnectionReceiver();
        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        ConnectionReceiver.connectionReceiverListener = this;
        registerReceiver(receiver, intentFilter);
        //

        // Pre load
        loader.setVisibility(View.VISIBLE);
        titles.setVisibility(View.GONE);
        turnOverList.setVisibility(View.GONE);


        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                setAnimation();
            }
        }, 1000);

    }

    private void loadTurnOverList() {
        Retrofit getTurnOverList = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        API api = getTurnOverList.create(API.class);
        Model_Token model_post = new Model_Token();
        model_post.setToken(Config.getToken(getApplicationContext()));
        Call<List<Model_Get_Turnover>> model = api.get_turnover(model_post);
        model.enqueue(new Callback<List<Model_Get_Turnover>>() {
            @Override
            public void onResponse(Call<List<Model_Get_Turnover>> call, Response<List<Model_Get_Turnover>> response) {
                loader.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    turnoverList = response.body();
                    if (turnoverList.size() == 0) {
                        nothingFound.setVisibility(View.VISIBLE);
                        nothingImage.setVisibility(View.VISIBLE);
                        nothingImage.setImageResource(R.drawable.ti1);
                        nothingFound.setText(getString(R.string.nothing_exist));
                    } else {
                        turnOverList.setVisibility(View.VISIBLE);
                        titles.setVisibility(View.VISIBLE);
                        visibility_flag = true;

                        adapter = new TurnOverListAdapter(turnoverList, TurnOverTestActivity.this);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(TurnOverTestActivity.this, LinearLayoutManager.VERTICAL, false);
                        turnOverList.setLayoutManager(layoutManager);
                        turnOverList.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                        //final loading
                        int totalDebateAmount = 0;
                        int totalCreditorAmount = 0;

                        for (int i = 0; i < turnoverList.size(); i++) {
                            if (turnoverList.get(i).getType() == 1) {
                                int amount = turnoverList.get(i).getTotal_amount();
                                totalDebateAmount = totalDebateAmount + amount;
                            } else {
                                int amount = turnoverList.get(i).getTotal_amount();
                                totalCreditorAmount = totalCreditorAmount + amount;
                            }
                        }

                        finalCreditor.setText(edit_price(String.valueOf(totalCreditorAmount)));
                        finalDebator.setText(edit_price(String.valueOf(totalDebateAmount)));

                        if (totalCreditorAmount > totalDebateAmount) {
                            finalCreditorLayout.setText(edit_price(String.valueOf(totalCreditorAmount - totalDebateAmount)));
                            finalDebatorLayout.setText("---");
                        } else if (totalDebateAmount > totalCreditorAmount) {
                            finalDebatorLayout.setText(edit_price(String.valueOf(totalCreditorAmount - totalDebateAmount)));
                            finalCreditorLayout.setText("---");
                        } else {
                            finalCreditorLayout.setText("0");
                            finalDebatorLayout.setText("0");
                        }

                    }
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.server_1191), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Model_Get_Turnover>> call, Throwable t) {
                loader.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), getString(R.string.server_1192), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public String edit_price(String price) {
        int lengh = price.length();
        StringBuilder price2 = new StringBuilder(price);
        while (lengh > 3) {
            lengh = lengh - 3;
            price2.insert(lengh, ",");
        }

        return price2.toString();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);

        loader = findViewById(R.id.loader);
        Sprite circle = new Circle();
        circle.setColor(ContextCompat.getColor(this, Constans.colorprimary));
        loader.setIndeterminateDrawable(circle);

        nothingFound = findViewById(R.id.tv1);
        nothingFound.setTextColor(ContextCompat.getColor(this, Constans.colorprimary));

        nothingImage = findViewById(R.id.im1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            nothingImage.setBackgroundTintList(ContextCompat.getColorStateList(this, Constans.colorprimary));
            nothingImage.setImageTintList(ContextCompat.getColorStateList(this, Constans.colorprimary));
        }

        titles = findViewById(R.id.titles);
        titles.setBackgroundColor(ContextCompat.getColor(this, Constans.colorprimary_dark));

        finalTurnOver = findViewById(R.id.finalTurnOver);

        finalPrice = findViewById(R.id.finalPrice);

        finalCreditor = findViewById(R.id.finalCreditor);

        finalDebator = findViewById(R.id.finalDebator);

        turnOverList = findViewById(R.id.turnOverList);

        finalAmount = findViewById(R.id.finalAmount);

        finalDebatorLayout = findViewById(R.id.finalDebatorLayout);

        finalCreditorLayout = findViewById(R.id.finalCreditorLayout);

        amount_remain = findViewById(R.id.amount_remain);
        amount_remain.setBackgroundColor(ContextCompat.getColor(this, Constans.colorprimary_dark));

        coordinatorLayout = findViewById(R.id.cd);
    }

    public void setAnimation()
    {
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.item_animation_fall_down);
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                if(visibility_flag)
                {
                    finalTurnOver.setVisibility(View.VISIBLE);
                    finalAmount.setVisibility(View.VISIBLE);
                    finalTurnOver.startAnimation(animation);
                    finalAmount.startAnimation(animation);
                }
            }
        }, 200);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        toolbar.setBackgroundColor(ContextCompat.getColor(this, Constans.colorprimary));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return true;
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (isConnected) {
            //load
            loadTurnOverList();
        } else {
            Snackbar.make(coordinatorLayout, getString(R.string.not_connected), Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ConnectionReceiver.connectionReceiverListener = null;
        unregisterReceiver(receiver);
    }
}
