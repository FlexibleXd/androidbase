package flexible.xd.android_base.base;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.yolanda.nohttp.rest.Request;

import java.io.Serializable;

import flexible.xd.android_base.R;
import flexible.xd.android_base.network.CallServer;
import flexible.xd.android_base.network.NoHttpListener;
import flexible.xd.android_base.network.NoHttpManager;
import flexible.xd.android_base.utils.ToastUtil;

import static flexible.xd.android_base.utils.ToastUtil.showShort;

/**
 * Created by flexibleXd on 2016/12/22.
 */

public class BaseFragment extends Fragment implements View.OnClickListener {
    public void startActivity(Class<? extends Activity> clazz) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), clazz);
        startActivity(intent);
    }

    public void startActivityForResult(Class<? extends Activity> clazz, int code) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), clazz);
        startActivityForResult(intent, code);
    }

    public void startActivityForResult(Class<? extends Activity> clazz, int code, String... data) {
        if (data.length % 2 == 1) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(getActivity(), clazz);
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
        intent.setClass(getActivity(), clazz);
        for (int i = 0; i < data.length / 2; i++) {
            intent.putExtra(data[i * 2], data[i * 2 + 1]);
        }
        startActivity(intent);
    }

    public void startActivity(Class<? extends Activity> clazz, String data, Serializable obj) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), clazz);
        intent.putExtra(data, obj);
        startActivity(intent);
    }

    public void toast(String content) {
        showShort(content);
    }

    public void toast(int resource) {
        showShort(resource);
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
                httpListener, isLoad, getActivity()));
    }

    @Override
    public void onClick(View v) {

    }

    private View loadProgress;

    public void isLoad(boolean isLoad) {
        if (loadProgress == null) {
            loadProgress = LayoutInflater.from(getActivity()).inflate(R.layout.view_load_progress, null);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER;
            getActivity().addContentView(loadProgress, params);
        }
        if (isLoad) {
            loadProgress.setVisibility(View.VISIBLE);
        } else {
            loadProgress.setVisibility(View.GONE);
        }
    }
}
