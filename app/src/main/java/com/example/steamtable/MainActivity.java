package com.example.steamtable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            ReadSteamtable();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Spinner spinner1=findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter1=ArrayAdapter.createFromResource(this,R.array.property1,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);


    }
    ArrayList<Steamtable> steamtableArrayList=new ArrayList<>();
    private void ReadSteamtable() throws IOException {
        Steamtable atpoint;
        InputStream is=getResources().openRawResource(R.raw.steamtable);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );
        String line;
        reader.readLine();
        while((line=reader.readLine())!=null){
            String[] properties=line.split(",");
            Steamtable sample=new Steamtable();
            sample.setEnthalpy_gas(Double.parseDouble(properties[8]));
            sample.setEnthalpy_liquid(Double.parseDouble(properties[6]));
            sample.setEntropy_gas(Double.parseDouble(properties[10]));
            sample.setEntropy_liquid(Double.parseDouble(properties[9]));
            sample.setInternal_energy_gas(Double.parseDouble(properties[5]));
            sample.setInternal_energy_liquid(Double.parseDouble(properties[4]));
            sample.setPressure(Double.parseDouble(properties[0]));
            sample.setSpecific_volume_gas(Double.parseDouble(properties[3]));
            sample.setSpecific_volume_liquid(Double.parseDouble(properties[2]));
            sample.setTemperature(Double.parseDouble(properties[1]));
            sample.setVaporization(Double.parseDouble(properties[7]));
            steamtableArrayList.add(sample);
            Log.d("Myactivity","Just created    "+sample.getEnthalpy_gas());

        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
