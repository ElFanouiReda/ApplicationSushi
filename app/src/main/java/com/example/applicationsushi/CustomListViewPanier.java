package com.example.applicationsushi;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CustomListViewPanier extends ArrayAdapter<String> {

    private String[] nom;
    private String[] description;
    private int[] quantite;
    int idPlatt[];
    String loginUti[] ;
    int l ;
    private String[] imagePath;
    private int[] idPlat;
    private Activity context;
    ViewHolder viewHolder;

    BufferedInputStream is;
    String line = null;
    String result = null;

    Bitmap bitmap;

    public CustomListViewPanier(Activity context, int[] idPlat, String[] nom, String[] description, int[] quant, String[] imagePath) {
        super(context, R.layout.row_panier, nom);

        this.context = context;
        this.nom = nom;
        this.idPlat = idPlat;
        this.description = description;
        this.quantite = quant;
        this.imagePath = imagePath;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r = convertView;
        ImageView info, unbuy, buy;

        //dp data
        if (r == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            r = layoutInflater.inflate(R.layout.row_panier, null, true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) r.getTag();

        }
        viewHolder.dpNom.setText(nom[position]);
        viewHolder.dpDescription.setText(description[position]);
        viewHolder.dpQ.setText("" + quantite[position]);
        new GetImageFromUrl(viewHolder.imagePlat).execute(imagePath[position]);

        /*//image clickable
        info = (ImageView)r.findViewById(R.id.imageView2);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context , LoginActivity.class);
                context.startActivity(i);
            }
        });*/

        buy = (ImageView) r.findViewById(R.id.imageView4);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ss;

                ss = LoginActivity.S;
                int idd = idPlat[position];

                Retrofit retrofit = new Retrofit.Builder().baseUrl("https://miamsushi.000webhostapp.com/connection/addPanier.php/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RequestInterface request = retrofit.create(RequestInterface.class);
                Call<JsonResponse> call = request.addPanier(ss, idd);
                call.enqueue(new Callback<JsonResponse>() {
                    @Override
                    public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                        if (response.code() == 200) {
                            JsonResponse jsonResponse = response.body();

                            Toast.makeText(context.getApplicationContext(), "Un article ajouté", Toast.LENGTH_SHORT).show();
                            try {
                                Toast.makeText(context.getApplicationContext(), "gsdgs", Toast.LENGTH_SHORT).show();
                                l=idPlat[position];
                                l = getQ(l);
                                viewHolder.dpQ.setText(""+l);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }

                    @Override
                    public void onFailure(Call<JsonResponse> call, Throwable t) {
                        Toast.makeText(context.getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            ;


        });

        unbuy = (ImageView) r.findViewById(R.id.imageView3);
        unbuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ss;

                ss = LoginActivity.S;
                int idd = idPlat[position];

                Retrofit retrofit = new Retrofit.Builder().baseUrl("https://miamsushi.000webhostapp.com/connection/delPanier.php/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RequestInterface request = retrofit.create(RequestInterface.class);
                Call<JsonResponse> call = request.delPanier(ss, idd);
                call.enqueue(new Callback<JsonResponse>() {
                    @Override
                    public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                        if (response.code() == 200) {
                            JsonResponse jsonResponse = response.body();
                            //Toast.makeText(context.getApplicationContext(),jsonResponse.getResponse().toString(), Toast.LENGTH_SHORT).show();
                            //if(jsonResponse.getResponse().equals("Removed Successfully")){
                            Toast.makeText(context.getApplicationContext(), "Un article retiré", Toast.LENGTH_SHORT).show();
                            //}
                        }
                        //else{
                        //    Toast.makeText(context.getApplicationContext(), String.valueOf(response.code()),Toast.LENGTH_SHORT).show();
                        //}
                    }

                    @Override
                    public void onFailure(Call<JsonResponse> call, Throwable t) {
                        Toast.makeText(context.getApplicationContext(), "Erreur", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return r;
    }


    public class ViewHolder {

        TextView dpNom;
        TextView dpQ;
        TextView dpDescription;
        ImageView bInfo;
        ImageView bUnbuy;
        ImageView imagePlat;

        ViewHolder(View v) {
            dpNom = (TextView) v.findViewById(R.id.textView);
            dpDescription = (TextView) v.findViewById(R.id.textView2);
            dpQ = (TextView) v.findViewById(R.id.textView4);
            bInfo = (ImageView) v.findViewById(R.id.imageView2);
            bUnbuy = (ImageView) v.findViewById(R.id.imageView3);
            imagePlat = (ImageView) v.findViewById(R.id.imageView);

        }
    }

    public class GetImageFromUrl extends AsyncTask<String, Void, Bitmap> {

        ImageView imageView;

        public GetImageFromUrl(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... url) {
            String urldisplay = url[0];
            bitmap = null;


            try {

                InputStream inputStream = new java.net.URL(urldisplay).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);

            } catch (Exception e) {
                e.printStackTrace();
            }


            return bitmap;
        }


        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
        }
    }

    public int getQ(Integer id) throws JSONException {
        try {
            URL url = new URL("https://miamsushi.000webhostapp.com/connection/dpPanier.php/");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            is = new BufferedInputStream(con.getInputStream());
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }

            is.close();
            result = sb.toString();

        } catch (Exception ex) {
            ex.printStackTrace();
        }


        try {

            JSONArray js = new JSONArray(result);
            JSONObject jo = null;

            idPlatt = new int[js.length()];
            loginUti = new String[js.length()];

            for (int i = 0; i <= js.length(); i++) {
                jo = js.getJSONObject(i);

                if ((LoginActivity.S).equals(jo.getString("loginUtilisateur")) && id.equals(jo.getInt("idPlat")) ) {

                    int ll = jo.getInt("quantite");
                    ll++ ;
                    return ll;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

}