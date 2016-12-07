package com.yzk.practice_brain.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.yzk.practice_brain.R;
import com.yzk.practice_brain.adapter.ImagePracticeLeftAdapter;
import com.yzk.practice_brain.adapter.ImagePracticeRightAdapter;
import com.yzk.practice_brain.application.GlobalApplication;
import com.yzk.practice_brain.base.BaseFragmentActivity;
import com.yzk.practice_brain.bean.ImageResult;
import com.yzk.practice_brain.log.LogUtil;
import com.yzk.practice_brain.setting.Setting;
import com.yzk.practice_brain.ui.Controller;
import com.yzk.practice_brain.ui.RuleDialog;
import com.yzk.practice_brain.utils.SoundEffect;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by android on 11/24/16.
 */

public class ImageRemeberPracticeActivity extends BaseFragmentActivity implements Controller.ControllerCallBack {

    @Bind(R.id.controlPanel)
    Controller controlPanel;

    @Bind(R.id.rightGrid)
    GridView rightGrid;

    @Bind(R.id.leftGrid)
    GridView leftGrid;



    private ArrayList<ImageResult.Image> dataList;


    private ArrayList<ImageResult.Image> prsientDataList=new ArrayList<>();
    private ArrayList<ImageResult.Image> sortDataList=new ArrayList<>();
    private ArrayList<ImageResult.Image> randomList=new ArrayList<>();
    private ArrayList<ImageResult.Image> leftList=new ArrayList<>();
    private int index;
    private ImagePracticeRightAdapter rightAdapter;
    private ImagePracticeLeftAdapter leftAdapter;
    private int score=10;

    @OnClick(R.id.rule)
    public void click(View view){
        switch (view.getId()){
            case R.id.rule:
                RuleDialog.Builder builder=new RuleDialog.Builder(this,"4");
                builder.create().show();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataList = (ArrayList<ImageResult.Image>) getIntent().getSerializableExtra("data");
        setContentView(R.layout.image_remeber_practice_layout);
    }


    @Override
    protected void uIViewInit() {
        prsientDataList.addAll(dataList);
        sortDataList.addAll(prsientDataList);
        randomList=randomList(prsientDataList);

        rightGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtil.e("position:"+position);
                assetValue(position);
            }
        });
        controlPanel.setClickCallBack(this);
    }

    private void assetValue(int position){
        if (index > sortDataList.size() - 1) {
//            index = sortDataList.size() - 1;
            return;
        }
        ImageResult.Image o = randomList.get(position);
        ImageResult.Image target= sortDataList.get(index);

        if (o.key.equals(target.key)) {

            if (null==leftAdapter){
                leftAdapter=new ImagePracticeLeftAdapter();
                leftGrid.setAdapter(leftAdapter);
            }
            leftList.add(target);
            leftAdapter.setData(leftList);

            if (index == sortDataList.size() - 1) {
                Toast.makeText(this, "闯关成功", Toast.LENGTH_SHORT).show();

                if (1== Setting.getVoice()) {
                    SoundEffect.getInstance().play(SoundEffect.SUCCESS);
                }
            }else {
                Toast.makeText(this, "正确", Toast.LENGTH_SHORT).show();
                if (1 == Setting.getVoice()) {
                    SoundEffect.getInstance().play(SoundEffect.CORRECT);
                }
            }
            ++index;
        } else {
            Toast.makeText(this, "错误", Toast.LENGTH_SHORT).show();
            --score;
            if (1==Setting.getVoice()) {
                SoundEffect.getInstance().play(SoundEffect.FAIL);
            }
            if (score<0){
                //index=0;
            }
        }

    }

    @Override
    protected void uIViewDataApply() {
        if (null==rightAdapter){
            rightAdapter = new ImagePracticeRightAdapter();
            rightGrid.setAdapter(rightAdapter);
        }
        rightAdapter.setData(randomList);
    }

    @Override
    public void controll(View view) {
        switch (view.getId()){
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
            index=0;
            randomList.clear();
            prsientDataList.clear();
            prsientDataList.addAll(dataList);
            randomList.addAll(prsientDataList);
            randomList=randomList(prsientDataList);
            rightAdapter.setData(randomList);

            leftList.clear();
            leftAdapter.setData(leftList);
    }
}
