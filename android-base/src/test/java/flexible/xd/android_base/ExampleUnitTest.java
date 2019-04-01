package flexible.xd.android_base;

import android.app.AlertDialog;
import android.app.Dialog;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;

import org.junit.Test;

import java.io.IOException;

import flexible.xd.android_base.mvpBase.IBaseModel;
import flexible.xd.android_base.network.rtfhttp.RtfHelper;
import flexible.xd.android_base.network.rtfhttp.Transformer;
import flexible.xd.android_base.network.rtfhttp.observer.BaseObserver;
import io.reactivex.disposables.Disposable;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static junit.framework.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void rtfTest() {
        RtfHelper.getInstance().init("http://www.baidu.com");

        RtfHelper.getInstance().getOkHttpClient().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .header("platform", "platform")//平台
                        .header("sysVersion", "sysVersion")//系统版本号
                        .header("device", "device")//设备信息
                        .header("screen", "screen")//屏幕大小
                        .header("uuid", "uuid")//设备唯一码
                        .header("version", "version")//app版本
                        .header("apiVersion", "apiVersion")//api版本
                        .header("token", "token")//令牌
                        .header("channelId", "channelId")//渠道
                        .header("networkType", "networkType");//网络类型
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
    }
}