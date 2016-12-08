package com.yzk.brain.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yzk.brain.R;
import com.yzk.brain.application.GlobalApplication;
import com.yzk.brain.bean.SuTableResult;

import java.util.List;

/**
 * Created by android on 12/2/16.
 */

public class SutableAdapter extends BaseAdapter {

    private List<SuTableResult.Table> mDataList;
    private ViewHolder viewHolder;

    public void setData(List<SuTableResult.Table> dataList){
        this.mDataList=dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return null!=mDataList?mDataList.size():0;
    }

    @Override
    public Object getItem(int i) {
        return null!=mDataList?mDataList.get(i):null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (null==view){
            viewHolder = new ViewHolder();
            view= LayoutInflater.from(GlobalApplication.instance).inflate(R.layout.sutable_item_layout,null);
            viewHolder.background= (RelativeLayout) view.findViewById(R.id.backgroud);
            viewHolder.tvText= (TextView) view.findViewById(R.id.tvText);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        final SuTableResult.Table table = mDataList.get(i);
        if (table.flag){
            viewHolder.background.setBackgroundResource(R.drawable.home_bgview_blue);
        }else{
            viewHolder.background.setBackgroundResource(R.drawable.home_bgview_white);
        }
        viewHolder.tvText.setText(table.value);



        return view;
    }

    private static class ViewHolder{
        RelativeLayout background;
        TextView tvText;
    }
}
