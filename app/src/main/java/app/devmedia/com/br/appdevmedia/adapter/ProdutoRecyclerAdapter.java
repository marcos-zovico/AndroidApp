package app.devmedia.com.br.appdevmedia.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by marcos on 07/03/17.
 */

public class ProdutoRecyclerAdapter extends RecyclerView.Adapter<ProdutoRecyclerAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View viewItem) {
            super(viewItem);
        }
    }

    @Override
    public ProdutoRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ProdutoRecyclerAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
