package app.devmedia.com.br.appdevmedia.util;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;


public class BitmapCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {

    public BitmapCache() {
        super(getDefaultCacheSize());
    }

    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    public BitmapCache(int maxSize) {
        super(maxSize);
    }

    public static int getDefaultCacheSize() {
        final int memoriaMax = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int tamanhoCache = memoriaMax / 8;

        return tamanhoCache;
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight() / 1024;
    }

    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }
}
