package ru.igorsharov.uberconvertprice.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.lang.ref.WeakReference;

import ru.igorsharov.uberconvertprice.model.UberCalculator;
import ru.igorsharov.uberconvertprice.database.CalcDbSchema.CalcTable;

/**
 * Created by IgorSE on 15.02.2018.
 */

public class CalcDb {

    private static CalcDb calcDb;
    private WeakReference weakReference; // слабая ссылка для хранения контекста
    private SQLiteDatabase sqLiteDatabase;

    public static CalcDb get(Context context) {
        if (calcDb == null) {
            calcDb = new CalcDb(context);
        }
        return calcDb;
    }

    private CalcDb(Context context) {
        weakReference = new WeakReference<>(context.getApplicationContext());
        sqLiteDatabase = new CalcDbHelper(context).getWritableDatabase();
    }

    public void addCalc(UberCalculator uberCalculator) {
        ContentValues values = getContentValues(uberCalculator);
        sqLiteDatabase.insert(CalcTable.NAME, null, values);
    }

    private static ContentValues getContentValues(UberCalculator uberCalculator) {
        ContentValues values = new ContentValues();
//        values.put(CalcTable.Columns.PRICE_NAME, uberCalculator.getTitle());
//        values.put(CalcTable.Columns.COST, uberCalculator.getPreCost());
//        values.put(CalcTable.Columns.PROFIT, uberCalculator.getProfit());
//        values.put(CalcTable.Columns.RATIO, uberCalculator.getRatio());
        return values;
    }

}
