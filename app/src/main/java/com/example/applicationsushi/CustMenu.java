package com.example.applicationsushi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CustMenu extends AppCompatActivity {

    ListView listView;
    String nom[] ;
    String description[] ;
    int mImage[] ;
    Button Logout;
    ImageView icon ;

    BufferedInputStream is ;
    String line=null ;
    String result=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_menu);

    listView = findViewById(R.id.listview);


    Logout = findViewById(R.id.buttonView);
    Logout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(CustMenu.this, LoginActivity.class));
        }
    });



    StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
    collectData();
    CustomListViewPlat customListViewPlat = new CustomListViewPlat(this , nom , description);
    listView.setAdapter(customListViewPlat);

    }


    private void collectData(){
        try {
            URL url = new URL("https://miamsushi.000webhostapp.com/connection/dpPlat.php/");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            is=new BufferedInputStream(con.getInputStream());
        } catch (Exception ex){
            ex.printStackTrace();
        }



        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            while ((line=br.readLine())!=null){
                sb.append(line+"\n");
            }

            is.close();
            result = sb.toString();

        } catch (Exception ex){
            ex.printStackTrace();
        }


        try {

            JSONArray js = new JSONArray(result);
            JSONObject jo = null;

            nom=new String[js.length()];
            description=new String[js.length()];

            for (int i = 0 ; i<=js.length();i++){
                jo = js.getJSONObject(i);
                nom[i]=jo.getString("nom");
                description[i]=jo.getString("description");
            }

        } catch (Exception e ){
            e.printStackTrace();
        }

    }

}
