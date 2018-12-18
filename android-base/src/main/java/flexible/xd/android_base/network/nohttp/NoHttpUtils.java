package flexible.xd.android_base.network.nohttp;

import android.graphics.Bitmap;

import com.yanzhenjie.nohttp.BitmapBinary;
import com.yanzhenjie.nohttp.FileBinary;
import com.yanzhenjie.nohttp.InitializationConfig;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.cache.DBCacheStore;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONObject;

import java.io.File;
import java.util.Map;

import flexible.xd.android_base.base.BaseApp;

/**
 * Created by flexibleXd on 2016/12/22.
 */

public class NoHttpUtils {

    public static void noHttpConfig() {
        InitializationConfig config = InitializationConfig.newBuilder(BaseApp.getAppContext())
                // 全局连接服务器超时时间，单位毫秒，默认10s。
                .connectionTimeout(10 * 1000)
                // 全局等待服务器响应超时时间，单位毫秒，默认10s。
                .readTimeout(10 * 1000).cacheStore(new DBCacheStore(BaseApp.getAppContext()).
                        setEnable(true))
                .build();
        NoHttp.initialize(config);
//        Logger.setDebug(Config.DEBUG); // 开启NoHttp调试模式。
        Logger.setTag("flexible"); // 设置NoHttp打印Log的TAG。
    }

    /**
     * stringRequest
     *
     * @param url    api地址
     * @param method 请求方式
     */
    public static Request<String> stringRequest(String url, RequestMethod method) {
        Request<String> request = NoHttp.createStringRequest(url, method);
        return request;
    }

    public static Request<String> stringRequest(String url) {
        return stringRequest(url, RequestMethod.GET);
    }

    /**
     * jsonObeject
     */

    public static Request<JSONObject> jsonObjectRequest(String url) {
        return jsonObjectRequest(url, RequestMethod.GET);
    }

    public static Request<JSONObject> jsonObjectRequest(String url, RequestMethod method) {
        return jsonObjectRequest(url, method, null);
    }


    public static Request<JSONObject> jsonObjectRequest(String url, RequestMethod method, Map<String, Object> param) {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(url, method);
        if (param != null) {
            request.add(param);
        }
        return request;
    }


    /**
     * FastJson
     */

    public static FastJsonRequest fastJsonObjectRequest(String url) {
        return fastJsonObjectRequest(url, RequestMethod.GET);
    }

    public static FastJsonRequest fastJsonObjectRequest(String url, RequestMethod method) {
        return fastJsonObjectRequest(url, method, null);
    }

    public static FastJsonRequest fastJsonObjectRequest(String url, Map<String, Object> param) {
        return fastJsonObjectRequest(url, RequestMethod.GET, param);
    }

    public static FastJsonRequest fastJsonObjectRequest(String url, RequestMethod method, Map<String, Object> param) {
        FastJsonRequest request = new FastJsonRequest(url, method);
        if (param != null) {
            request.add(param);
        }
        return request;
    }

    /**
     * 同步
     *
     * @param url
     * @param param
     * @return
     */
    public static Response<com.alibaba.fastjson.JSONObject> fastJosnAsyn(String url, Map<String, Object> param) {
        // 创建请求。
        FastJsonRequest request = new FastJsonRequest(url, RequestMethod.GET);
        if (param != null) {
            request.add(param);
        }
        // 调用同步请求，直接拿到请求结果。
        Response<com.alibaba.fastjson.JSONObject> response = NoHttp.startRequestSync(request);
        return response;
    }


    /**
     * javaBeanRequsst
     *
     * @param url    api地址
     * @param method 请求方式
     * @param clazz  解析成的Bean
     * @param <T>
     */
    public static <T> JavaBeanRequest<T> beanRequest(String url, Class<T> clazz, RequestMethod method) {
        return beanRequest(url, clazz, method, null);
    }

    public static <T> JavaBeanRequest<T> beanRequest(String url, Class<T> clazz, RequestMethod method, Map<String, Object> param) {
        JavaBeanRequest<T> request = new JavaBeanRequest(url, method, clazz);
        if (param != null) {
            request.add(param);
        }
        return request;
    }

    public static <T> JavaBeanRequest<T> beanRequest(String url, Class<T> clazz) {
        return beanRequest(url, clazz, RequestMethod.GET);
    }

    /**
     * 上传
     *
     * @param url
     * @param method
     * @return
     */

    public static Request<com.alibaba.fastjson.JSONObject> fileRequset(String url, RequestMethod method, Map<String, File> param) {
        FastJsonRequest request = new FastJsonRequest(url, method);
        for (Map.Entry<String, File> entry : param.entrySet()) {
            request.add(entry.getKey(), new FileBinary(entry.getValue()));
        }
        return request;
    }

    /**
     * 上传
     *
     * @param url
     * @param method
     * @return
     */

    public static Request<com.alibaba.fastjson.JSONObject> bitmapRequset(String url, RequestMethod method, Map<String, Bitmap> param) {
        FastJsonRequest request = new FastJsonRequest(url, method);
        for (Map.Entry<String, Bitmap> entry : param.entrySet()) {
            request.add(entry.getKey(), new BitmapBinary(entry.getValue(), "img.jpg"));
        }
        return request;
    }


}
