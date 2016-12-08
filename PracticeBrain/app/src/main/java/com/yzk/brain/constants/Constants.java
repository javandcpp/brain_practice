package com.yzk.brain.constants;

import com.yzk.brain.application.GlobalApplication;
import com.yzk.brain.utils.FileUtils;

import java.io.File;

/**
 * Created by android on 11/23/16.
 */

public interface Constants {
    String TWENTY_ONE="21record";


    //缓存下载背景音乐文件的文件名key
    public static final String sp_music="musics";

    //背景音乐存储路径
//    public static final String MUSIC_PATH= FileUtils.getDiskCacheDir(GlobalApplication.instance)+ File.separator+"music_file";
    public static final String MUSIC_PATH= FileUtils.getDiskCacheDir(GlobalApplication.instance)+ File.separator+"music_file";
    public static final String EXPLAIN_PATH= FileUtils.getDiskCacheDir(GlobalApplication.instance)+ File.separator+"voice_file";




}
