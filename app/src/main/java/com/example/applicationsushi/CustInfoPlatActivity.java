package com.example.applicationsushi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_info_plat);

        textNom = findViewById(R.id.textView7);
        textDescription = findViewById(R.id.textView9);
        textNote = findViewById(R.id.textView11);
        textPrix = findViewById(R.id.textView13);

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

        nom = b.getString("nom");
        description = b.getString("description");
        note = b.getDouble("note");
        prix = b.getDouble("prix");

        textNom.setText(""+nom);
        textDescription.setText(""+description);
        textNote.setText(""+note);
        textPrix.setText(""+prix);
    }
}
