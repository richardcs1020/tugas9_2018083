package com.example.pertemuan5_recycleview;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import com.example.pertemuan5_recycleview.databinding.ActivityMain4Binding;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
public class MainActivity4 extends AppCompatActivity implements
        View.OnClickListener{
    //declaration variable
    private ActivityMain4Binding binding;
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;
    String index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setup view binding
        binding =
                ActivityMain4Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.fetchButton.setOnClickListener(this);

        dl = (DrawerLayout) findViewById(R.id.dl);
        abdt = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);
        dl.addDrawerListener(abdt);
        abdt.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_profile) {
                    Intent a = new Intent(MainActivity4.this, MainActivity.class);
                    startActivity(a);
                } else if (id == R.id.nav_alarm) {
                    Intent a = new Intent(MainActivity4.this, MainActivity2.class);
                    startActivity(a);
                } else if (id == R.id.nav_sql) {
                    Intent a = new Intent(MainActivity4.this, MainActivity3.class);
                    startActivity(a);
                }else if (id == R.id.nav_api){
                    Intent a = new Intent(MainActivity4.this, MainActivity4.class);
                    startActivity(a);
                }
                return true;
            }
        });
    }
    //onclik button fetch
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.fetch_button){
            index = binding.inputId.getText().toString();
            try {
                getData();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }
    //get data using api link
    public void getData() throws MalformedURLException {
        Uri uri = Uri.parse("https://www.thecocktaildb.com/api/json/v1/1/search.php?s=margarita")
                .buildUpon().build();
        URL url = new URL(uri.toString());
        new DOTask().execute(url);
    }
    class DOTask extends AsyncTask<URL, Void, String>{
        //connection request
        @Override
        protected String doInBackground(URL... urls) {
            URL url = urls [0];
            String data = null;
            try {
                data = NetworkUtils.makeHTTPRequest(url);
            }catch (IOException e){
                e.printStackTrace();
            }
            return data;
        }
        @Override
        protected void onPostExecute(String s){
            try {
                parseJson(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //get data json
        public void parseJson(String data) throws JSONException{
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(data);
            }catch (JSONException e){
                e.printStackTrace();
            }

            JSONArray cityArray = jsonObject.getJSONArray("drinks");
            for (int i =0; i <cityArray.length(); i++){
                JSONObject obj = cityArray.getJSONObject(i);
                String Sobj = obj.get("idDrink").toString();
                if (Sobj.equals(index)){
                    String id = obj.get("idDrink").toString();
                    binding.resultId.setText(id);
                    String name = obj.get("strDrink").toString();
                    binding.resultName.setText(name);
                    String date = obj.get("dateModified").toString();
                    binding.resultDate.setText(date);
                    String category = obj.get("strCategory").toString();
                    binding.resultCategory.setText(category);
                    String intructions = obj.get("strInstructions").toString();
                    binding.resultIntructions.setText(intructions);
                    String measure1 = obj.get("strMeasure1").toString();
                    binding.resultMeasure1.setText(measure1);
                    String imageattribution = obj.get("strImageAttribution").toString();
                    binding.resultAttribution.setText(imageattribution);
                    String imagesource = obj.get("strImageSource").toString();
                    binding.resultImage.setText(imagesource);
                    String drinkthumb = obj.get("strDrinkThumb").toString();
                    binding.resultDrinkthumb.setText(drinkthumb);
                    break;
                }
                else{
                    binding.resultName.setText("Not Found");
                }
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}

