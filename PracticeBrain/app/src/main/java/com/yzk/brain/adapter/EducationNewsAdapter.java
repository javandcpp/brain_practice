package com.yzk.brain.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yzk.brain.R;
import com.yzk.brain.application.GlobalApplication;
import com.yzk.brain.bean.EducationResult;
import com.yzk.brain.utils.TimeUtils;

import java.util.List;

/**
 * Created by android on 11/30/16.
 */

public class EducationNewsAdapter extends BaseAdapter {

    private List<EducationResult.EducationNews> mDataList;
    private ViewHolder viewHolder;


    public void setData(List<EducationResult.EducationNews>dataList){
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
            view= LayoutInflater.from(GlobalApplication.instance).inflate(R.layout.news_item_layout,null);
            viewHolder.tvTime= (TextView) view.findViewById(R.id.tvTime);
            viewHolder.tvTitle= (TextView) view.findViewById(R.id.tvTitle);

            view.setTag(viewHolder);
        }else{

            viewHolder= (ViewHolder) view.getTag();
        }
        final EducationResult.EducationNews educationNews = mDataList.get(i);
        viewHolder.tvTitle.setText(educationNews.eduCounseTitle);
        viewHolder.tvTime.setText(TimeUtils.milliseconds2String(educationNews.createTime));

        return view;

    }


    private static class ViewHolder{
        TextView tvTitle;
        TextView tvTime;
    }
}
