package app.devmedia.com.br.appdevmedia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalService;

import java.util.List;

import app.devmedia.com.br.appdevmedia.adapter.CustomProdutoAdapter;
import app.devmedia.com.br.appdevmedia.entity.Produto;
import app.devmedia.com.br.appdevmedia.util.Constantes;

public class ListaProdutosActivity extends AppCompatActivity implements CustomProdutoAdapter.ProdutoListAdapterListener {

    private ListView lstProdutos;
    private Button btnCheckout;

    private CustomProdutoAdapter customProdutoAdapter;
    private List<Produto> produtos;

    private ProgressDialog progressDialog;

    private PayPalConfiguration payPalConfig = new PayPalConfiguration().environment(Constantes.PAYPAL_ENV)
                                                                        .clientId(Constantes.PAYPAL_CLIENT_ID)
                                                                        .languageOrLocale("pt_BR");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_produtos);

        lstProdutos = (ListView) findViewById(R.id.lstProdutos);
        btnCheckout = (Button) findViewById(R.id.btnCheckout);

        customProdutoAdapter = new CustomProdutoAdapter(this, produtos, this);
        lstProdutos.setAdapter(customProdutoAdapter);

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfig);
        startService(intent);

        buscarListaProdutos();
    }

    private void buscarListaProdutos() {
        showProgressDialog();

    }

    @Override
    public void onAddCarrinhoPressed(Produto produto) {

    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Carregando...");
        progressDialog.show();
    }
}
