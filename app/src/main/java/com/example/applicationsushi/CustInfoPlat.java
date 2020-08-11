package com.example.applicationsushi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class CustInfoPlat extends AppCompatActivity {

    TextView textNom ;
    TextView textNote ;
    TextView textDescription ;
    TextView textPrix ;
    TextView Prix ;

    String nom ;
    String description ;
    Double note ;
    Double prix ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_info_plat);

        textNom = findViewById(R.id.textView7);
        textDescription = findViewById(R.id.textView9);
        textNote = findViewById(R.id.textView11);
        textPrix = findViewById(R.id.textView13);


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
