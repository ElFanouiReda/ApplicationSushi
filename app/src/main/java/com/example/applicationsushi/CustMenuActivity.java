package com.example.applicationsushi;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CustMenuActivity extends AppCompatActivity {

    ListView listView;
    String nom[] ;
    String description[] ;
    int mImage[] ;
    Double prix[] ;
    Double note[] ;
    String urlImages[];
    Button Logout;
    ImageView icon ;
    CardView cardViewAcceuil ;
    CardView cardViewCategories ;
    CardView cardViewPanier ;
    CardView cardViewRestaurant ;




    BufferedInputStream is ;
    String line=null ;
    String result=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_menu);

        cardViewAcceuil = findViewById(R.id.cardView1);
        cardViewAcceuil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustMenuActivity.this , CustMenuActivity.class);
                startActivity(intent);
            }
        });



    listView = findViewById(R.id.listview);


        cardViewRestaurant = findViewById(R.id.cardView4);
        cardViewRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CustMenuActivity.this,CustListViewRestaurantActivity.class);
            }
        });



        Logout = findViewById(R.id.buttonView);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustMenuActivity.this, LoginActivity.class));
            }
        });


        cardViewCategories = findViewById(R.id.cardView2);
        cardViewCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustMenuActivity.this, CustCategoriesActivity.class));
            }
        });


        cardViewRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustMenuActivity.this, CustListViewRestaurantActivity.class));
            }
        });




        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
    collectData();
    final CustomListViewPlat customListViewPlat = new CustomListViewPlat(this , nom , description , urlImages);
    listView.setAdapter(customListViewPlat);

    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent i = new Intent(CustMenuActivity.this , CustInfoPlatActivity.class);
            i.putExtra("nom" , nom[position] );
            i.putExtra("description" , description[position]);
            i.putExtra("prix" , prix[position]);
            i.putExtra("note" , note[position]);
            i.putExtra("imgUrl" , urlImages[position]);
            startActivity(i);
        }
    });

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
            prix=new Double[js.length()];
            note=new Double[js.length()];
            urlImages=new String[js.length()];

            for (int i = 0 ; i<=js.length();i++){
                jo = js.getJSONObject(i);
                nom[i]=jo.getString("nom");
                description[i]=jo.getString("description");
                note[i] = jo.getDouble("note");
                prix[i] = jo.getDouble("prix");
                urlImages[i] = jo.getString("imageUrl");
            }

        } catch (Exception e ){
            e.printStackTrace();
        }

    }

}
