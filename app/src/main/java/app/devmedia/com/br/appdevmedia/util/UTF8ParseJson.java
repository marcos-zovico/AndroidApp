package app.devmedia.com.br.appdevmedia.util;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import java.io.UnsupportedEncodingException;

/**
 * Created by Marcos on 25/06/2016.
 */
public class UTF8ParseJson extends JsonArrayRequest {

    public UTF8ParseJson(String url, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    @Override
    protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
        Response<JSONArray> arrayResponse = null;
        try {
            String string = new String(response.data, "UTF-8");
            arrayResponse = Response.success(new JSONArray(string), HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayResponse;
    }
}
