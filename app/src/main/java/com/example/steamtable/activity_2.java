package com.example.steamtable;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import static com.example.steamtable.MainActivity.list;

public class activity_2 extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);
        TextView tx1=findViewById(R.id.temp);

        tx1.setText(""+ list.get(0) +" K");
        TextView tx2=findViewById(R.id.press);
        tx2.setText(""+ list.get(1) +" K");
        TextView tx3=findViewById(R.id.spec_vol);
        tx3.setText(""+ list.get(2) +" K");
        TextView tx4=findViewById(R.id.int_energy);
        tx4.setText(""+ list.get(3) +" K");
        TextView tx5=findViewById(R.id.enthalpy);
        tx5.setText(""+ list.get(4) +" K");
        TextView tx6=findViewById(R.id.entropy);
        tx6.setText(""+ list.get(5) +" K");


    }
}