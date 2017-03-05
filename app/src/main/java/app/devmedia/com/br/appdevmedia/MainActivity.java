package app.devmedia.com.br.appdevmedia;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import app.devmedia.com.br.appdevmedia.adapter.ViewPagerAdapter;
import app.devmedia.com.br.appdevmedia.fragment.FragmentPerfil;
import app.devmedia.com.br.appdevmedia.fragment.FragmentProduto;

public class MainActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Drawer drawer;

    private static final long ID_ND_FOOTER = 500L;

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


        final PrimaryDrawerItem itemPerfil = new PrimaryDrawerItem().withName("Perfil");

        final PrimaryDrawerItem itemProdutos = new PrimaryDrawerItem()
                .withName("Produtos")
                .withBadge("43")
                .withBadgeStyle(new BadgeStyle()
                                 .withTextColor(Color.WHITE)
                                 .withColorRes(R.color.md_orange_700));

        final PrimaryDrawerItem itemCompras = new PrimaryDrawerItem()
                .withName("Ultimas compras")
                .withBadge("2")
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
                        return  true;
                    }
                })
                .build();

//        drawer.addItem(new DividerDrawerItem());
        drawer.addStickyFooterItem(new PrimaryDrawerItem().withName("Sobre o App").withIdentifier(ID_ND_FOOTER));

    }

    private void prepararDrawerItem(int position, IDrawerItem drawerItem ) {
        viewPager.setCurrentItem(position);

        switch ((int) drawerItem.getIdentifier()){
            case (int) ID_ND_FOOTER:

                try {
                    PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
                    Toast.makeText(MainActivity.this, "Versão : " + info.versionName, Toast.LENGTH_SHORT).show();
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

                    break;
        }


        drawer.closeDrawer();
    }


    private void configurarViewPager(ViewPager viewPager){

        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
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
}
