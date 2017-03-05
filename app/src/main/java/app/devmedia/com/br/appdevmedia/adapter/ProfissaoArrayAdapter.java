package app.devmedia.com.br.appdevmedia.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import app.devmedia.com.br.appdevmedia.R;
import app.devmedia.com.br.appdevmedia.async.AsyncImageHelper;
import app.devmedia.com.br.appdevmedia.entity.Profissao;
import app.devmedia.com.br.appdevmedia.util.Constantes;

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

        Profissao profissao = profissoes.get(position);

        TextView txtProfissao = (TextView) linha.findViewById(R.id.txtProfissao);
        txtProfissao.setText(profissao.getDescricao());

        TextView txtDescricao = (TextView) linha.findViewById(R.id.txtDescricao);
        txtDescricao.setText(profissao.getSubDescricao());


        ImageView imageView = (ImageView) linha.findViewById(R.id.imgProfissao);
        new AsyncImageHelper(imageView).execute(Constantes.URL_WEB_BASE + profissao.getUrlImg());

        return linha;
    }


}
