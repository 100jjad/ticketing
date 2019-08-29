package com.example.ssoheyli.ticketing_newdesign;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ssoheyli.ticketing_newdesign.Financial.Test_PreInvoice_List;
import com.example.ssoheyli.ticketing_newdesign.Helpfull.API;
import com.example.ssoheyli.ticketing_newdesign.Helpfull.Config;
import com.example.ssoheyli.ticketing_newdesign.Helpfull.ConnectionReceiver;
import com.example.ssoheyli.ticketing_newdesign.Helpfull.Constans;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Get_Basket_Amount;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Get_Basket_Confirm;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Get_Shopping_Basket;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Post_Basket_Amount;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Post_Basket_Confirm;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Post_Shopping_Basket;
import com.example.ssoheyli.ticketing_newdesign.Model.Shopping_Basket_List_Model;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShoppingBasket extends Activity implements SwipeRefreshLayout.OnRefreshListener, ConnectionReceiver.ConnectionReceiverListener, MyCallback {

    RecyclerView rcv;
    TextView txvtotal_amount;
    ProgressBar lp;
    int id;
    ImageView imv1;
    TextView txv1;
    Shopping_Basket_Adapter update = new Shopping_Basket_Adapter(this, this);
    private SwipeRefreshLayout swipeRefreshLayout;
    private CoordinatorLayout coordinatorLayout;
    ConnectionReceiver receiver;
    Button complete_purchase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Constans.theme);
        setContentView(R.layout.shoppingbasket);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, Constans.colorprimary));
        rcv = findViewById(R.id.rv1);
        coordinatorLayout = findViewById(R.id.cd);
        lp = findViewById(R.id.dl);
        complete_purchase = findViewById(R.id.complete_purchase);
        complete_purchase.setBackgroundColor(ContextCompat.getColor(this, Constans.colorprimary));

        Sprite circle = new Circle();
        circle.setColor(ContextCompat.getColor(this, Constans.colorprimary));
        lp.setIndeterminateDrawable(circle);

        //registering receiver
        receiver = new ConnectionReceiver();
        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        ConnectionReceiver.connectionReceiverListener = this;
        registerReceiver(receiver, intentFilter);
        //

        txvtotal_amount = findViewById(R.id.tvtotal_amount);
        imv1 = findViewById(R.id.im1);
        txv1 = findViewById(R.id.tv1);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imv1.setImageTintList(ContextCompat.getColorStateList(this, Constans.colorprimary));
        }
        txv1.setTextColor(ContextCompat.getColor(this, Constans.colorprimary));

        ImageView imvback = findViewById(R.id.ivback);
        imvback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        swipeRefreshLayout = findViewById(R.id.refresher);
        swipeRefreshLayout.setOnRefreshListener(this);

        complete_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmPayment();
            }
        });
    }

    private void confirmPayment() {
        Retrofit retrofit2 = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        API api = retrofit2.create(API.class);
        Model_Post_Basket_Confirm postModel = new Model_Post_Basket_Confirm();
        postModel.setToken(Config.getToken(this));
        Call<Model_Get_Basket_Confirm> model = api.confirmBasket(postModel);
        model.enqueue(new Callback<Model_Get_Basket_Confirm>() {
            @Override
            public void onResponse(Call<Model_Get_Basket_Confirm> call, Response<Model_Get_Basket_Confirm> response) {
                if (response.isSuccessful()) {
                    startActivity(new Intent(ShoppingBasket.this, Test_PreInvoice_List.class));
                } else {
                    Toast.makeText(ShoppingBasket.this, getString(R.string.confirm_basket), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Model_Get_Basket_Confirm> call, Throwable t) {
                Toast.makeText(ShoppingBasket.this, getString(R.string.confirm_basket), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void Get_Total_Amount() {

        Retrofit retrofit2 = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        API api = retrofit2.create(API.class);
        Model_Post_Basket_Amount model_post = new Model_Post_Basket_Amount();
        model_post.setToken(Config.getToken(ShoppingBasket.this));
        Call<List<Model_Get_Basket_Amount>> model = api.Get_Basket_Amount(model_post);
        model.enqueue(new Callback<List<Model_Get_Basket_Amount>>() {
            @Override
            public void onResponse(Call<List<Model_Get_Basket_Amount>> call, Response<List<Model_Get_Basket_Amount>> response) {
                if (response.isSuccessful()) {
                    List<Model_Get_Basket_Amount> list;
                    list = response.body();
                    if (list.size() != 0) {
                        String total_amount = "" + list.get(0).getAmount();
                        id = list.get(0).getId();
                        total_amount = edit_price(total_amount);
                        txvtotal_amount.setText(total_amount);
                        imv1.setVisibility(View.GONE);
                        txv1.setText("");
                        Get_Basket_List();
                    } else {
                        imv1.setVisibility(View.VISIBLE);
                        imv1.setImageResource(R.drawable.ti1);
                        txv1.setText(getString(R.string.nothing_exist));
                        lp.setVisibility(View.INVISIBLE);
                        swipeRefreshLayout.setRefreshing(false);
                    }

                } else {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(ShoppingBasket.this, getString(R.string.server_1151), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Model_Get_Basket_Amount>> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(ShoppingBasket.this, getString(R.string.server_1152), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void Get_Basket_List() {
        Retrofit retrofit2 = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        API api = retrofit2.create(API.class);
        Model_Post_Shopping_Basket model_post = new Model_Post_Shopping_Basket();
        model_post.setToken(Config.getToken(ShoppingBasket.this));
        model_post.setInvoice_id(id);
        model_post.setLCID(Config.getLCID(ShoppingBasket.this));
        Call<List<Model_Get_Shopping_Basket>> model = api.Get_Shoppin_Bascket(model_post);
        model.enqueue(new Callback<List<Model_Get_Shopping_Basket>>() {
            @Override
            public void onResponse(Call<List<Model_Get_Shopping_Basket>> call, Response<List<Model_Get_Shopping_Basket>> response) {
                complete_purchase.setVisibility(View.VISIBLE);
                if (response.isSuccessful()) {
                    lp.setVisibility(View.INVISIBLE);
                    List<Model_Get_Shopping_Basket> list = response.body();

                    ArrayList<Shopping_Basket_List_Model> temp;
                    temp = new ArrayList<>();

                    for (int i = 0; i < list.size(); i++) {
                        Shopping_Basket_List_Model entry = new Shopping_Basket_List_Model("" + list.get(i).getName(), "" + list.get(i).getNumber(), edit_price("" + list.get(i).getPrice()), edit_price("" + list.get(i).getTotal_amount()), list.get(i).getId());
                        temp.add(entry);
                    }
                    update.entries = new ArrayList<>(temp);
                    rcv = findViewById(R.id.rv1);
                    rcv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                    update.notifyDataSetChanged();
                    rcv.setVisibility(View.VISIBLE);
                    rcv.setAdapter(update);
                    swipeRefreshLayout.setRefreshing(false);

                    if (update.entries.size() == 0) {
                        complete_purchase.setVisibility(View.GONE);
                        imv1.setVisibility(View.VISIBLE);
                        imv1.setImageResource(R.drawable.ti1);
                        txv1.setText(getString(R.string.nothing_exist));
                        lp.setVisibility(View.INVISIBLE);
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    rcv.scheduleLayoutAnimation();

                } else {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(ShoppingBasket.this, getString(R.string.server_1161), Toast.LENGTH_LONG).show();
                    lp.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<Model_Get_Shopping_Basket>> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                lp.setVisibility(View.INVISIBLE);
                Toast.makeText(ShoppingBasket.this, getString(R.string.server_1162), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void getTotalAmount() {

        Retrofit retrofit2 = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        API api = retrofit2.create(API.class);
        Model_Post_Basket_Amount model_post = new Model_Post_Basket_Amount();
        model_post.setToken(Config.getToken(ShoppingBasket.this));
        Call<List<Model_Get_Basket_Amount>> model = api.Get_Basket_Amount(model_post);
        model.enqueue(new Callback<List<Model_Get_Basket_Amount>>() {
            @Override
            public void onResponse(Call<List<Model_Get_Basket_Amount>> call, Response<List<Model_Get_Basket_Amount>> response) {
                if (response.isSuccessful()) {
                    List<Model_Get_Basket_Amount> list;
                    list = response.body();
                    if (list.size() != 0) {
                        String total_amount = "" + list.get(0).getAmount();
                        id = list.get(0).getId();
                        total_amount = edit_price(total_amount);
                        txvtotal_amount.setText(total_amount);
                        imv1.setVisibility(View.GONE);
                        txv1.setText("");
                        getBasketList();
                    } else {
                        imv1.setVisibility(View.VISIBLE);
                        imv1.setImageResource(R.drawable.ti1);
                        txv1.setText(getString(R.string.nothing_exist));
                        lp.setVisibility(View.INVISIBLE);
                    }

                } else {
                    Toast.makeText(ShoppingBasket.this, getString(R.string.server_1151), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Model_Get_Basket_Amount>> call, Throwable t) {
                Toast.makeText(ShoppingBasket.this, getString(R.string.server_1152), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void getBasketList() {
        Retrofit retrofit2 = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        API api = retrofit2.create(API.class);
        Model_Post_Shopping_Basket model_post = new Model_Post_Shopping_Basket();
        model_post.setToken(Config.getToken(ShoppingBasket.this));
        model_post.setInvoice_id(id);
        model_post.setLCID(Config.getLCID(ShoppingBasket.this));
        Call<List<Model_Get_Shopping_Basket>> model = api.Get_Shoppin_Bascket(model_post);
        model.enqueue(new Callback<List<Model_Get_Shopping_Basket>>() {
            @Override
            public void onResponse(Call<List<Model_Get_Shopping_Basket>> call, Response<List<Model_Get_Shopping_Basket>> response) {
                if (response.isSuccessful()) {
                    List<Model_Get_Shopping_Basket> list = response.body();

                    if (list.size() == 0) {
                        imv1.setVisibility(View.VISIBLE);
                        imv1.setImageResource(R.drawable.ti1);
                        txv1.setText(getString(R.string.nothing_exist));
                        complete_purchase.setVisibility(View.GONE);
                    }


                } else {
                    Toast.makeText(ShoppingBasket.this, getString(R.string.server_1161), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Model_Get_Shopping_Basket>> call, Throwable t) {
                Toast.makeText(ShoppingBasket.this, getString(R.string.server_1162), Toast.LENGTH_LONG).show();
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

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(this, Constans.colorprimary));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Get_Total_Amount();
            }
        }, 100);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (isConnected) {
            preLoad();
            Get_Total_Amount();
        } else {
            Snackbar.make(coordinatorLayout, getString(R.string.not_connected), Snackbar.LENGTH_LONG).show();
        }
    }

    private void preLoad() {
        lp.setVisibility(View.VISIBLE);
        rcv.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ConnectionReceiver.connectionReceiverListener = null;
        unregisterReceiver(receiver);
    }

    /**
     * When Pressed on delete button in adapter
     **/
    @Override
    public void onDelete() {
        getTotalAmount();
    }
}
