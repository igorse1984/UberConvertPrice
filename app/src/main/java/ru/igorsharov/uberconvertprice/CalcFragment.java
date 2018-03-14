package ru.igorsharov.uberconvertprice;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import ru.igorsharov.uberconvertprice.calculation.TariffOne;
import ru.igorsharov.uberconvertprice.calculation.TariffTwo;
import ru.igorsharov.uberconvertprice.database.CalcDb;

/**
 * Created by Игорь on 07.03.2018.
 */

public class CalcFragment extends Fragment implements View.OnClickListener {

    private StateBox stateBox;
    private TariffTwo tariffTwo;
    private TariffOne tariffOne;

    private TextView tvResultOfNewPrice, tvResultOfOldPrice;
    private TextView tvResultOfNewPrice1, tvResultOfOldPrice1;
    private Spinner boostSpinner;
    private CheckBox chBoxOblast, chBoxGarantpik;
    private Switch switchShowPrice;
    private LinearLayout oldPrice;
    private Button btnCls;
    private CustomEditText etParthnerCommission, etTime, etWay;
    private Toolbar toolbar;

    private void initView(View view) {
        oldPrice = view.findViewById(R.id.oldPrice);
        view.findViewById(R.id.buttonCalc).setOnClickListener(this);
        view.findViewById(R.id.buttonClearEditText).setOnClickListener(this);
        etTime = view.findViewById(R.id.editTextTime);
        etWay = view.findViewById(R.id.editTextWay);
        tvResultOfNewPrice = view.findViewById(R.id.textViewNewPrice);
        tvResultOfOldPrice = view.findViewById(R.id.textViewOldPrice);
        tvResultOfNewPrice1 = view.findViewById(R.id.textViewNewPrice1);
        tvResultOfOldPrice1 = view.findViewById(R.id.textViewOldPrice1);
        boostSpinner = view.findViewById(R.id.boostSpinner);
        chBoxOblast = view.findViewById(R.id.chbPrigorod);
        chBoxGarantpik = view.findViewById(R.id.chbGarantpik);
        switchShowPrice = view.findViewById(R.id.switch2);
        btnCls = view.findViewById(R.id.buttonClearEditText);

        etParthnerCommission = view.findViewById(R.id.editTextPrthnerCommisson);
        // фокус на поле "время" при загрузке приложения
        etTime.requestFocus();

        // TODO переделать на добавление расчета в БД
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Здесь будет реализовано добавление в БД", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void initToolbar(View view) {
        // делает ActionBar активным (появляется системный текст),
        // но блокирует установку ручных титлов
        toolbar = view.findViewById(R.id.my_toolbar);
//        setSupportActionBar(toolbar);
        toolbar.setTitle("Калькулятор");
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calc, container, false);
        initView(view);
        initToolbar(view);

        // для отслеживания в поле EditText километража свыше заданного
        listenerHandler();

        // инициализируем хранилище состояний чекбоксов и вводимых данных из активити
        stateBox = new StateBox();

        // инициализируем тарифы
        tariffOne = new TariffOne(stateBox);
        tariffTwo = new TariffTwo(stateBox);

        etParthnerCommission.setText(String.valueOf(tariffTwo.getParthnerComission()));
        return view;
    }


    // выполнение расчета по нажатию кнопки
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonCalc) {
            stateBox.setData(getDataOfView(etWay), getDataOfView(etTime), getDataOfView(boostSpinner), getDataOfView(etParthnerCommission));
            // добавление данных в БД
            CalcDb.get(getActivity()).addCalc(tariffTwo);
            calculateAndSetResult();
            printSnackbar(view, "Посчитано", R.color.colorGreen).show();
        } else {
            printSnackbar(view, "Очищено", R.color.colorAccent).show();
            clsView();
        }
    }


    private Snackbar printSnackbar(View view, String msg, int color) {
        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        //TODO не нравится способ получения контекста, да и под вопросом способ получения цвета из ресурсов
        snackBarView.setBackgroundColor(ContextCompat.getColor(getActivity(), color));
        return snackbar;
    }

    private void clsView() {
        // очистка полей ввода
        clearViewText(etTime);
        clearViewText(etWay);
        clearViewText(tvResultOfOldPrice);
        clearViewText(tvResultOfNewPrice);
        clearViewText(tvResultOfOldPrice1);
        clearViewText(tvResultOfNewPrice1);
        etTime.requestFocus();
        boostSpinner.setSelection(0);
    }


    private void clearViewText(TextView view) {
        view.setText("");
    }

    private void listenerHandler() {
        // слушаем поле EditText расстояние
        etWay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (getDataOfView(etWay) > tariffTwo.getKmOver()) {
                    chBoxOblast.setVisibility(View.VISIBLE);
                } else {
                    chBoxOblast.setChecked(false);
                    chBoxOblast.setVisibility(View.GONE);
                }

                // отслеживание пустотности EditText
                if (String.valueOf(etWay.getText()).equals("") && String.valueOf(etTime.getText()).equals(""))
                    btnCls.setVisibility(View.GONE);
                else
                    btnCls.setVisibility(View.VISIBLE);
            }
        });

        // слушаем поле EditText время
        etTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // отслеживание пустотности EditText
                if (String.valueOf(etWay.getText()).equals("") && String.valueOf(etTime.getText()).equals(""))
                    btnCls.setVisibility(View.GONE);
                else
                    btnCls.setVisibility(View.VISIBLE);
            }
        });

        // слушаем spinner
        boostSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    chBoxGarantpik.setVisibility(View.VISIBLE);
                } else {
                    chBoxGarantpik.setChecked(false);
                    chBoxGarantpik.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // TODO оптимизировать чекбоксы, сделать имплемент интерфейса и свитч
        // слушаем чекбоксы
        chBoxOblast.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                stateBox.setChBoxOblastState(b);
            }
        });

        chBoxGarantpik.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                stateBox.setChBoxGarantpikState(b);
            }
        });

        // переключатель видимости старого тарифа
        switchShowPrice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                oldPrice.setVisibility(b ? View.VISIBLE : View.GONE);
            }
        });

    }


    // общий метод получения данных из полей ввода
    private float getDataOfView(View v) {
        if (v instanceof TextView) {
            TextView tv = (TextView) v;
            if (!tv.getText().toString().equals("")) {
                return Float.parseFloat(tv.getText().toString());
            }
        } else if (v instanceof Spinner) {
            Spinner sp = (Spinner) v;
            return Float.parseFloat((String) sp.getSelectedItem());
        }
        return 0;
    }


    private void calculateAndSetResult() {
        setResults(tvResultOfNewPrice, tariffTwo.getCost());
        setResults(tvResultOfNewPrice1, tariffTwo.getProfit());
        setResults(tvResultOfOldPrice, tariffOne.getCost());
        setResults(tvResultOfOldPrice1, tariffOne.getProfit());
    }


    private void setResults(TextView tv, float priceTotal) {
        tv.setText(String.valueOf((int) priceTotal).concat(" руб."));
    }

}
