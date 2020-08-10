package com.example.applicationsushi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class CustInfoPlat extends AppCompatActivity {

    TextView textView;

    int pos ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_info_plat);

        textView = findViewById(R.id.textView);


        Intent iin = getIntent();
        Bundle b = iin.getExtras();

        pos = b.getInt("position");
        textView.setText(""+pos);
    }
}
