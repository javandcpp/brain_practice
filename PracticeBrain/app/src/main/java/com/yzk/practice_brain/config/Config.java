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


}
