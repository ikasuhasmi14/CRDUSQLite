package com.example.asus.latihansqlite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HalamanUtamaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_utama);

        Button btn = (Button) findViewById(R.id.buttonmhs);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mhs = new Intent(HalamanUtamaActivity.this, MainActivity.class);
                startActivity(mhs);
            }
        });

        Button btns = (Button) findViewById(R.id.buttonruangan);
        btns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ruangan = new Intent(HalamanUtamaActivity.this, RuanganActivity.class);
                startActivity(ruangan);
            }
        });
    }


}
