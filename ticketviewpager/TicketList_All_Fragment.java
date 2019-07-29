package com.example.ssoheyli.ticketing_newdesign.Ticket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.agrawalsuneet.dotsloader.loaders.LazyLoader;
import com.example.ssoheyli.ticketing_newdesign.Helpfull.API;
import com.example.ssoheyli.ticketing_newdesign.Helpfull.Config;
import com.example.ssoheyli.ticketing_newdesign.Model.ModelGetTicketList;
import com.example.ssoheyli.ticketing_newdesign.Model.ModelPostListTicket;
import com.example.ssoheyli.ticketing_newdesign.Model.ticktinlistmodel;
import com.example.ssoheyli.ticketing_newdesign.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TicketList_All_Fragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    TicketlistAdapter update = new TicketlistAdapter(getContext());
    String TAG;
    ImageView imv1;
    RecyclerView rcv;
    View ChildView;
    int RecyclerViewItemPosition;
    static int position;
    List<ModelGetTicketList> ticketList;
    static String hashkey;
    static int ticketid;
    int skipl, takel;
    LazyLoader lp;
    TextView txv1;
    private SwipeRefreshLayout swipeRefreshLayout;

    public static TicketList_All_Fragment newInstance() {
        TicketList_All_Fragment fragment = new TicketList_All_Fragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ticket, container, false);

        lp = v.findViewById(R.id.dl);
        txv1 = v.findViewById(R.id.tv1);
        rcv = v.findViewById(R.id.rv);
        imv1 = v.findViewById(R.id.im1);
        getlist(getContext(), 8);

        swipeRefreshLayout = v.findViewById(R.id.refresher);
        swipeRefreshLayout.setOnRefreshListener(this);

        rcv.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent motionEvent) {
                    return true;
                }
            });

            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                ChildView = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                if (ChildView != null && gestureDetector.onTouchEvent(motionEvent)) {
                    RecyclerViewItemPosition = recyclerView.getChildAdapterPosition(ChildView);
                    if (RecyclerViewItemPosition != update.entries.size()) {
                        position = RecyclerViewItemPosition;
                        TicketListwithViewPager.hashkey = ticketList.get(position).getHashkey();
                        ticketid = ticketList.get(position).getTicketid();

                        Intent intent = new Intent(getContext(), Detail_Ticket_List.class);
                        startActivity(intent);

                    }
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        return v;
    }

    public void getlist(Context context, int position) {

        Retrofit retrofit2 = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        API myApi2 = retrofit2.create(API.class);
        ModelPostListTicket modelPostListTicket = new ModelPostListTicket();
        modelPostListTicket.setSkip(0);
        modelPostListTicket.setStatus(position);
        modelPostListTicket.setTake(100);
        modelPostListTicket.setToken(Config.getToken(context));

        Call<List<ModelGetTicketList>> model = myApi2.getListTicket(modelPostListTicket);
        model.enqueue(new Callback<List<ModelGetTicketList>>() {
            @Override
            public void onResponse(Call<List<ModelGetTicketList>> call, Response<List<ModelGetTicketList>> response) {
                lp.setVisibility(View.INVISIBLE);
                if (response.isSuccessful()) {
                    ticketList = response.body();
                    skipl += 0;
                    takel += 10;
                    ArrayList<ticktinlistmodel> temp;
                    temp = new ArrayList<>();
                    for (int i = 0; i < ticketList.size(); i++) {
                        ticktinlistmodel enrty = new ticktinlistmodel(ticketList.get(i).getSubject(), ticketList.get(i).getTicketid(), ticketList.get(i).getDate());
                        temp.add(enrty);
                    }
                    update.entries = new ArrayList<>(temp);


                    rcv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                    update.notifyDataSetChanged();
                    rcv.setAdapter(update);

                    if (update.entries.size() == 0) {
                        imv1.setVisibility(View.VISIBLE);
                        imv1.setImageResource(R.drawable.ti1);
                        txv1.setText(getString(R.string.nothing_exist));
                    } else {
                        imv1.setVisibility(View.GONE);
                        txv1.setText("");
                    }

                } else {
                    Log.d(TAG, "Response" + response.toString());
                    Toast.makeText(getContext(), getString(R.string.server_1061), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ModelGetTicketList>> call, Throwable t) {
                lp.setVisibility(View.INVISIBLE);
                Log.d(TAG, "Response" + t.getMessage());
                Toast.makeText(getContext(), getString(R.string.server_1062), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getlist(getContext(), 8);
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }

}
