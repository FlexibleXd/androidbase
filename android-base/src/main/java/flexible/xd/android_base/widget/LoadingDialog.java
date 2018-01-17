package flexible.xd.android_base.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.ImageView;
import android.widget.LinearLayout;

import flexible.xd.android_base.R;


/**
 * Created by Flexible on 2017/5/12 0012.
 */

public class LoadingDialog extends Dialog {

    private ImageView loadIv;

    private int defaultColor = getContext().getResources().getColor(R.color.colorPrimary);
    private int defaultImg = R.drawable.loading;

    public LoadingDialog(Context context, int defaultImg, int defaultColor) {
        super(context, R.style.loading_dialog);
        if (defaultColor != -1)
            this.defaultColor = defaultColor;
        if (defaultImg != -1)
            this.defaultImg = defaultImg;
        init();
    }

    private void init() {
        this.setCancelable(false);
        loadIv = new ImageView(getContext());
        loadIv.setImageResource(defaultImg);
        Drawable drawable = loadIv.getDrawable();
        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, ColorStateList.valueOf(defaultColor));
        loadIv.setImageDrawable(wrappedDrawable);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startAnimation(loadIv);
        setContentView(loadIv, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
    }

    private void startAnimation(ImageView loadIv) {
        ObjectAnimator valueAnimator = ObjectAnimator.ofFloat(loadIv, "rotation", 0f, 360f);
        valueAnimator.setDuration(2000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);//无限循
        valueAnimator.start();
    }
}
