package com.ics.catro.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ics.catro.API.CatroAPI;
import com.ics.catro.API.RetrofitService;
import com.ics.catro.Object.Login;
import com.ics.catro.Object.Value;
import com.ics.catro.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    TextView judul,error,register;
    EditText email,password;
    Button login;
    ProgressDialog progressDialog;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Preference
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        if (preferences.contains("emailId")){
            startActivity(new Intent(LoginActivity.this,MenuActivity.class));
            finish();
        }


        judul = (TextView)findViewById(R.id.judul);
        progressDialog = new ProgressDialog(this);
        //Font
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "font/streetwear.ttf");
        judul.setTypeface(typeFace);

        intiate_UI();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Loading");
                progressDialog.show();
                Flogin();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegisterUser.class));
            }
        });
    }

    private void Flogin() {
        CatroAPI api = RetrofitService.service().create(CatroAPI.class);
//        Call<Login> call = api.login(email.getText().toString().trim(),password.getText().toString().trim());
        Call<Login> call = api.login(email.getText().toString().trim(),password.getText().toString().trim());
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")){
                    progressDialog.dismiss();
                    //Put session
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("emailId",email.getText().toString());
                    editor.putString("getUsername",response.body().getUsername());
                    editor.putString("getImage",response.body().getImage());
                    editor.apply();
                    startActivity(new Intent(LoginActivity.this, MenuActivity.class));
                    clear_UI();
                }else{
                    progressDialog.dismiss();
                    error.setVisibility(View.VISIBLE);
                    error.setText("*" + message);
                    clear_UI();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "No Response", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
//        call.enqueue(new Callback<Login>() {
//            @Override
//            public void onResponse(Call<Login> call, Response<Login> response) {
//                String value = response.body().getValue();
//                String message = response.body().getMessage();
//                String getUsername = response.body().getUsername();
//                String getImage = response.body().getImage();
//
//                if (value.equals("1")){
//                    progressDialog.dismiss();
//                    //Put session
//                    SharedPreferences.Editor editor = preferences.edit();
//                    editor.putString("emailId",email.getText().toString());
//                    editor.putString("getUsername",getUsername);
//                    editor.putString("getImage",getImage);
//                    editor.apply();
//                    startActivity(new Intent(LoginActivity.this, MenuActivity.class));
//                    clear_UI();
//                }else{
//                    progressDialog.dismiss();
//                    error.setVisibility(View.VISIBLE);
//                    error.setText("*" + message);
//                    clear_UI();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Login> call, Throwable t) {
//                progressDialog.dismiss();
//                Toast.makeText(LoginActivity.this, "No Response", Toast.LENGTH_SHORT).show();
//                t.printStackTrace();
//             }
//           });
    }

    private void intiate_UI() {
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.login);
        register = (TextView)findViewById(R.id.register);
        error = (TextView)findViewById(R.id.error);
    }

    private void clear_UI(){
        password.setText("");
        email.setText("");
    }

}
