package app.devmedia.com.br.appdevmedia.gcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by marcos on 09/04/17.
 * <p>
 * ouve sempre que o token do usuario form modifiado
 */

public class CustomFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final  String TAG = CustomFirebaseInstanceIdService.class.getSimpleName();


    @Override
    public void onTokenRefresh() {

        //Getting registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        //Displaying token on logcat
        Log.d(TAG, "Refreshed token: " + refreshedToken);

    }

    private void sendRegistrationToServer(String token) {
        //You can implement this method to store the token on your server
        //Not required for current project
    }
}
