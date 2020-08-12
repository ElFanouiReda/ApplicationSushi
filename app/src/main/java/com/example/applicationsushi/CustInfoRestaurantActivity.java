package com.example.applicationsushi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class CustInfoRestaurantActivity extends AppCompatActivity {

    TextView textNom ;
    TextView textAdresse ;
    TextView textNumDeTelephone ;
    ImageView imageView ;

    String nom ;
    String adresse ;
    String numTel ;
    String urlImg ;

    CardView cardViewCategories ;

    Button bouttonLogOut ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_info_restaurant);

        textNom = findViewById(R.id.textView7);
        textAdresse = findViewById(R.id.textView9);
        textNumDeTelephone = findViewById(R.id.textView11);
        imageView = findViewById(R.id.imageView5);

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
        adresse = b.getString("LocalisationDestination");
        numTel = b.getString("numeroTelephone");


        textNom.setText(""+nom);
        textAdresse.setText(""+adresse);
        textNumDeTelephone.setText(""+numTel);





        //display image
        imageView.setImageBitmap(null);
        String urlLink = urlImg ;
        LoadImage loadImage = new LoadImage(imageView);
        loadImage.execute(urlLink);

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
