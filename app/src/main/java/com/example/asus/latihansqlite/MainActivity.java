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

public class MainActivity extends AppCompatActivity {
    //Deklarasi Komponen
    private EditText nimmhs, namamhs, alamatmhs, nohpmhs;
    private RadioGroup groupjeniskelamin;
    private RadioButton jeniskelamin, rbpria, rbwanita;
    private CheckBox cbolahraga, cbmembaca, cbtidur;

    private Button btnCari, btnSimpan, btnHapus, btnUbah, btnTutup, btnBersih, btnLihat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BuatDatabase dc = new BuatDatabase(this);

        nimmhs = (EditText)findViewById(R.id.editTextNIM);
        namamhs = (EditText)findViewById(R.id.editTextNama);
        alamatmhs = (EditText)findViewById(R.id.editTextAlamat);
        nohpmhs = (EditText)findViewById(R.id.editTextHp);

        groupjeniskelamin = (RadioGroup)findViewById(R.id.RadioButtonJk);

        cbolahraga = (CheckBox)findViewById(R.id.checkBoxOlahraga);
        cbmembaca = (CheckBox)findViewById(R.id.CheckBoxMembaca);
        cbtidur = (CheckBox)findViewById(R.id.checkBoxTidur);

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
        nimmhs.setText("");
        namamhs.setText("");
        alamatmhs.setText("");
        nohpmhs.setText("");
        rbpria = (RadioButton)findViewById(R.id.radioButtonPria);
        rbpria.setChecked(true);
        cbolahraga.setChecked(false);
        cbmembaca.setChecked(false);
        cbtidur.setChecked(false);
        btnLihat.setFocusable(true);
    }

    public void aksi(){
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jk, checkboxhobby = "";
                BuatDatabase db1 = new BuatDatabase(getApplication());

                //pengambilan data perdasarkan pilihan radio button
                int pilih = groupjeniskelamin.getCheckedRadioButtonId();
                jeniskelamin = (RadioButton)findViewById(pilih);
                jk = jeniskelamin.getText().toString();

                //mengambil data pada checkbox
                if (cbolahraga.isChecked()){
                    checkboxhobby = checkboxhobby + cbolahraga.getText().toString()+ ",";
                }
                if (cbmembaca.isChecked()){
                    checkboxhobby = checkboxhobby + cbmembaca.getText().toString()+ ",";
                }
                if (cbtidur.isChecked()){
                    checkboxhobby = checkboxhobby + cbtidur.getText().toString();
                }


                boolean hasil1 = db1.insertDatamhs(nimmhs.getText().toString(),
                        namamhs.getText().toString(),
                        alamatmhs.getText().toString(),
                        nohpmhs.getText().toString(),
                        jk,
                        checkboxhobby
                );
                if (hasil1){
                    bersihdata();
                    Toast.makeText(MainActivity.this, "Data gagal disimpan !!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Data berhasil disimpan !!" , Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jk,hobby = "";
                BuatDatabase dbl = new BuatDatabase(getApplication());
                String cari_nim = nimmhs.getText().toString();

                if (cari_nim.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Silahkan Tuliskan NIM yang di cari !!", Toast.LENGTH_SHORT).show();
                    nimmhs.setFocusable(true);
                }else{
                    Cursor cr = dbl.selectDatamhs(cari_nim);
                    Boolean hasil = cr.moveToFirst();

                    if (hasil){
                        nimmhs.setText(cr.getString(0));
                        namamhs.setText(cr.getString(1));
                        alamatmhs.setText(cr.getString(2));
                        nohpmhs.setText(cr.getString(3));


                        //Mengisi Radio Button Jenis Kelamin
                        jk = (cr.getString(4));
                        rbpria = (RadioButton)findViewById(R.id.radioButtonPria);
                        rbwanita = (RadioButton)findViewById(R.id.radioButtonWanita);

                        if (jk.equals("Laki-Laki")){
                            rbpria.setChecked(true);
                        }else{
                            rbwanita.setChecked(true);
                        }

                        //Mengisi Checkbox Hobby
                        hobby = (cr.getString(5));

                        if (hobby.equals("Olahraga,")){
                            cbolahraga.setChecked(true);
                        }else
                        if (hobby.equals("Membaca Buku,")){
                            cbmembaca.setChecked(true);
                        }else
                        if (hobby.equals("Tidur")){
                            cbtidur.setChecked(true);
                        }else
                        if (hobby.equals("Olahraga,Membaca Buku,")){
                            cbolahraga.setChecked(true);
                            cbmembaca.setChecked(true);
                        }else
                        if (hobby.equals("Membaca Buku,Tidur")){
                            cbmembaca.setChecked(true);
                            cbmembaca.setChecked(true);
                        }else
                        if (hobby.equals("Olahraga,Tidur")){
                            cbolahraga.setChecked(true);
                            cbtidur.setChecked(true);
                        }else
                        if (hobby.equals("Olahraga,Membaca Buku,Tidur")){
                            cbolahraga.setChecked(true);
                            cbmembaca.setChecked(true);
                            cbtidur.setChecked(true);
                        }

                        Toast.makeText(MainActivity.this, "Data ditemukan !!" , Toast.LENGTH_SHORT).show();

                    }
                    else{
                        bersihdata();
                        Toast.makeText(MainActivity.this, "Maaf data tidak ditemukan !!", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuatDatabase db = new BuatDatabase(getApplicationContext());
                String cari_nim = nimmhs.getText().toString();
                if (cari_nim.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Silahkan Tuliskan NIM yang akan dihapus !!", Toast.LENGTH_SHORT).show();
                    nimmhs.setFocusable(true);
                } else {
                    boolean hasil = db.hapusDatamhs(cari_nim);

                    if (hasil) {
                        bersihdata();
                        Toast.makeText(MainActivity.this, "Data berhasil dihapus !!", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(MainActivity.this, "Data gagal dihapus !!", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(MainActivity.this, "Form data telah dibersihkan !!", Toast.LENGTH_SHORT).show();
            }
        });

        btnLihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent frmlihat = new Intent(MainActivity.this, LihatDataMahasiswa.class);
                startActivity(frmlihat);
            }
        });
    }



}
