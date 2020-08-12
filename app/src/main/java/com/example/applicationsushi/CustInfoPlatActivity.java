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

public class CustInfoPlatActivity extends AppCompatActivity {

    TextView textNom ;
    TextView textNote ;
    TextView textDescription ;
    TextView textPrix ;
    TextView Prix ;
    ImageView imageView ;

    String nom ;
    String description ;
    Double note ;
    Double prix ;
    String urlImg ;

    CardView cardViewCategories ;
    CardView cardViewRestaurant;
    CardView cardViewAcceuil ;

    Button bouttonLogOut ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_info_plat);

        cardViewAcceuil = findViewById(R.id.cardView1);
        cardViewAcceuil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustInfoPlatActivity.this , CustMenuActivity.class);
                startActivity(intent);
            }
        });


        cardViewRestaurant = findViewById(R.id.cardView4);
        cardViewRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CustInfoPlatActivity.this,CustListViewRestaurantActivity.class);
            }
        });

        textNom = findViewById(R.id.textView7);
        textDescription = findViewById(R.id.textView9);
        textNote = findViewById(R.id.textView11);
        textPrix = findViewById(R.id.textView13);
        imageView = findViewById(R.id.imageView5);

        bouttonLogOut = findViewById(R.id.buttonView);
        cardViewCategories = findViewById(R.id.cardView2);

        bouttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustInfoPlatActivity.this, LoginActivity.class));
            }
        });

        cardViewCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustInfoPlatActivity.this, CustCategoriesActivity.class));
            }
        });

        Intent iin = getIntent();
        Bundle b = iin.getExtras();

        urlImg = b.getString("imgUrl");
        nom = b.getString("nom");
        description = b.getString("description");
        note = b.getDouble("note");
        prix = b.getDouble("prix");


        textNom.setText(""+nom);
        textDescription.setText(""+description);
        textNote.setText(""+note);
        textPrix.setText(""+prix);





        //display image
        imageView.setImageBitmap(null);
        String urlLink = urlImg ;
        LoadImage loadImage = new LoadImage(imageView);
        loadImage.execute(urlLink);

    }


    private class LoadImage extends AsyncTask<String,Void,Bitmap>{

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
