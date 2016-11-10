package com.yzk.practice_brain.base;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {

    protected Handler mHandler = new Handler();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = getFragmentContentView();
        initFragmentView(view);
        initFragmentData();
        return view;
    }


    /**
     * get FragmentContentView
     *
     * @return
     */

    protected abstract View getFragmentContentView();

    /**
     *init fragment view
     */
    protected abstract void initFragmentView(View view);


    /**
     * apply fragment data
     */
    protected abstract void initFragmentData();

    protected abstract  void fragmentCreate();

    protected abstract void fragmentDestroy();



    protected void onPullDownRefresh(){};

    protected  void onPullUpRefresh(){};


}
