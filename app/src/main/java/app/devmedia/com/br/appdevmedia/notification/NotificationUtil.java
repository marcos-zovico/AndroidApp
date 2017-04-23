package app.devmedia.com.br.appdevmedia.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Diogo on 15/05/2016.
 */
public class NotificationUtil {

    public static final int NOTIFICATION_ID = 357;

    private Context context;

    public NotificationUtil(Context context) {
        this.context = context;
    }

    private void showSmallNotification(NotificationCompat.Builder builder, //
                                       int icon, //
                                       String titulo, //
                                       String msg, //
                                       String timestamp, //
                                       PendingIntent pendingIntent, //
                                       Uri somAlarme) {
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

        inboxStyle.addLine(msg);

        Notification notification = builder.setSmallIcon(icon)
                .setTicker(titulo)
                .setContentTitle(titulo)
                .setContentText(msg)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSound(somAlarme)
                .setWhen(getTimeInMilliSec(timestamp))
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), icon))
                .setNumber(4)
                .setStyle(inboxStyle)
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    private void showBigNotification(Bitmap bitmap, //
                                     NotificationCompat.Builder builder, //
                                     int icon, //
                                     String titulo, //
                                     String msg, //
                                     String timestamp, //
                                     PendingIntent pendingIntent, //
                                     Uri somAlarme) {

    }

    public static long getTimeInMilliSec(String timestamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date data = format.parse(timestamp);
            return data.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
