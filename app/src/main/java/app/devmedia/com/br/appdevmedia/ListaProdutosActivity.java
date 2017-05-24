package app.devmedia.com.br.appdevmedia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalItem;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalPaymentDetails;
import com.paypal.android.sdk.payments.PayPalService;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import app.devmedia.com.br.appdevmedia.adapter.CustomProdutoAdapter;
import app.devmedia.com.br.appdevmedia.entity.Produto;
import app.devmedia.com.br.appdevmedia.util.Constantes;
import app.devmedia.com.br.appdevmedia.util.UTF8ParseJson;

public class ListaProdutosActivity extends AppCompatActivity implements CustomProdutoAdapter.ProdutoListAdapterListener {

    private ListView lstProdutos;
    private Button btnCheckout;

    private CustomProdutoAdapter customProdutoAdapter;
    private List<Produto> produtos = new ArrayList<>();

    private ProgressDialog progressDialog;

    private List<PayPalItem> produtosCarrinho = new ArrayList<>();

    private PayPalConfiguration payPalConfig = new PayPalConfiguration()
            .environment(Constantes.PAYPAL_ENV)
            .clientId(Constantes.PAYPAL_CLIENT_ID)
            .languageOrLocale("pt_BR");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_produtos);
        setTitle("Lista de Produtos");

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(Boolean.TRUE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(Boolean.TRUE);

        lstProdutos = (ListView) findViewById(R.id.lstProdutos);
        btnCheckout = (Button) findViewById(R.id.btnCheckout);

        customProdutoAdapter = new CustomProdutoAdapter(this, produtos, this);
        lstProdutos.setAdapter(customProdutoAdapter);

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfig);
        startService(intent);

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (produtosCarrinho.isEmpty()) {
                    Toast.makeText(ListaProdutosActivity.this, "O carrinho está vazio!", Toast.LENGTH_SHORT).show();
                } else {
                    executarPagtoPayPal();
                }
            }
        });

        buscarListaProdutos();
    }

    private void buscarListaProdutos() {
        showProgressDialog();

        produtos.clear();
        JsonArrayRequest request = new UTF8ParseJson(Constantes.URL_WS_PRODUTOS, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Type type = new TypeToken<List<Produto>>() {
                }.getType();
                List<Produto> produtos = new Gson().fromJson(response.toString(), type);
                ListaProdutosActivity.this.produtos.addAll(produtos);

                customProdutoAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.e("Erro no carregamento de produtos", error);
                        Toast.makeText(ListaProdutosActivity.this, "Erro na listagem de produtos.", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        DevMediaApp.getInstance().addToRequestQueue(request);
    }

    private PayPalPayment montarPagtoFinal() {
        PayPalItem[] itens = new PayPalItem[produtosCarrinho.size()];
        itens = produtosCarrinho.toArray(itens);

        // Valor total do pagto
        BigDecimal total = PayPalItem.getItemTotal(itens);

        PayPalPaymentDetails detalhes = new PayPalPaymentDetails(BigDecimal.ZERO, total, BigDecimal.ZERO);

        PayPalPayment payPalPayment = new PayPalPayment(total, Constantes.PAYPAL_CURRENCY, "Transação de compra em processamento...", Constantes.PAYPAL_INTENT);

        payPalPayment.items(itens).paymentDetails(detalhes);

        payPalPayment.custom("Compra de Produtos - Loja DevMedia");

        return payPalPayment;
    }

    private void executarPagtoPayPal() {
        PayPalPayment pagto = montarPagtoFinal();
    }

    @Override
    public void onAddCarrinhoPressed(Produto produto) {
        PayPalItem payPalItem = new PayPalItem(
                produto.getTitulo(),
                1,
                produto.getValor(),
                Constantes.PAYPAL_CURRENCY,
                produto.getSku()
        );

        produtosCarrinho.add(payPalItem);

        Toast.makeText(ListaProdutosActivity.this, "Produto " + produto.getTitulo() + " adicionado ao carrinho!", Toast.LENGTH_SHORT).show();
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Carregando...");
        progressDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

}
