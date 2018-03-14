package ru.igorsharov.uberconvertprice;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class StatisticFragment extends Fragment {

    private Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;

    // Recycler
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistic, container, false);

        // делает ActionBar активным (появляется системный текст),
        // но блокирует установку ручных титлов
        toolbar = view.findViewById(R.id.my_toolbar);
//        setSupportActionBar(toolbar);

        toolbar.setTitle("Статистика поездок");

        DrawerLayout drawer = ((MainActivity) getActivity()).getDrawerLayout();
        // инициализация кнопки гамбургера на тулбаре
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        // вешаем слушателя на drawer
        drawer.addDrawerListener(toggle);

        // синхронизирует hamburger menu с drawer
        // без данной строки кнопка гамбургер не появится
        toggle.syncState();

        // работа с видимостью тулбара
//        collapsingToolbarLayout = findViewById(R.id.toolbar_layout);

        // отключает коллапсовый титл, позволяя задавать текст tollbar-а
        // находится ниже основного и имеет более крупный шрифт
        // данный титл виден только при ручном указании ширины AppBar-а
        // в параметре android:layout_height (по умолчанию true)
//        collapsingToolbarLayout.setTitleEnabled(false);
//        alwaysToolbar();

        initRecyclerView(view);

        return view;
    }

    private void initRecyclerView(View view) {
//        mRecyclerView = view.findViewById(R.id.my_recycler_view);
        // use a linear layout manager
//        mLayoutManager = new LinearLayoutManager(getActivity());
//        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
//        mAdapter = new MyAdapter(myDataset);
//        mRecyclerView.setAdapter(mAdapter);

    }

    // делает тулбар статичным если пытается уехать за границы экрана
    private void alwaysToolbar() {
        ((AppBarLayout.LayoutParams) collapsingToolbarLayout.getLayoutParams()).setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
    }

    // делает тулбар прокручиваемым
    private void scrollToolbar() {
        ((AppBarLayout.LayoutParams) collapsingToolbarLayout.getLayoutParams()).setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL);
    }


    @Override
    public void onResume() {
        super.onResume();

    }
}
