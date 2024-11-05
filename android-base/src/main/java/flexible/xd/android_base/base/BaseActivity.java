package flexible.xd.android_base.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import java.io.Serializable;
import flexible.xd.android_base.R;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import static flexible.xd.android_base.utils.ToastUtil.showShort;


/**
 * Created by flexible on 2016/12/12.
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {


    private View loadProgress;

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initEvent();

    protected abstract int layoutId();

    protected CompositeDisposable disposables;

    protected void addDisposable(@NonNull Disposable d) {
        if (disposables == null)
            disposables = new CompositeDisposable();
        disposables.add(d);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId());
        initView();
        initData();
        initEvent();
    }

    /**
     * 全局的loading 可进行其他操作
     *
     * @param isLoad
     */
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
        showShort(content);
    }

    public void toast(int resource) {
        showShort(resource);
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposables != null && !disposables.isDisposed())
            disposables.dispose();
    }

    @Override
    public void onClick(View v) {

    }
}
