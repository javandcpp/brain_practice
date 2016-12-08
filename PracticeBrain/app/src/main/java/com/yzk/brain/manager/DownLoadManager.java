package com.yzk.brain.manager;

import com.yzk.brain.task.DownloadRunnable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 下载单例
 * Created by android on 12/1/16.
 */

public class DownLoadManager {


    private final ExecutorService threadPool;

    private final LinkedBlockingQueue linkedBlockingQueue;

    public enum MEDIA_TYPE {
        BGMUSIC, NORMAL
    }

    public enum METHOD {
        POST, GET
    }

    private static final ReentrantLock reentrantLock = new ReentrantLock();

    public LinkedBlockingQueue getQueue(){
        return linkedBlockingQueue;
    }

    private DownLoadManager() {
        threadPool = Executors.newCachedThreadPool();
        linkedBlockingQueue = new LinkedBlockingQueue();
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        DownloadRunnable task = null;
                        task = (DownloadRunnable) linkedBlockingQueue.take();
                        threadPool.execute(task);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static DownLoadManager instance;

    public static DownLoadManager getInstance() {
        if (null == instance) {
            reentrantLock.lock();
            try {
                if (null == instance) {
                    instance = new DownLoadManager();
                    return instance;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
            }
        }
        return instance;
    }
}
