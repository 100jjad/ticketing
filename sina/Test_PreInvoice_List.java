package com.example.ssoheyli.ticketing_newdesign.Financial;

import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ssoheyli.ticketing_newdesign.Helpfull.API;
import com.example.ssoheyli.ticketing_newdesign.Helpfull.Config;
import com.example.ssoheyli.ticketing_newdesign.Helpfull.ConnectionReceiver;
import com.example.ssoheyli.ticketing_newdesign.Helpfull.Constans;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Get_QouteList;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Get_invoice_detail_list;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Post_Qoute;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Post_invoice_detail_list;
import com.example.ssoheyli.ticketing_newdesign.Model.Pre_Invoice_List_Model1;
import com.example.ssoheyli.ticketing_newdesign.Model.Pre_invoce_detail_list_Model;
import com.example.ssoheyli.ticketing_newdesign.R;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Test_PreInvoice_List extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, ConnectionReceiver.ConnectionReceiverListener {

  // UI
  private SwipeRefreshLayout swipeRefreshLayout;
  private RecyclerView rcv;
  ProgressBar lp;
  TextView txv1;
  ImageView imv1;
  ConstraintLayout constraintLayout;

  // list
  List<Pre_invoce_detail_list_Model> detail_list_models = new ArrayList<>();
  ArrayList<Pre_Invoice_List_Model1> Invoice_List_temp;
  List<Model_Get_QouteList> invoice_list;
  ArrayList<Pre_Invoice_List_Model1> temp_invoice;
  Pre_Invoice_List_Model1 enrty;
  ArrayList<Pre_Invoice_List_Model1> empty;

  // Values
  Test_Adapter_PreInvoice_List update = new Test_Adapter_PreInvoice_List(Test_PreInvoice_List.this);
  ConnectionReceiver receiver;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setTheme(Constans.theme);
    setContentView(R.layout.activity_test_preivoice_list);
    rcv = findViewById(R.id.rv1);
    imv1 = findViewById(R.id.im1);
    lp = findViewById(R.id.dl);
    txv1 = findViewById(R.id.tv1);
    constraintLayout = findViewById(R.id.constraintLayout);

    //registering receiver
    receiver = new ConnectionReceiver();
    IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
    ConnectionReceiver.connectionReceiverListener = this;
    registerReceiver(receiver, intentFilter);
    //

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      imv1.setImageTintList(ContextCompat.getColorStateList(this, Constans.colorprimary));
    }
    txv1.setTextColor(ContextCompat.getColor(this, Constans.colorprimary));

    setupToolbar();

    swipeRefreshLayout = findViewById(R.id.refresher);
    swipeRefreshLayout.setOnRefreshListener(this);

    Sprite circle = new Circle();
    circle.setColor(ContextCompat.getColor(this, Constans.colorprimary));
    lp.setIndeterminateDrawable(circle);

  }

  public void get_List_QouteList() {
    Retrofit retrofit2 = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();
    API api = retrofit2.create(API.class);
    Model_Post_Qoute model_post = new Model_Post_Qoute();
    model_post.setToken(Config.getToken(getApplicationContext()));
    model_post.setType(1);
    Call<List<Model_Get_QouteList>> model = api.get_QouteList(model_post);
    model.enqueue(new Callback<List<Model_Get_QouteList>>() {
      @Override
      public void onResponse(Call<List<Model_Get_QouteList>> call, Response<List<Model_Get_QouteList>> response) {
        if (response.isSuccessful()) {
          lp.setVisibility(View.GONE);
          invoice_list = response.body();
          String amount = "";

          // Added new
          if (invoice_list.size() == 0) {
            imv1.setVisibility(View.VISIBLE);
            imv1.setImageResource(R.drawable.ti1);
            txv1.setText(getResources().getString(R.string.nothing_exist));
          }
          //

          Invoice_List_temp = new ArrayList<>();
          for (int i = 0; i < invoice_list.size(); i++) {
            amount = edit_price("" + invoice_list.get(i).getTotal_amount());

            Retrofit retrofit2 = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();
            API api = retrofit2.create(API.class);
            Model_Post_invoice_detail_list model_post = new Model_Post_invoice_detail_list();
            model_post.setInvoice_id(invoice_list.get(i).getNumber_factor());
            model_post.setToken(Config.getToken(getApplicationContext()));
            model_post.setLcid(Config.getLCID(Test_PreInvoice_List.this));
            Call<List<Model_Get_invoice_detail_list>> model = api.get_invoice_detail_list(model_post);

            final int finalI = i;
            final String finalAmount = amount;
            model.enqueue(new Callback<List<Model_Get_invoice_detail_list>>() {
              @Override
              public void onResponse(Call<List<Model_Get_invoice_detail_list>> call, Response<List<Model_Get_invoice_detail_list>> response) {
                if (response.isSuccessful()) {
                  List<Model_Get_invoice_detail_list> list;
                  list = response.body();

                  Pre_Invoice_List_Model1 enrty = new Pre_Invoice_List_Model1("" + invoice_list.get(finalI).getDate(), "" + invoice_list.get(finalI).getStatus(), invoice_list.get(finalI).getNumber_factor(), finalAmount, list);
                  Invoice_List_temp.add(enrty);

                  sort(Invoice_List_temp);

                  update.entries = new ArrayList<>(Invoice_List_temp);
                  sort(update.entries);
                  rcv = findViewById(R.id.rv1);
                  rcv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                  update.notifyDataSetChanged();
                  rcv.setVisibility(View.VISIBLE);
                  rcv.setAdapter(update);

                  if (update.entries.size() == 0) {
                    imv1.setVisibility(View.VISIBLE);
                    imv1.setImageResource(R.drawable.ti1);
                    txv1.setText(getResources().getString(R.string.nothing_exist));
                  } else {
                    imv1.setVisibility(View.GONE);
                    txv1.setText("");
                  }
                } else {
                  Toast.makeText(Test_PreInvoice_List.this, getString(R.string.server_1041), Toast.LENGTH_SHORT).show();
                }
              }

              @Override
              public void onFailure(Call<List<Model_Get_invoice_detail_list>> call, Throwable t) {
                Toast.makeText(Test_PreInvoice_List.this, getString(R.string.server_1042), Toast.LENGTH_SHORT).show();
              }
            });

          }
        } else {
          Toast.makeText(getApplicationContext(), getString(R.string.server_1131), Toast.LENGTH_SHORT).show();
        }
      }

      @Override
      public void onFailure(Call<List<Model_Get_QouteList>> call, Throwable t) {
        lp.setVisibility(View.GONE);
        Toast.makeText(getApplicationContext(), getString(R.string.server_1132), Toast.LENGTH_SHORT).show();
      }
    });

  }

  public void get_detail_invoice_list(int id, final int i, final String amount) {
    Retrofit retrofit2 = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();
    API api = retrofit2.create(API.class);
    Model_Post_invoice_detail_list model_post = new Model_Post_invoice_detail_list();
    model_post.setInvoice_id(id);
    model_post.setToken(Config.getToken(getApplicationContext()));
    model_post.setLcid(Config.getLCID(Test_PreInvoice_List.this));
    Call<List<Model_Get_invoice_detail_list>> model = api.get_invoice_detail_list(model_post);
    model.enqueue(new Callback<List<Model_Get_invoice_detail_list>>() {
      @Override
      public void onResponse(Call<List<Model_Get_invoice_detail_list>> call, Response<List<Model_Get_invoice_detail_list>> response) {
        lp.setVisibility(View.GONE);
        if (response.isSuccessful()) {
          List<Model_Get_invoice_detail_list> list;
          list = response.body();

          Pre_Invoice_List_Model1 enrty = new Pre_Invoice_List_Model1("" + invoice_list.get(i).getDate(), "" + invoice_list.get(i).getStatus(), invoice_list.get(i).getNumber_factor(), amount, list);
          Invoice_List_temp.add(enrty);

        } else {
          Toast.makeText(Test_PreInvoice_List.this, getString(R.string.server_1041), Toast.LENGTH_SHORT).show();

        }
      }

      @Override
      public void onFailure(Call<List<Model_Get_invoice_detail_list>> call, Throwable t) {
        lp.setVisibility(View.GONE);
        Toast.makeText(Test_PreInvoice_List.this, getString(R.string.server_1042), Toast.LENGTH_SHORT).show();
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

  private void setupToolbar() {
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    toolbar.setBackgroundColor(ContextCompat.getColor(this, Constans.colorprimary));
    getSupportActionBar().setTitle("");
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      finish();
    }
    return true;
  }

  @Override
  public void onRefresh() {
    swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        get_List_QouteList();
        swipeRefreshLayout.setRefreshing(false);
      }
    }, 2000);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    ConnectionReceiver.connectionReceiverListener = null;
    unregisterReceiver(receiver);
  }

  @Override
  public void onNetworkConnectionChanged(boolean isConnected) {
    if (isConnected) {
      //load
      preLoad();
      get_List_QouteList();
    } else {
      Snackbar.make(constraintLayout, getString(R.string.not_connected), Snackbar.LENGTH_LONG).show();
    }
  }

  private void preLoad() {
    lp.setVisibility(View.VISIBLE);
    rcv.setVisibility(View.INVISIBLE);
  }


  public void sort(List<Pre_Invoice_List_Model1> entries)
  {
    int[] id = new int[entries.size()];
    empty = new ArrayList<>();
    for (int i = 0; i < entries.size(); i++)
    {
      id[i] = entries.get(i).getFactor_number();
    }
    Arrays.sort(id);
    for (int j = entries.size() - 1; j > -1; j--)
    {
      for (int h = 0; h < entries.size(); h++)
      {
        if (id[j] == entries.get(h).getFactor_number())
        {
          Pre_Invoice_List_Model1 temp = new Pre_Invoice_List_Model1(entries.get(h).getDate(), entries.get(h).getStatus(), entries.get(h).getFactor_number(), entries.get(h).getTotal_amount(), entries.get(h).getDetails());
          empty.add(temp);
        }
      }
    }
    update.entries = new ArrayList<>(empty);
  }

}