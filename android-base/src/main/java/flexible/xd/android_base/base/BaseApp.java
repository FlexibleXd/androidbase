package flexible.xd.android_base.base;

import android.app.Application;
import android.content.Context;
import android.util.Config;

import com.yanzhenjie.nohttp.InitializationConfig;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.cache.DiskCacheStore;


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
        InitializationConfig config = InitializationConfig.newBuilder(this).connectionTimeout(30 * 1000)
                .readTimeout(30 * 1000).cacheStore(new DiskCacheStore(this)).build();
        NoHttp.initialize(config);
//        Logger.setDebug(Config.DEBUG); // 开启NoHttp调试模式。
        Logger.setTag("flexible"); // 设置NoHttp打印Log的TAG。
    }

    public static Context getAppContext() {
        return ctx;
    }
}
