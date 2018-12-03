package flexible.xd.android_base.network.rtfhttp;

import android.util.Log;

import com.valdio.cookiejar.serializablecookiejar.SerializableCookieJar;

import java.util.concurrent.TimeUnit;

import flexible.xd.android_base.base.BaseApp;
import flexible.xd.android_base.network.rtfhttp.conver.FastJsonConverterFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


/**
 * author : flexible
 * email : lgd19940421@163.com
 **/
public class RtfHelper<T> {
    private static RtfHelper instance;
    private T api;
    private static String rtfBaseUrl;
    private static Boolean DEBUG = false;
    private static Class<?> apiClazz;
    private static int rtfConnectTimeout = 10;
    private static int rtfReadTimeout = 15;
    private static int rtfWriteTimeout = 15;

    public static RtfHelper getInstance() {
        if (instance == null) {
            synchronized (RtfHelper.class) {
                if (instance == null) {
                    instance = new RtfHelper();
                }
            }
        }

        return instance;
    }

    public static void init(String baseUrl, Class<?> clazz) {
        rtfBaseUrl = baseUrl;
        apiClazz = clazz;
    }

    public static void init(String baseUrl, Class<?> clazz, int connectTimeout, int readTimeout, int writeTimeout) {
        init(baseUrl, clazz);
        rtfConnectTimeout = connectTimeout;
        rtfReadTimeout = readTimeout;
        rtfWriteTimeout = writeTimeout;
    }

    private RtfHelper() {
        if (rtfBaseUrl == null || rtfBaseUrl.length() == 0 || apiClazz == null) {
            throw new RuntimeException("you should init first!");
        }
        SerializableCookieJar cookieJar = new SerializableCookieJar(BaseApp.getAppContext());
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder()
                .hostnameVerifier((s, sslSession) -> true)
                .cookieJar(cookieJar)
                .connectTimeout(rtfConnectTimeout, TimeUnit.SECONDS)
                .readTimeout(rtfReadTimeout, TimeUnit.SECONDS)
                .writeTimeout(rtfWriteTimeout, TimeUnit.SECONDS);

        if (DEBUG) {
            //NONE：没有记录
            //BASIC：日志请求类型，URL，请求体的大小，响应状态和响应体的大小
            //HEADERS：日志请求和响应头，请求类型，URL，响应状态
            //BODY：日志请求和响应标头和正文
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(
                    message -> Log.i("flexible", message)
            );
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpBuilder.addInterceptor(loggingInterceptor);
        }

        OkHttpClient okHttpClient = httpBuilder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(rtfBaseUrl)
                .client(okHttpClient)
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        api = retrofit.create((Class<T>) apiClazz);
    }

    public T getApiService() {
        return api;
    }


}
