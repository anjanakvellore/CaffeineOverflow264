package com.example.caffeineoverflow264.repository.service.api;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "caffeineoverflow";
    private static final int DB_VERSION = 1;

    private static DatabaseHelper dbHelper;

    public DatabaseHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    public static void createDatabaseHelper(Context context) {
        dbHelper = new DatabaseHelper(context);
        updateDatabase(dbHelper.getWritableDatabase());
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        System.out.println("DB: onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,int newVersion){
        System.out.println("DB: onUpgrade");
    }
    /*
    //added dummy data in coffee and coffesize tables
    //https://www.homegrounds.co/what-coffee-has-the-most-caffeine/
    //https://us.keepcup.com/size-guide

     */
    public static void updateDatabase(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS COFFEE");
        db.execSQL("DROP TABLE IF EXISTS COFFEESIZE");
        db.execSQL("DROP TABLE IF EXISTS USER");
        db.execSQL("DROP TABLE IF EXISTS LOG");
        db.execSQL("CREATE TABLE COFFEE(_id INTEGER PRIMARY KEY AUTOINCREMENT, COFFEENAME TEXT,CAFFEINEAMOUNT REAL)");
        db.execSQL("CREATE TABLE COFFEESIZE(_id INTEGER PRIMARY KEY AUTOINCREMENT, SIZENAME TEXT,QUANTITY REAL)");
        db.execSQL("CREATE TABLE USER(_id INTEGER PRIMARY KEY AUTOINCREMENT, HEIGHT REAL, WEIGHT REAL,AGE INTEGER)");
        db.execSQL("CREATE TABLE LOG(_id INTEGER PRIMARY KEY AUTOINCREMENT, DATE TEXT, COFFEEID INTEGER,QUANTITY INTEGER)");
        insertCoffeeItem( db,"Decaf Coffee (instant coffee)",.38); //mg per oz
        insertCoffeeItem( db,"Decaf Coffee (brewed)",.5);
        insertCoffeeItem( db,"Drip Coffee",15);
        insertCoffeeItem( db,"Espresso",51.34);
        insertCoffeeItem( db,"Mocha",11);
        insertCoffeeItem( db,"Latte",9.4);
    }

    public static void insertCoffeeItem(SQLiteDatabase db, String coffeeName, double caffeineAmount) {
        ContentValues coffeeItem = new ContentValues();
        coffeeItem.put("COFFEENAME",coffeeName);
        coffeeItem.put("CAFFEINEAMOUNT",caffeineAmount);
        db.insert("COFFEE",null,coffeeItem);
    }

    public static void insertCoffeeItem(String coffeeName,double caffeineAmount){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues coffeeItem = new ContentValues();
        coffeeItem.put("COFFEENAME",coffeeName);
        coffeeItem.put("CAFFEINEAMOUNT",caffeineAmount);
        db.insert("COFFEE",null,coffeeItem);

    }

    public static void insertCoffeeSizeItem(String sizeName,double quantity) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues coffeeSizeItem = new ContentValues();
        coffeeSizeItem.put("SIZENAME", sizeName);
        coffeeSizeItem.put("QUANTITY", quantity);
        db.insert("COFFEESIZE", null, coffeeSizeItem);
    }

    public static void insertUser(double height, double weight, int age){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues user = new ContentValues();
        user.put("HEIGHT",height);
        user.put("WEIGHT",weight);
        user.put("AGE",age);
        db.insert("USER",null,user);
    }

    //insert into Log table
    public static void insertIntoLog(String date,int coffeeId,int coffeeQuantity){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues logItems = new ContentValues();
        logItems.put("DATE",date);
        logItems.put("COFFEEID",coffeeId);
        logItems.put("QUANTITY",coffeeQuantity);
        System.out.println("InsertIntoLog " + date + " : " + coffeeId + " : " + coffeeQuantity);
        db.insert("LOG",null,logItems);

    }

    //select queries
    public static Cursor getCoffeeList(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("COFFEE",
                new String[]{"_id","COFFEENAME","CAFFEINEAMOUNT"},
                null,
                null,
                null,null,null);
        return cursor;

    }

    public static String getCoffeeNameById(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("COFFEE",
                new String[]{"_id","COFFEENAME","CAFFEINEAMOUNT"},
                "_id = ?",
                new String[] { Integer.toString(id) },
                null,null,null);
        cursor.moveToFirst();
        return cursor.getString(1);
    }

    public static int getCaffeineAmount(int coffeeId) {
        System.out.println(" getCaffeineAmount coffeeId: " + coffeeId);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("COFFEE",
                new String[]{"_id","COFFEENAME","CAFFEINEAMOUNT"},
                "_id = ?",
                new String[] { Integer.toString(coffeeId) },
                null,null,null);
        cursor.moveToFirst();
        return cursor.getInt(2);
    }

    public static int getCoffeeIdByName(String coffeeName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("COFFEE",
                new String[]{"_id","COFFEENAME","CAFFEINEAMOUNT"},
                "COFFEENAME = ?",
                new String[] { coffeeName },
                null,null,null);
        cursor.moveToFirst();
        return cursor.getInt(0 );
    }

    public static Cursor getCoffeeSizeList(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("COFFEESIZE",
                new String[]{"_id","SIZENAME","QUANTITY"},
                null,
                null,
                null,null,null);
        return cursor;

    }

    public static Cursor getUserDetails(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("USER",
                new String[]{"_id","HEIGHT","WEIGHT","AGE"},
                null,
                null,
                null,null,null);
        return cursor;

    }

    public static Cursor getLogDetails() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("LOG",
                new String[]{"_id", "DATE", "COFFEEID", "QUANTITY"},
                null,
                null,
                null, null, null);
        return cursor;
    }

    public static Cursor getLogDetailsOnOneDay(String date) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("LOG",
                new String[]{"_id", "DATE", "COFFEEID", "QUANTITY"},
                "DATE = ?",
                new String[]{date},
                null, null, null);
        return cursor;
    }


}



