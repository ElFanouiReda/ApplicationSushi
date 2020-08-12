package com.example.applicationsushi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CustCategoriePlatActivity extends AppCompatActivity {

    ListView listView;
    String nom[] ;
    String description[] ;
    Double prix[] ;
    Double note[] ;
    String urlImages[];
    Button Logout;
    ImageView icon;
    CardView cardViewAcceuil;
    CardView cardViewCategories;
    CardView cardViewPanier;
    CardView cardViewRestaurant;


    TextView sous_Titre ;


    BufferedInputStream bufferedInputStream;

    String line = null;
    String result = null;

    TextView textView ;
    int _idCat ;
    String _nomCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_categorie_plat);

        cardViewAcceuil = findViewById(R.id.cardView1);
        cardViewAcceuil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustCategoriePlatActivity.this , CustMenuActivity.class);
                startActivity(intent);
            }
        });


        cardViewRestaurant = findViewById(R.id.cardView4);
        cardViewRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CustCategoriePlatActivity.this,CustListViewRestaurantActivity.class);
            }
        });

        listView = (ListView) findViewById(R.id.listview);

        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        collectData();

        cardViewCategories = findViewById(R.id.cardView2);
        Logout = findViewById(R.id.buttonView);


        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustCategoriePlatActivity.this, LoginActivity.class));
            }
        });

        cardViewCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustCategoriePlatActivity.this, CustCategoriesActivity.class));
            }
        });


        Intent iin = getIntent();
        Bundle b = iin.getExtras();

//        _idCat = b.getInt("idCat");
//        textView = findViewById(R.id.applicationname);
//        textView.setText(""+_idCat);

        _nomCat = b.getString("nomCat");
        sous_Titre = findViewById(R.id.recom);
        sous_Titre.setText("Les plats de la categorie : " + _nomCat);


        CustomListViewCategoriePlat customListViewCategoriePlat = new CustomListViewCategoriePlat(this, nom, description , urlImages);
        listView.setAdapter(customListViewCategoriePlat);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(CustCategoriePlatActivity.this , CustInfoPlatActivity.class);
                i.putExtra("nom" , nom[position] );
                i.putExtra("description" , description[position]);
                i.putExtra("prix" , prix[position]);
                i.putExtra("note" , note[position]);
                i.putExtra("imgUrl" , urlImages[position]);
                startActivity(i);
            }
        });


    }

    private void collectData() {

        Intent iin = getIntent();
        Bundle b = iin.getExtras();
        int j = 0;

        if (b != null) {
            j = (int) b.getInt("id");
        }


        try {

            URL url = new URL("https://miamsushi.000webhostapp.com/connection/dpPlat.php/");
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
            prix=new Double[js.length()];
            note=new Double[js.length()];
            urlImages=new String[js.length()];


            for (int i = 0 ; i<=js.length();i++){
                jo = js.getJSONObject(i);
                int idcat = b.getInt("idCat");

                if(jo.getInt("idCategorie")==idcat){
                    nom[i]=jo.getString("nom");
                    description[i]=jo.getString("description");
                    note[i] = jo.getDouble("note");
                    prix[i] = jo.getDouble("prix");
                    urlImages[i] = jo.getString("imageUrl");
                }

            }

        } catch (Exception e ){
            e.printStackTrace();
        }

    }


    }



