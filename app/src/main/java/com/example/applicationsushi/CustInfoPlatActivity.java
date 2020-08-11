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

    String nom ;
    String description ;
    Double note ;
    Double prix ;

    CardView cardViewCategories ;

    Button bouttonLogOut ;
    ImageView imageView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_info_plat);

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


        //display image
        imageView.setImageBitmap(null);
        String urlLink = "https://static.lexpress.fr/medias_12020/w_2048,h_1146,c_crop,x_0,y_154/w_960,h_540,c_fill,g_north/v1550742170/sushi-saumon-maki-saumon-japonais_6154396.jpg";

        LoadImage loadImage = new LoadImage(imageView);
        loadImage.execute(urlLink);







        Intent iin = getIntent();
        Bundle b = iin.getExtras();

        nom = b.getString("nom");
        description = b.getString("description");
        note = b.getDouble("note");
        prix = b.getDouble("prix");

        textNom.setText(""+nom);
        textDescription.setText(""+description);
        textNote.setText(""+note);
        textPrix.setText(""+prix);

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
