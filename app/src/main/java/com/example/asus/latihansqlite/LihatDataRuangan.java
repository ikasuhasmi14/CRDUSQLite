package com.example.asus.latihansqlite;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LihatDataRuangan extends AppCompatActivity {

    private TextView textViewKode;
    private Button btnCari, btnReset;
    private EditText editTextCariKode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_data_ruangan);

        textViewKode = (TextView)findViewById(R.id.textViewKode);
        btnCari = (Button)findViewById(R.id.buttonCariKode);
        btnReset = (Button)findViewById(R.id.buttonReset);
        editTextCariKode = (EditText)findViewById(R.id.editTextCariKode);

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
                editTextCariKode.setText("");
                bacadata();
                editTextCariKode.setFocusable(true);
            }
        });
    }

    private void bacadata(){
        DatabaseRuangan db = new DatabaseRuangan(getApplicationContext());
        Cursor res = db.getAllData();
        StringBuffer stringBuffer = new StringBuffer();

        if(res!=null && res.getCount()>0){
            while(res.moveToNext()){
                stringBuffer.append("\n===========================================\n");
                stringBuffer.append("Kode Ruangan       : "+ res.getString(0)+"\n"); //25 spasi
                stringBuffer.append("Nama Ruangan       : "+ res.getString(1)+"\n"); //1 spasi
                stringBuffer.append("Kapasitas Ruangan  : "+ res.getString(2)+"\n"); //20 spasi
                stringBuffer.append("Status Ruangan     : "+ res.getString(3)+"\n"); //6 spasi

            }
            textViewKode.setText(stringBuffer.toString());
        }else{
            textViewKode.setText("Data tidak tersedia !!");
        }
    }

    private void bacadataByID(){
        btnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kode = editTextCariKode.getText().toString();
                if (kode.isEmpty()){
                    Toast.makeText(LihatDataRuangan.this, "Silahkan Tuliskan Kode Ruangan yang di cari !!", Toast.LENGTH_SHORT).show();
                    editTextCariKode.setFocusable(true);
                }else {
                    DatabaseRuangan db = new DatabaseRuangan(getApplicationContext());
                    Cursor res = db.getDataByID(kode);
                    StringBuffer stringBuffer = new StringBuffer();

                    if (res != null && res.getCount() > 0) {
                        while (res.moveToNext()) {
                            stringBuffer.append("\n===========================================\n");
                            stringBuffer.append("Kode Ruangan       : "+ res.getString(0)+"\n"); //25 spasi
                            stringBuffer.append("Nama Ruangan       : "+ res.getString(1)+"\n"); //1 spasi
                            stringBuffer.append("Kapasitas Ruangan  : "+ res.getString(2)+"\n"); //20 spasi
                            stringBuffer.append("Status Ruangan     : "+ res.getString(3)+"\n"); //6 spasi

                        }
                        textViewKode.setText(stringBuffer.toString());
                    } else {
                        textViewKode.setText("Data tidak tersedia !!");
                    }
                }
            }
        });
    }
}
