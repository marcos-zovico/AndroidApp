package app.devmedia.com.br.appdevmedia.async;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by marcos on 04/03/17.
 */

public class AsyncImageHelper extends AsyncTask<String, Void, Bitmap> {

    private ImageView imageView;

    public AsyncImageHelper(ImageView imageView){
        this.imageView = imageView;

    }

    @Override
    protected Bitmap doInBackground(String... params) {

        String imgUrl = params[0];
        Bitmap bitmapImg = null;

        try {
            InputStream inputStream = new URL(imgUrl).openStream();
            bitmapImg = BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmapImg;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}
