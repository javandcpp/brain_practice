package com.yzk.brain.config;

/**
 * Created by android on 11/12/15.
 */
public class Config {



    private static final String BaseUrl="http://123.56.190.160:8999";

    //新闻资讯获取
    public static final String EDUCATION_NEWS_URL=BaseUrl+"/kujisoftware/eduCounse/findEduCounseAll";

    //背景音乐获取
    public static final String BACKGROUND_MUSIC_URL=BaseUrl+"/kujisoftware/musicUpload/findMusic";

    //舒尔特表训练
    public static final String SUTABLE_PRACTICE_URL=BaseUrl+"/kujisoftware/questionFive/findFiveByExerciseAndWhichDayAndType?exerciseId=1000&Type=1&whichDay=1";

    //记忆训练
    public static final String REMEBER_PRACTICE_URL=BaseUrl+"/kujisoftware/memoryTrain/findMemoryTrainByExerciseAndWhichDayAndType?exerciseId=1000&whichDay=1&type=2";

    //测试记忆训练
    public static final String TEST_REMEBER_PRACTICE_URL=BaseUrl+"/kujisoftware/memoryTrain/findMemoryTrainByExerciseAndWhichDayAndType?exerciseId=1000&whichDay=c1&type=2";

    //规则
    public static final String RULE=BaseUrl+"/kujisoftware/typeRules/findTypeRulesById?id=";

    //学习讲解
    public static final String EXPLAIN=BaseUrl+"/kujisoftware/learnExplain/findAllLearnExplain";

    //图片记忆
    public static final String IMAGE_REMEBER=BaseUrl+"/kujisoftware/pictureMemory/findPictureByExerciseAndWhichDayAndType?exerciseId=1000&Type=4&whichDay=1";

    //获取积分
    public static final String GET_SCORE=BaseUrl+"/kujisoftware/score/findScoreByDevice?device=";

    //提交积分
    public static final String COMMIT_SCORE=BaseUrl+"/kujisoftware/score/saveScore?exerciseId=1000";

    //帮助说明
    public static final String HELP_URL=BaseUrl+"/kujisoftware/help/findHelpByExerciseId?whichExercise=1000";

}
