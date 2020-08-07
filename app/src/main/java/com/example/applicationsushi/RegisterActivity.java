package com.example.applicationsushi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {


    EditText eLogin ;
    EditText eNom ;
    EditText ePrenom ;
    EditText eNumTel ;
    EditText eMotDePasse ;
    EditText eConfirmMotDePasse ;
    Button buInscription ;
    Button buRetour ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        eLogin = (EditText) findViewById(R.id.editText);
        eNom = (EditText) findViewById(R.id.editText2);
        ePrenom = (EditText) findViewById(R.id.editText3);
        eNumTel = (EditText) findViewById(R.id.editText4);
        eMotDePasse = (EditText) findViewById(R.id.editText5);
        eConfirmMotDePasse = (EditText) findViewById(R.id.editText6);
        buInscription = (Button) findViewById(R.id.buttonView);
        buRetour = (Button) findViewById(R.id.buttonView2);

        buRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Loginintent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(Loginintent);
                finish();
            }
        });


        buInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String login = eLogin.getText().toString();
                String nom = eNom.getText().toString();
                String prenom = ePrenom.getText().toString();
                String numerotel = eNumTel.getText().toString();
                String motdepasse = eMotDePasse.getText().toString();
                String confirmerMotDePasse = eConfirmMotDePasse.getText().toString();


                if(login.equals("") || nom.equals("") || prenom.equals("") || numerotel.equals("") || motdepasse.equals("") || confirmerMotDePasse.equals("")){
                    Toast.makeText(getApplicationContext(),"Un champ est vide", Toast.LENGTH_SHORT).show();
                } else{
                    if(motdepasse.equals(confirmerMotDePasse)){
                        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://miamsushi.000webhostapp.com/connection/register.php/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        RequestInterface request = retrofit.create(RequestInterface.class);
                        Call<JsonResponse> call = request.register(login,nom,prenom,numerotel,motdepasse);
                        call.enqueue(new Callback<JsonResponse>() {
                            @Override
                            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                                if(response.code()==200){
                                    JsonResponse jsonResponse = response.body();
                                    Toast.makeText(getApplicationContext(),jsonResponse.getResponse().toString(), Toast.LENGTH_SHORT).show();
                                    if(jsonResponse.getResponse().equals("Registered Successfully")){
                                        Intent loginIntent = new Intent(RegisterActivity.this,LoginActivity.class);
                                        startActivity(loginIntent);
                                        finish();
                                    }
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), String.valueOf(response.code()),Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<JsonResponse> call, Throwable t) {
                                Toast.makeText(getApplicationContext(),"Erreur",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Les mot de passe ne se correspondent pas",Toast.LENGTH_SHORT).show();
                    }}
            }
        });

    }
}
