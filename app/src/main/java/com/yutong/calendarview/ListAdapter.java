package com.yutong.calendarview;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.List;
import java.util.Properties;


/**
 * Created by Administrator on 2017/10/22.
 */

public class ListAdapter extends BaseAdapter {

    Context context;
    String[] ls;

    /**
     * @param context
     */
    public ListAdapter(Context context, String[] ls) {
        this.context = context;
        this.ls = ls;
    }

    @Override
    public int getCount() {

        return ls.length;
    }

    @Override
    public Object getItem(int position) {
        return ls[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (null == convertView) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_item, null);
            vh.price = (TextView) convertView.findViewById(R.id.content);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.price.setText(ls[position]);

        return convertView;
    }

    class ViewHolder {
        TextView price;
    }
}