package com.yzk.practice_brain.database;


import com.yzk.practice_brain.application.ApplicationController;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DBManager {
    static private DBManager dbMgr = new DBManager();
    private DbOpenHelper dbHelper;
    private static final Lock lock = new ReentrantLock();

    private DBManager() {
        dbHelper = DbOpenHelper.getInstance(ApplicationController.getInstance().getApplicationContext());
    }

    public static synchronized DBManager getInstance() {
        lock.lock();
        try {
            if (dbMgr == null) {
                dbMgr = new DBManager();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return dbMgr;
    }
}