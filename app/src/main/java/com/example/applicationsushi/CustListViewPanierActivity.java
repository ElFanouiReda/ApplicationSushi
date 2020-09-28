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

public class CustListViewPanierActivity extends AppCompatActivity {

    int idPlat[];
    String nom[] ;
    String description[] ;
    int id ;
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

    ListView listView;

    BufferedInputStream is ;
    String line=null ;
    String result=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_list_view_panier);

        Logout = findViewById(R.id.buttonView);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustListViewPanierActivity.this, LoginActivity.class));
            }
        });

        cardViewAcceuil = findViewById(R.id.cardView1);
        cardViewAcceuil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustListViewPanierActivity.this , CustMenuActivity.class);
                startActivity(intent);
            }
        });

        cardViewCategories = findViewById(R.id.cardView2);
        cardViewCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustListViewPanierActivity.this, CustCategoriesActivity.class));
            }
        });

        cardViewPanier = findViewById(R.id.cardView3) ;
        cardViewPanier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustListViewPanierActivity.this, CustListViewPanierActivity.class));
            }
        });

        cardViewRestaurant = findViewById(R.id.cardView4);
        cardViewRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustListViewPanierActivity.this, CustListViewRestaurantActivity.class));
            }
        });

        listView = findViewById(R.id.listview);
        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        collectData();
        final CustomListViewPanier customListViewPanier = new CustomListViewPanier(this , id , nom , description , urlImages);
        listView.setAdapter(customListViewPanier);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long iid) {
                Intent i = new Intent(CustListViewPanierActivity.this , CustInfoPlatActivity.class);

                i.putExtra("id" , id) ;

                i.putExtra("idPlat" , idPlat[position]);

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

            id=new Integer(js.length());

            idPlat= new int[js.length()];

            nom=new String[js.length()];
            description=new String[js.length()];
            prix=new Double[js.length()];
            note=new Double[js.length()];
            urlImages=new String[js.length()];

            for (int i = 0 ; i<=js.length();i++){
                jo = js.getJSONObject(i);

                id=jo.getInt("idPlat");

                idPlat[i]=jo.getInt("idPlat");

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