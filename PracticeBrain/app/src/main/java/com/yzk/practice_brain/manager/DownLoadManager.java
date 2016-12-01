package com.yzk.practice_brain.manager;

import com.android.volley.HTTPSTrustManager;
import com.yzk.practice_brain.FileDownloadError;
import com.yzk.practice_brain.log.LogUtil;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.BasicHttpEntity;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 下载单例
 * Created by android on 12/1/16.
 */

public class DownLoadManager {

    private static final String HEADER_CONTENT_TYPE = "Content-Type";
    private static final String DEFAULT_PARAMS_ENCODING = "UTF-8";
    private final ExecutorService threadPool;
    private final int timeoutMs = 2500;//2500毫秒
    private final LinkedBlockingQueue linkedBlockingQueue;

    public enum MEDIA_TYPE {
        BGMUSIC, NORMAL
    }

    public enum METHOD {
        POST, GET
    }

    private static final ReentrantLock reentrantLock = new ReentrantLock();

    private DownLoadManager() {
        threadPool = Executors.newCachedThreadPool();
        linkedBlockingQueue = new LinkedBlockingQueue();
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

    /**
     * 开启下载
     *
     * @param method
     * @param media_type
     * @param url
     * @param fileName
     * @param cachedPath
     * @param headers
     */
    public void startDownLoad(METHOD method, MEDIA_TYPE media_type, String url, String fileName, String cachedPath, Map<String, String> headers) {
        DownloadRunnable downloadRunnable = new DownloadRunnable(method, media_type, url, fileName, cachedPath, headers);
        threadPool.execute(downloadRunnable);
        com.yzk.practice_brain.log.LogUtil.e("start download:" + url);
    }


    /**
     * 下载任务
     */
    private class DownloadRunnable implements Runnable {

        private final MEDIA_TYPE mMedia_type;
        private final String mFileName;
        private final String mCachePath;
        private final String mHttpUrl;
        private final METHOD mMethod;
        private final Map<String, String> mHeaders;
        private int retryTime = 3;


        /**
         * @param media_type 下载音乐类型
         * @param url        音乐链接
         * @param fileName   音乐文件名
         * @param cachedPath 存储路径
         */
        public DownloadRunnable(METHOD method, MEDIA_TYPE media_type, String url, String fileName, String cachedPath, Map<String, String> headers) {
            this.mMedia_type = media_type;
            this.mHttpUrl = url;
            this.mFileName = fileName;
            this.mMethod = method;
            this.mCachePath = cachedPath;
            this.mHeaders = headers;
        }

        @Override
        public void run() {
            while (retryTime > 0) {
                try {

                    URL url = new URL(mHttpUrl);
                    HttpURLConnection connection = createConnection(url);
                    if (null != mHeaders) {
                        for (String headerName : mHeaders.keySet()) {
                            connection.addRequestProperty(headerName, mHeaders.get(headerName));
                        }
                        LogUtil.e("header:" + mHeaders.toString());
                    }
                    connection.addRequestProperty(HEADER_CONTENT_TYPE, "application/x-www-form-urlencoded; charset=UTF-8");
                    connection.setRequestProperty("Accept-Encoding", "identity");
                    if (mMethod == METHOD.POST) {
                        connection.setDoInput(true);
                        connection.setDoOutput(true);
                        connection.setRequestMethod("POST");
                        byte[] params = encodeParameters(mHeaders, DEFAULT_PARAMS_ENCODING);
                        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                        out.write(params);
                        out.close();

                    } else {
                        connection.setDoInput(true);
                        connection.setRequestMethod("GET");
                    }
//                    int statusCode = connection.getResponseCode();
//                    if (200 == statusCode) {
//                        InputStream inputStream = connection.getInputStream();
//                        byte data[] = new byte[4096];
//                        long total = 0;
//                        int count;
//                        LogUtil.e(connection.getContentLength() + "");
//                        ByteArrayOutputStream output = new ByteArrayOutputStream();
//                        while ((count = inputStream.read(data)) != -1) {
//                            total += count;
//                            output.write(data, 0, count);
//                            LogUtil.e("read size:" + total + ",contentlength" + connection.getContentLength());
//                        }
//
//                        inputStream.close();
//                        output.close();
//                        connection.disconnect();
//
//                        retryTime = 0;
//                    }


//                    }
//                    ProtocolVersion protocolVersion = new ProtocolVersion("HTTP", 1, 1);
                    int responseCode = connection.getResponseCode();
                    if (responseCode == -1) {
                        throw new IOException("Could not retrieve response code from HttpUrlConnection.");
                    }
//                    StatusLine responseStatus = new BasicStatusLine(protocolVersion,
//                            connection.getResponseCode(), connection.getResponseMessage());
//                    BasicHttpResponse response = new BasicHttpResponse(responseStatus);
                    LogUtil.e(responseCode + "----->>requestUrl:" + mHttpUrl);
                    if (responseCode == HttpStatus.SC_OK) {
                        HttpEntity entity=entityFromConnection(connection);
                        entityToBytes(entity);
                    }
                    com.yzk.practice_brain.log.LogUtil.e("Download http response code:" + responseCode);

                } catch (SocketTimeoutException e) {
                    com.yzk.practice_brain.log.LogUtil.e("socket timeout");
                    fileIsExist();
                } catch (ConnectTimeoutException e) {
                    com.yzk.practice_brain.log.LogUtil.e("connect timeout");
                    fileIsExist();
                } catch (MalformedURLException e) {
                    com.yzk.practice_brain.log.LogUtil.e("malformed url");
                    fileIsExist();
                } catch (IOException e) {
                    com.yzk.practice_brain.log.LogUtil.e("ioexception");
                    fileIsExist();
                } catch (Exception e) {
                    com.yzk.practice_brain.log.LogUtil.e("exception");
                    fileIsExist();
                } finally {
                    com.yzk.practice_brain.log.LogUtil.e("retry Time:" + retryTime);
                }
            }

        }

        /**
         * 网络异常时,将之前下载的文件清除
         */
        private void fileIsExist() {
            --retryTime;
            File file = new File(mCachePath + File.separator + mFileName);
            if (file.exists()) {
                file.delete();
                com.yzk.practice_brain.log.LogUtil.e(file.getAbsolutePath() + ": 下载未完成 is delected");
            }
        }


        /**
         * 创建连接
         *
         * @param url
         * @return
         * @throws IOException
         */
        private HttpURLConnection createConnection(URL url) throws IOException {

            if (url.getProtocol().contains("https")) {
                HTTPSTrustManager.allowAllSSL();
            }
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(timeoutMs);
            urlConnection.setReadTimeout(timeoutMs);
            urlConnection.setUseCaches(false);


            return urlConnection;
        }


        private byte[] encodeParameters(Map<String, String> params, String paramsEncoding) {
            StringBuilder encodedParams = new StringBuilder();
            try {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    encodedParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
                    encodedParams.append('=');
                    encodedParams.append(URLEncoder.encode(entry.getValue(), paramsEncoding));
                    encodedParams.append('&');
                }
                return encodedParams.toString().getBytes(paramsEncoding);
            } catch (UnsupportedEncodingException uee) {
                throw new RuntimeException("Encoding not supported: " + paramsEncoding, uee);
            }
        }

        /**
         * Reads the contents of HttpEntity into a byte[].
         */
        private void entityToBytes(HttpEntity entity) throws IOException, FileDownloadError {
//            ByteArrayPool pool = new ByteArrayPool(3000);
//            PoolingByteArrayOutputStream bytes =
//                    new PoolingByteArrayOutputStream(pool, (int) entity.getContentLength());
////

//
            File dir = new File(mCachePath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File destFile = new File(dir, mFileName);
            FileOutputStream fileOutputStream = new FileOutputStream(destFile);
            BufferedOutputStream bufferedOutputStream=new BufferedOutputStream(fileOutputStream);
//
            byte[] buffer = new byte[2048];
            InputStream in = null;
            try {
                in = entity.getContent();
                long contentLength = entity.getContentLength();
                if (in == null) {
                    throw new FileDownloadError("server inputStream isNull");
                }
                int count;
                int len = 0;
                while ((count = in.read(buffer)) != -1) {
                    bufferedOutputStream.write(buffer,0,count);
                    len += count;
                    bufferedOutputStream.flush();
                    com.yzk.practice_brain.log.LogUtil.e("download size:" + len);
                }
                retryTime=0;

                com.yzk.practice_brain.log.LogUtil.e("download size:" + len);
            } finally {
                try {
                    // Close the InputStream and release the resources by "consuming the content".
                    entity.consumeContent();

                    bufferedOutputStream.close();
                    fileOutputStream.close();
                } catch (IOException e) {
                    // This can happen if there was an exception above that left the entity in
                    throw new IOException("Error occured when calling consumingContent");
                }

            }


        }

        private HttpEntity entityFromConnection(HttpURLConnection connection) throws IOException {
            BasicHttpEntity entity = new BasicHttpEntity();
            InputStream inputStream;
            try {
                inputStream = connection.getInputStream();
            } catch (IOException ioe) {
                throw new IOException(ioe.toString());
//                inputStream = connection.getErrorStream();
            }
            entity.setContent(inputStream);
            entity.setContentLength(connection.getContentLength());
            entity.setContentEncoding(connection.getContentEncoding());
            entity.setContentType(connection.getContentType());
            return entity;
        }

    }


}
