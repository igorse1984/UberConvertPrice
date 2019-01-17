package ru.igorsharov.uberconvertprice.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import ru.igorsharov.uberconvertprice.MainActivity;
import ru.igorsharov.uberconvertprice.R;

/**
 * Created by Игорь on 22.03.2018.
 */

public class CalcContainerFragment extends Fragment {

    private Toolbar toolbar;
    CalcFragment calcFragment;
    RideFragment rideFragment;
    final String NAME_FRAGMENT = "Рассчет поездок";
    final String TAG = "@@@" + getClass().getName();
    TabLayout tabLayout;

    private static final String BUNDLE_CONTENT = "bundle_content";
    private static final Bundle arguments = new Bundle();

    public static CalcContainerFragment newInstance(final int fragmentNum) {
        final CalcContainerFragment fragment = new CalcContainerFragment();
        arguments.putInt(BUNDLE_CONTENT, fragmentNum);
        fragment.setArguments(arguments);
        Log.d("@@@", "newInstance: ");
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null && !getArguments().containsKey(BUNDLE_CONTENT)) {
            throw new IllegalArgumentException("Must be created through newInstance(...)");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_calculation, container, false);
        calcFragment = CalcFragment.newInstance(null);
        rideFragment = RideFragment.newInstance(null);
        initTabs(view);
        initToolbar(view);
        return view;
    }

    /**
     * Возвращение состояния выбраного таба
     */
    @Override
    public void onStart() {
        super.onStart();

        int savedTabsState = getArguments().getInt(BUNDLE_CONTENT);
        Log.d(TAG, "onStart: savedTabsState = " + savedTabsState);

        // т.к. работа с табами организована напрямую в обход ViewPager,
        // применяем данный код получения нужного таба
        TabLayout.Tab tab = tabLayout.getTabAt(savedTabsState);
        if (tab != null)
            tab.select();

        changeFragmentOnTabPosition(savedTabsState);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    /**
     * Сохранение текущего состояния выбора таба
     */
    @Override
    public void onStop() {
        super.onStop();
        arguments.putInt(BUNDLE_CONTENT, tabLayout.getSelectedTabPosition());
        Log.d(TAG, "onStop: content = " + tabLayout.getSelectedTabPosition());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    /**
     * Вызывается для смены фрагмента в зависимости от выбранного таба
     */
    private void changeFragmentOnTabPosition(int tabSelect) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();


        if (tabSelect == 0) {
            ft.replace(R.id.sub_container, calcFragment);
        } else if (tabSelect == 1) {
            hideKeyboard();
            ft.replace(R.id.sub_container, rideFragment);
        }
        ft.commit();
    }

    private void initTabs(View view) {
        // находим табы
        tabLayout = view.findViewById(R.id.tabs);
        // слушатель нажатий на табы
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                changeFragmentOnTabPosition(tabLayout.getSelectedTabPosition());
                Log.d(TAG, "onTabSelected = " + tabLayout.getSelectedTabPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    // TODO правильнее бы было реализовать данный метод в Активити
    private void initToolbar(View view) {
        toolbar = view.findViewById(R.id.my_toolbar);
        toolbar.setTitle(NAME_FRAGMENT);
        DrawerLayout drawer = ((MainActivity) getActivity()).getDrawerLayout();
        // инициализация кнопки гамбургера на тулбаре
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        // вешаем слушателя на drawer
        drawer.addDrawerListener(toggle);

        // синхронизирует hamburger menu с drawer
        // без данной строки кнопка гамбургер не появится
        toggle.syncState();
    }

    // Способ скрывать клавиатуру с экрана
    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(toolbar.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
