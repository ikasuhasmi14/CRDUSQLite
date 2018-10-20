package com.example.asus.latihansqlite;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LihatDataMahasiswa extends AppCompatActivity {

    private TextView textViewNIM;
    private Button btnCari, btnReset;
    private EditText editTextCariNIM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_data_mahasiswa);
        textViewNIM = (TextView)findViewById(R.id.textViewNim);
        btnCari = (Button)findViewById(R.id.buttonCariNIM);
        btnReset = (Button)findViewById(R.id.buttonReset);
        editTextCariNIM = (EditText)findViewById(R.id.editTextCariNIm);

        bacadata();
        bacadataByID();
        resetData();
        btnCari.setFocusable(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        btnCari.setFocusable(true);
    }

    protected void resetData(){
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextCariNIM.setText("");
                bacadata();
                editTextCariNIM.setFocusable(true);
            }
        });
    }

    private void bacadata(){
        BuatDatabase db = new BuatDatabase(getApplicationContext());
        Cursor res = db.getAllData();
        StringBuffer stringBuffer = new StringBuffer();

        if(res!=null && res.getCount()>0){
            while(res.moveToNext()){
                stringBuffer.append("\n===========================================\n");
                stringBuffer.append("NIM                         : "+ res.getString(0)+"\n"); //25 spasi
                stringBuffer.append("Nama Mahasiswa : "+ res.getString(1)+"\n"); //1 spasi
                stringBuffer.append("Alamat                    : "+ res.getString(2)+"\n"); //20 spasi
                stringBuffer.append("No Handphone      : "+ res.getString(3)+"\n"); //6 spasi
                stringBuffer.append("Jenis Kelamin       : "+ res.getString(4)+"\n"); //7 spasi
                stringBuffer.append("Hobby                     : "+ res.getString(5)); //21 spasi

            }
            textViewNIM.setText(stringBuffer.toString());
        }else{
            textViewNIM.setText("Data tidak tersedia !!");
        }
    }

    private void bacadataByID(){
        btnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nim = editTextCariNIM.getText().toString();
                if (nim.isEmpty()){
                    Toast.makeText(LihatDataMahasiswa.this, "Silahkan Tuliskan NIM yang di cari !!", Toast.LENGTH_SHORT).show();
                    editTextCariNIM.setFocusable(true);
                }else {
                    BuatDatabase db = new BuatDatabase(getApplicationContext());
                    Cursor res = db.getDataByID(nim);
                    StringBuffer stringBuffer = new StringBuffer();

                    if (res != null && res.getCount() > 0) {
                        while (res.moveToNext()) {
                            stringBuffer.append("\n===========================================\n");
                            stringBuffer.append("NIM                         : " + res.getString(0) + "\n"); //25 spasi
                            stringBuffer.append("Nama Mahasiswa : " + res.getString(1) + "\n"); //1 spasi
                            stringBuffer.append("Alamat                    : " + res.getString(2) + "\n"); //20 spasi
                            stringBuffer.append("No Handphone      : " + res.getString(3) + "\n"); //6 spasi
                            stringBuffer.append("Jenis Kelamin       : " + res.getString(4) + "\n"); //7 spasi
                            stringBuffer.append("Hobby                     : " + res.getString(5)); //21 spasi

                        }
                        textViewNIM.setText(stringBuffer.toString());
                    } else {
                        textViewNIM.setText("Data tidak tersedia !!");
                    }
                }
            }
        });
    }


}
