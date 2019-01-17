package ru.igorsharov.uberconvertprice.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.igorsharov.uberconvertprice.MainActivity;
import ru.igorsharov.uberconvertprice.R;
import ru.igorsharov.uberconvertprice.recycler_view.CustomModelCard;
import ru.igorsharov.uberconvertprice.recycler_view.CustomRecyclerViewAdapter;


public class StatFragment extends Fragment {

    private Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;

    // Recycler
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static final String BUNDLE_CONTENT = "bundle_content";


    public static StatFragment newInstance(final String content) {
        final StatFragment fragment = new StatFragment();
        final Bundle arguments = new Bundle();
        arguments.putString(BUNDLE_CONTENT, content);
        fragment.setArguments(arguments);
        Log.d("@@@", "newInstance: ");
        return fragment;
    }

    private String content;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(BUNDLE_CONTENT)) {
            content = getArguments().getString(BUNDLE_CONTENT);
        } else {
            throw new IllegalArgumentException("Must be created through newInstance(...)");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stat, container, false);

        initToolbar(view);
        initRecyclerView(view);


        return view;
    }

    private void initToolbar(View view) {
        // делает ActionBar активным (появляется системный текст),
        // но блокирует установку ручных титлов
        toolbar = view.findViewById(R.id.my_toolbar);
//        setSupportActionBar(toolbar);

//        toolbar.setTitle("test");

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
    }

    private void initRecyclerView(View view) {
        List<CustomModelCard> customModelCardsList = new ArrayList<CustomModelCard>();
        int imgID;
        for (int i = 1; i <= 100; i++) {
            if (i % 2 == 0) {
                imgID = R.drawable.ic_menu_send;
            } else {
                imgID = R.drawable.ic_menu_gallery;
            }
            customModelCardsList.add(new CustomModelCard("Title " + i, "Description " + i, imgID));
        }

        recyclerView = view.findViewById(R.id.my_recycler_view);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        // specify an adapter (see also next example)
        adapter = new CustomRecyclerViewAdapter(customModelCardsList);
        recyclerView.setAdapter(adapter);

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
