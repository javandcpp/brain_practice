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
import com.yzk.brain.adapter.ImagePracticeLeftAdapter;
import com.yzk.brain.adapter.ImagePracticeRightAdapter;
import com.yzk.brain.application.GlobalApplication;
import com.yzk.brain.base.BaseFragmentActivity;
import com.yzk.brain.bean.ImageResult;
import com.yzk.brain.config.Config;
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

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by android on 11/24/16.
 */

public class ImageRemeberPracticeActivity extends BaseFragmentActivity implements Controller.ControllerCallBack {

    private static final int REQUEST_COMMIT_TASK = 0x1;
    @Bind(R.id.controlPanel)
    Controller controlPanel;

    @Bind(R.id.rightGrid)
    GridView rightGrid;

    @Bind(R.id.leftGrid)
    GridView leftGrid;

    @Bind(R.id.tvHint)
    TextView tvScore;


    private final String IMAGE_PRACTICE_FINISH = "image_practice_finish";
    private final String IMAGE_PRACTICE_SCORE = "image_practice_score";
    private boolean isFinish;
    private int totalScore;
    private ArrayList<ImageResult.Image> dataList;


    private ArrayList<ImageResult.Image> prsientDataList = new ArrayList<>();
    private ArrayList<ImageResult.Image> sortDataList = new ArrayList<>();
    private ArrayList<ImageResult.Image> randomList = new ArrayList<>();
    private ArrayList<ImageResult.Image> leftList = new ArrayList<>();
    private int index;
    private ImagePracticeRightAdapter rightAdapter;
    private ImagePracticeLeftAdapter leftAdapter;
    private int errorNumber;
    private int errorCount;

    @OnClick(R.id.rule)
    public void click(View view) {
        switch (view.getId()) {
            case R.id.rule:
                RuleDialog.Builder builder = new RuleDialog.Builder(this, "4");
                builder.create().show();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        dataList = (ArrayList<ImageResult.Image>) getIntent().getSerializableExtra("data");
        ImageResult.ImageEntity entity = (ImageResult.ImageEntity) getIntent().getSerializableExtra("entity");

        totalScore=entity.pictureMemory.pictureMemoryScore;
        errorNumber=entity.pictureMemory.pictureMemoryErrorNumber;
        setContentView(R.layout.image_remeber_practice_layout);
    }


    @Override
    protected void uIViewInit() {

        isFinish = PreferenceHelper.getBool(IMAGE_PRACTICE_FINISH);
//        totalScore = PreferenceHelper.getScore(IMAGE_PRACTICE_SCORE);

        if (isFinish) {//已练习过,不再记录积分和错误次数
            tvScore.setVisibility(View.GONE);
        } else {//未练习过或积分大于等于0
            tvScore.setVisibility(View.VISIBLE);
            tvScore.setText("错误次数:" + errorNumber);
        }

        prsientDataList.addAll(dataList);
        sortDataList.addAll(prsientDataList);
        randomList = randomList(prsientDataList);

        rightGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtil.e("position:" + position);
                assetValue(position);
            }
        });
        controlPanel.setClickCallBack(this);
    }

    private void assetValue(int position) {
        if (index > sortDataList.size() - 1) {
//            index = sortDataList.size() - 1;
            return;
        }
        ImageResult.Image o = randomList.get(position);
        if (o.clicked){
            return;
        }
        ImageResult.Image target = sortDataList.get(index);

        if (isFinish) {
            if (o.key.equals(target.key)) {
                o.clicked=true;
                if (index == sortDataList.size() - 1) {
                    HintDialog.Builder builder = new HintDialog. Builder(ImageRemeberPracticeActivity.this);
                    HintDialog hintDialog = builder.setStatus(1).setScoreVisiblle(0).create();
                    hintDialog.show();
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
                HintDialog.Builder builder = new HintDialog. Builder(ImageRemeberPracticeActivity.this);
                HintDialog hintDialog = builder.setStatus(0).setScoreVisiblle(0).create();
                hintDialog.show();
                if (1 == Setting.getVoice()) {
                    SoundEffect.getInstance().play(SoundEffect.FAILURE);
                }
//                PreferenceHelper.writeBool(IMAGE_PRACTICE_FINISH, true);//记录第一次
            } else if (o.key.equals(target.key)) {
                o.clicked=true;
                if (null == leftAdapter) {
                    leftAdapter = new ImagePracticeLeftAdapter();
                    leftGrid.setAdapter(leftAdapter);
                }
                leftList.add(target);
                leftAdapter.setData(leftList);

                if (index == sortDataList.size() - 1) {

                    HintDialog.Builder builder = new HintDialog. Builder(ImageRemeberPracticeActivity.this);
                    HintDialog hintDialog = builder.setStatus(1).setScoreVisiblle(1).setTvScore(totalScore-errorCount).create();
                    hintDialog.show();

                    if (1 == Setting.getVoice()) {
                        SoundEffect.getInstance().play(SoundEffect.SUCCESS);
                    }
                    //上传积分
                    if (NetworkUtils.isConnected(this)) {
//                    score=90&exerciseId=1000&whichDay=1&device=asd123&type=2
                        String params = "&score=" + (totalScore-errorCount) + "&whichDay=1" + "&type=4" + "&device=" + PhoneUtils.getPhoneIMEI(this);
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
                    PreferenceHelper.writeBool(IMAGE_PRACTICE_FINISH, true);//记录第一次

                } else {
                    if (1 == Setting.getVoice()) {
                        SoundEffect.getInstance().play(SoundEffect.CORRECT);
                    }
                }
                ++index;
            } else {
                --errorNumber;
                ++errorCount;
                if (errorNumber>=0){
                    if (1 == Setting.getVoice()) {
                        SoundEffect.getInstance().play(SoundEffect.FAIL);
                    }
                    Toast toast = Toast.makeText(this, "还是不对，再检查下吧", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }

                if (errorNumber < 0) {
                    errorNumber = -1;
                    HintDialog.Builder builder = new HintDialog. Builder(ImageRemeberPracticeActivity.this);
                    HintDialog hintDialog = builder.setStatus(0).setScoreVisiblle(0).create();
                    hintDialog.show();
                    if (1 == Setting.getVoice()) {
                        SoundEffect.getInstance().play(SoundEffect.FAILURE);
                    }
                }
                int score = errorNumber >= 0 ? errorNumber : 0;
                tvScore.setText("错误次数:" + score);

            }
            PreferenceHelper.writeInt(IMAGE_PRACTICE_SCORE, errorNumber);//每次错误记录分数
        }


    }

    @Override
    protected void uIViewDataApply() {
        if (null == rightAdapter) {
            rightAdapter = new ImagePracticeRightAdapter();
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
        randomList = randomList(prsientDataList);

        for (ImageResult.Image image:randomList){
           image .clicked=false;
        }
        rightAdapter.setData(randomList);
        if (null != leftAdapter) {
            leftList.clear();
            leftAdapter.setData(leftList);
        }
    }
}
