package com.yzk.practice_brain.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.inter.ResponseStringDataListener;
import com.yzk.practice_brain.R;
import com.yzk.practice_brain.adapter.RemberPracticeEnterAdapter;
import com.yzk.practice_brain.base.BaseFragmentActivity;
import com.yzk.practice_brain.bean.RemberPracticeResult;
import com.yzk.practice_brain.config.Config;
import com.yzk.practice_brain.network.HttpRequestUtil;
import com.yzk.practice_brain.ui.CircularProgressView;
import com.yzk.practice_brain.ui.RuleDialog;
import com.yzk.practice_brain.utils.NetworkUtils;
import com.yzk.practice_brain.utils.ParseJson;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by android on 11/24/16.
 */

public class RemeberPracticeEnterActivity extends BaseFragmentActivity implements ResponseStringDataListener {

    private static final int REQEUST_REMEMBER_TASK =0x1 ;
    @Bind(R.id.gridView)
    GridView gridView;

    @Bind(R.id.loading)
    CircularProgressView loading;

    private ArrayList<RemberPracticeResult.Practice> tempList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remeber_practice_enter_layout);
    }

    @OnClick({R.id.begin,R.id.rule})
    public void click(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.begin:
                if (tempList.size()>0) {
                    intent = new Intent(this, RemeberPracticeActivity.class);
                    intent.putExtra("data", tempList);
                    startActivity(intent);
                }
                break;
            case R.id.rule:
                RuleDialog.Builder builder=new RuleDialog.Builder(this,"2");
                builder.create().show();
                break;
        }

    }


    @Override
    protected void uIViewInit() {


        getDataFromNet();

    }

    private void getDataFromNet() {
        if (NetworkUtils.isConnected(this)){
            loading.setVisibility(View.VISIBLE);
            HttpRequestUtil.HttpRequestByGet(Config.REMEBER_PRACTICE_URL,this,REQEUST_REMEMBER_TASK);
        }else{
            Toast.makeText(this, R.string.net_error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void uIViewDataApply() {

    }

    @Override
    public void onDataDelivered(int taskId, String data) {
        switch (taskId){
            case REQEUST_REMEMBER_TASK:
                loading.setVisibility(View.GONE);
                RemberPracticeResult remberPracticeResult = ParseJson.parseJson(data, RemberPracticeResult.class);
                if (null!=remberPracticeResult&&null!=remberPracticeResult.data&&null!=remberPracticeResult.data.memoryTrainWordsView){
                    tempList.clear();
                    tempList.addAll(remberPracticeResult.data.memoryTrainWordsView);
                    RemberPracticeEnterAdapter adapter=new RemberPracticeEnterAdapter(remberPracticeResult.data.memoryTrainWordsView);
                    gridView.setAdapter(adapter);
                }
                break;
        }
    }

    @Override
    public void onErrorHappened(int taskId, String errorCode, String errorMessage) {
        Toast.makeText(this, R.string.request_error, Toast.LENGTH_SHORT).show();
        loading.setVisibility(View.GONE);
    }
}
