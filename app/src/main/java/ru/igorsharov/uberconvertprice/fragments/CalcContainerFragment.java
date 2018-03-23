package ru.igorsharov.uberconvertprice.fragments;

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
    CalcFragment calcFragment = new CalcFragment();
    RideFragment rideFragment = new RideFragment();
    final String NAME_FRAGMENT = "Рассчет поездок";
    final String TAG = "@@@" + getClass().getName();



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculation, container, false);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        initView(view);
        initToolbar(view);
        Log.d(TAG, "onCreateView: ");
        ft.replace(R.id.sub_container, calcFragment).commit();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
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
        // чтобы при повторном попаданиии на фрагмент он не был пустым
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.remove(calcFragment).commit();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    private void initView(View view) {
        // табы
        final TabLayout tabLayout = view.findViewById(R.id.tabs);
        // слушатель нажатий на табы
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                if (tabLayout.getSelectedTabPosition() == 0) {
                    ft.replace(R.id.sub_container, calcFragment);
                } else if (tabLayout.getSelectedTabPosition() == 1) {
                    hideKeyboard();
                    ft.replace(R.id.sub_container, rideFragment);
                }
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initToolbar(View view) {
        // делает ActionBar активным (появляется системный текст),
        // но блокирует установку ручных титлов
        toolbar = view.findViewById(R.id.my_toolbar);
//        setSupportActionBar(toolbar);
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
