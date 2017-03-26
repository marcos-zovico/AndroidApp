package app.devmedia.com.br.appdevmedia.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import app.devmedia.com.br.appdevmedia.R;
import app.devmedia.com.br.appdevmedia.util.Constantes;
import cz.msebera.android.httpclient.Header;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.view.CardViewNative;

/**
 * Created by marcos on 02/03/17.
 */

public class FragmentCompras extends Fragment{


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.fragment_compras, container, false);

        final RelativeLayout lytLoading = (RelativeLayout) viewRoot.findViewById(R.id.lytLoading);
        lytLoading.setVisibility(View.VISIBLE);

        Card card = new Card(getContext());

        CardHeader header = new CompraInnerHeader(getContext());

        header.setPopupMenu(R.menu.menu_main, new CardHeader.OnClickCardHeaderPopupMenuListener() {
            @Override
            public void onMenuItemClick(BaseCard card, MenuItem item) {
                Toast.makeText(getActivity(), "Click on " + item.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        card.addCardHeader(header);

        CardViewNative cardView = (CardViewNative) viewRoot.findViewById(R.id.carddemo);
        cardView.setCard(card);

        Card cardCollapse = new Card(getContext());

        header = new CardHeader(getContext());
        header.setOtherButtonVisible(true);
        header.setOtherButtonDrawable(R.drawable.ic_notifications_active_black_24dp);
        header.setOtherButtonClickListener(new CardHeader.OnClickCardHeaderOtherButtonListener() {
            @Override
            public void onButtonItemClick(Card card, View view) {
                Toast.makeText(getActivity(), "Click on Other Button", Toast.LENGTH_LONG).show();
            }
        });
        header.setTitle("Mochila Mickey");
        cardCollapse.addCardHeader(header);

//        CardExpand expand = new CardExpand(getContext());
//        expand.setTitle("Mochila Detalhe");
//        cardCollapse.addCardExpand(expand);

        CardViewNative cardViewCollapse = (CardViewNative) viewRoot.findViewById(R.id.cardCollapse);
        cardViewCollapse.setCard(cardCollapse);

        new AsyncHttpClient().get(Constantes.URL_WS_BASE + "/produto/list", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                lytLoading.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("Response", responseString);
                Toast.makeText(getActivity(), "Falha: " + responseString, Toast.LENGTH_SHORT).show();
            }
        });

        return viewRoot;
    }

    private class CompraInnerHeader extends CardHeader {

        public CompraInnerHeader(Context context) {
            super(context, R.layout.linha_header_compra);
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View view) {
            TextView txtTitulo = (TextView) view.findViewById(R.id.txtTitulo);
            TextView txtDescricao = (TextView) view.findViewById(R.id.txtDescricao);
            ImageView imgProduto = (ImageView) view.findViewById(R.id.imgProduto);

            txtTitulo.setText("Profissão Teste");
            txtDescricao.setText("Descrição Teste");
            Picasso.with(imgProduto.getContext()).load(Constantes.URL_WEB_BASE + "img/produtos/colecao_lapis_cor.jpg").into(imgProduto);
        }
    }

}
