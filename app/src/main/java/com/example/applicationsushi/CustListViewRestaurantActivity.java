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

public class CustListViewRestaurantActivity extends AppCompatActivity {

    ListView listView;
    int[] idRestauarant;
    String urlImages[];
    String nom[] ;
    String adresse[] ;
    String numeroTelephone[] ;
    Button Logout;
    CardView cardViewProfil ;
    CardView cardViewCategories ;
    CardView cardViewPanier ;
    CardView cardViewRestaurant ;



    BufferedInputStream is ;
    String line=null ;
    String result=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_list_view_restaurant);

        listView = findViewById(R.id.listview);
        cardViewCategories = findViewById(R.id.cardView2);
        Logout = findViewById(R.id.buttonView);

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustListViewRestaurantActivity.this, LoginActivity.class));
            }
        });

        cardViewCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustListViewRestaurantActivity.this, CustCategoriesActivity.class));
            }
        });



        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        collectData();
        final CustomListViewRestaurant customListViewRestaurant = new CustomListViewRestaurant(this , nom , adresse , numeroTelephone , urlImages);
        listView.setAdapter(customListViewRestaurant);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(CustListViewRestaurantActivity.this , CustInfoPlatActivity.class);
                i.putExtra("nom" , nom[position] );
                i.putExtra("adresse" , adresse[position]);
                i.putExtra("numeroTelephone" , numeroTelephone[position]);
                i.putExtra("imgUrl" , urlImages[position]);
                startActivity(i);
            }
        });

    }


    private void collectData(){
        try {
            URL url = new URL("https://miamsushi.000webhostapp.com/connection/dpRestaurant.php/");
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

            idRestauarant= new int[js.length()];
            nom=new String[js.length()];
            adresse=new String[js.length()];
            numeroTelephone=new String[js.length()];
            urlImages=new String[js.length()];

            for (int i = 0 ; i<=js.length();i++){
                jo = js.getJSONObject(i);
                idRestauarant[i]=jo.getInt("idRestaurant");
                nom[i]=jo.getString("nom");
                adresse[i]=jo.getString("adresse");
                numeroTelephone[i] = jo.getString("numeroTelephone");
                urlImages[i] = jo.getString("urlImage");
            }

        } catch (Exception e ){
            e.printStackTrace();
        }

    }

}
