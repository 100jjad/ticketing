package com.example.ssoheyli.ticketing_newdesign.News;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_News_Groups;
import com.example.ssoheyli.ticketing_newdesign.R;

import java.util.ArrayList;
import java.util.List;

public class NewsGroupAdapter extends RecyclerView.Adapter<NewsGroupAdapter.viewHolder> {

    private List<Model_News_Groups> modelNewsGroups = new ArrayList<>();
    private Context context;

    NewsGroupAdapter(Context context, List<Model_News_Groups> modelNewsGroups) {
        this.context = context;
        this.modelNewsGroups = modelNewsGroups;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.list_item_news_group, viewGroup, false);
        return new NewsGroupAdapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {
        viewHolder.bindAddress(modelNewsGroups.get(i));
    }

    @Override
    public int getItemCount() {
        return modelNewsGroups.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        private ImageView newsImage;
        private CardView cardView;
        private TextView newsTitle;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            newsImage = itemView.findViewById(R.id.news_image);
            newsTitle = itemView.findViewById(R.id.news_title);
        }

        public void bindAddress(final Model_News_Groups model_news_groups) {
            newsTitle.setText(model_news_groups.getTitle());
            Glide.with(context)
                    .load(context.getResources().getDrawable(R.drawable.download))
                    .into(newsImage);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, NewsItemsActivity.class);
                    intent.putExtra("category", model_news_groups.getTitle());
                    context.startActivity(intent);

                }
            });
        }
    }
}
