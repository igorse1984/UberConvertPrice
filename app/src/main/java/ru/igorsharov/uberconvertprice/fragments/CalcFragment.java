package ru.igorsharov.uberconvertprice.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.igorsharov.uberconvertprice.CalcFragmentInterface;
import ru.igorsharov.uberconvertprice.CustomEditText;
import ru.igorsharov.uberconvertprice.R;
import ru.igorsharov.uberconvertprice.presenters.CalcPresenter;

/**
 * Created by Игорь on 07.03.2018.
 */

public class CalcFragment extends Fragment implements CalcFragmentInterface {

    private TextView tvResultOfNewPrice, tvResultOfOldPrice;
    private TextView tvResultOfNewPrice1, tvResultOfOldPrice1;
    private Spinner boostSpinner;
    private CheckBox chBoxOblast, chBoxGarantpik;
    private Switch switchShowPrice;
    private LinearLayout oldPrice;
    private CustomEditText etParthnerCommission, etTime, etWay;
    final String TAG = "@@@" + getClass().getName();
    private static final String BUNDLE_CONTENT = "bundle_content";
    private CalcPresenter calcPresenter;
    private View view;

    private Unbinder unbinder;
    @BindView(R.id.buttonClearEditText)
    Button btnCls;

    public static CalcFragment newInstance(final String content) {
        final CalcFragment fragment = new CalcFragment();
        final Bundle arguments = new Bundle();
        arguments.putString(BUNDLE_CONTENT, content);
        fragment.setArguments(arguments);
        Log.d("@@@", "CalcFragment newInstance");
        return fragment;
    }

    private String content;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // заготовка инициализации аргументов фрагмента, пока не используется
        if (getArguments() != null && getArguments().containsKey(BUNDLE_CONTENT)) {
            content = getArguments().getString(BUNDLE_CONTENT);
        } else {
            throw new IllegalArgumentException("Must be created through newInstance(...)");
        }
        calcPresenter = new CalcPresenter(this);
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
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.calculator, container, false);

        unbinder = ButterKnife.bind(this, view);

        initView();
        setListeners();

        // отображение дефолтной комиссии из тарифа
        calcPresenter.setDefaultPatnerCommission();

        Log.d(TAG, "onCreateView: ");
        return view;
    }


    // выполнение расчета по нажатию кнопки
    @OnClick(R.id.buttonCalc)
    public void onClickButtonCalc() {

        // сбор данных из View и передача их в презентер
        calcPresenter.getStateBox().setData(
                getFloatOfView(etWay),
                getFloatOfView(etTime),
                getFloatOfView(boostSpinner),
                getFloatOfView(etParthnerCommission));

        calcPresenter.buttonClick(calcPresenter.BUTTON_CALC);
        calcPresenter.printSnackBar(getString(R.string.snackbar_msg_calculate), 0, R.color.colorGreen);
    }

    @OnClick(R.id.buttonClearEditText)
    public void onClickButtonCls() {
        calcPresenter.buttonClick(calcPresenter.BUTTON_CLC);
        calcPresenter.printSnackBar(getString(R.string.snackbar_msg_cls), 0, R.color.colorAccent);
    }

    private void initView() {
        oldPrice = view.findViewById(R.id.oldPrice);
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
                // добавление данных в БД
                calcPresenter.addToDb(getActivity());
                calcPresenter.printSnackBar(getString(R.string.snackbar_msg_add_to_llist), Color.BLACK, R.color.colorYellow);
            }
        });
    }


    private void setListeners() {
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
                // отслеживание превышения километража по тарифу
                calcPresenter.changeChBoxOblastVisibility(getFloatOfView(etWay));

                // отслеживание пустотности EditText
                checkEmptyEditText();
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
                checkEmptyEditText();
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

        // слушаем чекбоксы
        chBoxOblast.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                calcPresenter.getStateBox().setChBoxOblastState(b);
            }
        });

        chBoxGarantpik.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                calcPresenter.getStateBox().setChBoxGarantpikState(b);
            }
        });

        // переключатель видимости старого тарифа
        switchShowPrice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                // TODO убрать доп поля, нужно менять отображение в пределах одного поля
                oldPrice.setVisibility(b ? View.VISIBLE : View.GONE);
            }
        });

    }

    private void checkEmptyEditText() {
        calcPresenter.checkEmptyView(String.valueOf(etWay.getText()), String.valueOf(etTime.getText()));
    }

    // универсальный метод получения данных из полей ввода
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

    // отображение результатов рассчета
    @Override
    public void setResults(float costOne, float profitOne, float costTwo, float profitTwo) {
        setResult(tvResultOfNewPrice, costTwo);
        setResult(tvResultOfNewPrice1, profitTwo);
        setResult(tvResultOfOldPrice, costOne);
        setResult(tvResultOfOldPrice1, profitOne);
    }


    private void setResult(TextView tv, float priceTotal) {
        tv.setText(String.valueOf((int) priceTotal).concat(getString(R.string.rub_name)));
    }

    // отображение чекбокса увеличения цены превышенного километража по тарифу
    @Override
    public void setVisibilityChBoxOblast(boolean visibility) {
        if (visibility) {
            chBoxOblast.setVisibility(View.VISIBLE);
        } else {
            chBoxOblast.setChecked(false);
            chBoxOblast.setVisibility(View.GONE);
        }
    }

    @Override
    public void clsView() {
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

    @Override
    public void printSnackbar(String msg, int textColor, int backgroundColor) {
        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();

        //TODO не нравится способ получения контекста, да и под вопросом способ получения цвета из ресурсов
        if (textColor != 0) {
            ((TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text)).setTextColor(textColor);
        }
        snackbarView.setBackgroundColor(ContextCompat.getColor(getActivity(), backgroundColor));

        snackbar.show();
    }

    @Override
    public void setVisibilityBtnCls(boolean flag) {
        if (flag) {
            btnCls.setVisibility(View.GONE);
        } else {
            btnCls.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setDefaultPartnerCommission(String partnerCommission) {
        etParthnerCommission.setText(partnerCommission);
    }


}