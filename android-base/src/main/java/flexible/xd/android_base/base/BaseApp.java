package flexible.xd.android_base.base;

import android.app.Application;
import android.content.Context;

import com.yanzhenjie.nohttp.InitializationConfig;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.cache.DBCacheStore;


/**
 * Created by flexibleXd on 2016/12/22.
 */

public class BaseApp extends Application {
    private static Context ctx;

    @Override
    public void onCreate() {
        super.onCreate();
        noHttpConfig();
        ctx = getApplicationContext();
    }


    private void noHttpConfig() {
        InitializationConfig config = InitializationConfig.newBuilder(this)
                // 全局连接服务器超时时间，单位毫秒，默认10s。
                .connectionTimeout(10 * 1000)
                // 全局等待服务器响应超时时间，单位毫秒，默认10s。
                .readTimeout(10 * 1000).cacheStore(new DBCacheStore(this).setEnable(true))
                .build();
        NoHttp.initialize(config);
//        Logger.setDebug(Config.DEBUG); // 开启NoHttp调试模式。
        Logger.setTag("flexible"); // 设置NoHttp打印Log的TAG。
    }

    public static Context getAppContext() {
        return ctx;
    }
}
