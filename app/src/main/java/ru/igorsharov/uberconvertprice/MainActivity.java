package ru.igorsharov.uberconvertprice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import ru.igorsharov.uberconvertprice.calculation.Tarif3_12;
import ru.igorsharov.uberconvertprice.calculation.Tarif7_7;
import ru.igorsharov.uberconvertprice.database.CalcDb;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etTime, etWay;
    private TextView tvResultOfNewPrice, tvResultOfOldPrice;
    private TextView tvResultOfNewPrice1, tvResultOfOldPrice1;
    private Spinner boostSpinner;
    private CheckBox chBoxOblast, chBoxBeznal, chBoxGarantpik;
    private Switch switchShowPrice;
    private LinearLayout oldPrice;
    private StateBox stateBox;
    private Tarif3_12 tarif3_12;
    private Tarif7_7 tarif7_7;

    private void initView() {
        oldPrice = findViewById(R.id.oldPrice);
        findViewById(R.id.buttonCalc).setOnClickListener(this);
        findViewById(R.id.buttonClearEditText).setOnClickListener(this);
        etTime = findViewById(R.id.editTextTime);
        etWay = findViewById(R.id.editTextWay);
        tvResultOfNewPrice = findViewById(R.id.textViewNewPrice);
        tvResultOfOldPrice = findViewById(R.id.textViewOldPrice);
        tvResultOfNewPrice1 = findViewById(R.id.textViewNewPrice1);
        tvResultOfOldPrice1 = findViewById(R.id.textViewOldPrice1);
        boostSpinner = findViewById(R.id.boostSpinner);
        chBoxOblast = findViewById(R.id.chbPrigorod);
        chBoxBeznal = findViewById(R.id.chbBeznal);
        chBoxGarantpik = findViewById(R.id.chbGarantpik);
        switchShowPrice = findViewById(R.id.switch2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        // для отслеживания в поле EditText километража свыше 25км
        listenerHandler(); // TODO должна быть привязка к тарифу
        // инициализируем хранилище состояний чекбоксов и вводимых данных из активити
        stateBox = new StateBox();
        // инициализируем тарифы
        tarif3_12 = new Tarif3_12(stateBox);
        tarif7_7 = new Tarif7_7(stateBox);
    }


    // выполнение расчета по нажатию кнопки
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonCalc) {
            stateBox.setData(getDataOfView(etWay), getDataOfView(etTime), getDataOfView(boostSpinner));
            // добавление данных в БД
            CalcDb.get(getApplicationContext()).addCalc(tarif3_12);
            calculateAndSetResult();
        } else {
            clsView();
        }
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
                if (getDataOfView(etWay) > 25) {
                    chBoxOblast.setVisibility(View.VISIBLE);
                } else {
                    chBoxOblast.setChecked(false);
                    chBoxOblast.setVisibility(View.GONE);
                }
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

        chBoxBeznal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                stateBox.setChBoxBeznalState(b);
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
        setResults(tvResultOfNewPrice, tarif3_12.getCost());
        setResults(tvResultOfNewPrice1, tarif3_12.getProfit());
        setResults(tvResultOfOldPrice, tarif7_7.getCost());
        setResults(tvResultOfOldPrice1, tarif7_7.getProfit());
    }


    private void setResults(TextView tv, float priceTotal) {
        tv.setText(String.valueOf((int) priceTotal).concat(" руб."));
    }

// Способ скрыть клавиатуру с экрана
//    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(boostSpinner.getWindowToken(),
//    InputMethodManager.HIDE_NOT_ALWAYS);
}
