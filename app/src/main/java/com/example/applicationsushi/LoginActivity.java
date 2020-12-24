package com.example.applicationsushi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    Button buttonLogin;
    EditText eusername;
    EditText epassword;
    TextView inscription ;

    public static String S ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonLogin = (Button) findViewById(R.id.buttonView);
        eusername = (EditText) findViewById(R.id.editText);
        epassword = (EditText) findViewById(R.id.editText2);
        inscription = (TextView) findViewById(R.id.textView);


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = eusername.getText().toString();
                String motdepasse = epassword.getText().toString();

                if (login == "" || motdepasse == "") {
                    Toast.makeText(getApplicationContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                } else{
                    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://miamsushi.000webhostapp.com/connection/login.php/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                RequestInterface requestInterface = retrofit.create(RequestInterface.class);
                Call<JsonResponse> call = requestInterface.login(login, motdepasse);
                call.enqueue(new Callback<JsonResponse>() {
                    @Override
                    public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                        if (response.code() == 200) {
                            JsonResponse jsonResponse = response.body();
                            Toast.makeText(getApplicationContext(), jsonResponse.getResponse(), Toast.LENGTH_SHORT).show();
                            if (jsonResponse.getResponse().equals("Admin connecté avec succès")) {
                                S = eusername.getText().toString();
                                Intent registerIntent = new Intent(LoginActivity.this, AdminMenu.class);
                                startActivity(registerIntent);
                            } else if (jsonResponse.getResponse().equals("Connecté avec succès")) {
                                S = eusername.getText().toString();
                                Intent registerIntent = new Intent(LoginActivity.this, CustMenuActivity.class);
                                startActivity(registerIntent);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }
        });


        inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

    }
}
