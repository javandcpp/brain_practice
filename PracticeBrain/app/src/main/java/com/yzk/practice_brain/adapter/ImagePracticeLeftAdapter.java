package com.yzk.practice_brain.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yzk.practice_brain.R;
import com.yzk.practice_brain.application.GlobalApplication;
import com.yzk.practice_brain.bean.ImageResult;
import com.yzk.practice_brain.log.LogUtil;

import java.util.List;

/**
 * Created by android on 12/6/16.
 */

public class ImagePracticeLeftAdapter extends BaseAdapter {

    private List<ImageResult.Image> mDatalist;
    private ViewHolder viewHolder;

    public void setData(List<ImageResult.Image> dataList) {
        this.mDatalist = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return null != mDatalist ? mDatalist.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return null != mDatalist ? mDatalist.get(i) : null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (null == view) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(GlobalApplication.instance).inflate(R.layout.image_practice_left_item_layout, null);
            viewHolder.simpleDraweeView = (SimpleDraweeView) view.findViewById(R.id.draweeView);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        ImageResult.Image image = mDatalist.get(i);
        Uri uri = Uri.parse(image.url);
        viewHolder.simpleDraweeView.setImageURI(uri);
        LogUtil.e(image.url);
        return view;
    }

    private static class ViewHolder {
        SimpleDraweeView simpleDraweeView;
    }
}
