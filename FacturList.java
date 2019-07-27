package com.example.ssoheyli.ticketing_newdesign.Financial;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.agrawalsuneet.dotsloader.loaders.LazyLoader;
import com.example.ssoheyli.ticketing_newdesign.Helpfull.API;
import com.example.ssoheyli.ticketing_newdesign.Helpfull.Config;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Get_QouteList;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Get_invoice_detail_list;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Post_Qoute;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Post_invoice_detail_list;
import com.example.ssoheyli.ticketing_newdesign.Model.Pre_Invoice_List_Model;
import com.example.ssoheyli.ticketing_newdesign.Model.Pre_invoce_detail_list_Model;
import com.example.ssoheyli.ticketing_newdesign.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FacturList extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    // UI
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rcv;
    LazyLoader lp;
    TextView txv1;
    ImageView imv1;
    List<Pre_invoce_detail_list_Model> detail_list_models = new ArrayList<>();

    // Values
    List<Model_Get_QouteList> list;
    FacturListAdapter update = new FacturListAdapter(FacturList.this);
    Pre_Invoice_List_Model enrty;
    ArrayList<Pre_Invoice_List_Model> temp_invoice;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factur_list);
        rcv = findViewById(R.id.factur_list);
        imv1 = findViewById(R.id.im1);
        lp = findViewById(R.id.dl);
        txv1 = findViewById(R.id.tv1);

        setupToolbar();

        swipeRefreshLayout = findViewById(R.id.refresher);
        swipeRefreshLayout.setOnRefreshListener(this);

        get_List_QouteList();
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
                lp.setVisibility(View.INVISIBLE);
                if (response.isSuccessful()) {
                    list = response.body();
                    String amount = "";
                    ArrayList<Pre_Invoice_List_Model> temp_invoice;
                    temp_invoice = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        amount = edit_price("" + list.get(i).getTotal_amount());
                        enrty = new Pre_Invoice_List_Model("" + list.get(i).getDate(), "" + list.get(i).getStatus(), list.get(i).getNumber_factor(), amount, detail_list_models);
                        // invoice updated -> add details to entry
                        temp_invoice.add(enrty);
                    }

                    for (int i = 0; i < temp_invoice.size(); i++) {
                        get_detail_invoice_list(temp_invoice.get(i));
                    }


                    if (update.entries.size() == 0) {
                        imv1.setVisibility(View.VISIBLE);
                        imv1.setImageResource(R.drawable.ti1);
                        txv1.setText(R.string.nothing_exist);
                    } else {
                        imv1.setVisibility(View.GONE);
                        txv1.setText("");
                    }
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.server_1131), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Model_Get_QouteList>> call, Throwable t) {
                lp.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), getString(R.string.server_1132), Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void get_detail_invoice_list(final Pre_Invoice_List_Model model2) {
        Retrofit retrofit2 = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        API api = retrofit2.create(API.class);
        Model_Post_invoice_detail_list model_post = new Model_Post_invoice_detail_list();
        model_post.setInvoice_id(model2.getFactor_number());
        model_post.setToken(Config.getToken(getApplicationContext()));
        Call<List<Model_Get_invoice_detail_list>> model = api.get_invoice_detail_list(model_post);
        model.enqueue(new Callback<List<Model_Get_invoice_detail_list>>() {
            @Override
            public void onResponse(Call<List<Model_Get_invoice_detail_list>> call, Response<List<Model_Get_invoice_detail_list>> response) {
                if (response.isSuccessful()) {
                    List<Model_Get_invoice_detail_list> list;
                    list = response.body();
                    String price = "", discount = "", total_amount = "";

                    ArrayList<Pre_invoce_detail_list_Model> temp;
                    temp = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        price = edit_price("" + list.get(i).getPrice());
                        discount = edit_price("" + list.get(i).getDiscount());
                        total_amount = edit_price("" + list.get(i).getTotal_amount());
                        Pre_invoce_detail_list_Model enrty = new Pre_invoce_detail_list_Model(list.get(i).getName(), price, discount, list.get(i).getNumber(), total_amount);
                        temp.add(enrty);
                        if (i == list.size() - 1) {
                            update.entries = new ArrayList<>(temp_invoice);
                            rcv = findViewById(R.id.factur_list);
                            rcv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                            update.notifyDataSetChanged();
                            rcv.setAdapter(update);
                        }
                    }

                    //
                    model2.setDetails(temp);
                } else {
                    Toast.makeText(FacturList.this, getString(R.string.server_1041), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<List<Model_Get_invoice_detail_list>> call, Throwable t) {
                Toast.makeText(FacturList.this, getString(R.string.server_1042), Toast.LENGTH_SHORT).show();
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
        getSupportActionBar().setTitle(getString(R.string.menu_factor));
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
}
