package app.devmedia.com.br.appdevmedia.util;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;

/**
 * Created by Marcos Souza on 12/11/2015.
 */
public class Constantes {

    public static final String PAYPAL_CLIENT_ID = "AZ2h6bkhNNX84itUmVKvqoBLVCuJ9Tm46zI2iLsmBWG4ovcBiG-powSiAiIGEEmKTBkAG5NB8yRp_pJz";

    public static final String PAYPAL_CLIENT_SECRET = "EGB_XI_cecvhqEx6Cc7X3MIBZBLohINKYvDv0iuDVDBdcs0m_nFkj9svJAmBw38dAWhdheQuNIoZ71f6";

    public static final String PAYPAL_ENV = PayPalConfiguration.ENVIRONMENT_SANDBOX;

    public static final String PAYPAL_INTENT = PayPalPayment.PAYMENT_INTENT_SALE;

    public static final String PAYPAL_CURRENCY = "BRL";

    public static final String URL_WS_BASE = "http://192.168.0.12:8080/rest-web/rest";

    public static final String URL_WEB_BASE = "http://192.168.0.12:8080/rest-web/";

    public static final String URL_WS_PRODUTOS = URL_WEB_BASE + "produto/list";

    public static final String URL_WS_CHECK_PAYMENT = URL_WEB_BASE + "paypal/check";
}
