package flexible.xd.android_base.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.io.ByteArrayOutputStream;
import flexible.xd.android_base.GlideApp;


/**
 * Created by flexibleXd on 2016/12/22.
 * <p>
 * desc  : glide封装
 */

public class GlideUtils {

//      .diskCacheStrategy(DiskCacheStrategy.ALL)
//
//
//        Glide.with(fragment)
//            .load(url)
//  .transition(
//      new DrawableTransitionOptions
//            .crossFade())
//            .into(imageView);
    /**
     * 普通加载 无默认 int
     *
     * @param ctx
     * @param resouceId
     * @param imageView
     */
    public static void resouce2Iv(Context ctx, int resouceId, ImageView imageView) {
        GlideApp.with(ctx).load(resouceId).into(imageView);
    }

    /**
     * url
     *
     * @param ctx
     * @param imageUrl
     * @param imageView
     */
    public static void url2Iv(Context ctx, String imageUrl,ImageView imageView) {
        GlideApp.with(ctx).load(imageUrl).into(imageView);
    }


    /**
     * string 带默认图片
     *
     * @param ctx
     * @param imageUrl
     * @param imageView
     */
    public static void default2Iv(Context ctx, String imageUrl, int defaultImg, ImageView imageView) {
        GlideApp.with(ctx).load(imageUrl).placeholder(defaultImg).into(imageView);
    }
    /**
     * uri
     *
     * @param ctx
     * @param imageUri
     * @param imageView
     */
    public static void uri2Iv(Context ctx, Uri imageUri, ImageView imageView) {
        GlideApp.with(ctx).load(imageUri).centerCrop().into(imageView);
    }

//
//
    public static void glide2IvBitmap(Context ctx, Bitmap imageUri, ImageView imageView) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageUri.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        GlideApp.with(ctx).load(bytes).centerCrop().into(imageView);
    }
}
