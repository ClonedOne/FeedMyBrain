package com.feedmybrain.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feedmybrain.R;
import com.feedmybrain.feedlyclient.Article;

import java.util.List;
import java.util.Date;

/**
 * Created by yogaub on 17/05/15.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {

    List<Article> mDataset;
    Context callingActivity;

    public NewsAdapter(List<Article> freebleList, Context activ) {
        callingActivity = activ;
        mDataset = freebleList;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public void onBindViewHolder(NewsViewHolder fieldListViewHolder, int i) {
        Article ci = mDataset.get(i);

        //    Log.d("IMGDBG", "IMAGE NUMBER IS = " + imageIds.getResourceId(ci.getImg(), -1));
        //    Log.d("IMGDBG", "IMAGE REFERENCE WAS = " + ci.getImg());
        fieldListViewHolder.news_website.setText("News by: " + ci.getWebsite());
        fieldListViewHolder.news_title.setText(ci.getTitle());
        fieldListViewHolder.news_text.setText(ci.getContent());
        fieldListViewHolder.news_author.setText(ci.getAuthor());
        Date d = new Date(ci.getPublished() * 1000);
        fieldListViewHolder.news_published.setText(d.toString());


    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.news_card, viewGroup, false);


        return new NewsViewHolder(itemView);
    }


    public void addItem(int position, Article data) {
        mDataset.add(position, data);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        mDataset.remove(position);
        notifyItemRemoved(position);
    }


}
