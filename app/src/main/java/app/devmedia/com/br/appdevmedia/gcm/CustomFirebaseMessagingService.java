package app.devmedia.com.br.appdevmedia.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import app.devmedia.com.br.appdevmedia.MainActivity;
import app.devmedia.com.br.appdevmedia.R;

/**
 * Created by marcos on 09/04/17.
 */

public class CustomFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = CustomFirebaseMessagingService.class.getSimpleName();

    private SharedPreferences preferences;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Displaying data in log
        //It is optional
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());

        //Calling method to generate notification
        sendNotification(remoteMessage.getNotification().getBody());
    }

    //This method is only generating push notification
    //It is same as we did in earlier posts
    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Firebase Push Notification")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }

//    public RegistrationIntentService(String name) {
//        super(TAG);
//    }
//
//    @Override
//    protected void onHandleIntent(Intent intent) {
//
//        InstanceID instanceID = InstanceID.getInstance(this);
//
//        try {
//            String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
//
//            preferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
//            String login = preferences.getString("login", null);
//            String senha = preferences.getString("senha", null);
//            if (login != null && senha != null) {
//
//                RequestParams params = new RequestParams();
//                params.put("login", login);
//                params.put("token", token);
//
//                AsyncUsuarioHttpClient.post("gcm;sebdToken", null, new JsonHttpResponseHandler(){
//                    @Override
//                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                        try {
//                            Log.d(TAG, response.getString("msg"));
//                        } catch (JSONException e) {
//                            Log.e(TAG, e.getMessage(), e);
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                        Log.e(TAG, responseString, throwable);
//                    }
//                });
//
//            } else {
//                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
//                startActivity(i);
//                return;
//            }
//
//        } catch (IOException e) {
//            Log.e(TAG, e.getMessage(), e);
//        }
//    }

}
