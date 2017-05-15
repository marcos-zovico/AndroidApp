package app.devmedia.com.br.appdevmedia.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import app.devmedia.com.br.appdevmedia.entity.Produto;

/**
 * Marcos on 25/06/2016.
 */
public class CustomProdutoAdapter extends BaseAdapter {

    private final Activity activity;
    private final List<Produto> produtos;
    private final ProdutoListAdapterListener produtoListAdapterListener;

    public CustomProdutoAdapter( final Activity activity, final List<Produto> produtos, final ProdutoListAdapterListener produtoListAdapterListener) {
        this.activity = activity;
        this.produtos = produtos;
        this.produtoListAdapterListener = produtoListAdapterListener;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    public interface ProdutoListAdapterListener {
        public void onAddCarrinhoPressed(Produto produto);
    }
}
