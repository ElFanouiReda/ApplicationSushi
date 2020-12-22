package com.example.applicationsushi;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CustomListViewRestaurant extends ArrayAdapter<String> {

    private String[] nom ;
    private String[] adresse ;
    private String[] numeroTelephone ;
    private String[] imagePath ;
    private Activity context ;
    String maLocalisation;
    FusedLocationProviderClient fusedLocationProviderClient;

    Bitmap bitmap;

    public CustomListViewRestaurant(Activity context , String[] nom , String[] adresse , String[] numeroTelephone , String[] imagePath ){
        super(context,R.layout.row_restaurant,nom);

        this.context=context;
        this.nom=nom;
        this.adresse=adresse;
        this.numeroTelephone=numeroTelephone;
        this.imagePath=imagePath;
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r = convertView;
        CustomListViewRestaurant.ViewHolder viewHolder;
        ImageView itineaire ;

        //dp data
        if(r==null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            r=layoutInflater.inflate(R.layout.row_restaurant , null , true);
            viewHolder=new CustomListViewRestaurant.ViewHolder(r);
            r.setTag(viewHolder);

        } else {
            viewHolder=(CustomListViewRestaurant.ViewHolder)r.getTag();
        }

        viewHolder.dpNom.setText(nom[position]);
        viewHolder.dpAdresse.setText(adresse[position]);
        viewHolder.dpNumeroTelephone.setText(numeroTelephone[position]);
        new GetImageFromUrl(viewHolder.imageRestaurant).execute(imagePath[position]);

        getLocation();

        itineaire = (ImageView)r.findViewById(R.id.imageView3);
        itineaire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sSource = maLocalisation;
                String sDestination = adresse[position];


                if (sSource.equals("") && sDestination.equals("")) {
                    Toast.makeText(context.getApplicationContext(), "Both are Empty", Toast.LENGTH_SHORT).show();
                } else {
                    DisplayTrack(sSource, sDestination);
                }

            }
        });


        return r;
    }

    public class ViewHolder{

        TextView dpNom;
        TextView dpAdresse;
        TextView dpNumeroTelephone;
        ImageView imageRestaurant;
        ImageView imageViewItineraire;

        ViewHolder(View v){
            dpNom=(TextView)v.findViewById(R.id.textView);
            dpAdresse=(TextView)v.findViewById(R.id.textView2);
            dpNumeroTelephone=(TextView)v.findViewById(R.id.textView3);
            imageRestaurant =(ImageView) v.findViewById(R.id.imageView);
            imageViewItineraire=(ImageView) v.findViewById(R.id.imageView3);


        }}

        class GetImageFromUrl extends AsyncTask<String,Void, Bitmap> {

        ImageView imageView;

        public GetImageFromUrl(ImageView imageView){
            this.imageView=imageView;
        }

        @Override
        protected Bitmap doInBackground(String... url) {
            String urldisplay=url[0];
            bitmap=null;

            try {
                InputStream inputStream = new java.net.URL(urldisplay).openStream();
                bitmap= BitmapFactory.decodeStream(inputStream);

            } catch (Exception e){
                e.printStackTrace();
            }

            return bitmap;
        }


        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
        }
    }

    private void getLocation() {

        if (ActivityCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {

                    try {

                        Geocoder geocoder = new Geocoder(context.getApplicationContext(),
                                Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1
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


        if(ActivityCompat.checkSelfPermission(context.getApplicationContext()
                , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

        } /*else {
            ActivityCompat.requestPermissions(CustInfoRestaurantActivity.this ,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
        }*/


        try {
            Uri uri = Uri.parse("https://www.google.co.in/maps/dir/"+sSource+"/"+sDestination);
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);

            intent.setPackage("com.google.android.apps.maps");

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);

        } catch (ActivityNotFoundException e){
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");

            Intent intent = new Intent(Intent.ACTION_VIEW,uri);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);

        }


    }

    /*private void DisplayTrack(String sSource , String sDestination){
        //if map dont exist

        try {
            Uri uri = Uri.parse("https://www.google.co.in/maps/dir/"+sSource+"/"+sDestination);

            Intent intent = new Intent(Intent.ACTION_VIEW,uri);

            intent.setPackage("com.google.android.apps.maps");

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);

        } catch (ActivityNotFoundException e){

            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.map");

            Intent intent = new Intent(Intent.ACTION_VIEW,uri);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);
        }


    }*/


}

