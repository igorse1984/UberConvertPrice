package ru.igorsharov.uberconvertprice.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import ru.igorsharov.uberconvertprice.calculation.Calculator;
import ru.igorsharov.uberconvertprice.database.CalcDbSchema.CalcTable;

/**
 * Created by IgorSE on 15.02.2018.
 */

public class CalcDb {

    private static CalcDb calcDb;
    private Context context;
    private SQLiteDatabase sqLiteDatabase;

    public static CalcDb get(Context context) {
        if (calcDb == null) {
            calcDb = new CalcDb(context);
        }
        return calcDb;
    }


    private CalcDb(Context context) {
        this.context = context.getApplicationContext();
        sqLiteDatabase = new CalcDbHelper(context).getWritableDatabase();
    }

    public void addCalc(Calculator calculator) {
        ContentValues values = getContentValues(calculator);
        sqLiteDatabase.insert(CalcTable.NAME, null, values);
    }

    private static ContentValues getContentValues(Calculator calculator) {
        ContentValues values = new ContentValues();
        values.put(CalcTable.Columns.PRICE_NAME, calculator.getTitle());
        values.put(CalcTable.Columns.COST, calculator.getCost());
        values.put(CalcTable.Columns.PROFIT, calculator.getProfit());
        values.put(CalcTable.Columns.RATIO, calculator.getRatio());
        return values;
    }

}
