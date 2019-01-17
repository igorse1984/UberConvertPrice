package ru.igorsharov.uberconvertprice.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.igorsharov.uberconvertprice.CustomEditText;
import ru.igorsharov.uberconvertprice.R;
import ru.igorsharov.uberconvertprice.presenters.CalcPresenter;

/**
 * Created by Игорь on 07.03.2018.
 */

public class CalcFragment extends MvpAppCompatFragment implements CalcFragmentInterface {

    final int DESC_HIGH_PRICE = R.id.tvDescHighPrice;

    @BindViews({
            DESC_HIGH_PRICE,
            R.id.tvDescGarantPeakSurcharge,
            R.id.tvDescLineClientCost,
    })
    List<TextView> tvDescsList;

    @BindViews({
            R.id.tvResultCost,
            R.id.tvResultHighPrice,
            R.id.tvResultClientCost,
            R.id.tvResultCommissionUber,
            R.id.tvResultGarantPeakSurcharge,
            R.id.tvResultCommissionPartner,
            R.id.tvResultProfit})
    List<TextView> tvResultsList;


    private Spinner boostSpinner;
    private CheckBox chBoxRegion, chBoxWarrantyPeak;
    private Switch switchShowPrice;
    private CustomEditText etPartnerCommission, etTime, etDistance;
    final String TAG = "@@@" + getClass().getName();
    private static final String BUNDLE_CONTENT = "bundle_content";

    @InjectPresenter
    CalcPresenter calcPresenter;
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
        view = inflater.inflate(R.layout.fragment_calculator, container, false);

        // для фрагментов необходимо инстанцировать унбиндер,
        // чтобы увязать его с жизненным циклом фрагмента
        unbinder = ButterKnife.bind(this, view);

        initView();
        setListeners();

        // отображение дефолтной комиссии из тарифа
        calcPresenter.setDefaultPatnerCommission();

        Log.d(TAG, "onCreateView: ");
        return view;
    }

    public void getRideData() {
        calcPresenter.setRideDataOfView(
                getFloatOfView(etDistance),
                getFloatOfView(etTime),
                getFloatOfView(boostSpinner),
                getFloatOfView(etPartnerCommission));

//        return "";
    }

    public void getChBoxWarrantyPeakState() {
        calcPresenter.setChBoxWarrantyPeakState(
                chBoxWarrantyPeak.isChecked());
    }

    public void getChBoxRegionState() {
        calcPresenter.setChBoxRegionState(
                chBoxRegion.isChecked());
    }

    // выполнение расчета по нажатию кнопки
    @OnClick(R.id.buttonCalc)
    public void onClickButtonCalc() {

        calcPresenter.buttonClick(calcPresenter.BUTTON_CALC);

        //TODO Должен решать презентер вывод сообщений
        calcPresenter.printSnackBar(getString(R.string.snackbar_msg_calculate), 0, R.color.colorGreen);
    }

    @OnClick(R.id.buttonClearEditText)
    public void onClickButtonCls() {
        calcPresenter.buttonClick(calcPresenter.BUTTON_CLC);
        //TODO так же как с посчитано, в презентер
        calcPresenter.printSnackBar(getString(R.string.snackbar_msg_cls), 0, R.color.colorAccent);
    }

    private void initView() {
        etTime = view.findViewById(R.id.editTextTime);
        etDistance = view.findViewById(R.id.editTextWay);
        boostSpinner = view.findViewById(R.id.boostSpinner);
        chBoxRegion = view.findViewById(R.id.chbPrigorod);
        chBoxWarrantyPeak = view.findViewById(R.id.chbGarantpik);
        switchShowPrice = view.findViewById(R.id.switch2);
        btnCls = view.findViewById(R.id.buttonClearEditText);

        etPartnerCommission = view.findViewById(R.id.editTextPrthnerCommisson);
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
        etDistance.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // отслеживание превышения километража по тарифу
                calcPresenter.changeChBoxOblastVisibility(getFloatOfView(etDistance));

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
                    chBoxWarrantyPeak.setVisibility(View.VISIBLE);

                } else {
                    chBoxWarrantyPeak.setChecked(false);
                    chBoxWarrantyPeak.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        // слушаем switch
        switchShowPrice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            }
        });

    }

    private void checkEmptyEditText() {
        calcPresenter.checkEmptyView(String.valueOf(etDistance.getText()), String.valueOf(etTime.getText()));
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


    private void setDataInView(TextView tv, String data) {
        tv.setText(data.concat(" ").concat(getString(R.string.rub_name)));
    }

    private void setVisibilityView(TextView tv, boolean visible) {
        tv.setVisibility(visibility(visible));
    }


    // отображение результатов рассчета
    @Override
    public void displayResults(final String[] calculationData) {
        ButterKnife.Action setData = new ButterKnife.Action<TextView>() {
            @Override
            public void apply(@NonNull TextView tv, int index) {
                setDataInView(tv, calculationData[index]);
//                setVisibilityView(tv, visibilityData[index]);
            }
        };
        ButterKnife.apply(tvResultsList, setData);
    }


    @Override
    public void clsView() {
        // очистка полей ввода
        clearViewText(etTime);
        clearViewText(etDistance);

        etTime.requestFocus();
        boostSpinner.setSelection(0);

        // очистка всех TextView участвующих в отображении данных калькуляции
        for (TextView tv : tvResultsList) {
            clearViewText(tv);
        }
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
    public void setDefaultPartnerCommission(String partnerCommission) {
        etPartnerCommission.setText(partnerCommission);
    }

    @Override
    public void setTextInTextView(int tvName, String str) {
        tvDescsList.get(tvName).setText(str);
    }

    /**
     * Установка различных visibility
     */

    // отображение чекбокса увеличения цены превышенного километража по тарифу
    @Override
    public void setVisibilityChBoxOblast(boolean visibility) {
        if (visibility) {
            chBoxRegion.setVisibility(View.VISIBLE);
        } else {
            chBoxRegion.setChecked(false);
            chBoxRegion.setVisibility(View.GONE);
        }
    }


    private int visibility(boolean flag) {
        return flag ? View.VISIBLE : View.GONE;
    }


    public void setVisibilityResult(int indexResultTv, boolean flag) {
        tvResultsList.get(indexResultTv).setVisibility(visibility(flag));
    }

    public void setVisibilityDesc(int indexDescTv, boolean flag) {
        tvDescsList.get(indexDescTv).setVisibility(visibility(flag));
    }


    @Override
    public void setVisibilityBtnCls(boolean flag) {
        btnCls.setVisibility(visibility(!flag));
    }

}