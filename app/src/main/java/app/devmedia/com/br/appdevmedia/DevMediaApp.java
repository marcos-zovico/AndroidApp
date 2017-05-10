package app.devmedia.com.br.appdevmedia;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.SystemClock;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.squareup.picasso.Picasso;

import java.util.concurrent.TimeUnit;

import app.devmedia.com.br.appdevmedia.util.BitmapCache;

/**
 * Created by Marcos Souza on 21/02/2016.
 */
public class DevMediaApp extends Application {

    private static DevMediaApp devMediaApp;

    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    @Override
    public void onCreate() {
        super.onCreate();
        devMediaApp = this;

        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                Picasso.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {
                Picasso.with(imageView.getContext()).cancelRequest(imageView);
            }
        });
        SystemClock.sleep(TimeUnit.SECONDS.toMillis(3));
    }

    public static synchronized DevMediaApp getInstance() {
        return devMediaApp;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }

    public ImageLoader getImageLoader() {
        if (imageLoader == null) {
            imageLoader = new ImageLoader(getRequestQueue(), new BitmapCache());
        }
        return imageLoader;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }

    public void cancelarRequestPending() {
        if (requestQueue != null) {
            requestQueue.cancelAll(DevMediaApp.class.getSimpleName());
        }
    }
}
