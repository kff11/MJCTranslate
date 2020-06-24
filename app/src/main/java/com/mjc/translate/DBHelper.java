package com.mjc.translate;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, "TransDB", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String transSQL = "create table latestTrans (" +
                "id integer primary key autoincrement," +
                "input," +
                "output," +
                "language," +
                "toLanguage)";
        db.execSQL(transSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion == DATABASE_VERSION) {
            db.execSQL("drop table latestTrans");
            onCreate(db);
        }
    }

    public static void addLatestTrans(String input, String output, String language, String toLanguage, Context context) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("INSERT INTO latestTrans (input, output, language, toLanguage) VALUES (?, ?, ?, ?)", new String[]{input, output, language, toLanguage});
        db.close();
    }
}
