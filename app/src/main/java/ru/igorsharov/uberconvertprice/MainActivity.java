package ru.igorsharov.uberconvertprice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etTime, etWay;
    private TextView tvResultOfNewPrice, tvResultOfOldPrice;
    private TextView tvResultOfNewPrice1, tvResultOfOldPrice1;
    private Spinner boostSpinner;
    private BoxData oldPriceBox, newPriceBox;
    private CheckBox chbPrigorod, chbBeznal, chbWaiting, chbGarantpik;
    private float time, way, boost;

    private class BoxData {
        private int priceTime, priceWay, priceWayOver25, minPrice;
        private float uberCommission, pathnerCommission;

        BoxData(int priceTime, int priceWay, int priceWayOver25, int minPrice) {
            this.priceTime = priceTime;
            this.priceWayOver25 = priceWayOver25;
            this.priceWay = priceWay;
            this.minPrice = minPrice;
        }

        int getPriceTime() {
            return priceTime;
        }

        int getpriceWayOver25() {
            return priceWayOver25;
        }

        int getPriceWay() {
            return priceWay;
        }

        int getMinPrice() {
            return minPrice;
        }
    }

    private class CalculatePrice {


        //(((((time * bd.getPriceTime() + way * bd.getPriceWay())) + bd.getMinPrice()) * boost)) * uberCommission;

        private float core() {
            return (time * bd.getPriceTime() + way * bd.getPriceWay())) + bd.getMinPrice());
        }
    }

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
        chbPrigorod = (CheckBox) findViewById(R.id.chbPrigorod);
        chbBeznal = (CheckBox) findViewById(R.id.chbBeznal);
        chbWaiting = (CheckBox) findViewById(R.id.chbWaiting);
        chbGarantpik = (CheckBox) findViewById(R.id.chbGarantpik);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        etWayHandler();
        oldPriceBox = new BoxData(7, 7, 7, 50);
        newPriceBox = new BoxData(3, 15, 18, 39);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonCalc) {
            getTimeWayBoost();
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

    private void etWayHandler() {
        etWay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (way() > 25) {
                    chbPrigorod.setVisibility(View.VISIBLE);
//                    chbWaiting.setTextSize(12);
//                    chbGarantpik.setTextSize(12);
                } else {
                    chbPrigorod.setVisibility(View.GONE);
                }
            }
        });
    }

    private void clearViewText(TextView view) {
        view.setText("");
    }

    // получение данных из полей ввода
    private float getFloatOfEditText(EditText et) {
        if (!et.getText().toString().equals("")) {
            return Float.parseFloat(et.getText().toString());
        }
        return 0;
    }

    private void getTimeWayBoost() {
        time = getFloatOfEditText(etTime);
        way = way();
        boost = Float.parseFloat((String) boostSpinner.getSelectedItem());
    }

    private float way() {
        return getFloatOfEditText(etWay);
    }

    private void calculateAndSet() {
        setResults(tvResultOfOldPrice, tvResultOfOldPrice1, calculateOfPrice(oldPriceBox));
        setResults(tvResultOfNewPrice, tvResultOfNewPrice1, calculateOfPrice(newPriceBox));
    }

    private float calculateOfPrice(BoxData bd) {
        return (((((time * bd.getPriceTime() + way * bd.getPriceWay())) + bd.getMinPrice()) * boost)) * uberCommission;
    }

    private void setResults(TextView tv, float priceTotal) {
        tv.setText(String.valueOf((int) priceTotal).concat(" руб."));
    }
}
