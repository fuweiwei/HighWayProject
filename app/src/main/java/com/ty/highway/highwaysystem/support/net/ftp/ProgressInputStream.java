package com.ty.highway.highwaysystem.support.net.ftp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
/**
 * Created by fuweiwei on 2015/9/7.
 * 有进度输入流
 */
public class ProgressInputStream extends InputStream {

    private static final int TEN_KILOBYTES = 1024 * 1;  //每上传1K返回一次进度
    /**
     * 输入流
     */
    private InputStream mInputStream;
    /**
     * 进度
     */
    private long mProgress;
    /**
     * 最后更新长度
     */
    private long mLastUpdate;
    /**
     * 是否关闭
     */
    private boolean isClosed;
    /**
     * 监听器
     */
    private FTP.UploadProgressListener mListener;
    /**
     * 本地地址
     */
    private File mLocalFile;

    public ProgressInputStream(InputStream inputStream,FTP.UploadProgressListener listener,File localFile) {
        this.mInputStream = inputStream;
        this.mProgress = 0;
        this.mLastUpdate = 0;
        this.mListener = listener;
        this.mLocalFile = localFile;
        this.isClosed = false;
    }

    @Override
    public int read() throws IOException {
        int count = mInputStream.read();
        return incrementCounterAndUpdateDisplay(count);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int count = mInputStream.read(b, off, len);
        return incrementCounterAndUpdateDisplay(count);
    }

    @Override
    public void close() throws IOException {
        super.close();
        if (isClosed)
            throw new IOException("already closed");
        isClosed = true;
    }

    private int incrementCounterAndUpdateDisplay(int count) {
        if (count > 0)
            mProgress += count;
        mLastUpdate = maybeUpdateDisplay(mProgress, mLastUpdate);
        return count;
    }

    private long maybeUpdateDisplay(long progress, long lastUpdate) {
        if (progress - lastUpdate > TEN_KILOBYTES) {
            lastUpdate = progress;
            this.mListener.onUploadProgress(FTP.FTP_UPLOAD_LOADING, progress, this.mLocalFile);
        }
        return lastUpdate;
    }
    
  
    
}
