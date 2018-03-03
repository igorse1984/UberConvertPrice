package ru.igorsharov.uberconvertprice;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Игорь on 03.03.2018.
 */


public class CustomEditText extends android.support.v7.widget.AppCompatEditText {


    public CustomEditText(Context context) {
        super(context);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    // переопределяем для того, чтобы курсор невозможно было переместить
    public void onSelectionChanged(int start, int end) {

        CharSequence text = getText();
        if (text != null) {
            if (start != text.length() || end != text.length()) {
                setSelection(text.length(), text.length());
                return;
            }
        }

        super.onSelectionChanged(start, end);
    }
}
