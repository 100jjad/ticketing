package com.example.ssoheyli.ticketing_newdesign.Products;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
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
import com.example.ssoheyli.ticketing_newdesign.Helpfull.Constans;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Get_AddtoBasekt;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Post_AddtoBascket;
import com.example.ssoheyli.ticketing_newdesign.Products.Models.AttachFilesModel;
import com.example.ssoheyli.ticketing_newdesign.Products.Models.ProductDetailModelPost;
import com.example.ssoheyli.ticketing_newdesign.Products.Models.ProductDetailModel;
import com.example.ssoheyli.ticketing_newdesign.Products.Models.RelatedProductModelPost;
import com.example.ssoheyli.ticketing_newdesign.Products.Models.RelatedProductsModel;
import com.example.ssoheyli.ticketing_newdesign.R;
import com.example.ssoheyli.ticketing_newdesign.ShoppingBasket;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.smarteist.autoimageslider.SliderView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductDetailsActivity extends AppCompatActivity {

    private ProductDetailModel pModel;

    private Toolbar toolbar;
    private TextView p_title, p_price_no_sale, p_price_sale, tv_more_info, p_description, p_code;
    private TextView p_related_title, p_simliar_title, p_complete_title, p_replace_title;
    private ImageView imageView21;
    private ConstraintLayout btn_add_to_cart;
    private View view13, view12;
    private MaterialSpinner spinner;
    private CardView card;
    private ProgressBar loader;
    private NestedScrollView scrollView;
    private RecyclerView related_list, similar_list, complete_list, replace_list;
    private RecyclerView propertyList;
    private RelatedProductsAdapter adapter;
    private RelatedProductsAdapter adapter_similar;
    private RelatedProductsAdapter adapter_complete;
    private RelatedProductsAdapter adapter_replace;
    private PropertyListAdapter adapter_property;
    private SliderView sliderView;
    private LinearLayout linearLayout;

    private Animation btnanim;

    private boolean isOpened = false;
    private int MAX_LINES = 10;
    private int number_Product = 0;

    private int DEFAULT_ID = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Constans.theme);
        setContentView(R.layout.activity_product_detail);

        initViews();
        setupToolbar();

        loader.setVisibility(View.VISIBLE);
        card.setVisibility(View.GONE);

        if (getIntent().getExtras() != null) {
            int id = getIntent().getIntExtra("productId", 1);
            loadProductDetails(id);
        } else {
            loadProductDetails(DEFAULT_ID);
        }

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isOpened) {

                    animateTextViewMaxLinesChange(p_description, MAX_LINES, 200);
                    //p_description.setMaxLines(MAX_LINES);
                    tv_more_info.setText(getString(R.string.product_detail_moreinfo));
                    imageView21.setImageResource(R.drawable.arrow_down);
                    isOpened = false;

                } else {

                    animateTextViewMaxLinesChange(p_description, 999, 200);
                    //p_description.setMaxLines(999);
                    tv_more_info.setText(getString(R.string.product_detail_close));
                    imageView21.setImageResource(R.drawable.arrow_up);
                    isOpened = true;

                }

            }
        });

        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                number_Product = position;
            }
        });

        btn_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });

    }

    private void loadProductDetails(int default_id) {

        Retrofit getDetails = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        API api = getDetails.create(API.class);
        ProductDetailModelPost postModel = new ProductDetailModelPost(default_id, Config.getLCID(ProductDetailsActivity.this));
        Call<ProductDetailModel> model = api.getProductDetails(postModel);
        model.enqueue(new Callback<ProductDetailModel>() {
            @Override
            public void onResponse(Call<ProductDetailModel> call, Response<ProductDetailModel> response) {
                loader.setVisibility(View.GONE);
                card.setVisibility(View.VISIBLE);
                if (response.isSuccessful()) {
                    pModel = response.body();
                    loadUI(pModel);
                } else {
                    Toast.makeText(ProductDetailsActivity.this, getString(R.string.product_detail_errors), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductDetailModel> call, Throwable t) {
                loader.setVisibility(View.GONE);
                Toast.makeText(ProductDetailsActivity.this, "Connection Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadUI(ProductDetailModel pModel) {

        p_title.setText(pModel.getName());
        toolbar.setTitle(pModel.getName());
        p_code.setText(pModel.getProductNumber());
        List<AttachFilesModel> attachFilesModels = pModel.getAttachFilesModels();

        if (String.valueOf(pModel.getDiscount()).equals("0")) {

            p_price_sale.setVisibility(View.GONE);
            p_price_no_sale.setTextSize(16);
            p_price_no_sale.setText(edit_price(String.valueOf(pModel.getPrice())));
            p_price_no_sale.setTextColor(ContextCompat.getColor(ProductDetailsActivity.this, Constans.colorprimary));

        } else {
            p_price_no_sale.setText(edit_price(String.valueOf(pModel.getPrice())));
            p_price_no_sale.setPaintFlags(p_price_no_sale.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            long finalPrice = pModel.getPrice() - pModel.getDiscount();
            p_price_sale.setText(edit_price(String.valueOf(finalPrice)));

            p_price_sale.setTextColor(ContextCompat.getColor(ProductDetailsActivity.this, Constans.colorprimary));
        }

        p_description.setText(pModel.getDescription());

        // Load images
        sliderView.setVisibility(View.VISIBLE);
        SliderAdapterImage sliderAdapter = new SliderAdapterImage(ProductDetailsActivity.this, attachFilesModels);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorSelectedColor(ContextCompat.getColor(this, Constans.colorprimary));

        // Load property list
        if (pModel.getPropertyModels().size() == 0) {
            propertyList.setVisibility(View.GONE);
        } else {
            adapter_property = new PropertyListAdapter(pModel.getPropertyModels(), ProductDetailsActivity.this);
            LinearLayoutManager layoutManager = new LinearLayoutManager(ProductDetailsActivity.this, LinearLayoutManager.VERTICAL, false);
            propertyList.setLayoutManager(layoutManager);
            propertyList.setAdapter(adapter_property);
            adapter_property.notifyDataSetChanged();
        }

        // Load Related Lists
        loadRelatedProducts(pModel.getId());
        loadSimilarProducts(pModel.getId());
        loadCompleteProducts(pModel.getId());
        loadReplaceProducts(pModel.getId());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.smoothScrollTo(0, 0);
            }
        }, 100);

    }

    private void loadRelatedProducts(int id) {

        Retrofit getRelatedItems = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        API api = getRelatedItems.create(API.class);
        RelatedProductModelPost postModel = new RelatedProductModelPost(id, Config.getLCID(ProductDetailsActivity.this), 2);
        Call<List<RelatedProductsModel>> models = api.getRelatedProducts(postModel);
        models.enqueue(new Callback<List<RelatedProductsModel>>() {
            @Override
            public void onResponse(Call<List<RelatedProductsModel>> call, Response<List<RelatedProductsModel>> response) {
                if (response.isSuccessful()) {
                    List<RelatedProductsModel> list = response.body();

                    if (list.size() != 0) {
                        p_related_title.setVisibility(View.VISIBLE);
                        related_list.setVisibility(View.VISIBLE);
                        scrollView.scrollTo(0, 0);
                    }

                    adapter = new RelatedProductsAdapter(ProductDetailsActivity.this, list);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(ProductDetailsActivity.this, LinearLayoutManager.HORIZONTAL, true);
                    layoutManager.setReverseLayout(true);
                    related_list.setLayoutManager(layoutManager);
                    related_list.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(ProductDetailsActivity.this, getString(R.string.nosimilaritemfound), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<RelatedProductsModel>> call, Throwable t) {
                Toast.makeText(ProductDetailsActivity.this, "Connection failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadSimilarProducts(int id) {

        Retrofit getSimilarItems = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        API api = getSimilarItems.create(API.class);
        RelatedProductModelPost postModel = new RelatedProductModelPost(id, Config.getLCID(ProductDetailsActivity.this), 1);
        Call<List<RelatedProductsModel>> models = api.getRelatedProducts(postModel);
        models.enqueue(new Callback<List<RelatedProductsModel>>() {
            @Override
            public void onResponse(Call<List<RelatedProductsModel>> call, Response<List<RelatedProductsModel>> response) {
                if (response.isSuccessful()) {
                    List<RelatedProductsModel> list = response.body();

                    if (list.size() != 0) {
                        p_simliar_title.setVisibility(View.VISIBLE);
                        similar_list.setVisibility(View.VISIBLE);
                        scrollView.scrollTo(0, 0);
                    }

                    adapter_similar = new RelatedProductsAdapter(ProductDetailsActivity.this, list);
                    LinearLayoutManager layoutManager_similar = new LinearLayoutManager(ProductDetailsActivity.this, LinearLayoutManager.HORIZONTAL, true);
                    layoutManager_similar.setReverseLayout(true);
                    similar_list.setLayoutManager(layoutManager_similar);
                    similar_list.setAdapter(adapter_similar);
                    adapter_similar.notifyDataSetChanged();
                } else {
                    Toast.makeText(ProductDetailsActivity.this, getString(R.string.nosimilaritemfound), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<RelatedProductsModel>> call, Throwable t) {
                Toast.makeText(ProductDetailsActivity.this, "Connection failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadCompleteProducts(int id) {

        Retrofit getSimilarItems = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        API api = getSimilarItems.create(API.class);
        RelatedProductModelPost postModel = new RelatedProductModelPost(id, Config.getLCID(ProductDetailsActivity.this), 3);
        Call<List<RelatedProductsModel>> models = api.getRelatedProducts(postModel);
        models.enqueue(new Callback<List<RelatedProductsModel>>() {
            @Override
            public void onResponse(Call<List<RelatedProductsModel>> call, Response<List<RelatedProductsModel>> response) {
                if (response.isSuccessful()) {
                    List<RelatedProductsModel> list = response.body();

                    if (list.size() != 0) {
                        p_complete_title.setVisibility(View.VISIBLE);
                        complete_list.setVisibility(View.VISIBLE);
                        scrollView.scrollTo(0, 0);
                    }

                    adapter_complete = new RelatedProductsAdapter(ProductDetailsActivity.this, list);
                    LinearLayoutManager layoutManager_complete = new LinearLayoutManager(ProductDetailsActivity.this, LinearLayoutManager.HORIZONTAL, true);
                    layoutManager_complete.setReverseLayout(true);
                    complete_list.setLayoutManager(layoutManager_complete);
                    complete_list.setAdapter(adapter_complete);
                    adapter_complete.notifyDataSetChanged();
                } else {
                    Toast.makeText(ProductDetailsActivity.this, getString(R.string.nosimilaritemfound), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<RelatedProductsModel>> call, Throwable t) {
                Toast.makeText(ProductDetailsActivity.this, "Connection failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadReplaceProducts(int id) {

        Retrofit getSimilarItems = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        API api = getSimilarItems.create(API.class);
        RelatedProductModelPost postModel = new RelatedProductModelPost(id, Config.getLCID(ProductDetailsActivity.this), 4);
        Call<List<RelatedProductsModel>> models = api.getRelatedProducts(postModel);
        models.enqueue(new Callback<List<RelatedProductsModel>>() {
            @Override
            public void onResponse(Call<List<RelatedProductsModel>> call, Response<List<RelatedProductsModel>> response) {
                if (response.isSuccessful()) {
                    List<RelatedProductsModel> list = response.body();

                    if (list.size() != 0) {
                        p_replace_title.setVisibility(View.VISIBLE);
                        replace_list.setVisibility(View.VISIBLE);
                        scrollView.scrollTo(0, 0);
                    }

                    adapter_replace = new RelatedProductsAdapter(ProductDetailsActivity.this, list);
                    LinearLayoutManager layoutManager_replace = new LinearLayoutManager(ProductDetailsActivity.this, LinearLayoutManager.HORIZONTAL, true);
                    layoutManager_replace.setReverseLayout(true);
                    replace_list.setLayoutManager(layoutManager_replace);
                    replace_list.setAdapter(adapter_replace);
                    adapter_replace.notifyDataSetChanged();
                } else {
                    Toast.makeText(ProductDetailsActivity.this, getString(R.string.nosimilaritemfound), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<RelatedProductsModel>> call, Throwable t) {
                Toast.makeText(ProductDetailsActivity.this, "Connection failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addToCart() {
        btn_add_to_cart.startAnimation(btnanim);
        Retrofit retrofit2 = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        API myApi2 = retrofit2.create(API.class);
        Model_Post_AddtoBascket model_post = new Model_Post_AddtoBascket();

        model_post.setNumber(number_Product + 1);

        model_post.setProductid(pModel.getId());
        model_post.setToken(Config.getToken(ProductDetailsActivity.this));
        Call<Model_Get_AddtoBasekt> model = myApi2.Add_toBasket(model_post);
        model.enqueue(new Callback<Model_Get_AddtoBasekt>() {
            @Override
            public void onResponse(Call<Model_Get_AddtoBasekt> call, Response<Model_Get_AddtoBasekt> response) {
                if (response.isSuccessful()) {
                    int res = response.body().getResult();
                    if (res == -1) {
                        Toast.makeText(ProductDetailsActivity.this, getString(R.string.product_detail_count_reenter), Toast.LENGTH_SHORT).show();
                    } else if (res == -2) {
                        Toast.makeText(ProductDetailsActivity.this, getString(R.string.product_detail_price_na), Toast.LENGTH_SHORT).show();

                    } else if (res == -3) {
                        Toast.makeText(ProductDetailsActivity.this, getString(R.string.product_detail_server), Toast.LENGTH_SHORT).show();

                    } else if (res > 0 || res == 0) {
                        Toast.makeText(ProductDetailsActivity.this, getString(R.string.product_detail_added), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ProductDetailsActivity.this, ShoppingBasket.class));
                    } else if (res == -44) {
                        Toast.makeText(ProductDetailsActivity.this, getString(R.string.product_detail_invalid_token), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ProductDetailsActivity.this, getString(R.string.product_detail_server), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Model_Get_AddtoBasekt> call, Throwable t) {
                Toast.makeText(ProductDetailsActivity.this, getString(R.string.product_detail_server), Toast.LENGTH_SHORT).show();
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
        loader = findViewById(R.id.loader);
        Sprite circle = new Circle();
        circle.setColor(ContextCompat.getColor(this, Constans.colorprimary));
        loader.setIndeterminateDrawable(circle);

        card = findViewById(R.id.card);

        toolbar = findViewById(R.id.toolbar);

        p_title = findViewById(R.id.p_title);
        p_title.setTextColor(ContextCompat.getColor(this, Constans.colorprimary));

        p_price_no_sale = findViewById(R.id.p_price_no_sale);

        p_price_sale = findViewById(R.id.p_price_sale);

        tv_more_info = findViewById(R.id.tv_more_info);

        p_description = findViewById(R.id.p_description);

        p_code = findViewById(R.id.p_code);

        btn_add_to_cart = findViewById(R.id.btn_add_to_cart);
        btn_add_to_cart.setBackground(getResources().getDrawable(Constans.bg_item_menu2));

        view13 = findViewById(R.id.view13);
//        view13.setBackgroundColor(ContextCompat.getColor(this, Constans.colorprimary));

        view12 = findViewById(R.id.view12);
//        view12.setBackgroundColor(ContextCompat.getColor(this, Constans.colorprimary));

        spinner = findViewById(R.id.materialSpinner);
        spinner.setBackgroundColor(Color.parseColor("#f5f5f5"));
        spinner.setTextColor(ContextCompat.getColor(this, Constans.colorprimary));
        spinner.setItems("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");

        p_related_title = findViewById(R.id.p_related_title);
        p_related_title.setTextColor(ContextCompat.getColor(ProductDetailsActivity.this, Constans.colorprimary));

        p_simliar_title = findViewById(R.id.p_simliar_title);
        p_simliar_title.setTextColor(ContextCompat.getColor(ProductDetailsActivity.this, Constans.colorprimary));

        p_complete_title = findViewById(R.id.p_complete_title);
        p_complete_title.setTextColor(ContextCompat.getColor(ProductDetailsActivity.this, Constans.colorprimary));

        p_replace_title = findViewById(R.id.p_replace_title);
        p_replace_title.setTextColor(ContextCompat.getColor(ProductDetailsActivity.this, Constans.colorprimary));

        related_list = findViewById(R.id.related_list);
        similar_list = findViewById(R.id.similar_list);
        complete_list = findViewById(R.id.complete_list);
        replace_list = findViewById(R.id.replace_list);

        imageView21 = findViewById(R.id.imageView21);
        scrollView = findViewById(R.id.scrollView);

        sliderView = findViewById(R.id.imageSlider);

        btnanim = AnimationUtils.loadAnimation(this, R.anim.button_anim1);

        propertyList = findViewById(R.id.propertyList);

        linearLayout = findViewById(R.id.linearLayout);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public static void animateTextViewMaxLinesChange(final TextView textView, final int maxLines, final int duration) {
        final int startHeight = textView.getMeasuredHeight();
        textView.setMaxLines(maxLines);/* w  ww . ja  v a  2 s  .co m*/
        textView.measure(View.MeasureSpec.makeMeasureSpec(
                textView.getWidth(), View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0,
                        View.MeasureSpec.UNSPECIFIED));
        final int endHeight = textView.getMeasuredHeight();
        ObjectAnimator animation = ObjectAnimator.ofInt(textView,
                "maxHeight", startHeight, endHeight);
        animation.addListener(new AnimatorListenerAdapter() {
                                  @Override
                                  public void onAnimationEnd(Animator animation) {
                                      if (textView.getMaxHeight() == endHeight) {
                                          textView.setMaxLines(maxLines);
                                      }
                                  }
                              }

        );
        animation.setDuration(duration).start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return true;
    }
}
