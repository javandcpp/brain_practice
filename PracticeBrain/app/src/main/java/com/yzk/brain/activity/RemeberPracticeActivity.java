package com.yzk.brain.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.inter.ResponseStringDataListener;
import com.yzk.brain.R;
import com.yzk.brain.adapter.RemberPracticeLeftAdapter;
import com.yzk.brain.adapter.RemberPracticeRightAdapter;
import com.yzk.brain.application.GlobalApplication;
import com.yzk.brain.base.BaseFragmentActivity;
import com.yzk.brain.bean.RemberPracticeResult;
import com.yzk.brain.config.Config;
import com.yzk.brain.constants.Constants;
import com.yzk.brain.log.LogUtil;
import com.yzk.brain.network.HttpRequestUtil;
import com.yzk.brain.preference.PreferenceHelper;
import com.yzk.brain.setting.Setting;
import com.yzk.brain.ui.Controller;
import com.yzk.brain.ui.HintDialog;
import com.yzk.brain.ui.RuleDialog;
import com.yzk.brain.utils.NetworkUtils;
import com.yzk.brain.utils.PhoneUtils;
import com.yzk.brain.utils.SoundEffect;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by android on 11/24/16.
 */

public class RemeberPracticeActivity extends BaseFragmentActivity implements Controller.ControllerCallBack {

    private static final int REQUEST_COMMIT_TASK = 0x1;
    private static final String REMEBER_TEST_PRACTICE_FINISH ="test" ;
    @Bind(R.id.controlPanel)
    Controller controllPanel;

    @Bind(R.id.leftGrid)
    GridView leftGrid;

    @Bind(R.id.rightGrid)
    GridView rightGrid;

    @Bind(R.id.tvHint)
    TextView tvScore;
    private ArrayList<RemberPracticeResult.Practice> dataList;


    private ArrayList<RemberPracticeResult.Practice> prsientDataList = new ArrayList<>();
    private List<RemberPracticeResult.Practice> sortDataList = new ArrayList<>();
    private ArrayList<RemberPracticeResult.Practice> randomList = new ArrayList<>();
    private List<RemberPracticeResult.Practice> leftList = new ArrayList<>();

    private RemberPracticeRightAdapter rightAdapter;
    private int index;
    private int totalScore;

    private static final String REMEBER_PRACTICE_FINISH = "remember_practice_finish";
    private static final String REMEMBER_PRACTICE_SCORE = "remember_practice_score";
    private RemberPracticeLeftAdapter leftAdapter;
    private boolean isTest;
    private boolean isFinish;
    private RemberPracticeResult.PracticeEntity practiceEntity;
    private int errorNumber;
    private int errorCount;

    @OnClick({R.id.rule})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.rule:
                RuleDialog.Builder builder = new RuleDialog.Builder(this, "2");
                builder.create().show();
                break;
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isTest = getIntent().getBooleanExtra("isTest", false);
        dataList = (ArrayList<RemberPracticeResult.Practice>) getIntent().getSerializableExtra("data");
        practiceEntity = (RemberPracticeResult.PracticeEntity) getIntent().getSerializableExtra("entity");

        totalScore=practiceEntity.memoryTrain.memoryTrainScore;
        errorNumber=practiceEntity.memoryTrain.memoryTrainErrorNumber;

        setContentView(R.layout.remeber_practice_layout);
    }

    private void assertResult(View view, int i) {
        if (index > sortDataList.size() - 1) {
//            index = sortDataList.size() - 1;
            return;
        }
        RemberPracticeResult.Practice o = randomList.get(i);
        if (o.clicked){
            return;
        }
        RemberPracticeResult.Practice target = sortDataList.get(index);

        if (isFinish) {
            if (o.key.equals(target.key)) {
                o.clicked=true;
                if (null == leftAdapter) {
                    leftAdapter = new RemberPracticeLeftAdapter();
                    leftGrid.setAdapter(leftAdapter);
                }
                leftList.add(target);
                leftAdapter.setData(leftList);

                if (index == sortDataList.size() - 1) {
                    HintDialog.Builder builder = new HintDialog. Builder(RemeberPracticeActivity.this);
                    HintDialog hintDialog = builder.setStatus(1).setScoreVisiblle(0).setTest(isTest).create();
                    hintDialog.show();
                    if (isTest) {
                        PreferenceHelper.writeInt(Constants.TWENTY_ONE, 1);
                    }
                    if (1 == Setting.getVoice()) {
                        SoundEffect.getInstance().play(SoundEffect.SUCCESS);
                    }

                } else {
                    if (1 == Setting.getVoice()) {
                        SoundEffect.getInstance().play(SoundEffect.CORRECT);
                    }
                }
                ++index;
            } else {
                Toast toast = Toast.makeText(this, "还是不对，再检查下吧", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

                if (1 == Setting.getVoice()) {
                    SoundEffect.getInstance().play(SoundEffect.FAIL);
                }

            }
        } else {
            if (errorNumber < 0) {
                HintDialog.Builder builder = new HintDialog. Builder(RemeberPracticeActivity.this);
                HintDialog hintDialog = builder.setStatus(0).setScoreVisiblle(0).setTest(isTest).create();
                hintDialog.show();
                if (1 == Setting.getVoice()) {
                    SoundEffect.getInstance().play(SoundEffect.FAILURE);
                }
//                PreferenceHelper.writeBool(REMEBER_PRACTICE_FINISH, true);//记

            } else if (o.key.equals(target.key)) {

                o.clicked=true;
                if (null == leftAdapter) {
                    leftAdapter = new RemberPracticeLeftAdapter();
                    leftGrid.setAdapter(leftAdapter);
                }
                leftList.add(target);
                leftAdapter.setData(leftList);

                if (index == sortDataList.size() - 1) {
                    HintDialog.Builder builder = new HintDialog. Builder(RemeberPracticeActivity.this);
                    HintDialog hintDialog = builder.setStatus(1).setScoreVisiblle(1).setTest(isTest).setTvScore(totalScore-errorCount).create();
                    hintDialog.show();

                    //上传积分
                    if (NetworkUtils.isConnected(this)) {
//                    score=90&exerciseId=1000&whichDay=1&device=asd123&type=2
                        String params = "&score=" +( totalScore-errorCount) + "&whichDay=1" + "&type=2" + "&device=" + PhoneUtils.getPhoneIMEI(this);
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
                    if (isTest) {
                        PreferenceHelper.writeBool(REMEBER_TEST_PRACTICE_FINISH, true);//记录第一次
                    }else{
                        PreferenceHelper.writeBool(REMEBER_PRACTICE_FINISH, true);//记录第一次
                    }

                    if (isTest) {
                        PreferenceHelper.writeInt(Constants.TWENTY_ONE, 1);
                    }
                    if (1 == Setting.getVoice()) {
                        SoundEffect.getInstance().play(SoundEffect.SUCCESS);
                    }

                } else {
                    if (1 == Setting.getVoice()) {
                        SoundEffect.getInstance().play(SoundEffect.CORRECT);
                    }
                }
                ++index;
            } else {
                --errorNumber;
                ++errorCount;
                if (errorNumber>=0) {
                  if (1 == Setting.getVoice()){
                    SoundEffect.getInstance().play(SoundEffect.FAIL);
                  }
                    Toast toast = Toast.makeText(this, "还是不对，再检查下吧", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                if (errorNumber < 0) {
                    errorNumber = -1;
                    HintDialog.Builder builder = new HintDialog. Builder(RemeberPracticeActivity.this);
                    HintDialog hintDialog = builder.setStatus(0).setScoreVisiblle(0).setTest(isTest).create();
                    hintDialog.show();
                    if (1 == Setting.getVoice()) {
                        SoundEffect.getInstance().play(SoundEffect.FAILURE);
                    }
                }
                int score = errorNumber >= 0 ? errorNumber : 0;
                tvScore.setText("错误次数:" + score);
            }
            PreferenceHelper.writeInt(REMEMBER_PRACTICE_SCORE, totalScore);//每次错误记录分数
        }


    }

    @Override
    protected void uIViewInit() {
        if (isTest) {
            isFinish = PreferenceHelper.getBool(REMEBER_TEST_PRACTICE_FINISH);
        }else{
            isFinish = PreferenceHelper.getBool(REMEBER_PRACTICE_FINISH);
        }


//        totalScore = PreferenceHelper.getScore(REMEMBER_PRACTICE_SCORE);

        if (isFinish) {//已练习过,不再记录积分和错误次数
            tvScore.setVisibility(View.GONE);
        } else {//未练习过或积分大于等于0
            tvScore.setVisibility(View.VISIBLE);
            tvScore.setText("错误次数:" + errorNumber);
        }


        prsientDataList.addAll(dataList);
        sortDataList.addAll(prsientDataList);
        randomList = randomList(prsientDataList);
        controllPanel.setClickCallBack(this);


        rightGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                assertResult(view, i);
            }
        });


    }

    @Override
    protected void uIViewDataApply() {

        if (null == rightAdapter) {
            rightAdapter = new RemberPracticeRightAdapter();
            rightGrid.setAdapter(rightAdapter);
        }
        rightAdapter.setData(randomList);
    }

    @Override
    public void controll(View view) {
        switch (view.getId()) {
            case R.id.retry:
                retry();
                break;
            case R.id.back:
                if (isTest) {

                } else {

                }
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

    private void retry() {
        index = 0;
        randomList.clear();
        prsientDataList.clear();
        prsientDataList.addAll(dataList);
        randomList.addAll(prsientDataList);
        randomList = randomList(randomList);

        for (RemberPracticeResult.Practice practice:randomList){
                practice.clicked=false;
        }
        rightAdapter.setData(randomList);

        if (null != leftAdapter) {
            leftList.clear();
            leftAdapter.setData(leftList);
        }
    }
}
