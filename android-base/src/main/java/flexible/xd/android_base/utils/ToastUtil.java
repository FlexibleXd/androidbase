package flexible.xd.android_base.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;


/**
 * Created by flexible on 2016/8/30.
 */
public class ToastUtil {

    private static String oldMsg;
    protected static Toast toast = null;
    private static long oneTime = 0;
    private static long twoTime = 0;

    public static void showToast(Context context, String content) {
        if (toast == null) {
            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (content.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            } else {
                oldMsg = content;
                toast.setText(content);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
        oneTime = twoTime;
    }

    public static void showToastBottom(Context context, String content) {
        if (toast == null) {
            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (content.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    toast.show();
                }
            } else {
                oldMsg = content;
                toast.setText(content);
                toast.show();
            }
        }
        oneTime = twoTime;
    }
}
