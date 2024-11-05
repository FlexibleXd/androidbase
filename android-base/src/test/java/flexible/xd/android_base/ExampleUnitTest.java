package flexible.xd.android_base;

import android.app.AlertDialog;
import android.app.Dialog;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;

import org.junit.Test;

import flexible.xd.android_base.mvpBase.IBaseModel;
import flexible.xd.android_base.network.rtfhttp.RtfHelper;
import flexible.xd.android_base.network.rtfhttp.Transformer;
import flexible.xd.android_base.network.rtfhttp.observer.BaseObserver;
import io.reactivex.disposables.Disposable;

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
        RtfHelper.getInstance().init("");
        RtfHelper.getInstance().init("", 10, 10, 10);
        RtfHelper.getInstance().getApiService(ApiService.class).login("","").compose(Transformer.schedule())
                .subscribe(new BaseObserver<IBaseModel>() {
            @Override
            public void doOnSubscribe(Disposable d) {

            }

            @Override
            public void onFail(String errorMsg) {

            }

            @Override
            public void onSuccess(IBaseModel iBaseModel) {

            }


        });
    }
}