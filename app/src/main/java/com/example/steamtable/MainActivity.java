package com.example.steamtable;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    double p1,p2;
    public static ArrayList<Double> list;

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

        Button go=findViewById(R.id.submit);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et1=findViewById(R.id.edit1);

                try {
                    p1=Double.parseDouble(et1.getText().toString());
                } catch (NumberFormatException e) {

                }
                EditText et2=findViewById(R.id.edit2);
                try {
                    p2=Double.parseDouble(et2.getText().toString());
                } catch (NumberFormatException e) {

                }
                list= solve();
                Intent i= new Intent(MainActivity.this,activity_2.class);
                startActivity(i);

            }
        });





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
    public double interpolation(double x,double x1,double x2,double y1,double y2){
        double m=(y2-y1)/(x2-x1);
        return m*(x-x1) + y1;
    }

    public ArrayList<Double> solve(){
        ArrayList<Double> ans=new ArrayList<Double>();
        double z_temp=0,z_press=0,z_spec_vol=0,z_int_energy=0,z_enthalpy=0,z_entropy=0;
        int row=-1;
        double pressure=0,temp=0,value=0;
        Steamtable obj=steamtableArrayList.get(0);
        double p=obj.getPressure();
        if(a==0){
            pressure=p1;
            value=p2;
            int low=0,high=118;
            while(low<=high){
                int mid=(low+high)/2;
                obj=steamtableArrayList.get(mid);

                Log.d("myact","low is  "+low+ "  mid=" + mid+ "  high="+high+"  obj="+obj.getPressure()+" press="+pressure);

                if(obj.getPressure()==pressure){
                    row=mid;
                    break;
                }
                else if(obj.getPressure()<pressure){
                    low=mid+1;
                }
                else high=mid-1;
            }
            Log.d("myact","row is  "+row+ "high="+high+ "   "+obj.getPressure());


            double x;

            if(row==-1){
                Steamtable s1,s2;
                s1=steamtableArrayList.get(high);
                s2=steamtableArrayList.get(low);
                obj.setTemperature(interpolation(pressure,s1.getPressure(),s2.getPressure(),s1.getTemperature(),s2.getTemperature()));
                obj.setSpecific_volume_liquid(interpolation(pressure,s1.getPressure(),s2.getPressure(),s1.getSpecific_volume_liquid(),s2.getSpecific_volume_liquid()));
                obj.setSpecific_volume_gas(interpolation(pressure,s1.getPressure(),s2.getPressure(),s1.getSpecific_volume_gas(),s2.getSpecific_volume_gas()));
                obj.setInternal_energy_liquid(interpolation(pressure,s1.getPressure(),s2.getPressure(),s1.getInternal_energy_liquid(),s2.getInternal_energy_liquid()));
                obj.setInternal_energy_gas(interpolation(pressure,s1.getPressure(),s2.getPressure(),s1.getInternal_energy_gas(),s2.getInternal_energy_gas()));
                obj.setEnthalpy_liquid(interpolation(pressure,s1.getPressure(),s2.getPressure(),s1.getEnthalpy_liquid(),s2.getEnthalpy_liquid()));
                obj.setEnthalpy_gas(interpolation(pressure,s1.getPressure(),s2.getPressure(),s1.getEnthalpy_gas(),s2.getEnthalpy_gas()));
                obj.setEntropy_liquid(interpolation(pressure,s1.getPressure(),s2.getPressure(),s1.getEntropy_liquid(),s2.getEntropy_liquid()));
                obj.setEntropy_gas(interpolation(pressure,s1.getPressure(),s2.getPressure(),s1.getEntropy_gas(),s2.getEntropy_gas()));
            }

            if(b==0){
                x=(value-obj.getEnthalpy_liquid())/(obj.getEnthalpy_gas()-obj.getEnthalpy_liquid());
            }
            else if(b==1){
                x=(value-obj.getEntropy_liquid())/(obj.getEntropy_gas()-obj.getEntropy_liquid());
            }
            else if(b==2){
                x=value;
            }
            else if(b==3){
                Log.d("hhhhh", "solve: "+obj.getSpecific_volume_liquid()+"  "+obj.getSpecific_volume_gas()+"  value: "+value);
                x=(value-obj.getSpecific_volume_liquid())/(obj.getSpecific_volume_gas()-obj.getSpecific_volume_liquid());
            }
            else{
                x=(value-obj.getInternal_energy_liquid())/(obj.getInternal_energy_gas()-obj.getInternal_energy_liquid());
            }
            z_press=pressure;
            z_temp=obj.getTemperature();
            z_spec_vol=obj.getSpecific_volume_liquid()+x*(obj.getSpecific_volume_gas()-obj.getSpecific_volume_liquid());
            z_int_energy=obj.getInternal_energy_liquid()+x*(obj.getInternal_energy_gas()-obj.getInternal_energy_liquid());
            z_enthalpy=obj.getEnthalpy_liquid()+x*(obj.getEnthalpy_gas()-obj.getEnthalpy_liquid());
            z_entropy=obj.getEntropy_liquid()+x*(obj.getEntropy_gas()-obj.getEntropy_liquid());
            Log.d("spec",""+value+"   "+x+"\n");
        }
        else if(a==1){
            int low=0,high=steamtableArrayList.size()-1;
            while(low<=high){
                int mid=(low+high)/2;
                obj=steamtableArrayList.get(mid);
                if(obj.getTemperature()==temp){
                    row=mid;
                    break;
                }
                else if(obj.getTemperature()<temp){
                    low=mid+1;
                }
                else high=mid-1;
            }
            double x=0;
            if(row==-1){
                Steamtable s1,s2;
                s1=steamtableArrayList.get(high);
                s2=steamtableArrayList.get(low);
                obj.setPressure(interpolation(temp,s1.getTemperature(),s2.getTemperature(),s1.getPressure(),s2.getPressure()));
                obj.setSpecific_volume_liquid(interpolation(temp,s1.getTemperature(),s2.getTemperature(),s1.getSpecific_volume_liquid(),s2.getSpecific_volume_liquid()));
                obj.setSpecific_volume_gas(interpolation(temp,s1.getTemperature(),s2.getTemperature(),s1.getSpecific_volume_gas(),s2.getSpecific_volume_gas()));
                obj.setInternal_energy_liquid(interpolation(temp,s1.getTemperature(),s2.getTemperature(),s1.getInternal_energy_liquid(),s2.getInternal_energy_liquid()));
                obj.setInternal_energy_gas(interpolation(temp,s1.getTemperature(),s2.getTemperature(),s1.getInternal_energy_gas(),s2.getInternal_energy_gas()));
                obj.setEnthalpy_liquid(interpolation(temp,s1.getTemperature(),s2.getTemperature(),s1.getEnthalpy_liquid(),s2.getEnthalpy_liquid()));
                obj.setEnthalpy_gas(interpolation(temp,s1.getTemperature(),s2.getTemperature(),s1.getEnthalpy_gas(),s2.getEnthalpy_gas()));
                obj.setEntropy_liquid(interpolation(temp,s1.getTemperature(),s2.getTemperature(),s1.getEntropy_liquid(),s2.getEntropy_liquid()));
                obj.setEntropy_gas(interpolation(temp,s1.getTemperature(),s2.getTemperature(),s1.getEntropy_gas(),s2.getEntropy_gas()));
            }

            if(b==0){
                x=(value-obj.getEnthalpy_liquid())/(obj.getEnthalpy_gas()-obj.getEnthalpy_liquid());
            }
            else if(b==1){
                x=(value-obj.getEntropy_liquid())/(obj.getEntropy_gas()-obj.getEntropy_liquid());
            }
            else if(b==2){
                x=value;
            }
            else if(b==3){
                x=(value-obj.getSpecific_volume_liquid())/(obj.getSpecific_volume_gas()-obj.getSpecific_volume_liquid());
            }
            else{
                x=(value-obj.getInternal_energy_liquid())/(obj.getInternal_energy_gas()-obj.getInternal_energy_liquid());
            }




            z_temp=temp;
            z_press=obj.getPressure();

            z_spec_vol=obj.getSpecific_volume_liquid()+x*(obj.getSpecific_volume_gas()-obj.getSpecific_volume_liquid());
            z_int_energy=obj.getInternal_energy_liquid()+x*(obj.getInternal_energy_gas()-obj.getInternal_energy_liquid());
            z_enthalpy=obj.getEnthalpy_liquid()+x*(obj.getEnthalpy_gas()-obj.getEnthalpy_liquid());
            z_entropy=obj.getEntropy_liquid()+x*(obj.getEntropy_gas()-obj.getEntropy_liquid());
        }

        Log.d("myact","hii"+z_press+ "   sec  "+z_enthalpy);
        ans.add(z_temp);
        ans.add(z_press);
        ans.add(z_spec_vol);
        ans.add(z_int_energy);
        ans.add(z_enthalpy);
        ans.add(z_entropy);

        return ans;
    }




}
