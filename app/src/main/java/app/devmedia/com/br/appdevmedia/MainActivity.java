package app.devmedia.com.br.appdevmedia;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.io.Serializable;

import app.devmedia.com.br.appdevmedia.adapter.ViewPagerAdapter;
import app.devmedia.com.br.appdevmedia.entity.ProdutoNotification;
import app.devmedia.com.br.appdevmedia.fragment.FragmentCompras;
import app.devmedia.com.br.appdevmedia.fragment.FragmentPerfil;
import app.devmedia.com.br.appdevmedia.fragment.FragmentProduto;
import app.devmedia.com.br.appdevmedia.gcm.RegistrationIntentService;

public class MainActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Drawer drawer;

    private BroadcastReceiver registrationBroadcastReceiver;

    private static final long ID_ND_FOOTER = 500L;
    private static final long ID_ND_PRODUTOS = 501L;

    private static final String REGISTRATION_COMPPLETE = "REGISTRATION_COMPPLETE";
    private static final String PUSH = "PUSH";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        viewPager = (ViewPager) findViewById(R.id.viewPager);
        configurarViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        final PrimaryDrawerItem itemPerfil = new PrimaryDrawerItem()
                .withName("Perfil")
                .withIcon(GoogleMaterial.Icon.gmd_person);

        final PrimaryDrawerItem itemProdutos = new PrimaryDrawerItem()
                .withName("Produtos")
                .withBadge("43")
                .withIdentifier(ID_ND_PRODUTOS)
                .withIcon(FontAwesome.Icon.faw_th_list)
                .withBadgeStyle(new BadgeStyle()
                        .withTextColor(Color.WHITE)
                        .withColorRes(R.color.md_orange_700));

        final PrimaryDrawerItem itemCompras = new PrimaryDrawerItem()
                .withName("Ultimas compras")
                .withBadge("2")
                .withIcon(GoogleMaterial.Icon.gmd_shop_two)
                .withBadgeStyle(new BadgeStyle()
                        .withTextColor(Color.WHITE)
                        .withColorRes(R.color.md_orange_700));

        AccountHeader accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        new ProfileDrawerItem().withName("Marcos Souza")
                                .withEmail("msouza@gmail.com")
                                .withIcon(R.drawable.profile)
                ).build();


        drawer = new DrawerBuilder()
                .withAccountHeader(accountHeader)
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        new SectionDrawerItem().withName("Conta do Usuário"),
                        itemPerfil,
                        new SectionDrawerItem().withName("Opçoẽs do Sistema"),
                        itemProdutos,
                        itemCompras
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        prepararDrawerItem(position, drawerItem);
                        return true;
                    }
                })
                .build();

        drawer.addStickyFooterItem(new PrimaryDrawerItem().withName("Sobre o App")
                .withIdentifier(ID_ND_FOOTER)
                .withIcon(GoogleMaterial.Icon.gmd_info));


        Serializable serializable = null; // getIntent().getExtras().getSerializable("nf_produto");
        if (serializable != null) {
            ProdutoNotification nf_produto = (ProdutoNotification) serializable;
            Toast.makeText(MainActivity.this, nf_produto.toString(), Toast.LENGTH_SHORT).show();
        }

        configurarGCM();

    }

    private void prepararDrawerItem(int position, IDrawerItem drawerItem) {
        viewPager.setCurrentItem(position);

        switch ((int) drawerItem.getIdentifier()) {
            case (int) ID_ND_FOOTER:

                try {
                    PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
                    Toast.makeText(MainActivity.this, "Versão : " + info.versionName, Toast.LENGTH_SHORT).show();
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

                break;
            case (int) ID_ND_PRODUTOS:
                Intent intent = new Intent(this, ListaProdutosActivity.class);
                startActivity(intent);
                break;
        }


        drawer.closeDrawer();
    }


    private void configurarViewPager(ViewPager viewPager) {

        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new FragmentCompras(), "Compras");
        pagerAdapter.addFragment(new FragmentProduto(), "Produtos");
        pagerAdapter.addFragment(new FragmentPerfil(), "Perfil");


        viewPager.setAdapter(pagerAdapter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void configurarGCM() {
        registrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String token = intent.getStringExtra("token");
                Toast.makeText(MainActivity.this, "Token: " + token, Toast.LENGTH_SHORT).show();
            }
        };

        Intent intent = new Intent(this, RegistrationIntentService.class);
        intent.putExtra("key", "register");
        startService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(this).registerReceiver(registrationBroadcastReceiver, new IntentFilter(REGISTRATION_COMPPLETE));

        LocalBroadcastManager.getInstance(this).registerReceiver(registrationBroadcastReceiver, new IntentFilter(PUSH));
    }
}
