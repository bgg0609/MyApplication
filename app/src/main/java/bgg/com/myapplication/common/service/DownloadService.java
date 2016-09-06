package bgg.com.myapplication.common.service;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.text.TextUtils;

import bgg.com.myapplication.R;


/**
 * 下载APK文件,完成后提示安装
 */

public class DownloadService extends Service {

    public static final String KEY_URL = "KEY_URL";

    /** 安卓系统下载类 **/
    DownloadManager manager;

    /** 接收下载完的广播 **/
    DownloadCompleteReceiver receiver;

    public static Intent startUpdateService(Context context, String downUrl) {

        return new Intent(context, DownloadService.class).putExtra(KEY_URL,
                downUrl);

    }

    /** 初始化下载器 **/
    @SuppressLint("NewApi")
    private void initDownManager(String url) {

        manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

        receiver = new DownloadCompleteReceiver();
        // 设置下载地址
        DownloadManager.Request down = new DownloadManager.Request(
                Uri.parse(url));
        // 设置允许使用的网络类型，这里是移动网络和wifi都可以
        down.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE
                | DownloadManager.Request.NETWORK_WIFI);
        // 下载时，通知栏显示途中
        down.setNotificationVisibility(Request.VISIBILITY_VISIBLE);
        // 显示下载界面
        down.setVisibleInDownloadsUi(true);
        down.allowScanningByMediaScanner();
        down.setDescription(getString(R.string.app_name));
        // 设置下载后文件存放的位置
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            down.setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS, "gohealth.apk");
        }

        manager.enqueue(down);
        // 注册下载广播
        registerReceiver(receiver, new IntentFilter(
                DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null){
            String url = intent.getStringExtra(KEY_URL);
            if (!TextUtils.isEmpty(url)) {
                // 调用下载
                initDownManager(url);
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onDestroy() {

        // 注销下载广播
        if (receiver != null)
            unregisterReceiver(receiver);

        super.onDestroy();
    }

    // 接受下载完成后的intent
    class DownloadCompleteReceiver extends BroadcastReceiver {

        @SuppressLint("NewApi")
        @Override
        public void onReceive(Context context, Intent intent) {

            // 判断是否下载完成的广播
            if (intent.getAction().equals(
                    DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {

                // 获取下载的文件id
                long downId = intent.getLongExtra(
                        DownloadManager.EXTRA_DOWNLOAD_ID, -1);

                // 自动安装apk
                installAPK(manager.getUriForDownloadedFile(downId));

                // 停止服务并关闭广播
                stopSelf();

            }
        }

        /**
         * 安装apk文件
         */
        private void installAPK(Uri apk) {

            // 通过Intent安装APK文件
            Intent intents = new Intent();
            intents.setAction("android.intent.action.VIEW");
            intents.addCategory("android.intent.category.DEFAULT");
            intents.setType("application/vnd.android.package-archive");
            intents.setData(apk);
            intents.setDataAndType(apk,
                    "application/vnd.android.package-archive");
            intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intents);

        }

    }
}