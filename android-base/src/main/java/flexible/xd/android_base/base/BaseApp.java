package flexible.xd.android_base.base;

import android.app.Application;
import android.content.Context;
import android.util.Config;

import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.cache.DiskCacheStore;



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
        NoHttp.initialize(this, new NoHttp.Config().setConnectTimeout(10000).setReadTimeout(10000).setCacheStore(new DiskCacheStore(this)));
//        Logger.setDebug(Config.DEBUG); // 开启NoHttp调试模式。
        Logger.setTag("flexible"); // 设置NoHttp打印Log的TAG。
    }

    public static Context getAppContext() {
        return ctx;
    }
}
