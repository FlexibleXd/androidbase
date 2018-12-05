package flexible.xd.android_base.network.rtfhttp;

import android.util.Log;

import com.valdio.cookiejar.serializablecookiejar.SerializableCookieJar;

import java.util.IdentityHashMap;
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
public class RtfHelper {
    private static RtfHelper instance;
    private  String rtfBaseUrl;
    private  Boolean DEBUG = false;
    private  int rtfConnectTimeout = 10;
    private  int rtfReadTimeout = 15;
    private  int rtfWriteTimeout = 15;
    private Retrofit retrofit;

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

    public   void init(String baseUrl) {
        rtfBaseUrl = baseUrl;
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

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();


    }

    public void init(String baseUrl, int connectTimeout, int readTimeout, int writeTimeout) {
        init(baseUrl);
        rtfConnectTimeout = connectTimeout;
        rtfReadTimeout = readTimeout;
        rtfWriteTimeout = writeTimeout;
    }

    private RtfHelper() {
    }

    public <T> T getApiService(Class<T> t) {
        if (rtfBaseUrl == null ) {
            throw new RuntimeException("need baseUrl or retrofit Api class");
        }
        return (T) retrofit.create(t);
    }
}
