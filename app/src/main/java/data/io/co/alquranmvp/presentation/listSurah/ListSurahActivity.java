package data.io.co.alquranmvp.presentation.listSurah;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import data.io.co.alquranmvp.R;
import data.io.co.alquranmvp.base.BaseActivity;
import data.io.co.alquranmvp.model.Surah;
import data.io.co.alquranmvp.presentation.listAyat.ListAyatActivity;

public class ListSurahActivity extends BaseActivity<ListSurahPresenter> implements ListSurahView, ListSurahAdapter.OnSurahItemClick {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.listSurah)
    RecyclerView listSurah;
    @BindView(R.id.mainNavigation)
    NavigationView mainNavigation;
    @BindView(R.id.mainDrawer)
    DrawerLayout mainDrawer;

    private ListSurahAdapter listSurahAdapter;
    private String loadTerjemahan = LOAD_INDONESIA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_surah);
        ButterKnife.bind(this);

        toolbar.setTitle("Alquran Q");

        listSurahAdapter = new ListSurahAdapter(new ArrayList<Surah>(), this);

        listSurah.setLayoutManager(new LinearLayoutManager(this));
        listSurah.setHasFixedSize(true);
        listSurah.setAdapter(listSurahAdapter);

        mPresenter.loadSurah(loadTerjemahan);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mainDrawer, toolbar, R.string.app_name, R.string.app_name);
        mainDrawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        mainNavigation.getLayoutParams().width = Resources.getSystem().getDisplayMetrics().widthPixels / 2;
        mainNavigation.requestLayout();
        mainNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.menu_indonesia:
                        loadTerjemahan = LOAD_INDONESIA;
                        mPresenter.loadSurah(loadTerjemahan);
                        break;
                    case R.id.menu_inggris:
                        loadTerjemahan = LOAD_ENGLISH;
                        mPresenter.loadSurah(loadTerjemahan);
                        break;
                }

                mainDrawer.closeDrawers();
                return true;
            }
        });

    }

    @Override
    public ListSurahPresenter initPresenter() {
        return  new ListSurahPresenter(this);

    }

    @Override
    public void onLoad(ArrayList<Surah> data) {
        listSurahAdapter.refresh(data);
    }

    @Override
    public void onCLick(Surah surah) {

        Intent ayat = new Intent(ListSurahActivity.this, ListAyatActivity.class);
        ayat.putExtra(ListAyatActivity.KEY_AYAT, surah.getAyat());
        ayat.putExtra(ListAyatActivity.KEY_SURAH, surah.getSurah());
        ayat.putExtra(ListAyatActivity.KEY_TERJEMAHAN, surah.getTerjemahan());
        ayat.putExtra(ListAyatActivity.KEY_LOAD_TERJEMAHAN, loadTerjemahan);
        startActivity(ayat);

    }
}
