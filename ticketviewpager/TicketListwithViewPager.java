package com.example.ssoheyli.ticketing_newdesign.Ticket;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.ssoheyli.ticketing_newdesign.Helpfull.Config;
import com.example.ssoheyli.ticketing_newdesign.Helpfull.Constans;
import com.example.ssoheyli.ticketing_newdesign.R;
import com.example.ssoheyli.ticketing_newdesign.TestActivities.TicketTestActivity;

import java.io.File;

public class TicketListwithViewPager extends AppCompatActivity {

    // values
    private Adapter_viewPager_ticket.Tab[] mItems = new Adapter_viewPager_ticket.Tab[]{Adapter_viewPager_ticket.Tab.New, Adapter_viewPager_ticket.Tab.Pending, Adapter_viewPager_ticket.Tab.Finished, Adapter_viewPager_ticket.Tab.Waiting, Adapter_viewPager_ticket.Tab.Responded, Adapter_viewPager_ticket.Tab.Expired, Adapter_viewPager_ticket.Tab.Suspended, Adapter_viewPager_ticket.Tab.All};
    static String hashkey;

    // UI
    ViewPager viewPager;
    TabLayout tabLayout;
    Adapter_viewPager_ticket av = new Adapter_viewPager_ticket(getSupportFragmentManager(), mItems);
    TicketList_New_Fragment fragment = new TicketList_New_Fragment();
    FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Constans.theme);
        setContentView(R.layout.activity_ticket_list);
        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager);
        floatingActionButton = findViewById(R.id.floatactionbutton);

        setupToolbar();
        floatingActionButton.setBackgroundTintList(ContextCompat.getColorStateList(this, Constans.colorAccent));
        floatingActionButton.setBackgroundColor(ContextCompat.getColor(this, Constans.colorAccent));


        Adapter_viewPager_ticket adapter = new Adapter_viewPager_ticket(getSupportFragmentManager(), mItems);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        File file = null;
        File Document = null;
        File Voice = null;
        File Picture = null;

        File root = android.os.Environment.getExternalStorageDirectory();
        file = new File(root.getAbsolutePath() + "/Ticketing");
        if (!file.exists()) {
            file.mkdir();
        }
        Document = new File(file.getPath() + "/Document");
        if (!Document.exists()) {
            Document.mkdir();
        }

        Voice = new File(file.getPath() + "/Voice");
        if (!Voice.exists()) {
            Voice.mkdir();
        }

        Picture = new File(file.getPath() + "/Picture");
        if (!Picture.exists()) {
            Picture.mkdir();
        }

        Config.Set_Storage_Location(getApplicationContext(), Document.getPath(), Voice.getPath(), Picture.getPath());

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), TicketTestActivity.class);
                startActivity(intent);
            }
        });

        if (!internetConnection()) {
            Toast.makeText(getApplicationContext(), getString(R.string.ticketlist_checkconnection), Toast.LENGTH_SHORT).show();

        }

    }


    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        toolbar.setBackgroundColor(ContextCompat.getColor(this, Constans.colorprimary));

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

    public boolean internetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifi != null && wifi.isConnected()) {
            return true;
        }
        NetworkInfo data = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (data != null && data.isConnected()) {
            return true;
        }
        NetworkInfo active = cm.getActiveNetworkInfo();
        if (active != null && active.isConnected()) {
            return true;
        }


        return false;
    }
}
