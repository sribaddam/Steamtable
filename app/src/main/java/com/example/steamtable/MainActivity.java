package com.example.steamtable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    int a,b;

    private Spinner spinner1;
    private Spinner spinner2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            ReadSteamtable();
        } catch (IOException e) {
            e.printStackTrace();
        }
         spinner1=findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter1=ArrayAdapter.createFromResource(this,R.array.property1,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(this);
         spinner2=findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(this,R.array.property2,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);
        EditText et1=findViewById(R.id.edit1);
        double p1,p2;
        try {
             p1=Double.parseDouble(et1.getText().toString());
        } catch (NumberFormatException e) {

        }
        EditText et2=findViewById(R.id.edit2);
        try {
             p2=Double.parseDouble(et1.getText().toString());
        } catch (NumberFormatException e) {

        }

        /*String property1="";
        property1=et1.getText().toString();
       //Double p1=Double.parseDouble(property1);



       // et2.setText("0");
        String property2="88";
         property2=et2.getText().toString();
         //double p2=Double.parseDouble(property2);*/



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


        String text= (String) parent.getItemAtPosition(position);

        Log.d("Myactivity",text);
        if(text.equals("Pressure") ){
            TextView textView=findViewById(R.id.units);
            textView.setText("bar");
            a=position;
            //Log.d("Myactivity   aaaa",text);

        }

        if(text.equals("Temperature") ){
            TextView textView=findViewById(R.id.units);
            textView.setText("K");
            a=position;
            //Log.d("Myactivity   aaaa",text);

        }
        if(text.equals("Enthalpy")){
            TextView textView=findViewById(R.id.unit2);
            textView.setText("KJ/Kg");
            b=position;
            Log.d("Myactivity   aaaa",text);
        }
        if(text.equals("Entropy")){
            TextView textView=findViewById(R.id.unit2);
            textView.setText(Html.fromHtml("KJ/kg-K"));
            b=position;
        }
        if(text.equals("specificvolume")){
            TextView textView=findViewById(R.id.unit2);
            textView.setText(Html.fromHtml("m<sup>3</sup>/kg"));
            b=position;
        }
        if(text.equals("quality")){
            TextView textView=view.findViewById(R.id.unit2);
            textView.setText("");
            b=position;
        }
        if(text.equals("Internalenergy")){
            TextView textView=findViewById(R.id.unit2);
            textView.setText("KJ/kg");
            b=position;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(MainActivity.this,"please select",Toast.LENGTH_SHORT);
    }
}
