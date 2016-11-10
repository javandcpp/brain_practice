package com.android.volley.inter;

/**
 * editByswordman
 * 请求接口回调
 * @param <T>
 */
public interface NetResponseDataListener<T> {
    /**
     * data arrived
     * @param data
     */
    void  onDataDelivered(int taskId, T data);

    /**
     * error happend
     * @param errorCode
     * @param errorMessage
     */
    void onErrorHappened(int taskId, String errorCode, String errorMessage);
}