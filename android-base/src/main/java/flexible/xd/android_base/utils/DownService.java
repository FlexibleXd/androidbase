package flexible.xd.android_base.utils;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.text.TextUtils;
import flexible.xd.android_base.R;


/**
 * Created by flexible on 2016/8/24
 */
public class DownService extends Service {
    private DownloadManager downManager;
    private String filePath;
    private String authority;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            String url = intent.getStringExtra("url");
            filePath = intent.getStringExtra("filePath");
            authority = intent.getStringExtra("authority");
            if (TextUtils.isEmpty(url) || !url.startsWith("http")) {
                throw new RuntimeException("请传入正确的下载链接！");
            }
            if (TextUtils.isEmpty(authority) || !authority.endsWith("apk")) {
                throw new RuntimeException("请传入正确的存放链接！");
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && TextUtils.isEmpty("authority")) {
                throw new RuntimeException("7.0 及以上安装需要传入清单文件中的 <provider>的 authorities 属性");
            }
            handleVersion(url);
        } catch (Exception e) {
        }
        broadcaster();
        return super.onStartCommand(intent, flags, startId);
    }

    private void broadcaster() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(new DownLoadCompleteReceiver(), filter);
    }

    private void handleVersion(String v) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(v));
        //设置在什么网络情况下进行下载
//            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        //设置通知栏标题
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setTitle(getResources().getString(R.string.app_name));
        request.setAllowedOverRoaming(false);
        //设置文件存放目录
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filePath);
        downManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        downManager.enqueue(request);

    }

    class DownLoadCompleteReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                AppUtil.installApp(filePath, authority);
                stopSelf();
                unregisterReceiver(this);
            }
        }
    }
}



