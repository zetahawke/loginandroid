package com.example.asus.enmimente;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asus.enmimente.models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;


//import retrofit2.RestAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText text = (EditText)findViewById(R.id.editText);
                String value = text.getText().toString();

                EditText text2 = (EditText)findViewById(R.id.editText2);
                String value2 = text2.getText().toString();

                try {
                    loged(value, value2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

//    // Trailing slash is needed
//    public static final String BASE_URL = "http://www.mimolido.com";
//    private static Retrofit.Builder builder =
//            new Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create(gson));
////    Retrofit retrofit = new Retrofit.Builder()
////            .baseUrl(BASE_URL)
////            .addConverterFactory(GsonConverterFactory.create())
////            .build();

    private void loged (String mEmail, String mPassword) throws IOException {
        // TODO: 4/27/2016 Custom login process consuming mimolido api
        URL url = new URL("http://www.mimolido.com");
        Toast.makeText(MainActivity.this, "email: " + mEmail + ", pass:" + mPassword, Toast.LENGTH_SHORT).show();
        Gson gson = new GsonBuilder()
//                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url.toString())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

//        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(URL).setLogLevel(RestAdapter.LogLevel.FULL).build();



        MyApiEndpointInterface service = retrofit.create(MyApiEndpointInterface.class);

        Call<User> user = service.getUser(mEmail, mPassword);

        user.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                int statusCode = response.code();
                User user = response.body();

                Toast.makeText(MainActivity.this, "Te haz autenticado (o eso es lo que mi mente cree)", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Log error here since request failed
                Toast.makeText(MainActivity.this, "No te haz podido autenticar men (o eso es lo que mi mente cree)", Toast.LENGTH_SHORT).show();
            }
        });
//
//        return true;

//        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//        try {
//            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
//
//            readStream(in);
//
//            int asd = 0;
//        }
//        finally {
//            urlConnection.disconnect();
//        }
//        return true;
    }

//    private String readStream(InputStream is) {
//        try {
//            ByteArrayOutputStream bo = new ByteArrayOutputStream();
//            int i = is.read();
//            while(i != -1) {
//                bo.write(i);
//                i = is.read();
//            }
//            return bo.toString();
//        } catch (IOException e) {
//            return "";
//        }
//    }

    public interface MyApiEndpointInterface {
        // Request method and URL specified in the annotation
        // Callback for the parsed response is the last parameter

        @GET("/servicio/mimolidoServices.aspx?service=login&email={email}&password={password}")
        Call<User> getUser(@Path("email") String email, @Path("password") String password);

//        @POST("?service={service_name}")
//        Call<User> createUser(@Body User user, @Path("service_name") String service_name);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void button_click(){

    }
}
