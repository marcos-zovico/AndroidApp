package app.devmedia.com.br.appdevmedia.gcm;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.gcm.GcmListenerService;

import app.devmedia.com.br.appdevmedia.MainActivity;
import app.devmedia.com.br.appdevmedia.entity.ProdutoNotification;
import app.devmedia.com.br.appdevmedia.notification.NotificationUtil;

/**
 * Created by Diogo on 15/05/2016.
 */
public class GCMPushReceiver extends GcmListenerService {

    private NotificationUtil notificationUtil = new NotificationUtil(getApplicationContext());

    @Override
    public void onMessageReceived(String from, Bundle bundle) {
        String titulo = bundle.getString("titulo");
        String message = bundle.getString("message");
        String data = bundle.getString("data");
        String idProduto = bundle.getString("id");

        ProdutoNotification produtoNotification = new ProdutoNotification(Integer.parseInt(idProduto), titulo, message, data);

        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.putExtra("nf_produto", produtoNotification);

        notificationUtil.showSmallNotificationMsg(titulo, message, data, i);
    }
}
