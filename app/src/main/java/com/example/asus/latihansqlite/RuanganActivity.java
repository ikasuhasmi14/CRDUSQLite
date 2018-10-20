package com.example.asus.latihansqlite;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RuanganActivity extends AppCompatActivity {
    //Deklarasi Komponen
    private EditText koderuangan, namaruangan, kapasistasruangan;
    private RadioGroup groupstatusruangan;
    private RadioButton statusruangan, rbbagus, rbrusak;

    private Button btnCari, btnSimpan, btnHapus, btnUbah, btnTutup, btnBersih, btnLihat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruangan);

        DatabaseRuangan dc = new DatabaseRuangan(this);

        koderuangan = (EditText)findViewById(R.id.editTextKode);
        namaruangan = (EditText)findViewById(R.id.editTextNamaruangan);
        kapasistasruangan = (EditText)findViewById(R.id.editTextKapasitas);

        groupstatusruangan = (RadioGroup)findViewById(R.id.RadioButtonStatus);

        btnSimpan = (Button)findViewById(R.id.buttonSimpan);
        btnCari = (Button)findViewById(R.id.buttonCari);
        btnHapus = (Button)findViewById(R.id.buttonHapus);
        btnUbah = (Button)findViewById(R.id.buttonUbah);
        btnTutup = (Button)findViewById(R.id.buttonTutup);
        btnBersih = (Button)findViewById(R.id.buttonBersih);
        btnLihat = (Button)findViewById(R.id.buttonLihat);

        aksi();
    }

    //procedure Bersihdata
    public void bersihdata(){
        koderuangan.setText("");
        namaruangan.setText("");
        kapasistasruangan.setText("");
        rbbagus = (RadioButton)findViewById(R.id.radioButtonBagus);
        rbbagus.setChecked(true);
        btnLihat.setFocusable(true);
    }

    public void aksi(){
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sr = "";
                DatabaseRuangan db1 = new DatabaseRuangan(getApplication());

                //pengambilan data perdasarkan pilihan radio button
                int pilih = groupstatusruangan.getCheckedRadioButtonId();
                statusruangan = (RadioButton)findViewById(pilih);
                sr = statusruangan.getText().toString();

                boolean hasil1 = db1.insertDataruangan(koderuangan.getText().toString(),
                        namaruangan.getText().toString(),
                        kapasistasruangan.getText().toString(),
                        sr
                );
                if (hasil1){
                    bersihdata();
                    Toast.makeText(RuanganActivity.this, "Data gagal disimpan !!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(RuanganActivity.this, "Data berhasil disimpan !!" , Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sr = "";
                DatabaseRuangan dbl = new DatabaseRuangan(getApplication());
                String cari_kode_ruangan = koderuangan.getText().toString();

                if (cari_kode_ruangan.isEmpty()) {
                    Toast.makeText(RuanganActivity.this, "Silahkan Tuliskan Kode Ruangan yang di cari !!", Toast.LENGTH_SHORT).show();
                    koderuangan.setFocusable(true);
                }else{
                    Cursor cr = dbl.selectDataruangan(cari_kode_ruangan);
                    Boolean hasil = cr.moveToFirst();

                    if (hasil){
                        koderuangan.setText(cr.getString(0));
                        namaruangan.setText(cr.getString(1));
                        kapasistasruangan.setText(cr.getString(2));


                        //Mengisi Radio Button Jenis Kelamin
                        sr = (cr.getString(3));
                        rbbagus = (RadioButton)findViewById(R.id.radioButtonBagus);
                        rbrusak = (RadioButton)findViewById(R.id.radioButtonRusak);

                        if (sr.equals("Bagus")){
                            rbbagus.setChecked(true);
                        }else{
                            rbrusak.setChecked(true);
                        }

                        Toast.makeText(RuanganActivity.this, "Data ditemukan !!" , Toast.LENGTH_SHORT).show();

                    }
                    else{
                        bersihdata();
                        Toast.makeText(RuanganActivity.this, "Maaf data tidak ditemukan !!", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseRuangan db = new DatabaseRuangan(getApplicationContext());
                String cari_kode_ruangan = koderuangan.getText().toString();
                if (cari_kode_ruangan.isEmpty()) {
                    Toast.makeText(RuanganActivity.this, "Silahkan Tuliskan NIM yang akan dihapus !!", Toast.LENGTH_SHORT).show();
                    koderuangan.setFocusable(true);
                } else {
                    boolean hasil = db.hapusDataruangan(cari_kode_ruangan);

                    if (hasil) {
                        bersihdata();
                        Toast.makeText(RuanganActivity.this, "Data berhasil dihapus !!", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(RuanganActivity.this, "Data gagal dihapus !!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnTutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnBersih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bersihdata();
                Toast.makeText(RuanganActivity.this, "Form data telah dibersihkan !!", Toast.LENGTH_SHORT).show();
            }
        });

        btnLihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent frmlihat = new Intent(RuanganActivity.this, LihatDataRuangan.class);
                startActivity(frmlihat);
            }
        });
    }
}
