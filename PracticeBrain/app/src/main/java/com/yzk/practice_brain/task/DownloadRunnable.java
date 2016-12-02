package com.yzk.practice_brain.task;

/**
 * Created by android on 12/2/16.
 */

import com.android.volley.HTTPSTrustManager;
import com.yzk.practice_brain.FileDownloadError;
import com.yzk.practice_brain.bean.MusicListResult;
import com.yzk.practice_brain.busevent.BackgroudMusicEvent;
import com.yzk.practice_brain.log.LogUtil;
import com.yzk.practice_brain.manager.DownLoadManager;
import com.yzk.practice_brain.preference.PreferenceHelper;

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
import java.util.concurrent.locks.ReentrantLock;

import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * 下载任务
 */
public class DownloadRunnable implements Runnable {
    private static final String HEADER_CONTENT_TYPE = "Content-Type";
    private static final String DEFAULT_PARAMS_ENCODING = "UTF-8";
    private final DownLoadManager.MEDIA_TYPE mMedia_type;
    private final String mFileName;
    private final String mCachePath;
    private final String mHttpUrl;
    private final DownLoadManager.METHOD mMethod;
    private final Map<String, String> mHeaders;
    private final MusicListResult.MusicEntity mMusicEntity;
    private static final ReentrantLock reentrantLock = new ReentrantLock();
    private final int timeoutMs = 2500;//2500毫秒
    private int retryTime = 3;


    /**
     * @param media_type  下载音乐类型
     * @param musicEntity 音乐文件
     * @param cachedPath  存储路径
     */
    public DownloadRunnable(DownLoadManager.METHOD method, DownLoadManager.MEDIA_TYPE media_type, MusicListResult.MusicEntity musicEntity, String cachedPath, Map<String, String> headers) {
        this.mMedia_type = media_type;
        this.mHttpUrl = musicEntity.url;
        this.mFileName = musicEntity.name;
        this.mMusicEntity = musicEntity;
        this.mMethod = method;
        this.mCachePath = cachedPath;
        this.mHeaders = headers;
    }

    @Override
    public void run() {
        while (retryTime > 0) {
            HttpURLConnection connection = null;
            try {
                reentrantLock.lock();

                com.yzk.practice_brain.log.LogUtil.e("start download:" + mHttpUrl);
                URL url = new URL(mHttpUrl);
                connection = createConnection(url);
                if (null != mHeaders) {
                    for (String headerName : mHeaders.keySet()) {
                        connection.addRequestProperty(headerName, mHeaders.get(headerName));
                    }
                    LogUtil.e("header:" + mHeaders.toString());
                }
                connection.addRequestProperty(HEADER_CONTENT_TYPE, "application/x-www-form-urlencoded; charset=UTF-8");
                connection.setRequestProperty("Accept-Encoding", "identity");
                if (mMethod == DownLoadManager.METHOD.POST) {
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
                    HttpEntity entity = entityFromConnection(connection);
                    entityToBytes(entity);
                    LogUtil.e("name:"+mMusicEntity.name+",version:"+mMusicEntity.version);

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
                if (null != connection) {
                    connection.disconnect();
                }
                reentrantLock.unlock();
            }
        }
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


    /**
     * Reads the contents of HttpEntity into a byte[].
     */
//        private void entityToBytes(HttpEntity entity) throws IOException, FileDownloadError {
//            ByteArrayPool mPool = new ByteArrayPool(3000);
//            PoolingByteArrayOutputStream bytes =
//                    new PoolingByteArrayOutputStream(mPool, (int)mMusicEntity.fileLength);
//
//            File dir = new File(mCachePath);
//            if (!dir.exists()) {
//                dir.mkdirs();
//            }
//            File destFile = new File(dir, mFileName);
//            FileOutputStream fileOutputStream = new FileOutputStream(destFile);
//
//            byte[] buffer = null;
//            try {
//                InputStream in = entity.getContent();
//                if (in == null) {
//                    throw new FileDownloadError("server inputStream isNull");
//                }
//                buffer = mPool.getBuf(1024);
//                int count;
//                int length = 0;
//                while ((count = in.read(buffer)) != -1) {
//                    bytes.writeTo(fileOutputStream);
////                    bytes.write(buffer, 0, count);
//                    length += count;
//                    com.yzk.practice_brain.log.LogUtil.e("downloaded size:" + length);
//                }
//                retryTime=0;
//                com.yzk.practice_brain.log.LogUtil.e("downloaded finish size:" + length+",filelenght:"+mMusicEntity.fileLength);
//
//            } finally {
//                try {
//                    // Close the InputStream and release the resources by "consuming the content".
//                    entity.consumeContent();
//                } catch (IOException e) {
//                    throw new IOException("Error occured when calling consumingContent");
//                }
//                mPool.returnBuf(buffer);
//                fileOutputStream.close();
//                bytes.close();
//
//
//            }
//        }

    /**
     * Reads the contents of HttpEntity into a byte[].
     */
    private void entityToBytes(HttpEntity entity) throws IOException, FileDownloadError {
        File dir = new File(mCachePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File destFile = new File(dir, mFileName);
        FileOutputStream fileOutputStream = new FileOutputStream(destFile);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        byte[] buffer = new byte[2048];
        InputStream in = null;
        try {
            in = entity.getContent();
            if (in == null) {
                throw new FileDownloadError("server inputStream isNull");
            }
            int count;
            int len = 0;
            while ((count = in.read(buffer)) != -1) {
                bufferedOutputStream.write(buffer, 0, count);
                len += count;
            }
            if (len==mMusicEntity.fileLength) {
                PreferenceHelper.writeInt(mMusicEntity.name, mMusicEntity.version);
                if (mMedia_type== DownLoadManager.MEDIA_TYPE.BGMUSIC) {
                    HermesEventBus.getDefault().post(new BackgroudMusicEvent().new DownloadFinishEvent(mMusicEntity));
                }else if (mMedia_type== DownLoadManager.MEDIA_TYPE.NORMAL){
                    //图片讲解事件

                }
            }
            retryTime = 0;
        } finally {
            try {
                // Close the InputStream and release the resources by "consuming the content".
                entity.consumeContent();
                if (null != bufferedOutputStream) {
                    bufferedOutputStream.close();
                }
                if (null != fileOutputStream) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
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
