package com.ics.catro.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ics.catro.API.CatroAPI;
import com.ics.catro.API.RetrofitService;
import com.ics.catro.Object.Value;
import com.ics.catro.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    TextView judul,error;
    EditText email,password;
    Button login;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
    }

    private void Flogin() {
        CatroAPI api = RetrofitService.service().create(CatroAPI.class);
        Call<Value> call = api.login(email.getText().toString().trim(),password.getText().toString().trim());
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")){
                    progressDialog.dismiss();
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
            public void onFailure(Call<Value> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void intiate_UI() {
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.login);
        error = (TextView)findViewById(R.id.error);
    }

    private void clear_UI(){
        password.setText("");
        email.setText("");
    }

}
