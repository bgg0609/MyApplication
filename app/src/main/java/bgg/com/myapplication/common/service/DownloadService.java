package bgg.com.myapplication.common.service;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.text.TextUtils;
import android.widget.Toast;


/**
 * 下载文件
 */

public class DownloadService extends Service {

    public static final String KEY_URL = "KEY_URL";

    /**
     * 安卓系统下载类
     **/
    DownloadManager manager;

    /**
     * 接收下载完的广播
     **/
    DownloadCompleteReceiver receiver;

    public static Intent startDownloadService(Context context, String downUrl) {

        return new Intent(context, DownloadService.class).putExtra(KEY_URL,
                downUrl);

    }

    /**
     * 初始化下载器
     **/
    private void initDownManager(String url) {

        manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

        receiver = new DownloadCompleteReceiver();
        // 设置下载地址
        Request down = new Request(Uri.parse(url));
        // 设置允许使用的网络类型，这里是移动网络和wifi都可以
        down.setAllowedNetworkTypes(Request.NETWORK_MOBILE | Request.NETWORK_WIFI);
        // 下载时，通知栏显示途中
        down.setNotificationVisibility(Request.VISIBILITY_VISIBLE);

        //禁止发出通知，既后台下载，如果要使用这一句必须声明一个权限：android.permission.DOWNLOAD_WITHOUT_NOTIFICATION
        //request.setShowRunningNotification(false);

        // 显示下载界面
        down.setVisibleInDownloadsUi(false);

		 /*设置下载后文件存放的位置,如果sdcard不可用，那么设置这个将报错，因此最好不设置如果sdcard可用，下载后的文件在/mnt/sdcard/Android/data/packageName/files目录下面，如果sdcard不可用,设置了下面这个将报错，不设置，下载后的文件在/cache这个目录下面*/
        //down.setDestinationInExternalFilesDir(this, null, "tar.apk");

        long id = manager.enqueue(down);

        // 注册下载广播
        registerReceiver(receiver, new IntentFilter(
                DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
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

            String action = intent.getAction();
            if (action.equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                Toast.makeText(context, "下载完成了....", Toast.LENGTH_LONG).show();

                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);                                                                                      //TODO 判断这个id与之前的id是否相等，如果相等说明是之前的那个要下载的文件
                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(id);
                manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                Cursor cursor = manager.query(query);

                int columnCount = cursor.getColumnCount();
                String path = null;                                                                                                                                       //TODO 这里把所有的列都打印一下，有什么需求，就怎么处理,文件的本地路径就是path
                while (cursor.moveToNext()) {
                    for (int j = 0; j < columnCount; j++) {
                        String columnName = cursor.getColumnName(j);
                        String string = cursor.getString(j);
                        if (columnName.equals("local_uri")) {
                            path = string;
                        }
                        if (string != null) {
                            System.out.println(columnName + ": " + string);
                        } else {
                            System.out.println(columnName + ": null");
                        }
                    }
                }
                cursor.close();

                //如果sdcard不可用时下载下来的文件，那么这里将是一个内容提供者的路径，这里打印出来，有什么需求就怎么样处理                                                   if(path.startsWith("content:")) {
                cursor = context.getContentResolver().query(Uri.parse(path), null, null, null, null);
                columnCount = cursor.getColumnCount();
                while (cursor.moveToNext()) {
                    for (int j = 0; j < columnCount; j++) {
                        String columnName = cursor.getColumnName(j);
                        String string = cursor.getString(j);
                        if (string != null) {
                            System.out.println(columnName + ": " + string);
                        } else {
                            System.out.println(columnName + ": null");
                        }
                    }
                }
                cursor.close();
            } else if (action.equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)) {
                Toast.makeText(context, "点击通知了....", Toast.LENGTH_LONG).show();
            }
        }
    }
}