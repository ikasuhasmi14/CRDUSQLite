package com.example.asus.latihansqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BuatDatabase extends SQLiteOpenHelper {

    //Deklarasi Pembuatan Database dan Tabel
    public static String database_name = "SQL15MI2";
    public static String tabel_name = "Mahasiswa15MI2";

    public static int versi_database = 1;

    public static String col1 = "nim";
    public static String col2 = "nama";
    public static String col3 = "alamat";
    public static String col4 = "nohp";
    public static String col5 = "jenkel";
    public static String col6 = "hobby";

    public BuatDatabase(Context context){
        super(context, database_name, null, versi_database);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    //Event yang digunakan untuk MengCreate Table
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ tabel_name +" (nim integer PRIMARY KEY, " +
                "nama varchar(25), " +
                "alamat varchar(50)," +
                "no_hp varchar(15), " +
                "jenkel varchar(10), " +
                "hobby varchar(25))");

    }

    //Event yang digunakan untuk melakukan pengecekan jika database telah tersedia
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+ tabel_name);
        onCreate(db);
    }

    //Fungsi atau Function untuk menampung InsertData
    public boolean insertDatamhs(String nim, String nama, String alamat, String nohp, String jenkel, String hobi){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nim", nim);
        cv.put("nama", nama);
        cv.put("alamat", alamat);
        cv.put("no_hp", nohp);
        cv.put("jenkel", jenkel);
        cv.put("hobby", hobi);

        long hasil = db.insert(tabel_name, null, cv);

        if (hasil == 1){
            return true;
        }else{
            return false;
        }
    }

    //Prosedure untuk mencari data berdasarkan id dan menampilkan pada masing-masing EditText
    public Cursor selectDatamhs(String cari_nim){
        SQLiteDatabase db = this.getWritableDatabase();

        String [] allkol = {"nim", "nama", "alamat", "no_hp","jenkel", "hobby"};

        Cursor cursor = db.query(tabel_name, allkol,"nim=" +cari_nim, null, null, null, null);
        return cursor;
    }

    //Prosedure HapusData
    public boolean hapusDatamhs(String cari_nim){
        SQLiteDatabase db = this.getWritableDatabase();
        String val = "nim="+cari_nim;
        long hasil = db.delete(tabel_name, val, null);

        if (hasil == 1){
            return true;
        }else{
            return false;
        }
    }

    //Prosedure untuk mempilkan keseluruhan data
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ tabel_name, null);
        return res;
    }

    //prosedure untuk memapilkan data berdasarkan id pada sebuah Activity ListData
    public Cursor getDataByID(String cari_nim){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ tabel_name + " where nim=" + cari_nim , null);
        return res;
    }

}
