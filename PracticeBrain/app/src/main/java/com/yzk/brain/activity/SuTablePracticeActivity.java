package com.yzk.brain.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.inter.ResponseStringDataListener;
import com.yzk.brain.R;
import com.yzk.brain.adapter.SutableAdapter;
import com.yzk.brain.application.GlobalApplication;
import com.yzk.brain.base.BaseFragmentActivity;
import com.yzk.brain.bean.SuTableResult;
import com.yzk.brain.config.Config;
import com.yzk.brain.log.LogUtil;
import com.yzk.brain.network.HttpRequestUtil;
import com.yzk.brain.preference.PreferenceHelper;
import com.yzk.brain.setting.Setting;
import com.yzk.brain.ui.CircularProgressView;
import com.yzk.brain.ui.Controller;
import com.yzk.brain.ui.HintDialog;
import com.yzk.brain.ui.RuleDialog;
import com.yzk.brain.utils.NetworkUtils;
import com.yzk.brain.utils.ParseJson;
import com.yzk.brain.utils.PhoneUtils;
import com.yzk.brain.utils.SoundEffect;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by android on 11/24/16.
 */

public class SuTablePracticeActivity extends BaseFragmentActivity implements Controller.ControllerCallBack, ResponseStringDataListener {

    private static final int REQUEST_DATA_TASK = 0x1;
    private static final int REQUEST_COMMIT_TASK = 0x2;

    @Bind(R.id.controlPanel)
    Controller controllPanel;

    @Bind(R.id.loading)
    CircularProgressView loading;
    private SutableAdapter sutableAdapter;

    @Bind(R.id.grid)
    GridView gridView;

    @Bind(R.id.tvScore)
    TextView tvScore;

    private List<SuTableResult.Table> sortTempList = new ArrayList<>();
    private ArrayList<SuTableResult.Table> randomList;
    private int index = 0;
    private int totalScore;

    private final String SuTable_FINISH = "Sutable_finish";
    private final String STUTABLE_SCORE = "sutable_score";


    private Handler mHanlder = new Handler();
    private boolean isFinish;
    private int errorNumber;
    private int errorCount;

    @OnClick({R.id.rule})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.rule:
                RuleDialog.Builder builder = new RuleDialog.Builder(this, "1");
                builder.create().show();
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sutable_layout);
    }

    @Override
    protected void uIViewInit() {

        isFinish = PreferenceHelper.getBool(SuTable_FINISH);
//        totalScore = PreferenceHelper.getScore(STUTABLE_SCORE);

        if (isFinish) {//已练习过,不再记录积分和错误次数
            tvScore.setVisibility(View.GONE);
        } else {//未练习过或积分大于等于0
            tvScore.setVisibility(View.VISIBLE);
            tvScore.setText("错误次数:" + totalScore);
        }


        controllPanel.setClickCallBack(this);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                assertResult(view, i);


            }

            private void assertResult(View view, int i) {
                if (index > sortTempList.size() - 1) {
                    return;
                }
                SuTableResult.Table o = randomList.get(i);
                SuTableResult.Table table = sortTempList.get(index);
                if (o.clicked){
                    return;
                }
                final RelativeLayout backGround = (RelativeLayout) view.findViewById(R.id.backgroud);
                TextView tvText = (TextView) view.findViewById(R.id.tvText);

                if (isFinish) {
                    if (o.key.equals(table.key)) {
                        backGround.setBackgroundResource(R.drawable.home_bgview_blue);
                        if (index == sortTempList.size() - 1) {
                            HintDialog.Builder builder = new HintDialog.Builder(SuTablePracticeActivity.this);
                            HintDialog hintDialog = builder.setStatus(1).setScoreVisiblle(0).create();
                            hintDialog.show();
                            if (1 == Setting.getVoice()) {
                                SoundEffect.getInstance().play(SoundEffect.SUCCESS);
                            }
                            backGround.setEnabled(false);
                            backGround.setClickable(false);
                        } else {

                            if (1 == Setting.getVoice()) {
                                SoundEffect.getInstance().play(SoundEffect.CORRECT);
                            }
                        }
                        o.clicked=true;
                        ++index;
                    } else {
                        Toast toast = Toast.makeText(SuTablePracticeActivity.this, "还是不对，再检查下吧", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                        backGround.setBackgroundResource(R.drawable.home_bgview_green);
                        mHanlder.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                backGround.setBackgroundResource(R.drawable.home_bgview_white);
                            }
                        }, 100);
                        if (1 == Setting.getVoice()) {
                            SoundEffect.getInstance().play(SoundEffect.FAIL);
                        }
                    }
                } else {
                    if (errorNumber < 0) {
                        HintDialog.Builder builder = new HintDialog. Builder(SuTablePracticeActivity.this);
                        HintDialog hintDialog = builder.setStatus(0).setScoreVisiblle(0).create();
                        hintDialog.show();
                        if (1 == Setting.getVoice()) {
                            SoundEffect.getInstance().play(SoundEffect.FAILURE);
                        }
//                        PreferenceHelper.writeBool(SuTable_FINISH, true);//记录第一次
                    } else if (o.key.equals(table.key)) {
                        backGround.setBackgroundResource(R.drawable.home_bgview_blue);
                        backGround.setEnabled(false);
                        backGround.setClickable(false);

                        if (index == sortTempList.size() - 1) {
                           // Toast.makeText(SuTablePracticeActivity.this, "闯关成功", Toast.LENGTH_SHORT).show();
                            if (1 == Setting.getVoice()) {
                                SoundEffect.getInstance().play(SoundEffect.SUCCESS);
                            }

                            HintDialog.Builder builder = new HintDialog.Builder(SuTablePracticeActivity.this);
                            HintDialog hintDialog = builder.setStatus(1).setScoreVisiblle(1).setTvScore(totalScore-errorCount).create();
                            hintDialog.show();

                            forEachList();

                            //上传积分
                            if (NetworkUtils.isConnected(SuTablePracticeActivity.this)) {
//                    score=90&exerciseId=1000&whichDay=1&device=asd123&type=2
                                String params = "&score=" + (totalScore-errorCount)+ "&whichDay=1" + "&type=1" + "&device=" + PhoneUtils.getPhoneIMEI(SuTablePracticeActivity.this);
                                HttpRequestUtil.HttpRequestByGet(Config.COMMIT_SCORE + params, new ResponseStringDataListener() {
                                    @Override
                                    public void onDataDelivered(int taskId, String data) {
                                        LogUtil.e(data);
                                    }

                                    @Override
                                    public void onErrorHappened(int taskId, String errorCode, String errorMessage) {

                                    }
                                }, REQUEST_COMMIT_TASK);
                            }
                            PreferenceHelper.writeBool(SuTable_FINISH, true);
                            return;
                        }else {
                            if (1 == Setting.getVoice()) {
                            //    Toast.makeText(SuTablePracticeActivity.this, "正确", Toast.LENGTH_SHORT).show();
                                SoundEffect.getInstance().play(SoundEffect.CORRECT);
                            }
                        }
                        o.clicked=true;
                        ++index;
                    } else {
                        --errorNumber;
                        ++errorCount;

                        if (errorNumber>=0) {
                            Toast toast = Toast.makeText(SuTablePracticeActivity.this, "还是不对，再检查下吧", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            backGround.setBackgroundResource(R.drawable.home_bgview_green);
                            if (1 == Setting.getVoice()) {
                                SoundEffect.getInstance().play(SoundEffect.FAIL);
                            }
                            mHanlder.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    backGround.setBackgroundResource(R.drawable.home_bgview_white);
                                }
                            }, 100);
                        }
                        if (errorNumber < 0) {
                            errorNumber = -1;

                            HintDialog.Builder builder = new HintDialog.Builder(SuTablePracticeActivity.this);
                            HintDialog hintDialog = builder.setStatus(0).setScoreVisiblle(0).create();
                            hintDialog.show();
                            if (1 == Setting.getVoice()) {
                                SoundEffect.getInstance().play(SoundEffect.FAILURE);
                            }
                        }
                        int score = errorNumber >= 0 ? errorNumber : 0;
                        tvScore.setText("错误次数:" + score);


                    }
                    PreferenceHelper.writeInt(STUTABLE_SCORE, errorNumber);//每次错误记录分数

                }
            }
        });


    }


    private void forEachList() {
        for (SuTableResult.Table table :
                randomList) {
            table.flag = true;
            table.clicked=false;
        }
        index = 0;
        gridView.setEnabled(false);
        sutableAdapter.notifyDataSetChanged();
    }


    @Override
    protected void uIViewDataApply() {
        getDataFromNet();
    }

    private void getDataFromNet() {
        if (NetworkUtils.isConnected(this)) {
            loading.setVisibility(View.VISIBLE);
            HttpRequestUtil.HttpRequestByGet(Config.SUTABLE_PRACTICE_URL, this, REQUEST_DATA_TASK);
        } else {
            Toast.makeText(this, R.string.net_error, Toast.LENGTH_SHORT).show();
        }
    }
    private void retry() {
        index = 0;
        randomList.clear();

        randomList.addAll(sortTempList);
        randomList = randomList(randomList);
        for (SuTableResult.Table table :
                randomList) {
            table.flag = false;
            table.clicked=false;
        }
        index = 0;
        gridView.setEnabled(true);
        sutableAdapter.setData(randomList);
    }


    @Override
    public void controll(View view) {
        switch (view.getId()) {
            case R.id.retry:
               retry();
                break;
            case R.id.back:
                finish();
                break;

            case R.id.play:
                try {
                    if (GlobalApplication.instance.getiMediaInterface().isPlaying()) {
                        ((ImageButton) view).setSelected(false);
                        GlobalApplication.instance.getiMediaInterface().pause();
                    } else {
                        ((ImageButton) view).setSelected(true);
                        GlobalApplication.instance.getiMediaInterface().play();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

        }
    }


    @Override
    public void onDataDelivered(int taskId, String data) {
        switch (taskId) {
            case REQUEST_DATA_TASK:
                if (null != loading) {
                    loading.setVisibility(View.GONE);
                }
                sortTempList.clear();
                SuTableResult suTableResult = ParseJson.parseJson(data, SuTableResult.class);
                if (null != suTableResult && null != suTableResult.data && suTableResult.data.fiveContentView.size() > 0) {
                    errorNumber = suTableResult.data.five.errorNumber;
                    totalScore=suTableResult.data.five.fiveScore;
                    tvScore.setText("错误次数:" + errorNumber);

                    sortTempList.addAll(suTableResult.data.fiveContentView);

                    randomList = randomList(suTableResult.data.fiveContentView);
                    LogUtil.e(suTableResult.toString());
                    if (null == sutableAdapter) {
                        sutableAdapter = new SutableAdapter();
                        gridView.setAdapter(sutableAdapter);
                    }
                    sutableAdapter.setData(randomList);
                }

                break;
        }
    }

    @Override
    public void onErrorHappened(int taskId, String errorCode, String errorMessage) {
        Toast.makeText(this, R.string.request_error, Toast.LENGTH_SHORT).show();
        if (null != loading) {
            loading.setVisibility(View.GONE);
        }
    }
}
