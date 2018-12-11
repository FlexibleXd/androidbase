package flexible.xd.android_base.utils;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * author : flexible
 * email : lgd19940421@163.com
 * github: https://github.com/FlexibleXd
 **/
public class FlexibleUtils {
    /**
     * 解决lv与sv嵌套  lv长度不正确
     *
     * @param listView
     */

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        ((ViewGroup.MarginLayoutParams) params).setMargins(15, 15, 15, 15);
        listView.setLayoutParams(params);

    }


    /**
     * 设置wv的一些通用属性
     */

    public static void initWebView(final WebView wv, final Activity ctx) {
        WebSettings webSettings = wv.getSettings();
        webSettings.setTextSize(WebSettings.TextSize.NORMAL);//设置字体大小
        webSettings.setUseWideViewPort(false);//设置此属性，可任意比例缩放
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局
        webSettings.setSupportZoom(false);
        wv.setWebViewClient(new WebViewClient() {


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        wv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
        wv.setWebChromeClient(new WebChromeClient() {


            private ProgressBar mProgressbar;

            public void onProgressChanged(WebView view, int progress) {
                if (mProgressbar == null) {
                    mProgressbar = new ProgressBar(ctx, null, android.R.attr.progressBarStyleLarge);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(200, 200);
                    RelativeLayout rv = new RelativeLayout(ctx);
                    params.addRule(RelativeLayout.CENTER_IN_PARENT);
                    rv.addView(mProgressbar, params);
                    RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                    wv.addView(rv, params1);
                }
                if (progress == 100) {
                    mProgressbar.setVisibility(View.GONE);
                } else {
                    mProgressbar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
//                ctx.setTitle(title);
            }
        });

    }



    /**
     * 倒计时
     *
     * @param txtTime
     * @param AllTime
     * @param JianGe
     */
    public static <T extends TextView> CountDownTimer timerSeckill(final T txtTime, long AllTime, final int JianGe, final int code) {
        if (code == 0) {
            txtTime.setClickable(false);
        }
        CountDownTimer timer = new CountDownTimer(AllTime, JianGe) {

            @Override
            public void onTick(long millisUntilFinished) {
                if (code == 0) {
                    txtTime.setText(String.valueOf(millisUntilFinished / JianGe) + "s后重新获取");
                } else {
                    txtTime.setText("跳过  " + String.valueOf(millisUntilFinished / JianGe) + "s");
                }
            }
            @Override
            public void onFinish() {

                if (code == 0) {
                    txtTime.setText("获取验证码");
                    txtTime.setClickable(true);
                } else {
                    txtTime.setText("跳过  0s");
                }
            }
        };
        return timer.start();
    }
}
