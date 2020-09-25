package com.example.applicationsushi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

public class CustInfoRestaurantActivity extends AppCompatActivity {

    TextView textNom ;
    TextView textAdresse ;
    TextView textNumDeTelephone ;
    ImageView imageView ;

    String nom ;
    String adresse ;
    String numTel ;
    String urlImg ;
    String maLocalisation;

    CardView cardViewCategories ;
    CardView cardViewRestaurant ;
    CardView cardViewAcceuil ;
    CardView cardViewPanier ;

    TextView app ;

    Button bouttonLogOut ;
    ImageButton bouttonItineraire ;

    FusedLocationProviderClient fusedLocationProviderClient ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_info_restaurant);

        cardViewAcceuil = findViewById(R.id.cardView1);
        cardViewAcceuil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustInfoRestaurantActivity.this , CustMenuActivity.class);
                startActivity(intent);
            }
        });


        cardViewRestaurant = findViewById(R.id.cardView4);
        cardViewRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CustInfoRestaurantActivity.this,CustListViewRestaurantActivity.class);
            }
        });

        cardViewPanier = findViewById(R.id.cardView3) ;
        cardViewPanier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustInfoRestaurantActivity.this, CustListViewPanierActivity.class));
            }
        });

        textNom = findViewById(R.id.textView7);
        textAdresse = findViewById(R.id.textView9);
        textNumDeTelephone = findViewById(R.id.textView11);
        imageView = findViewById(R.id.imageView5);
        app = findViewById(R.id.applicationname);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        bouttonItineraire =(ImageButton) findViewById(R.id.buttonView1);

        bouttonLogOut = findViewById(R.id.buttonView);
        cardViewCategories = findViewById(R.id.cardView2);

        bouttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustInfoRestaurantActivity.this, LoginActivity.class));
            }
        });

        cardViewCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustInfoRestaurantActivity.this, CustCategoriesActivity.class));
            }
        });

        Intent iin = getIntent();
        Bundle b = iin.getExtras();

        urlImg = b.getString("imgUrl");
        nom = b.getString("nom");
        adresse = b.getString("adresse");
        numTel = b.getString("numeroTelephone");
        maLocalisation = b.getString("localisationSource");


        textNom.setText(""+nom);
        textAdresse.setText(""+adresse);
        textNumDeTelephone.setText(""+numTel);




        //display image
        imageView.setImageBitmap(null);
        String urlLink = urlImg ;
        LoadImage loadImage = new LoadImage(imageView);
        loadImage.execute(urlLink);



        getLocation();




        //-------------------------------------


        //itineraire
        bouttonItineraire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sSource = maLocalisation ;
                String sDestination = adresse ;






                if(sSource.equals("") && sDestination.equals("")){
                    Toast.makeText(getApplicationContext() , "Both are Empty" , Toast.LENGTH_SHORT).show();
                } else {
                    DisplayTrack(sSource , sDestination);
                }


            }
        });

    }

    private void getLocation() {

        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if(location != null){

                    try {

                        Geocoder geocoder = new Geocoder(CustInfoRestaurantActivity.this ,
                                Locale.getDefault());
                        List<Address> addresses =  geocoder.getFromLocation(
                                location.getLatitude(),location.getLongitude(),1
                        );
                        maLocalisation = addresses.get(0).getAddressLine(0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    //to itineraire
    private void DisplayTrack(String sSource, String sDestination) {


        if(ActivityCompat.checkSelfPermission(CustInfoRestaurantActivity.this
            , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

        } else {
        ActivityCompat.requestPermissions(CustInfoRestaurantActivity.this ,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
        }


        try {
            Uri uri = Uri.parse("https://www.google.co.in/maps/dir/"+sSource+"/"+sDestination);
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);

            intent.setPackage("com.google.android.apps.maps");

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);

        } catch (ActivityNotFoundException e){
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");

            Intent intent = new Intent(Intent.ACTION_VIEW,uri);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);

        }


    }


    private class LoadImage extends AsyncTask<String,Void, Bitmap> {

        ImageView imageView ;

        public LoadImage(ImageView imageView){
            this.imageView = imageView ;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String urlLink = strings[0];
            Bitmap bitmap = null ;
            try {
                InputStream inputStream = new java.net.URL(urlLink).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap ;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }



}
