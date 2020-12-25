package com.example.applicationsushi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CustInfoFactureActivity extends AppCompatActivity {

    TextView textIdFacture;
    TextView textNbrPlat;
    TextView textPrixTot;

    int nbrPlat;
    Double prixTot;

    CardView cardViewCategories;
    CardView cardViewRestaurant;
    CardView cardViewAcceuil;
    CardView cardViewPanier;

    TextView app;

    Button bouttonLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_info_facture);

        cardViewAcceuil = findViewById(R.id.cardView1);
        cardViewAcceuil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustInfoFactureActivity.this, CustMenuActivity.class);
                startActivity(intent);
            }
        });


        cardViewRestaurant = findViewById(R.id.cardView4);
        cardViewRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CustInfoFactureActivity.this, CustListViewRestaurantActivity.class);
                startActivity(i);
            }
        });

        cardViewPanier = findViewById(R.id.cardView3);
        cardViewPanier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustInfoFactureActivity.this, CustListViewPanierActivity.class));
            }
        });

        textIdFacture = findViewById(R.id.textView7);
        textNbrPlat = findViewById(R.id.textView9);
        textPrixTot = findViewById(R.id.textView11);
        app = findViewById(R.id.applicationname);

        bouttonLogOut = findViewById(R.id.buttonView);
        cardViewCategories = findViewById(R.id.cardView2);

        bouttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustInfoFactureActivity.this, LoginActivity.class));
            }
        });

        cardViewCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustInfoFactureActivity.this, CustCategoriesActivity.class));
            }
        });

        Intent iin = getIntent();
        Bundle b = iin.getExtras();

        nbrPlat = b.getInt("nbrPla");
        prixTot = b.getDouble("prixTot");

        textIdFacture.setText("" + LoginActivity.S);
        textNbrPlat.setText("" + nbrPlat);
        textPrixTot.setText("" + prixTot);

    }

}