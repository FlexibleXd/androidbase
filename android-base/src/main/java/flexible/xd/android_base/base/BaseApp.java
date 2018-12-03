package flexible.xd.android_base.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Config;

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
        activityManager();
    }

    private void activityManager() {
        registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                AppManager.  getAppManager().addActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                AppManager.getAppManager().removeActivity(activity);
            }
        });
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
