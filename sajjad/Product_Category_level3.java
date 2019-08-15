package com.example.ssoheyli.ticketing_newdesign.Products;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.ssoheyli.ticketing_newdesign.Helpfull.API;
import com.example.ssoheyli.ticketing_newdesign.Helpfull.Config;
import com.example.ssoheyli.ticketing_newdesign.Helpfull.Constans;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_GetProductList;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Get_Category_Level1;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Post_Category_Level1;
import com.example.ssoheyli.ticketing_newdesign.Model.ProductListModel;
import com.example.ssoheyli.ticketing_newdesign.Model.Product_Scroll_Model;
import com.example.ssoheyli.ticketing_newdesign.Products.Models.Product_List_Post_Model;
import com.example.ssoheyli.ticketing_newdesign.R;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;

import java.util.ArrayList;
import java.util.List;

import static com.example.ssoheyli.ticketing_newdesign.MainActivity.category_list;

public class Product_Category_level3 extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    // SINA CHANGES
    ImageView iv_search;
    ImageView iv_filter;
    TextView textView16;
    SearchView searchView;
    Toolbar toolbar;
    SwipeRefreshLayout refresher;

    boolean flag_shown = false;
    //

    List<Model_GetProductList> list;
    Product_List_Adapter adapter = new Product_List_Adapter(Product_Category_level3.this);

    Category_level_3_List_Adapter update = new Category_level_3_List_Adapter(Product_Category_level3.this);
    ;
    ArrayList<Product_Scroll_Model> temp = new ArrayList<>();
    RecyclerView rcv_category, rcv_product;
    private ProgressBar loader;
    FloatingActionButton fbn_scroll_to_top;
    NestedScrollView scrollView;

    TextView txv1, txv2;
    CardView cardView;
    ImageView imv1;

    int category_id = 0;
    int child_count = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Constans.theme);
        setContentView(R.layout.product_category_level3);
        init_view();
        setupToolbar();

        click();


        if (getIntent().getExtras() != null) {
            category_id = getIntent().getIntExtra("category_id", 0);
            child_count = getIntent().getIntExtra("child_count", 0);
        }

        if (child_count > 0) {
            get_category_list(category_id);
        } else {
            txv1.setVisibility(View.GONE);
            txv2.setVisibility(View.GONE);
            cardView.setVisibility(View.GONE);
        }

        get_Product_list(category_id);

        // SINA CHANGES
        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag_shown) {
                    cancelSearch();
                    flag_shown = false;
                } else {
                    showSearch();
                    flag_shown = true;
                }
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                cancelSearch();
                flag_shown = false;
                return false;
            }
        });
        //
    }

    //SINA
    private void showSearch() {
        int x = searchView.getMeasuredWidth() / 2;
        int y = searchView.getMeasuredHeight() / 2;

        int finalRadius = Math.max(searchView.getWidth(), searchView.getHeight()) / 2;
        Animator anim = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            anim = ViewAnimationUtils.createCircularReveal(searchView, x, y, 0, finalRadius);
            anim.setDuration(100);
            searchView.setVisibility(View.VISIBLE);
            toolbar.setVisibility(View.GONE);
            anim.start();
            searchView.setFocusable(true);
            searchView.setIconified(false);
            searchView.requestFocusFromTouch();
        } else {
            searchView.setVisibility(View.VISIBLE);
            toolbar.setVisibility(View.GONE);
            searchView.setFocusable(true);
            searchView.setIconified(false);
            searchView.requestFocusFromTouch();
        }
    }

    private void cancelSearch() {
        int x = searchView.getMeasuredWidth() / 2;
        int y = searchView.getMeasuredHeight() / 2;
        int initialRadius = searchView.getWidth() / 2;

        Animator anim = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            anim = ViewAnimationUtils.createCircularReveal(searchView, x, y, initialRadius, 0);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    searchView.setVisibility(View.INVISIBLE);
                    toolbar.setVisibility(View.VISIBLE);
                }
            });

            anim.setDuration(100);
            anim.start();
        } else {
            searchView.setVisibility(View.INVISIBLE);
            toolbar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        if (flag_shown) {
            cancelSearch();
            flag_shown = false;
        } else {
            super.onBackPressed();
        }
    }
    //

    public void init_view() {
        rcv_category = findViewById(R.id.rv_category_level3);
        rcv_product = findViewById(R.id.rv_product_list);
        rcv_product = findViewById(R.id.rv_product_list);

        loader = findViewById(R.id.loader);
        Sprite circle = new Circle();
        circle.setColor(ContextCompat.getColor(this, Constans.colorprimary));
        loader.setIndeterminateDrawable(circle);

        fbn_scroll_to_top = findViewById(R.id.floatactionbutton);
        scrollView = findViewById(R.id.scrollView);
        txv1 = findViewById(R.id.tv_category_title);
        txv2 = findViewById(R.id.tv_category_title1);
        cardView = findViewById(R.id.cardView);
        imv1 = findViewById(R.id.im1);

        //Sina changes
        iv_search = findViewById(R.id.iv_search);
        iv_filter = findViewById(R.id.iv_filter);
        textView16 = findViewById(R.id.textView16);
        searchView = findViewById(R.id.searchView);
        toolbar = findViewById(R.id.toolbar);
        refresher = findViewById(R.id.refresher);
        refresher.setOnRefreshListener(this);
        //
    }

    public void click() {
        fbn_scroll_to_top.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                scrollView.smoothScrollTo(0, 0);
            }
        });
    }

    public void get_category_list(int position) {
        Retrofit retrofit2 = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        API api = retrofit2.create(API.class);
        Model_Post_Category_Level1 model_post = new Model_Post_Category_Level1();
        model_post.setCategory_id(position);
        model_post.setLanguage_id(1);
        Call<List<Model_Get_Category_Level1>> model = api.get_category_level1(model_post);

        model.enqueue(new Callback<List<Model_Get_Category_Level1>>() {
            @Override
            public void onResponse(Call<List<Model_Get_Category_Level1>> call, Response<List<Model_Get_Category_Level1>> response) {
                if (response.isSuccessful()) {
                    loader.setVisibility(View.INVISIBLE);
                    category_list = response.body();
                    ArrayList<Product_Scroll_Model> temp;
                    temp = new ArrayList<>();
                    for (int i = 0; i < category_list.size(); i++) {
                        Product_Scroll_Model entry = new Product_Scroll_Model(category_list.get(i).getCategory_title(), category_list.get(i).getCategory_id(), category_list.get(i).getChild_count(), i);
                        temp.add(entry);
                    }
                    update.entries = new ArrayList<>(temp);
                    rcv_category.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                    update.notifyDataSetChanged();
                    rcv_category.setAdapter(update);

                    if (update.entries.size() == 0) {
                        imv1.setVisibility(View.VISIBLE);
                        imv1.setImageResource(R.drawable.ti1);
                        txv1.setText(getString(R.string.nothing_exist));
                        txv1.setVisibility(View.GONE);
                        txv2.setVisibility(View.GONE);
                        cardView.setVisibility(View.GONE);
                    } else {
                        imv1.setVisibility(View.GONE);
                        txv1.setText("");
                    }

                    scroll_to_top();
                } else {
                    loader.setVisibility(View.INVISIBLE);
                    try {
                        Toast.makeText(Product_Category_level3.this, getString(R.string.server_1061), Toast.LENGTH_SHORT).show();
                    } catch (NullPointerException exeption) {
                        exeption.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Model_Get_Category_Level1>> call, Throwable t) {
                String te = t.toString();
                loader.setVisibility(View.INVISIBLE);
                try {
                    Toast.makeText(Product_Category_level3.this, getString(R.string.server_1062), Toast.LENGTH_SHORT).show();
                } catch (NullPointerException exeption) {
                    exeption.printStackTrace();
                }
            }
        });
    }

    public void scroll_to_top() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fbn_scroll_to_top.performClick();
            }
        }, 100);
    }

    public void get_Product_list(int position) {

        Retrofit retrofit2 = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        API api = retrofit2.create(API.class);
        Product_List_Post_Model post_model = new Product_List_Post_Model(
                position,
                Config.getLCID(Product_Category_level3.this),
                0,
                10
        );
        Call<List<Model_GetProductList>> model = api.getProductList(post_model);
        model.enqueue(new Callback<List<Model_GetProductList>>() {
            @Override
            public void onResponse(Call<List<Model_GetProductList>> call, Response<List<Model_GetProductList>> response) {
                loader.setVisibility(View.INVISIBLE);
                if (response.isSuccessful()) {
                    list = response.body();

                    ArrayList<ProductListModel> temp;
                    temp = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        String price = edit_price("" + list.get(i).getPrice());
                        try {
                            byte[] bytearray = list.get(i).getAttachfile().getImage();

                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytearray, 0, bytearray.length);
                            ProductListModel enrty = new ProductListModel("" + list.get(i).getName(), price, "" + list.get(i).getDiscount(), "" + list.get(i).getNumber(), bitmap);
                            temp.add(enrty);
                        } catch (NullPointerException exception) {
                            byte[] bytearray = Base64.decode(ProductList.captcha, Base64.DEFAULT);
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytearray, 0, bytearray.length);
                            ProductListModel enrty = new ProductListModel("" + list.get(i).getName(), price, "" + list.get(i).getDiscount(), "" + list.get(i).getNumber(), bitmap);
                            temp.add(enrty);
                        }

                    }
                    adapter.entries = new ArrayList<>(temp);
                    rcv_product.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                    update.notifyDataSetChanged();
                    rcv_product.setAdapter(adapter);

                    if (adapter.entries.size() == 0) {
                        loader.setVisibility(View.GONE);
                        imv1.setVisibility(View.VISIBLE);
                        imv1.setImageResource(R.drawable.ti1);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            imv1.setImageTintList(ContextCompat.getColorStateList(Product_Category_level3.this, Constans.colorprimary));
                            txv1.setTextColor(ContextCompat.getColor(Product_Category_level3.this, Constans.colorprimary));
                        }

                        txv1.setText(getString(R.string.product_list_no_item));
                    } else {
                        loader.setVisibility(View.GONE);
                        imv1.setVisibility(View.GONE);
                        txv1.setText(getString(R.string.nothing_exist));
                    }
                } else {
                    Toast.makeText(Product_Category_level3.this, getString(R.string.server_1021), Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<List<Model_GetProductList>> call, Throwable t) {
                loader.setVisibility(View.INVISIBLE);
                Toast.makeText(Product_Category_level3.this, getString(R.string.server_1022), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        toolbar.setBackgroundColor(ContextCompat.getColor(this, Constans.colorprimary));
        getSupportActionBar().setTitle("");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
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

    //SINA
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (child_count > 0) {
                    get_category_list(category_id);
                } else {
                    txv1.setVisibility(View.GONE);
                    txv2.setVisibility(View.GONE);
                    cardView.setVisibility(View.GONE);
                }

                get_Product_list(category_id);
                refresher.setRefreshing(false);
            }
        }, 2000);
    }
    //
}
