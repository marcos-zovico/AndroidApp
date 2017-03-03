package app.devmedia.com.br.appdevmedia.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import app.devmedia.com.br.appdevmedia.R;
import app.devmedia.com.br.appdevmedia.entity.Profissao;

/**
 * Created by marcos on 03/03/17.
 */

public class ProfissaoArrayAdapter extends ArrayAdapter<Profissao> {

    private List<Profissao> profissoes;
    private Context context;


    public ProfissaoArrayAdapter(Context context, int resource, List<Profissao> profissoes) {
        super(context, resource);
        this.profissoes = profissoes;
        this.context = context;
    }


    @Override
    public int getCount() {
        return profissoes.size();
    }


    @Nullable
    @Override
    public Profissao getItem(int position) {
        return profissoes.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getViewAux(position, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getViewAux(position, parent);
    }

    @NonNull
    private View getViewAux(int position, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View linha = layoutInflater.inflate(R.layout.linha_profissao, parent, false);

        TextView txtProfissao = (TextView) linha.findViewById(R.id.txtProfissao);
        txtProfissao.setText(profissoes.get(position).getDescricao());

        return linha;
    }


}
