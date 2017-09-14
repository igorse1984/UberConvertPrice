package ru.igorsharov.uberconvertprice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etTime, etWay;
    private TextView tvResultOfNewPrice, tvResultOfOldPrice;
    private TextView tvResultOfNewPrice1, tvResultOfOldPrice1;
    private Spinner boostSpinner;
    private DataBox oldPriceBox, newPriceBox;
    private HashMap<String, CheckBox> chBox;
    private final String PRIGOROD = "Prigorod";
    private final String BEZNAL = "Beznal";
    private final String WAITING = "Waiting";
    private final String GARANTPIK = "Garantpik";


    private void initView() {
        findViewById(R.id.buttonCalc).setOnClickListener(this);
        findViewById(R.id.buttonClearEditText).setOnClickListener(this);
        etTime = (EditText) findViewById(R.id.editTextTime);
        etWay = (EditText) findViewById(R.id.editTextWay);
        tvResultOfNewPrice = (TextView) findViewById(R.id.textViewNewPrice);
        tvResultOfOldPrice = (TextView) findViewById(R.id.textViewOldPrice);
        tvResultOfNewPrice1 = (TextView) findViewById(R.id.textViewNewPrice1);
        tvResultOfOldPrice1 = (TextView) findViewById(R.id.textViewOldPrice1);
        boostSpinner = (Spinner) findViewById(R.id.boostSpinner);
        chBox.put(PRIGOROD, (CheckBox) findViewById(R.id.chbPrigorod));
        chBox.put(BEZNAL, (CheckBox) findViewById(R.id.chbBeznal));
        chBox.put(WAITING, (CheckBox) findViewById(R.id.chbWaiting));
        chBox.put(GARANTPIK, (CheckBox) findViewById(R.id.chbGarantpik));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        editTextWayHandler();
        oldPriceBox = new DataBox(7, 7, 50);
        newPriceBox = new DataBox(3, 15, 18, 39);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonCalc) {
            getTimeAndWayAndBoost();
            calculateAndSet();
        } else {
            // очистка полей ввода
            clearViewText(etTime);
            clearViewText(etWay);
            clearViewText(tvResultOfOldPrice);
            clearViewText(tvResultOfNewPrice);
            clearViewText(tvResultOfOldPrice1);
            clearViewText(tvResultOfNewPrice1);
            boostSpinner.setSelection(0);
        }
    }

    private void editTextWayHandler() {
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
                    chBox.get(PRIGOROD).setVisibility(View.VISIBLE);
//                    chbWaiting.setTextSize(12);
//                    chbGarantpik.setTextSize(12);
                } else {
                    chBox.get(PRIGOROD).setVisibility(View.GONE);
                }
            }
        });
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
        DataBox.setWay(getFloatOfView(etWay));
        DataBox.setBoost(getFloatOfView(boostSpinner));
    }

    private void calculateAndSet() {
        Calculate oldCalculatePrice = new Calculate(oldPriceBox, chBox);
        setResults(tvResultOfOldPrice, oldCalculatePrice.getResultPrice());
        setResults(tvResultOfOldPrice1, oldCalculatePrice.getResultPriceAfterCommission());
    }

    private void setResults(TextView tv, float priceTotal) {
        tv.setText(String.valueOf((int) priceTotal).concat(" руб."));
    }
}
