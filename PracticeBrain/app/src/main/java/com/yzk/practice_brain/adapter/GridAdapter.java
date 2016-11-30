package com.yzk.practice_brain.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.yzk.practice_brain.R;
import com.yzk.practice_brain.activity.RemeberPracticeActivity;
import com.yzk.practice_brain.activity.TwentyOneEnterActivity;
import com.yzk.practice_brain.bean.PracticeEntity;

import java.util.List;

/**
 * Created by android on 11/17/16.
 */

public class GridAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<PracticeEntity> mDataList;
    private ViewHolder viewHolder;
    private final LayoutInflater mInflater;

    public GridAdapter(Context context, List<PracticeEntity> dataList) {

        this.mContext = context;
        this.mDataList = dataList;
        mInflater = LayoutInflater.from(mContext);
    }


    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int i) {
        return mDataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (null == view) {
            viewHolder = new ViewHolder();
            view = mInflater.inflate(R.layout.layout_grid_item, null);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.imageView);
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        final PracticeEntity practiceEntity = mDataList.get(i);

        if (practiceEntity.locked) {
            viewHolder.imageView.setImageResource(practiceEntity.normalResId);
            viewHolder.imageView.setEnabled(false);
        } else {
            viewHolder.imageView.setImageResource(practiceEntity.unlockResId);
            viewHolder.imageView.setEnabled(true);
        }
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PracticeEntity practiceEntity1 = mDataList.get(i);
                Intent intent=null;
                if (0==practiceEntity1.index){
                    intent=new Intent(mContext, RemeberPracticeActivity.class);
                    mContext.startActivity(intent);
                }else{
                    intent=new Intent(mContext, TwentyOneEnterActivity.class);
                    mContext.startActivity(intent);
                }
            }
        });

        int width = viewHolder.imageView.getWidth();



        return view;
    }

    private static class ViewHolder {
        ImageView imageView;
    }

}
