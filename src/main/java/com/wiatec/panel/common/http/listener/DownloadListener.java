package com.wiatec.panel.common.http.listener;


import com.wiatec.panel.common.http.pojo.DownloadInfo;

public interface DownloadListener  {
     void onPending(DownloadInfo downloadInfo);
     void onStart(DownloadInfo downloadInfo);
     void onPause(DownloadInfo downloadInfo);
     void onProgress(DownloadInfo downloadInfo);
     void onFinished(DownloadInfo downloadInfo);
     void onCancel(DownloadInfo downloadInfo);
     void onError(DownloadInfo downloadInfo);

}
