package com.example.steamtable;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import static com.example.steamtable.MainActivity.list;

public class activity_2 extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);
        TextView tx1=findViewById(R.id.temp);

        tx1.setText(""+ list.get(0) + " \u2103");
        TextView tx2=findViewById(R.id.press);
        tx2.setText(""+ list.get(1) +" bar");
        TextView tx3=findViewById(R.id.spec_vol);
        tx3.setText(""+ list.get(2) + Html.fromHtml("m<sup>3</sup>/kg"));
        TextView tx4=findViewById(R.id.int_energy);
        tx4.setText(""+ list.get(3) +" KJ/Kg");
        TextView tx5=findViewById(R.id.enthalpy);
        tx5.setText(""+ list.get(4) +" KJ/Kg");
        TextView tx6=findViewById(R.id.entropy);
        tx6.setText(""+ list.get(5) +" KJ/Kg-K");


    }
}
