package ru.igorsharov.uberconvertprice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private EditText etTime, etWay;
    private TextView tvResultOfNewPrice, tvResultOfOldPrice;
    private Editable strTime, strWay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculate();
            }
        });

    }

    private void initView() {
        button = (Button) findViewById(R.id.buttonCalc);
        etTime = (EditText) findViewById(R.id.editTextTime);
        etWay = (EditText) findViewById(R.id.editTextWay);
        tvResultOfNewPrice = (TextView) findViewById(R.id.textViewNewPrice);
        tvResultOfOldPrice = (TextView) findViewById(R.id.textViewOldPrice);
    }

    private void calculate() {

        if (etTime.getText().toString().equals("")) {
            //если пусто
        } else {
            float time = Float.parseFloat(etTime.getText().toString());
            float way = Float.parseFloat(etWay.getText().toString());
            String str = String.valueOf(time * 3 + way * 12 + 39) + " руб.";
            tvResultOfNewPrice.setText(str);
            str = String.valueOf(((time + way) * 7) + 50) + " руб.";
            tvResultOfOldPrice.setText(str);
        }


    }


}
