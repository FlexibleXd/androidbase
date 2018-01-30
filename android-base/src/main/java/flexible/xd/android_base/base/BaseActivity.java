package flexible.xd.android_base.base;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.yolanda.nohttp.rest.Request;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import flexible.xd.android_base.R;
import flexible.xd.android_base.network.CallServer;
import flexible.xd.android_base.network.NoHttpListener;
import flexible.xd.android_base.network.NoHttpManager;
import flexible.xd.android_base.utils.ToastUtil;

import static flexible.xd.android_base.utils.ToastUtil.showShort;


/**
 * Created by flexible on 2016/12/12.
 */
public class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    private View loadProgress;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        //修改状态栏颜色
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            if (MIUISetStatusBarLightMode(getWindow(), true)) {//判断是不是小米系统
//                MIUISetStatusBarLightMode(getWindow(), true);
//            } else if (FlymeSetStatusBarLightMode(getWindow(), true)) {//判断是不是魅族系统
//                FlymeSetStatusBarLightMode(getWindow(), true);
//            }
//        }
    }
    /**
     * 修改小米手机系统的
     * @param window
     * @param dark
     * @return
     */
    public static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if(dark){
                    extraFlagField.invoke(window,darkModeFlag,darkModeFlag);//状态栏透明且黑色字体
                }else{
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result=true;
            }catch (Exception e){

            }
        }
        return result;
    }
    /**
     * 魅族手机修改该字体颜色
     * @param window
     * @param dark
     * @return
     */
    public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }


    public void isLoad(boolean isLoad) {
        if (null == loadProgress) {
            loadProgress = LayoutInflater.from(this).inflate(R.layout.view_load_progress, null);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER;
            addContentView(loadProgress, params);
        }
        if (isLoad) {
            loadProgress.setVisibility(View.VISIBLE);
        } else {
            loadProgress.setVisibility(View.GONE);
        }
    }


    public void toast(String content) {
        showShort( content);
    }

    public void startActivity(Class<? extends Activity> clazz) {
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        startActivity(intent);
    }

    public void startActivityForResult(Class<? extends Activity> clazz, int code) {
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        startActivityForResult(intent, code);
    }


    public void startActivityForResult(Class<? extends Activity> clazz, int code, String... data) {
        if (data.length % 2 == 1) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        for (int i = 0; i < data.length / 2; i++) {
            intent.putExtra(data[i * 2], data[i * 2 + 1]);
        }
        startActivityForResult(intent, code);
    }

    public void startActivity(Class<? extends Activity> clazz, String... data) {
        if (data.length % 2 == 1) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        for (int i = 0; i < data.length / 2; i++) {
            intent.putExtra(data[i * 2], data[i * 2 + 1]);
        }
        startActivity(intent);
    }

    public void startActivity(Class<? extends Activity> clazz, String data, Serializable obj) {
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        intent.putExtra(data, obj);
        startActivity(intent);
    }

    private Object cancelObject = new Object();


    public <T> void request(int what, Request<T> request, NoHttpListener<T> httpListener) {
        // 这里设置一个sign给这个请求。
        request.setCancelSign(cancelObject);

        CallServer.getInstance().add(what, request, new NoHttpManager<T>(request,
                httpListener));
    }

    public <T> void request(int what, Request<T> request, NoHttpListener<T> httpListener, boolean isLoad) {
        // 这里设置一个sign给这个请求。
        request.setCancelSign(cancelObject);

        CallServer.getInstance().add(what, request, new NoHttpManager<T>(request,
                httpListener, isLoad, this));
    }

    @Override
    public void onClick(View v) {

    }
}
