package com.example.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {
    public NewsAdapter(Context context, List<News> objects) {
        super(context, 0, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (convertView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.news_layout, parent, false);
        }

        News currentNews = getItem(position);

        TextView text_id = (TextView) listItemView.findViewById(R.id.id);
        text_id.setText(currentNews.getId());

        TextView text_type = (TextView) listItemView.findViewById(R.id.type);
        text_type.setText(currentNews.getType());

        TextView text_WebPublicationDate = (TextView) listItemView.findViewById(R.id.webPublicationDate);
        text_WebPublicationDate.setText(currentNews.getWebPublicationDate());

        TextView text_webTitle = (TextView) listItemView.findViewById(R.id.webTitle);
        text_webTitle.setText(currentNews.getWebTitle());

        TextView text_webUrl = (TextView) listItemView.findViewById(R.id.webUrl);
        text_webUrl.setText(currentNews.getWebUrl());


        TextView text_sectionName = (TextView) listItemView.findViewById(R.id.sectionName);
        text_sectionName.setText(currentNews.getSectionName());

        return listItemView;
    }
}
