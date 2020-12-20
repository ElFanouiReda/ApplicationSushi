package com.example.applicationsushi;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CustListViewPanierActivity extends AppCompatActivity {

    int idPlat[];

    int quant[] ;
    int mImage[] ;
    int li[];
    int dis[];

    String nom[] ;
    String logUti[];
    String description[] ;
    String urlImages[];

    Double prix[] ;
    Double note[] ;

    ImageView icon ;

    Button Logout;
    Button Payer ;

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
        final CustomListViewPanier customListViewPanier = new CustomListViewPanier(this , idPlat , nom , description , quant , urlImages);
        listView.setAdapter(customListViewPanier);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long iid) {
                Intent i = new Intent(CustListViewPanierActivity.this , CustInfoPlatActivity.class);

                i.putExtra("idPlat" , idPlat[position]);
                i.putExtra("nom" , nom[position] );
                i.putExtra("description" , description[position]);
                i.putExtra("prix" , prix[position]);
                i.putExtra("note" , note[position]);
                i.putExtra("imgUrl" , urlImages[position]);
                i.putExtra("quant" , quant[position]);
                i.putExtra("li" , li[position]);
                i.putExtra("dis" , dis[position]);
                i.putExtra("logUti" , logUti[position]);

                startActivity(i);
            }
        });

        Payer = findViewById(R.id.buttonView2);
        Payer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ss ;

                ss = LoginActivity.S ;

                Retrofit retrofit = new Retrofit.Builder().baseUrl("https://miamsushi.000webhostapp.com/connection/delMyPanier.php/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RequestInterface request = retrofit.create(RequestInterface.class);
                Call<JsonResponse> call = request.delMyPanier(ss);
                call.enqueue(new Callback<JsonResponse>() {
                    @Override
                    public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                        if(response.code()==200){
                            JsonResponse jsonResponse = response.body();
                            Toast.makeText(getApplicationContext(),jsonResponse.getResponse().toString(), Toast.LENGTH_SHORT).show();
                            if(jsonResponse.getResponse().equals("Deleted")){
                                Toast.makeText(getApplicationContext(),"Deleted Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(), String.valueOf(response.code()),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Erreur",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    private void collectData(){
        try {

            URL url = new URL("https://miamsushi.000webhostapp.com/connection/dpPlatPanier.php/");
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

            /*int ff = 0 ;

            for (int i = 0 ; i<=js.length();i++){
                jo = js.getJSONObject(i);

                if ( (LoginActivity.S).equals(jo.getString("loginUtilisateur")) ) {

                    ff++ ;

                }

            }*/

            idPlat= new int[js.length()];
            nom=new String[js.length()];
            description=new String[js.length()];
            prix=new Double[js.length()];
            note=new Double[js.length()];
            urlImages=new String[js.length()];
            quant= new int[js.length()];
            logUti=new String[js.length()];
            li=new int[js.length()];
            dis=new int[js.length()];

            int j= 0 ;

            for (int i = 0 ; i<=js.length();i++){
                jo = js.getJSONObject(i);

                if ( (LoginActivity.S).equals(jo.getString("loginUtilisateur")) ) {

                    quant[j] = jo.getInt("quantite");
                    li[j] = jo.getInt("likee");
                    dis[j] = jo.getInt("dislike");
                    logUti[j] = jo.getString("loginUtilisateur");
                    idPlat[j] = jo.getInt("idPlat");
                    nom[j] = jo.getString("nom");
                    description[j] = jo.getString("description");
                    note[j] = jo.getDouble("note");
                    prix[j] = jo.getDouble("prix");
                    urlImages[j] = jo.getString("imageUrl");

                    j++ ;

                }

            }

        } catch (Exception e ){
            e.printStackTrace();
        }

    }

}