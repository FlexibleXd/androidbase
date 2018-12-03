package flexible.xd.android_base.base;

import android.app.Activity;
import android.content.Context;
import java.util.Stack;
import flexible.xd.android_base.utils.LogUtils;

/**
 * 应用程序Activity管理类：用于Activity管理和应用程序退出
 */
public class AppManager {

    private static Stack<Activity> activityStack;
    private static AppManager instance;
    private static final String TAG = "AppManager";

    private AppManager() {
    }

    /**
     * 单一实例
     */
    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    public int getActivityStack() {
        if (activityStack == null) {
            return 0;
        }
        return activityStack.size();
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 移除Activity到堆栈
     */
    public void removeActivity(Activity activity) {
        if (activityStack == null) return;

        if (activityStack.contains(activity))
            activityStack.remove(activity);

        if (!activity.isFinishing()) {
            activity.finish();
        }
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        if (activityStack == null) return null;
        return activityStack.lastElement();
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        if (activityStack == null) return;
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                Activity activity = activityStack.get(i);
                if (!activity.isFinishing()) {
                    activity.finish();
                }
            }
        }
        activityStack.clear();
    }

    /**
     * 完美退出应用程序
     */
    public void appExit(Context context) {
        try {
            finishAllActivity();
            LogUtils.LOGE(TAG,"-----exit----android.os.Process.killProcess------");
            //杀死该应用进程
            android.os.Process.killProcess(android.os.Process.myPid());
            /**
             * Force the system to close the app down completely instead of
             * retaining it in the background. The virtual machine that runs the
             * app will be killed. The app will be completely created as a new
             * app in a new virtual machine running in a new process if the user
             * starts the app again.
             */
            /**
             * System.exit() does not kill your app if you have more than one activity on the stack.
             * What actually happens is that the process is killed and immediately restarted
             * with one fewer activity on the stack.
             */
             LogUtils.LOGE(TAG,"-----exit------System.exit(0)-----");
            System.exit(0);
        } catch (Exception e) {
            LogUtils.LOGE(TAG,"-----exit----android.os.Process.killProcess------" + e);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

}