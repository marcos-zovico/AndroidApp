package app.devmedia.com.br.appdevmedia.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import app.devmedia.com.br.appdevmedia.R;
import app.devmedia.com.br.appdevmedia.adapter.ProfissaoArrayAdapter;
import app.devmedia.com.br.appdevmedia.entity.Profissao;
import app.devmedia.com.br.appdevmedia.entity.User;
import app.devmedia.com.br.appdevmedia.util.Constantes;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by marcos on 02/03/17.
 */

public class FragmentPerfil extends Fragment {

    private Button btnCadastrar;
    private TextInputLayout lytTxtNome;
    private TextView txtNome;
    private Spinner spnProfissao;

    private List<Profissao> profissoes;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        final RelativeLayout lytLoading = (RelativeLayout) view.findViewById(R.id.lytLoading);
        lytLoading.setVisibility(View.VISIBLE);

        btnCadastrar = (Button) view.findViewById(R.id.btnCadastrar);
        lytTxtNome = (TextInputLayout) view.findViewById(R.id.lytTxtNome);
        txtNome = (TextView) view.findViewById(R.id.txtNome);
        spnProfissao = (Spinner) view.findViewById(R.id.spnProfissao);

        new AsyncHttpClient().get(Constantes.URL_WS_BASE + "/user/getprofissoes", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("Response", response.toString());

                profissoes = new ArrayList<>();

                if (null != response) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject profissao = response.getJSONObject(i);
                            profissoes.add(new Gson().fromJson(profissao.toString(), Profissao.class));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                spnProfissao.setAdapter(new ProfissaoArrayAdapter(getActivity(), R.layout.linha_profissao, profissoes));
                lytLoading.setVisibility(View.GONE);
            }

        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validarNome()) {
                    return;
                }

                final Gson gson = new Gson();
                String json = gson.toJson(criarPessoa());

                try {
                    StringEntity stringEntity = new StringEntity(json);

                    new AsyncHttpClient().post(null, Constantes.URL_WS_BASE + "/user/add", stringEntity, "application/json", new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            Log.d("Response", response.toString());
                            User use = gson.fromJson(response.toString(), User.class);
                        }

                    });

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
        });

        return view;
    }

    private boolean validarNome() {
        if (txtNome.getText().toString().trim().isEmpty()) {
            lytTxtNome.setError("Campo nome obrigatório!");
            return false;
        } else {
            lytTxtNome.setErrorEnabled(false);
        }
        return true;
    }


    private User criarPessoa() {
        User pessoa = new User();
        pessoa.setNome("Marcos Souza");
        pessoa.setCodProfissao(1);
        pessoa.setEmail("msouza@mail.com");
        pessoa.setMinibio("Test mini bio");
        pessoa.setSexo('M');


        return pessoa;
    }
}
