package flexible.xd.android_base;

import android.content.Context;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;


/**
 * Created by Flexible on 2018/2/1 0001.
 */
@GlideModule
public final class BaseGlideMoudle extends AppGlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setMemoryCache(new LruResourceCache(20 * 1024 * 1024));
        builder.setBitmapPool(new LruBitmapPool(30 * 1024 * 1024));
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
