package com.example.applicationsushi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

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

    ListView listView ;
    String nomUtilisateur[] = {"Reda" , "Zaid" , "Mouad" , "Youssef" , "Steef" , "Ali" , "Mhammed"};
    String commentaireUtilisateur[] = {"Bon plat" , "jbzfuobzcvuob" , "Tres delicieux" , "A la hauteur" , "Bien savouré" , "Tres bon prix" , "Magnifique"};
    int photoUtilisateur[] = {R.drawable.categorie_dessert ,
                                R.drawable.categorie_summer_poke ,
                                R.drawable.categorie_accompagnement ,
                                R.drawable.categorie_rolls ,
                                R.drawable.categorie_plateau ,
                                R.drawable.categorie_nouveautes_sushi ,
                                R.drawable.categorie_boissons
                                };

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



        listView = (ListView) findViewById(R.id.listview);
        CommAdapter commAdapter = new CommAdapter(this , nomUtilisateur , commentaireUtilisateur , photoUtilisateur );

        listView.setAdapter(commAdapter);

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


    public class CommAdapter extends ArrayAdapter<String>{

        Context context;
        String myNomUtilisateur[] ;
        String myCommUtilisateur[] ;
        int myPhotoUtilisateur[] ;

        CommAdapter(Context con , String nmUt[] , String comUt[] , int phUt[] ) {
            super(con , R.layout.row_comment , R.id.NomUt , nmUt);

            this.context = con ;
            this.myNomUtilisateur = nmUt ;
            this.myCommUtilisateur = comUt ;
            this.myPhotoUtilisateur = phUt ;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater  = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View row = layoutInflater.inflate(R.layout.row_comment , parent , false );
            ImageView phUt = (ImageView) row.findViewById(R.id.imageView);
            TextView nmUt = (TextView) row.findViewById(R.id.NomUt);
            TextView comUt = (TextView) row.findViewById(R.id.CommUt);

            phUt.setImageResource(myPhotoUtilisateur[position]);
            nmUt.setText(myNomUtilisateur[position]);
            comUt.setText(myCommUtilisateur[position]);


            return super.getView(position, convertView, parent);
        }
    }

}
