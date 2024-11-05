package flexible.xd.android_base.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import com.yanzhenjie.nohttp.rest.Request;

import java.io.Serializable;

import flexible.xd.android_base.R;
import flexible.xd.android_base.network.nohttp.CallServer;
import flexible.xd.android_base.network.nohttp.NoHttpListener;
import flexible.xd.android_base.network.nohttp.NoHttpManager;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static flexible.xd.android_base.utils.ToastUtil.showShort;

/**
 * Created by flexibleXd on 2016/12/22.
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    private View v;

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initEvent();

    protected abstract int layoutId();


    public View getContentView() {
        return v;
    }

    protected CompositeDisposable disposables;

    protected void addDisposable(@NonNull Disposable d) {
        if (disposables == null)
            disposables = new CompositeDisposable();
        disposables.add(d);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(layoutId(), null);
        initView();
        initData();
        initEvent();
        return v;
    }

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


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposables != null && !disposables.isDisposed())
            disposables.dispose();
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
