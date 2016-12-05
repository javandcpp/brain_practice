package com.yzk.practice_brain.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yzk.practice_brain.R;
import com.yzk.practice_brain.application.GlobalApplication;
import com.yzk.practice_brain.bean.RemberPracticeResult;

import java.util.List;

/**
 * Created by android on 12/5/16.
 */

public class RemberPracticeLeftAdapter extends BaseAdapter {

    public List<RemberPracticeResult.Practice> tempList;
    private ViewHolder viewHolder;


    public void setData(List<RemberPracticeResult.Practice> dataList) {
        this.tempList = dataList;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return null != tempList ? tempList.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return null != tempList ? tempList.get(i) : null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (null == view) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(GlobalApplication.instance).inflate(R.layout.remember_practice_grid_left_layout, null);
            viewHolder.textView = (TextView) view.findViewById(R.id.tvText);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        final RemberPracticeResult.Practice practice = tempList.get(i);

        viewHolder.textView.setText(practice.value);

        return view;
    }

    private static class ViewHolder {
        TextView textView;
    }
}
