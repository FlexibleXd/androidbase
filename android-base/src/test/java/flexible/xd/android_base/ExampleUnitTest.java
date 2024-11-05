package flexible.xd.android_base;

import android.app.AlertDialog;
import android.app.Dialog;


import org.junit.Test;
import org.nutz.json.Json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flexible.xd.android_base.model.event.LoginEvent;
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

    }

    @Test
    public void nutz() {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            User u = new User();
            u.setName(i + "");
            u.setPhone("18653679189");
            userList.add(u);
        }
        System.out.println(Json.toJson(userList));
        Map<String, Object> param = new HashMap<>();
        param.put("a", Json.toJson(userList));
        Body body = new Body();
        body.setData(Json.toJson(userList));
        System.out.println(param);
        System.out.println(Json.toJson(body));
    }

    @Test
    public void rtfTest() {
    }
}