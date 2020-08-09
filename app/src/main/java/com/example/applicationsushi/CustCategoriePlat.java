package com.example.applicationsushi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CustCategoriePlat extends AppCompatActivity {

    ListView listView;
    String nom[] ;
    String description[] ;
    int mImage[] ;
    Button Logout;
    ImageView icon ;
    CardView cardViewProfil ;
    CardView cardViewCategories ;
    CardView cardViewPanier ;
    CardView cardViewRestaurant ;




    BufferedInputStream bufferedInputStream ;
    String line = null ;
    String result = null ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_categorie_plat);

        listView = (ListView) findViewById(R.id.listview);
        cardViewCategories = findViewById(R.id.cardView2);
        Logout = findViewById(R.id.buttonView);

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustCategoriePlat.this, LoginActivity.class));
            }
        });

        cardViewCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustCategoriePlat.this, CustCategories.class));
            }
        });



        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        collectData();
        CustomListViewCategoriePlat customListViewCategoriePlat = new CustomListViewCategoriePlat(this , nom , description);
        listView.setAdapter(customListViewCategoriePlat);

    }

    private void collectData() {

        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        int j = 0 ;

        if(b!=null)
        {
            j =(int) b.getInt("id");
        }


        try {

            URL url = new URL("https://miamsushi.000webhostapp.com/connection/dpPlatId.php/");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            bufferedInputStream = new BufferedInputStream(con.getInputStream());
        } catch (Exception ex){
            ex.printStackTrace();
        }


        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(bufferedInputStream));
            StringBuilder sb = new StringBuilder();

            while ((line=br.readLine())!=null){
                sb.append(line+"\n");
            }

            bufferedInputStream.close();
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
                if(jo.getInt("idCategorie") == j) {
                    nom[i] = jo.getString("nom");
                    description[i] = jo.getString("description");
                }
            }

        } catch (Exception e ){
            e.printStackTrace();
        }

    }


    }


    //  "+"?id="+j+"
