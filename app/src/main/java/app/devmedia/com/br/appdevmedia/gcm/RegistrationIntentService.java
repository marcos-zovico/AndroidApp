package app.devmedia.com.br.appdevmedia.gcm;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.IOException;

import app.devmedia.com.br.appdevmedia.LoginActivity;
import app.devmedia.com.br.appdevmedia.R;
import app.devmedia.com.br.appdevmedia.async.AsyncUsuarioHttpClient;
import cz.msebera.android.httpclient.Header;

/**
 * Created by Marcos on 28/04/2016.
 */
public class RegistrationIntentService extends IntentService {

    private static final String TAG = RegistrationIntentService.class.getSimpleName();

    private SharedPreferences preferences;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        InstanceID instanceID = InstanceID.getInstance(this);
        try {
            String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

            preferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
            String login = preferences.getString("login", null);
            String senha = preferences.getString("senha", null);
            if (login != null && senha != null) {
                RequestParams params = new RequestParams();
                params.put("login", login);
                params.put("token", token);


                AsyncUsuarioHttpClient.post("gcm/sendToken", params, new TextHttpResponseHandler() {

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String resultado, Throwable throwable) {
                        Log.e(RegistrationIntentService.class.getName(), "Erro no registro do token! Http Code: " + statusCode, throwable);
                        Toast.makeText(RegistrationIntentService.this, "Erro no registro do token: " + resultado, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String resultado) {
                        if (Boolean.valueOf(resultado)) {
                            Toast.makeText(RegistrationIntentService.this, "Registro do token: " + resultado, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegistrationIntentService.this, "Erro no registro do token: " + resultado, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                return;
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
        }

    }
}
