package com.example.ssoheyli.ticketing_newdesign;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Pair;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ssoheyli.ticketing_newdesign.Financial.Bank_Receipt;
import com.example.ssoheyli.ticketing_newdesign.Financial.Factor_List;
import com.example.ssoheyli.ticketing_newdesign.Financial.Internet_payment;
import com.example.ssoheyli.ticketing_newdesign.Financial.Invoice_List;
import com.example.ssoheyli.ticketing_newdesign.Financial.Test_PreInvoice_List;
import com.example.ssoheyli.ticketing_newdesign.Financial.Turnover_list;
import com.example.ssoheyli.ticketing_newdesign.Fragment.About_Us;
import com.example.ssoheyli.ticketing_newdesign.Helpfull.API;
import com.example.ssoheyli.ticketing_newdesign.Helpfull.Config;
import com.example.ssoheyli.ticketing_newdesign.Helpfull.Constans;
import com.example.ssoheyli.ticketing_newdesign.Helpfull.CustomTypefaceSpan;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Get_Basket_Amount;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Get_Shopping_Basket;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Post_Basket_Amount;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Post_Shopping_Basket;
import com.example.ssoheyli.ticketing_newdesign.Model.Shopping_Basket_List_Model;
import com.example.ssoheyli.ticketing_newdesign.Model.dashbord_Model;
import com.example.ssoheyli.ticketing_newdesign.Ticket.TicketList;
import com.example.ssoheyli.ticketing_newdesign.Ticket.TicketListwithViewPager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
{
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ConstraintLayout  products , invoice , increas_amount , turnover , factor , internet_payment , bank_recipt , setting , news;
    LinearLayout increaseAmount;
    ImageView imageView12, imageView11, imageView8 , imageView7 , imageView10 , imageView9 , imageView13 , imageView14 , imageView15 , imageView16;
    TextView txv1 , txv2 , txv3 , txv4 , txv5 , txv6 , txv7 , txv8;
    RelativeLayout ticket;
    private int time_interval = 2000;
    private long oldCurrentTimeMillis;
    int Position =8;
    TabLayout tabLayout;
    ImageView imvshop;
    RecyclerView rcv;
    ViewPager viewPager;
    int [] layout = {R.layout.dashbord_pager_1 , R.layout.dashbord_pager_2 , R.layout.dashbord_pager_3 , R.layout.dashbord_pager_4};
    viewpager_adapter_dashbord viewpager_adapter_dashbord;
    private LinearLayout Dots_Layout;
    ImageView[] dots;
    ImageView imv_next , imv_previous;
    ArrayList<dashbord_Model> temp;
    boolean flag_navigation = false;
//    dashbord_adapter update = new dashbord_adapter(MainActivity.this);
    int id;
    private boolean flagShown = false;
    String TAG;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Constans.theme);
        setContentView(R.layout.activity_main_with_viewpager2);
        setupToolbar();
        setupNavigationView();
        imvshop = findViewById(R.id.ivshop);
        rcv = findViewById(R.id.rv);
        increaseAmount = findViewById(R.id.increase_container);
        increas_amount = findViewById(R.id.increase_amount);
        products = findViewById(R.id.products);
        ticket = findViewById(R.id.ticket);
        invoice = findViewById(R.id.invoice);
        turnover = findViewById(R.id.turnover);
        factor = findViewById(R.id.factor);
        products = findViewById(R.id.products);
        internet_payment = findViewById(R.id.internet_payment);
        bank_recipt = findViewById(R.id.bank_recipt);
        setting = findViewById(R.id.setting);
        news = findViewById(R.id.news);
        imageView11 = findViewById(R.id.imageView11);
        imageView12 = findViewById(R.id.imageView12);
        imageView8 = findViewById(R.id.imageView8);
        imageView7 = findViewById(R.id.imageView7);
        imageView10 = findViewById(R.id.imageView10);
        imageView9 = findViewById(R.id.imageView9);
        imageView13 = findViewById(R.id.imageView13);
        imageView14 = findViewById(R.id.imageView14);
        imageView15 = findViewById(R.id.imageView15);
        imageView16 = findViewById(R.id.imageView16);
        txv1 = findViewById(R.id.textView34);
        txv2 = findViewById(R.id.textView32);
        txv3 = findViewById(R.id.textView36);
        txv4 = findViewById(R.id.textView35);
        txv5 = findViewById(R.id.textView38);
        txv6 = findViewById(R.id.textView37);
        txv7 = findViewById(R.id.textView50);
        txv8 = findViewById(R.id.textView51);
        imv_next = findViewById(R.id.iv_next);
        imv_previous = findViewById(R.id.iv_previous);
        temp = new ArrayList<>();
        viewPager = findViewById(R.id.viewpager1);
        viewpager_adapter_dashbord = new viewpager_adapter_dashbord(layout,this);
        viewPager.setAdapter(viewpager_adapter_dashbord);



        Typeface atf = Typeface.createFromAsset(getAssets(), "irsans.ttf");
        txv1.setTypeface(atf);
        txv2.setTypeface(atf);
        txv3.setTypeface(atf);
        txv4.setTypeface(atf);
        txv5.setTypeface(atf);
        txv6.setTypeface(atf);
        txv7.setTypeface(atf);
        txv8.setTypeface(atf);

        ticket.setBackground(getResources().getDrawable(Constans.bg_item_menu1));
        invoice.setBackground(getResources().getDrawable(Constans.bg_item_menu1));
        factor.setBackground(getResources().getDrawable(Constans.bg_item_menu1));
        news.setBackground(getResources().getDrawable(Constans.bg_item_menu1));

        products.setBackground(getResources().getDrawable(Constans.bg_item_menu2));
        increas_amount.setBackground(getResources().getDrawable(Constans.bg_item_menu2));
        turnover.setBackground(getResources().getDrawable(Constans.bg_item_menu2));
        setting.setBackground(getResources().getDrawable(Constans.bg_item_menu2));

        internet_payment.setBackground(getResources().getDrawable(Constans.bg_item_menu3));
        bank_recipt.setBackground(getResources().getDrawable(Constans.bg_item_menu3));

//        برای داینامیک کردن رنگ انتخابی کاربر
//        ticket.setBackgroundTintList(ContextCompat.getColorStateList(MainActivity.this , Constans.colorAccent));



        Dots_Layout = findViewById(R.id.dots_layout);

        createDots(0);
        imv_previous.setVisibility(View.INVISIBLE);



        imv_next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                    viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            }
        });

        imv_previous.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                    viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
            }
        });

        getSupportActionBar().setTitle(getResources().getString(R.string.main_title));


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i)
            {
                createDots(i);
                if(i==0)
                {
                    imv_previous.setVisibility(View.INVISIBLE);
                }
                else
                {
                    imv_previous.setVisibility(View.VISIBLE);
                }

                if(i==3)
                {
                    imv_next.setVisibility(View.INVISIBLE);
                }
                else
                {
                    imv_next.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        increas_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flagShown) {
                    increaseAmount.setVisibility(View.GONE);
                    //
//                    txv1.setTextSize(18);
//                    txv2.setTextSize(18);
//                    txv3.setTextSize(18);
//                    txv4.setTextSize(18);
//                    txv5.setTextSize(18);
//                    txv6.setTextSize(18);
                    //
//                    imageView11.getLayoutParams().height = (int) getResources().getDimension(R.dimen.medium_menu_image);
//                    imageView11.getLayoutParams().width = (int) getResources().getDimension(R.dimen.medium_menu_image);
//                    //
//                    imageView12.getLayoutParams().height = (int) getResources().getDimension(R.dimen.medium_menu_image);
//                    imageView12.getLayoutParams().width = (int) getResources().getDimension(R.dimen.medium_menu_image);
//                    //
//                    imageView7.getLayoutParams().height = (int) getResources().getDimension(R.dimen.normal_menu_image);
//                    imageView7.getLayoutParams().width = (int) getResources().getDimension(R.dimen.normal_menu_image);
//                    //
//                    imageView8.getLayoutParams().height = (int) getResources().getDimension(R.dimen.normal_menu_image);
//                    imageView8.getLayoutParams().width = (int) getResources().getDimension(R.dimen.normal_menu_image);
//                    //
//                    imageView9.getLayoutParams().height = (int) getResources().getDimension(R.dimen.normal_menu_image);
//                    imageView9.getLayoutParams().width = (int) getResources().getDimension(R.dimen.normal_menu_image);
//                    //
//                    imageView10.getLayoutParams().height = (int) getResources().getDimension(R.dimen.normal_menu_image);
//                    imageView10.getLayoutParams().width = (int) getResources().getDimension(R.dimen.normal_menu_image);

                    flagShown = false;
                }
                else
                    {
                    increaseAmount.setVisibility(View.VISIBLE);
                    //
//                        txv1.setTextSize(16);
//                        txv2.setTextSize(16);
//                        txv3.setTextSize(16);
//                        txv4.setTextSize(16);
//                        txv5.setTextSize(16);
//                        txv6.setTextSize(16);
                        //
//                    imageView11.getLayoutParams().height = (int) getResources().getDimension(R.dimen.changed_size);
//                    imageView11.getLayoutParams().width = (int) getResources().getDimension(R.dimen.changed_size);
//                    //
//                    imageView12.getLayoutParams().height = (int) getResources().getDimension(R.dimen.changed_size);
//                    imageView12.getLayoutParams().width = (int) getResources().getDimension(R.dimen.changed_size);
//                    //
//                    imageView7.getLayoutParams().height = (int) getResources().getDimension(R.dimen.small_size);
//                    imageView7.getLayoutParams().width = (int) getResources().getDimension(R.dimen.small_size);
//                    //
//                    imageView8.getLayoutParams().height = (int) getResources().getDimension(R.dimen.small_size);
//                    imageView8.getLayoutParams().width = (int) getResources().getDimension(R.dimen.small_size);
//                    //
//                    imageView9.getLayoutParams().height = (int) getResources().getDimension(R.dimen.small_size);
//                    imageView9.getLayoutParams().width = (int) getResources().getDimension(R.dimen.small_size);
//                    //
//                    imageView10.getLayoutParams().height = (int) getResources().getDimension(R.dimen.small_size);
//                    imageView10.getLayoutParams().width = (int) getResources().getDimension(R.dimen.small_size);
                    flagShown = true;
                }
            }
        });

        products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pair[] pair = new Pair[1];
                pair[0] = new Pair<View, String>(products, "logo_shop2");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,
                            pair);
                    Intent intent = new Intent(MainActivity.this, ProductList.class);
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(new Intent(MainActivity.this, ProductList.class));
                }
            }
        });


        ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pair[] pair = new Pair[1];
                pair[0] = new Pair<View, String>(ticket, "logo_ticket2");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,
                            pair);
                    Intent intent = new Intent(MainActivity.this, TicketList.class);
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(new Intent(MainActivity.this, TicketList.class));
                }
            }
        });

        invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pair[] pair = new Pair[1];
                pair[0] = new Pair<View, String>(invoice, "logo_invoice2");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,
                            pair);
                    Intent intent = new Intent(MainActivity.this, Invoice_List.class);
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(new Intent(MainActivity.this, Invoice_List.class));
                }
            }
        });

        factor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pair[] pair = new Pair[1];
                pair[0] = new Pair<View, String>(factor, "logo_factor2");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,
                            pair);
                    Intent intent = new Intent(MainActivity.this, Factor_List.class);
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(new Intent(MainActivity.this, Factor_List.class));
                }
            }
        });

        turnover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pair[] pair = new Pair[1];
                pair[0] = new Pair<View, String>(turnover, "logo_turnover2");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,
                            pair);
                    Intent intent = new Intent(MainActivity.this, Turnover_list.class);
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(new Intent(MainActivity.this, Turnover_list.class));
                }
            }
        });

        internet_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pair[] pair = new Pair[1];
                pair[0] = new Pair<View, String>(imageView13, "logo_increas_internet_payment");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,
                            pair);
                    Intent intent = new Intent(MainActivity.this, Internet_payment.class);
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(new Intent(MainActivity.this, Internet_payment.class));
                }
            }
        });

        bank_recipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pair[] pair = new Pair[1];
                pair[0] = new Pair<View, String>(imageView14, "logo_bank_recipt");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,
                            pair);
                    Intent intent = new Intent(MainActivity.this, Bank_Receipt.class);
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(new Intent(MainActivity.this, Bank_Receipt.class));
                }
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pair[] pair = new Pair[1];
                pair[0] = new Pair<View, String>(imageView15, "logo_setting");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,
                            pair);
                    Intent intent = new Intent(MainActivity.this, Setting.class);
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(new Intent(MainActivity.this, Setting.class));
                }
            }
        });


        if(Build.VERSION.SDK_INT >19)
        {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        else
        {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

//        get_cmr();

//        dashbord_Model enrty = new dashbord_Model(R.drawable.di1 , "C.M.R" , ""+548);
//        temp.add(enrty);
//        dashbord_Model enrty1 = new dashbord_Model(R.drawable.di2 , "تیکت های در حال بررسی" , ""+548);
//        temp.add(enrty1);
//        dashbord_Model enrty2 = new dashbord_Model(R.drawable.di3 , "موجودی حساب" , ""+"234");
//        temp.add(enrty2);
//        dashbord_Model enrty3 = new dashbord_Model(R.drawable.di4 , "پیش فاکتور ها" , ""+2345);
//        temp.add(enrty3);
//
//        update.entries = new ArrayList<>(temp);

////        rcv = findViewById(R.id.rv);
////        rcv.setLayoutManager(new StaggeredGridLayoutManager(1 , StaggeredGridLayoutManager.HORIZONTAL));
////        update.notifyDataSetChanged();
////        rcv.setAdapter(update);

//
//        final int scrollspeed = 20;
//        final Handler handler = new Handler();
//        final Runnable runnable = new Runnable()
//        {
//            int count=0;
//            @Override
//            public void run()
//            {
//                if(count<4)
//                {
//                    rcv.scrollBy(5 , 0);
//                    handler.postDelayed(this , scrollspeed);
//                    if (!rcv.canScrollHorizontally(1))
//                    {
//                        rcv.scrollToPosition(0);
//                    }
//                }
//            }
//        };
//        handler.postDelayed(runnable , scrollspeed);
//        rcv.postInvalidate();

        imvshop.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this , ShoppingBasket.class);
                startActivity(intent);
            }
        });

    }




    private void setupNavigationView()
    {
        navigationView = findViewById(R.id.navigation_view);
        Menu m = navigationView.getMenu();
        for (int i=0;i<m.size();i++) {
            MenuItem mi = m.getItem(i);

            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu!=null && subMenu.size() >0 ) {
                for (int j=0; j <subMenu.size();j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }

            //the method we have create in activity
            applyFontToMenuItem(mi);
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                item.setChecked(true);
                switch (item.getItemId())
                {
                    case R.id.navigation_menu_ticketList:
                        Intent intent = new Intent(MainActivity.this , TicketListwithViewPager.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(Gravity.START);
                        break;

                    case R.id.navigation_productList:
                        Intent intent1 = new Intent(MainActivity.this , ProductList.class);
                        startActivity(intent1);
                        drawerLayout.closeDrawer(Gravity.START);
                        break;

                    case R.id.navigation_menu_invoiceList:
                        Intent intent2 = new Intent(MainActivity.this , Test_PreInvoice_List.class);
                        startActivity(intent2);
                        drawerLayout.closeDrawer(Gravity.START);
                        break;

                    case R.id.navigation_menu_factorList:
                        Intent intent3 = new Intent(MainActivity.this , Factor_List.class);
                        startActivity(intent3);
                        drawerLayout.closeDrawer(Gravity.START);
                        break;

                    case R.id.navigation_menu_TurnOver:
                        Intent intent4 = new Intent(MainActivity.this , Turnover_list.class);
                        startActivity(intent4);
                        drawerLayout.closeDrawer(Gravity.START);
                        break;

                    case R.id.navigation_menu_setting:
                        Intent intent5 = new Intent(MainActivity.this , Setting.class);
                        startActivity(intent5);
                        drawerLayout.closeDrawer(Gravity.START);
                        break;

                    case R.id.navigation_menu_change_password:
                        Intent intent6 = new Intent(MainActivity.this , Digit_Password.class);
                        startActivity(intent6);
                        drawerLayout.closeDrawer(Gravity.START);
                        break;

                    case R.id.navigation_menu_AboutUs:
                        Intent intent7 = new Intent(MainActivity.this , About_Us.class);
                        startActivity(intent7);
                        drawerLayout.closeDrawer(Gravity.START);
                        break;


                    case R.id.navigation_menu_exite:
                        Config.SetData(MainActivity.this , "" , "" , 0);
                        Intent intent8 = new Intent(getApplicationContext() , Login.class);
                        startActivity(intent8);
                        drawerLayout.closeDrawer(Gravity.START);
                        finish();
                        break;
                }
                return false;
            }
        });
    }

    protected void closeNavDrawer() {
        if (drawerLayout != null) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    private void setupToolbar()
    {
        drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this , R.color.white));
        toolbar.setBackgroundColor(ContextCompat.getColor(this , Constans.colorprimary));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this , drawerLayout , toolbar , 0 , 0);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    public void onFirstBackpress()
    {
        Toast.makeText(MainActivity.this, getString(R.string.main_double_exist), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed()
    {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawers();
        }
        else
        {
            if(oldCurrentTimeMillis+time_interval > System.currentTimeMillis())
            {
                super.onBackPressed();
                return;
            }
            else
            {
                onFirstBackpress();
            }
            oldCurrentTimeMillis = System.currentTimeMillis();
        }

    }


    public void Get_Total_Amount()
    {

        Retrofit retrofit2 = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        API api = retrofit2.create(API.class);
        Model_Post_Basket_Amount model_post = new Model_Post_Basket_Amount();
        model_post.setToken(Config.getToken(MainActivity.this));
        Call<List<Model_Get_Basket_Amount>> model = api.Get_Basket_Amount(model_post);
        model.enqueue(new Callback<List<Model_Get_Basket_Amount>>() {
            @Override
            public void onResponse(Call<List<Model_Get_Basket_Amount>> call, Response<List<Model_Get_Basket_Amount>> response)
            {
                if(response.isSuccessful())
                {
                    List<Model_Get_Basket_Amount> list ;
                    list = response.body();
                    String total_amount = ""+list.get(0).getAmount();
                    id = list.get(0).getId();
                    Get_Basket_List();
                }
                else
                {
                    Toast.makeText(MainActivity.this , getString(R.string.server_1151) , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Model_Get_Basket_Amount>> call, Throwable t)
            {
                Toast.makeText(MainActivity.this , getString(R.string.server_1152) , Toast.LENGTH_LONG).show();
            }
        });

    }

    public void Get_Basket_List()
    {
        Retrofit retrofit2 = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        API api = retrofit2.create(API.class);
        Model_Post_Shopping_Basket model_post = new Model_Post_Shopping_Basket();
        model_post.setToken(Config.getToken(MainActivity.this));
        model_post.setInvoice_id(id);
        Call<List<Model_Get_Shopping_Basket>> model = api.Get_Shoppin_Bascket(model_post);
        model.enqueue(new Callback<List<Model_Get_Shopping_Basket>>() {
            @Override
            public void onResponse(Call<List<Model_Get_Shopping_Basket>> call, Response<List<Model_Get_Shopping_Basket>> response) {
                if(response.isSuccessful())
                {
                    List<Model_Get_Shopping_Basket> list = response.body();

                    ArrayList<Shopping_Basket_List_Model> temp;
                    temp = new ArrayList<>();
                }
                else
                {
                    Toast.makeText(MainActivity.this , getString(R.string.server_1161) , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Model_Get_Shopping_Basket>> call, Throwable t)
            {
                Toast.makeText(MainActivity.this , getString(R.string.server_1162) , Toast.LENGTH_LONG).show();
            }
        });

    }



    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "roboto-regular.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }


//
//    public void get_cmr()
//    {
//        Retrofit retrofit2 = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();
//        API myApi2 = retrofit2.create(API.class);
//        Model_Post_dashboard_cmr model_post = new Model_Post_dashboard_cmr();
//        model_post.setToken(Config.getToken(getApplicationContext()));
//        Call<Model_Get_dashboard_cmr> model = myApi2.get_dashboard_cmr(model_post);
//        model.enqueue(new Callback<Model_Get_dashboard_cmr>() {
//            @Override
//            public void onResponse(Call<Model_Get_dashboard_cmr> call, Response<Model_Get_dashboard_cmr> response) {
//                if(response.isSuccessful())
//                {
//                    dashbord_Model enrty = new dashbord_Model(R.drawable.di1 , "C.M.R" , ""+response.body().getResult());
//                    temp.add(enrty);
//                    get_ticket();
//                }
//                else
//                {
//                    Log.d(TAG , "Response" + response.toString());
//                    Toast.makeText(getApplicationContext() , "خطا سرور 1281" , Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Model_Get_dashboard_cmr> call, Throwable t)
//            {
//                Toast.makeText(getApplicationContext() , "خطا سرور 1282" , Toast.LENGTH_LONG).show();
//            }
//        });
//
//    }
//
//    public void get_ticket()
//    {
//        Retrofit retrofit2 = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();
//        API myApi2 = retrofit2.create(API.class);
//        Model_Post_dashboard_ticket model_post = new Model_Post_dashboard_ticket();
//        model_post.setCustomerId(Config.getuserid(getApplicationContext()));
//        Call<Model_Get_dashboard_ticket> model = myApi2.get_dashboard_ticket(model_post);
//        model.enqueue(new Callback<Model_Get_dashboard_ticket>() {
//            @Override
//            public void onResponse(Call<Model_Get_dashboard_ticket> call, Response<Model_Get_dashboard_ticket> response)
//            {
//                if(response.isSuccessful())
//                {
//                    dashbord_Model enrty = new dashbord_Model(R.drawable.di2 , "تیکت های در حال بررسی" , ""+response.body().getResult());
//                    temp.add(enrty);
//                    get_cash();
//                }
//                else
//                {
//                    Log.d(TAG , "Response" + response.toString());
//                    Toast.makeText(getApplicationContext() , "خطا سرور 1291" , Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Model_Get_dashboard_ticket> call, Throwable t)
//            {
//                Toast.makeText(getApplicationContext() , "خطا سرور 1292" , Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//
//    public void get_cash()
//    {
//        Retrofit retrofit2 = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();
//        API myApi2 = retrofit2.create(API.class);
//        Model_Post_dashboard_cmr model_post = new Model_Post_dashboard_cmr();
//        model_post.setToken(Config.getToken(getApplicationContext()));
//        Call<Model_Get_dashboard_Cash> model = myApi2.get_dashboard_cash(model_post);
//        model.enqueue(new Callback<Model_Get_dashboard_Cash>() {
//            @Override
//            public void onResponse(Call<Model_Get_dashboard_Cash> call, Response<Model_Get_dashboard_Cash> response)
//            {
//                if(response.isSuccessful())
//                {
//                    dashbord_Model enrty = new dashbord_Model(R.drawable.di3 , "موجودی حساب" , ""+response.body().getResultDouble());
//                    temp.add(enrty);
//                    get_invoice();
//                }
//                else
//                {
//                    Toast.makeText(getApplicationContext() , "خطا سرور 1301" , Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Model_Get_dashboard_Cash> call, Throwable t)
//            {
//                Toast.makeText(getApplicationContext() , "خطا سرور 1302" , Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//
//    public void get_invoice()
//    {
//        Retrofit retrofit2 = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();
//        API myApi2 = retrofit2.create(API.class);
//        Model_Post_dashboard_invoice model_post = new Model_Post_dashboard_invoice();
//        model_post.setCustomer_id(Config.getuserid(getApplicationContext()));
//        model_post.setToken(Config.getToken(getApplicationContext()));
//        Call<Model_Get_dashboard_invoce> model = myApi2.get_dashboard_invoice(model_post);
//        model.enqueue(new Callback<Model_Get_dashboard_invoce>() {
//            @Override
//            public void onResponse(Call<Model_Get_dashboard_invoce> call, Response<Model_Get_dashboard_invoce> response)
//            {
//                if(response.isSuccessful())
//                {
//                    dashbord_Model enrty = new dashbord_Model(R.drawable.di4 , "پیش فاکتور ها" , ""+response.body().getResult());
//                    temp.add(enrty);
//
////                    update.entries = new ArrayList<>(temp);
////
////                    rcv = findViewById(R.id.rv);
////                    rcv.setLayoutManager(new StaggeredGridLayoutManager(1 , StaggeredGridLayoutManager.HORIZONTAL));
////                    update.notifyDataSetChanged();
////                    rcv.setAdapter(update);
//                }
//                else
//                {
//                    Toast.makeText(getApplicationContext() , "خطا سرور 1311" , Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Model_Get_dashboard_invoce> call, Throwable t)
//            {
//                Toast.makeText(getApplicationContext() , "خطا سرور 1312" , Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//

    public void createDots(int current_position)
    {
        if(Dots_Layout != null)
        {
            Dots_Layout.removeAllViews();
        }

        dots = new ImageView[layout.length];
        for (int i=0 ; i<layout.length; i++)
        {
            dots[i] = new ImageView(this);
            if(i==current_position)
            {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this , R.drawable.active_dots));
            }
            else
            {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this , R.drawable.deactive_dots));
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT , ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(4,0,4,0);

            Dots_Layout.addView(dots[i] , params);
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if(Setting.flag)
        {
            recreate();
            Setting.flag = false;
        }
    }
}
