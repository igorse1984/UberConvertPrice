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
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etTime, etWay, etWait;
    private TextView tvResultOfNewPrice, tvResultOfOldPrice;
    private TextView tvResultOfNewPrice1, tvResultOfOldPrice1;
    private Spinner boostSpinner;
    private DataBox oldPriceBox, newPriceBox;
    private CheckBox chBoxPrigorod, chBoxBeznal, chBoxWaiting, chBoxGarantpik;


    private void initView() {
        findViewById(R.id.buttonCalc).setOnClickListener(this);
        findViewById(R.id.buttonClearEditText).setOnClickListener(this);
        etTime = (EditText) findViewById(R.id.editTextTime);
        etWay = (EditText) findViewById(R.id.editTextWay);
        etWait = (EditText) findViewById(R.id.textViewWait);
        tvResultOfNewPrice = (TextView) findViewById(R.id.textViewNewPrice);
        tvResultOfOldPrice = (TextView) findViewById(R.id.textViewOldPrice);
        tvResultOfNewPrice1 = (TextView) findViewById(R.id.textViewNewPrice1);
        tvResultOfOldPrice1 = (TextView) findViewById(R.id.textViewOldPrice1);
        boostSpinner = (Spinner) findViewById(R.id.boostSpinner);
        chBoxPrigorod = (CheckBox) findViewById(R.id.chbPrigorod);
        chBoxBeznal = (CheckBox) findViewById(R.id.chbBeznal);
        chBoxWaiting = (CheckBox) findViewById(R.id.chbWaiting);
        chBoxGarantpik = (CheckBox) findViewById(R.id.chbGarantpik);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        // для отслеживания в поле EditText километража свыше 25км
        listenerHandler();
        oldPriceBox = new DataBox(7, 7, 50);
        newPriceBox = new DataBox(3, 12, 18, 39);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonCalc) {
            calculateAndSetResult();
        } else {
            clear();
        }
    }

    void clear() {
        // очистка полей ввода
        clearViewText(etTime);
        clearViewText(etWay);
        clearViewText(etWait);
        clearViewText(tvResultOfOldPrice);
        clearViewText(tvResultOfNewPrice);
        clearViewText(tvResultOfOldPrice1);
        clearViewText(tvResultOfNewPrice1);
        boostSpinner.setSelection(0);
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
                if (getFloatOfView(etWay) > 25) {
                    chBoxPrigorod.setVisibility(View.VISIBLE);
                } else {
                    chBoxPrigorod.setChecked(false);
                    chBoxPrigorod.setVisibility(View.GONE);
                }
                chBoxTextSizeHandler();
            }
        });

        // слушаем spinner
        boostSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    chBoxGarantpik.setVisibility(View.VISIBLE);
                    calculateAndSetResult();
                } else {
                    chBoxGarantpik.setChecked(false);
                    chBoxGarantpik.setVisibility(View.GONE);
                }
                chBoxTextSizeHandler();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // слушаем чекбоксы
        chBoxPrigorod.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                newPriceBox.setWay25(b);
                calculateAndSetResult();
            }
        });

        chBoxBeznal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                oldPriceBox.setPartnerCommissionStatus(b);
                newPriceBox.setPartnerCommissionStatus(b);
                calculateAndSetResult();
            }
        });

        chBoxGarantpik.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                calculateAndSetResult();
            }
        });

        chBoxWaiting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                etWait.setVisibility(b ? View.VISIBLE : View.GONE);
                if (etWait.getVisibility() == View.GONE) {
                    etWait.setText("");
                    calculateAndSetResult();
                }
            }
        });

    }

    void chBoxTextSizeHandler() {
        if (chBoxGarantpik.getVisibility() == View.VISIBLE && chBoxPrigorod.getVisibility() == View.VISIBLE) {
            // убрать хардкор из размеров, решить задачу с масштабированием
            chBoxWaiting.setTextSize(12);
            chBoxGarantpik.setTextSize(12);
        }
        if (chBoxGarantpik.getVisibility() == View.GONE && chBoxPrigorod.getVisibility() == View.GONE) {
            chBoxWaiting.setTextSize(14);
            chBoxGarantpik.setTextSize(14);
        }
    }

    private void clearViewText(TextView view) {
        view.setText("");
    }

    // получение данных из полей ввода
    private float getFloatOfView(View v) {
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

    private void getTimeAndWayAndBoost() {
        DataBox.setTime(getFloatOfView(etTime));
        DataBox.setTimeWaiting(getFloatOfView(etWait));
        DataBox.setWay(getFloatOfView(etWay));
        DataBox.setBoost(getFloatOfView(boostSpinner));
    }

    private void calculateAndSetResult() {
        getTimeAndWayAndBoost();
        setResults(tvResultOfOldPrice, oldPriceBox.getCalculate().baseWithoutCommission());
        setResults(tvResultOfNewPrice, newPriceBox.getCalculate().baseWithoutCommission());

        float oldPrice1 = chBoxGarantpik.isChecked() ? oldPriceBox.getCalculate().gBoostWithCommission() : oldPriceBox.getCalculate().baseWithCommission();
        float newPrice1 = chBoxGarantpik.isChecked() ? newPriceBox.getCalculate().gBoostWithCommission() : newPriceBox.getCalculate().baseWithCommission();
        setResults(tvResultOfOldPrice1, oldPrice1);
        setResults(tvResultOfNewPrice1, newPrice1);
    }


    private void setResults(TextView tv, float priceTotal) {
        tv.setText(String.valueOf((int) priceTotal).concat(" руб."));
    }

// Скрыть клавиатуру с экрана
//    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(boostSpinner.getWindowToken(),
//    InputMethodManager.HIDE_NOT_ALWAYS);
}
