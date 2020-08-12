package com.example.applicationsushi;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Locale;

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

    String LocalisationSource;
    String LocalisationDestination;


    TextView appName;

    //loc
    FusedLocationProviderClient fusedLocationProviderClient;



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

        appName = findViewById(R.id.applicationname);

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
        final CustomListViewRestaurant customListViewRestaurant = new CustomListViewRestaurant(this , nom , adresse , numeroTelephone , urlImages , LocalisationDestination , LocalisationSource);
        listView.setAdapter(customListViewRestaurant);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(CustListViewRestaurantActivity.this , CustInfoPlatActivity.class);
                i.putExtra("nom" , nom[position] );
                i.putExtra("adresse" , adresse[position]);
                i.putExtra("numeroTelephone" , numeroTelephone[position]);
                i.putExtra("imgUrl" , urlImages[position]);
                i.putExtra("localisation" , LocalisationSource);
                LocalisationDestination = adresse[position];
                appName.setText(""+LocalisationDestination);
                startActivity(i);
            }
        });

    }


    private void collectData(){

        //get localisation
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if(ActivityCompat.checkSelfPermission(CustListViewRestaurantActivity.this
                , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            getLocation();
        }else{
            ActivityCompat.requestPermissions(CustListViewRestaurantActivity.this
            , new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
        }



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


    private void getLocation(){
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();

                if(location!=null){
                    try {
                    Geocoder geocoder = new Geocoder(CustListViewRestaurantActivity.this ,
                            Locale.getDefault());
                        List<Address> addresses =  geocoder.getFromLocation(
                                location.getLatitude(),location.getLongitude(),1
                        );
                        LocalisationSource = addresses.get(0).getAddressLine(0);
                        appName.setText(""+ LocalisationSource);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}
