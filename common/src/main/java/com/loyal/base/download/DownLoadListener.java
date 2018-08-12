package com.loyal.base.download;

/**
 * 下载进度listener
 */
public interface DownLoadListener {
    void update(long bytesRead, long contentLength, boolean done);
}
