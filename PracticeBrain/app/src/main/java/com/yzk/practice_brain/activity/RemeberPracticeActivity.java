package com.yzk.practice_brain.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.yzk.practice_brain.R;
import com.yzk.practice_brain.adapter.RemberPracticeLeftAdapter;
import com.yzk.practice_brain.adapter.RemberPracticeRightAdapter;
import com.yzk.practice_brain.application.GlobalApplication;
import com.yzk.practice_brain.base.BaseFragmentActivity;
import com.yzk.practice_brain.bean.RemberPracticeResult;
import com.yzk.practice_brain.constants.Constants;
import com.yzk.practice_brain.preference.PreferenceHelper;
import com.yzk.practice_brain.setting.Setting;
import com.yzk.practice_brain.ui.Controller;
import com.yzk.practice_brain.ui.RuleDialog;
import com.yzk.practice_brain.utils.SoundEffect;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by android on 11/24/16.
 */

public class RemeberPracticeActivity extends BaseFragmentActivity implements Controller.ControllerCallBack {

    @Bind(R.id.controlPanel)
    Controller controllPanel;

    @Bind(R.id.leftGrid)
    GridView leftGrid;

    @Bind(R.id.rightGrid)
    GridView rightGrid;

    private ArrayList<RemberPracticeResult.Practice> dataList;

    private ArrayList<RemberPracticeResult.Practice> prsientDataList = new ArrayList<>();
    private List<RemberPracticeResult.Practice> sortDataList = new ArrayList<>();
    private List<RemberPracticeResult.Practice> randomList = new ArrayList<>();
    private List<RemberPracticeResult.Practice> leftList = new ArrayList<>();

    private RemberPracticeRightAdapter rightAdapter;
    private int index;
    private int score = 10;
    private RemberPracticeLeftAdapter leftAdapter;
    private boolean isTest;

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

        setContentView(R.layout.remeber_practice_layout);
    }

    private void assertResult(View view, int i) {
        if (index > sortDataList.size() - 1) {
//            index = sortDataList.size() - 1;
            return;
        }
        RemberPracticeResult.Practice o = randomList.get(i);
        RemberPracticeResult.Practice target = sortDataList.get(index);

        if (o.value.equals(target.value)) {

            if (null == leftAdapter) {
                leftAdapter = new RemberPracticeLeftAdapter();
                leftGrid.setAdapter(leftAdapter);
            }
            leftList.add(target);
            leftAdapter.setData(leftList);

            if (index == sortDataList.size() - 1) {
                Toast.makeText(this, "闯关成功", Toast.LENGTH_SHORT).show();
                if (isTest) {
                    PreferenceHelper.writeInt(Constants.TWENTY_ONE, 1);
                }
                if (1 == Setting.getVoice()) {
                    SoundEffect.getInstance().play(SoundEffect.SUCCESS);
                }

            } else {

                Toast.makeText(this, "正确", Toast.LENGTH_SHORT).show();
                if (1 == Setting.getVoice()) {
                    SoundEffect.getInstance().play(SoundEffect.CORRECT);
                }
            }
            ++index;
        } else {
            Toast.makeText(this, "错误", Toast.LENGTH_SHORT).show();
            --score;
            if (1 == Setting.getVoice()) {
                SoundEffect.getInstance().play(SoundEffect.FAIL);
            }
            if (score < 0) {
                //index=0;
            }
        }


    }

    @Override
    protected void uIViewInit() {
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
        rightAdapter.setData(randomList);


        if (null != leftAdapter) {
            leftList.clear();
            leftAdapter.setData(leftList);
        }
    }
}
