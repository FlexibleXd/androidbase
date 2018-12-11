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
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import java.io.File;

import flexible.xd.android_base.R;


/**
 * Created by flexible on 2018/12/11
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
                installApp(getFileByPath(filePath), authority);
                stopSelf();
                unregisterReceiver(this);
            }
        }
    }

    public File getFileByPath(final String filePath) {
        if (filePath == null) return null;
        for (int i = 0, len = filePath.length(); i < len; ++i) {
            if (!Character.isWhitespace(filePath.charAt(i))) {
                return new File(filePath);
            }
        }
        return null;
    }

    public void installApp(final File file, final String authority) {
        if (file == null || !file.exists()) return;
        Utils.getApp().startActivity(getInstallAppIntent(file, authority, true));
    }

    public Intent getInstallAppIntent(final File file,
                                      final String authority,
                                      final boolean isNewTask) {
        if (file == null) return null;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data;
        String type = "application/vnd.android.package-archive";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            data = Uri.fromFile(file);
        } else {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            data = FileProvider.getUriForFile(Utils.getApp(), authority, file);
        }
        intent.setDataAndType(data, type);
        return isNewTask ? intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) : intent;
    }

}


