package com.feedmybrain.util;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.feedmybrain.R;

/**
 * Created by yogaub on 17/05/15.
 */
public class NewsViewHolder extends RecyclerView.ViewHolder  {

protected View view;
protected TextView news_website;
protected TextView news_title;
protected TextView news_text;
protected TextView news_published;
protected TextView news_author;


    public NewsViewHolder (View v) {
        super(v);
        view = v;
        news_website = (TextView) v.findViewById(R.id.news_website);
        news_title = (TextView)  v.findViewById(R.id.news_title);
        news_text = (TextView)  v.findViewById(R.id.news_text);
        news_published = (TextView) v.findViewById(R.id.news_published);
        news_published = (TextView) v.findViewById(R.id.news_author);

       /* view.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
            // item clicked
            Intent intent = new Intent(view.getContext(), FieldDetailActivity.class);
            intent.putExtra("field_id", fieldId.getText());
            view.getContext().startActivity(intent);
            }
        });*/
    }
}
