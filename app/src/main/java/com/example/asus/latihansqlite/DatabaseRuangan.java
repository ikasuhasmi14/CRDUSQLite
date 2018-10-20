package com.example.asus.latihansqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseRuangan extends SQLiteOpenHelper {

    //Deklarasi Pembuatan Database dan Tabel
    public static String database_name = "Ruangan";
    public static String tabel_name = "DataRuangan";

    public static int versi_database = 1;

    public static String col1 = "kode_ruangan";
    public static String col2 = "nama_ruangan";
    public static String col3 = "kapasitas_ruangan";
    public static String col4 = "status_ruangan";

    public DatabaseRuangan(Context context){
        super(context, database_name, null, versi_database);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    //Event yang digunakan untuk MengCreate Table
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ tabel_name +" (kode_ruangan integer PRIMARY KEY, " +
                "nama_ruangan varchar(20), " +
                "kapasitas_ruangan integer(20)," +
                "status_ruangan varchar(15))");

    }

    //Event yang digunakan untuk melakukan pengecekan jika database telah tersedia
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+ tabel_name);
        onCreate(db);
    }

    //Fungsi atau Function untuk menampung InsertData
    public boolean insertDataruangan(String kode_ruangan, String nama_ruangan, String kapasitas_ruangan, String status_ruangan){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("kode_ruangan", kode_ruangan);
        cv.put("nama_ruangan", nama_ruangan);
        cv.put("kapasitas_ruangan", kapasitas_ruangan);
        cv.put("status_ruangan", status_ruangan );

        long hasil = db.insert(tabel_name, null, cv);

        if (hasil == 1){
            return true;
        }else{
            return false;
        }
    }

    //Prosedure untuk mencari data berdasarkan id dan menampilkan pada masing-masing EditText
    public Cursor selectDataruangan(String cari_kode_ruangan){
        SQLiteDatabase db = this.getWritableDatabase();

        String [] allkol = {"kode_ruangan", "nama_ruangan", "kapasitas_ruangan", "status_ruangan"};

        Cursor cursor = db.query(tabel_name, allkol,"kode_ruangan=" +cari_kode_ruangan, null, null, null, null);
        return cursor;
    }

    //Prosedure HapusData
    public boolean hapusDataruangan(String cari_kode_ruangan){
        SQLiteDatabase db = this.getWritableDatabase();
        String val = "kode_ruangan="+cari_kode_ruangan;
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
    public Cursor getDataByID(String cari_kode){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ tabel_name + " where kode_ruangan=" + cari_kode , null);
        return res;
    }

}
