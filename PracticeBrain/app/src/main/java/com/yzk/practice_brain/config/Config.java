package com.yzk.practice_brain.config;

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
    public static final String REMEBER_PRACTICE_URL=BaseUrl+"/kujisoftware/memoryTrain/findMemoryTrainByExerciseAndWhichDayAndType?exerciseId=1001&whichDay=1&type=2";


    //舒尔特表规则
    public static final String STUTABLE_RULE=BaseUrl+"/kujisoftware/typeRules/findTypeRulesById?id=1";

    //记忆训练规则
    public static final String REMEMBER_PRACTICE_RULE=BaseUrl+"/kujisoftware/typeRules/findTypeRulesById?id=2";

    //学习讲解规则
    public static final String JIANJIE_PRACTICE_RULE=BaseUrl+"/kujisoftware/typeRules/findTypeRulesById?id=3";

    //图片记忆规则
    public static final String IMAGE_REMEBER_RULE=BaseUrl+"/kujisoftware/typeRules/findTypeRulesById?id=4";

    //曼陀罗规则
    public static final String MANDALA_RULE=BaseUrl+"/kujisoftware/typeRules/findTypeRulesById?id=5";
}
