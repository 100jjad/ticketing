package com.example.ssoheyli.ticketing_newdesign.News;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ssoheyli.ticketing_newdesign.Helpfull.Constans;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_News_NewsItems;
import com.example.ssoheyli.ticketing_newdesign.R;

import java.util.ArrayList;
import java.util.List;

public class NewsItemsAdapter extends RecyclerView.Adapter<NewsItemsAdapter.viewHolder> {

    private List<Model_News_NewsItems> modelNewsNewsItems = new ArrayList<>();
    private Context context;

    NewsItemsAdapter(Context context, List<Model_News_NewsItems> modelNewsNewsItems) {
        this.context = context;
        this.modelNewsNewsItems = modelNewsNewsItems;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.list_item_news_items, viewGroup, false);
        return new NewsItemsAdapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {
        viewHolder.bindAddress(modelNewsNewsItems.get(i));
    }

    @Override
    public int getItemCount() {
        return modelNewsNewsItems.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        private TextView title, date, description;
        private ImageView newsImage;
        private CardView cardView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.newsTitle);
            date = itemView.findViewById(R.id.newsDate);
            description = itemView.findViewById(R.id.newsDescription);
            newsImage = itemView.findViewById(R.id.news_image);
            cardView = itemView.findViewById(R.id.cardView);
        }

        public void bindAddress(final Model_News_NewsItems model_news_newsItems) {

            title.setText(model_news_newsItems.getTitle());
            date.setText(model_news_newsItems.getDate());
            description.setText(model_news_newsItems.getDescription());
            Glide.with(context)
                    .load(context.getResources().getDrawable(R.drawable.download))
                    .into(newsImage);

            title.setTextColor(ContextCompat.getColor(context, Constans.colorprimary));

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
