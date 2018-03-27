package ru.igorsharov.uberconvertprice;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import ru.igorsharov.uberconvertprice.fragments.CalcContainerFragment;
import ru.igorsharov.uberconvertprice.fragments.StatFragment;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;

    CalcContainerFragment calcContainerFragment;
    StatFragment statFragment;


    // возвращает адрес DrawerLayout для фрагментов
    public DrawerLayout getDrawerLayout() {
        return drawer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // инициализация фрагментов
        calcContainerFragment = CalcContainerFragment.newInstance(null);
        statFragment = StatFragment.newInstance(null);

        // нахоим родительский DrawerLayout для последующего использования в тулбарах фрагментов
        drawer = findViewById(R.id.drawer_layout);

        // вешаем листнер на Drawer меню
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // ставим дефолтно курсор меню
        navigationView.setCheckedItem(R.id.nav_calc);

        // SupportFragmentManager для фрагментов из библиотеки support
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, calcContainerFragment).commit();


    }

    // метод отслеживающий нажатие кнопки назад
    // в данном случае закрывыет Drawer-меню если оно открыто
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        // Handle navigation view item clicks here.
        int id = item.getItemId();
        // выбор фрагмента
        switch (id) {
            case R.id.nav_calc:
                fragment = calcContainerFragment;
                hideKeyboard();
                break;
            case R.id.nav_statistic:
                fragment = statFragment;
                hideKeyboard();
                break;
        }

        // включаем фрагмент
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

        // Выделяем выбранный пункт меню в шторке
        item.setChecked(true);

        // Выводим выбранный пункт в заголовке
//        setTitle(item.getTitle());

        // закрываем NavigationView
        // параметр определяет анимацию закрытия
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    // Способ скрывать клавиатуру с экрана
    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(drawer.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
