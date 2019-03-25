package com.tanvir.currency;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tanvir.currency.model.CurrencyModel;
import com.tanvir.currency.model.Rate;
import com.tanvir.currency.model.Rates;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText to, from;
    Button convert;
    Spinner to_list, from_list;
    ProgressDialog progressDialog;
    RadioButton super_reduce, reduce ,reduce1, reduce2, standard, parking;
      List<Rate> rate;

    CurrencyModel currencyModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        to = (EditText) findViewById(R.id.to_cur);
        from = (EditText) findViewById(R.id.from_cur);
        convert = (Button) findViewById(R.id.convert);
        super_reduce = (RadioButton) findViewById(R.id.super_reduced);
        reduce1 = (RadioButton) findViewById(R.id.reduced1);
        reduce = (RadioButton) findViewById(R.id.reduced);
        reduce2 = (RadioButton) findViewById(R.id.reduced2);
        standard = (RadioButton) findViewById(R.id.standard);
        parking = (RadioButton) findViewById(R.id.parking);
        to_list = (Spinner) findViewById(R.id.to_list);
        from_list = (Spinner) findViewById(R.id.from_list);



        new fetchData().execute("https://jsonvat.com/");
        to_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(currencyModel.getRates().get(i).getPeriods().get(0).getRates().getSuperReduced() == 0.0){

                    super_reduce.setVisibility(View.GONE);
                }
                else {
                    super_reduce.setVisibility(View.VISIBLE);
                }
                if(currencyModel.getRates().get(i).getPeriods().get(0).getRates().getReduced() == 0.0){

                    reduce.setVisibility(View.GONE);
                }
                else {
                    reduce.setVisibility(View.VISIBLE);
                }
                if(currencyModel.getRates().get(i).getPeriods().get(0).getRates().getReduced1() == 0.0){

                    reduce1.setVisibility(View.GONE);
                }
                else {
                    reduce1.setVisibility(View.VISIBLE);
                }
                if(currencyModel.getRates().get(i).getPeriods().get(0).getRates().getReduced2() == 0.0){

                    reduce2.setVisibility(View.GONE);
                }
                else {
                    reduce2.setVisibility(View.VISIBLE);
                }
                if(currencyModel.getRates().get(i).getPeriods().get(0).getRates().getStandard() == 0.0){

                    standard.setVisibility(View.GONE);
                }
                else {
                    standard.setVisibility(View.VISIBLE);
                }
                if(currencyModel.getRates().get(i).getPeriods().get(0).getRates().getParking() == 0.0){
                    parking.setVisibility(View.GONE);

                }
                else {
                    parking.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        from_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if(currencyModel.getRates().get(i).getPeriods().get(0).getRates().getReduced() == 0.0){
//
//                    reduce.setVisibility(View.GONE);
//                }
//                else {
//                    reduce.setVisibility(View.VISIBLE);
//                }
//                if(currencyModel.getRates().get(i).getPeriods().get(0).getRates().getReduced1() == 0.0){
//
//                    reduce1.setVisibility(View.GONE);
//                }
//                else {
//                    reduce1.setVisibility(View.VISIBLE);
//                }
//                if(currencyModel.getRates().get(i).getPeriods().get(0).getRates().getReduced2() == 0.0){
//
//                    reduce2.setVisibility(View.GONE);
//                }
//                else {
//                    reduce2.setVisibility(View.VISIBLE);
//                }
//                if(currencyModel.getRates().get(i).getPeriods().get(0).getRates().getStandard() == 0.0){
//
//                    standard.setVisibility(View.GONE);
//                }
//                else {
//                    standard.setVisibility(View.VISIBLE);
//                }
//                if(currencyModel.getRates().get(i).getPeriods().get(0).getRates().getParking() == 0.0){
//                    parking.setVisibility(View.GONE);
//
//                }
//                else {
//                    parking.setVisibility(View.VISIBLE);
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }

    private String getDataFromUrl(String demoIdUrl) {

        String result = null;
        int resCode;
        InputStream in;
        try {
            URL url = new URL(demoIdUrl);
            URLConnection urlConn = url.openConnection();

            HttpsURLConnection httpsConn = (HttpsURLConnection) urlConn;
            httpsConn.setAllowUserInteraction(false);
            httpsConn.setInstanceFollowRedirects(true);
            httpsConn.setRequestMethod("GET");
            httpsConn.connect();
            resCode = httpsConn.getResponseCode();

            if (resCode == HttpURLConnection.HTTP_OK) {
                in = httpsConn.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        in, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                in.close();
                result = sb.toString();
            } else {
                // error += resCode;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    class fetchData extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Please wait");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

        String result=     getDataFromUrl(strings[0]);
            return result;
        }

        @Override
        protected void onPostExecute(final String s) {
            super.onPostExecute(s);

            try {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();

                        Gson gson = new Gson();

                      currencyModel = gson.fromJson(s, CurrencyModel.class);

                        Toast.makeText(getApplicationContext(), String.valueOf(currencyModel.getRates().get(0).getPeriods().get(0).getRates().getStandard()), Toast.LENGTH_SHORT).show();
                rate = currencyModel.getRates();
//                for (int i = 0 ;i< currencyModel.getRates().size();i++){
//
//                    rate.add(currencyModel.getRates())
//                }


                        ArrayAdapter<Rate> curradapter  = new ArrayAdapter<Rate>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item, rate){

                            @Override
                            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                // TODO Auto-generated method stub

                                View view = super.getView(position, convertView, parent);

                                TextView text = (TextView)view.findViewById(android.R.id.text1);
                                text.setTextColor(Color.BLACK);

                                return view;

                            }

                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {
                                // TODO Auto-generated method stub

                                View view = super.getView(position, convertView, parent);

                                TextView text = (TextView)view.findViewById(android.R.id.text1);
                                text.setTextColor(Color.BLACK);

                                return view;

                            }
                        };
                        to_list.setAdapter(curradapter);
                        from_list.setAdapter(curradapter);
                    }
                });
            } catch (Exception e) {
                progressDialog.dismiss();

                e.printStackTrace();
            }
        }
    }

}
