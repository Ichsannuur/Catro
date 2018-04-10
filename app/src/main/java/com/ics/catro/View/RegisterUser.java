package com.ics.catro.View;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ics.catro.API.CatroAPI;
import com.ics.catro.API.RetrofitService;
import com.ics.catro.Object.Value;
import com.ics.catro.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterUser extends AppCompatActivity {
    TextView judul,error;
    EditText password_re,password,email;
    Intent i = new Intent();
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        judul = (TextView)findViewById(R.id.judul);
        error = (TextView)findViewById(R.id.error);

        //Font
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "font/streetwear.ttf");
        judul.setTypeface(typeFace);

        intiate_UI();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if (email.getText().toString().equals("") || password.getText().toString().equals("") || password_re.getText().toString().equals("")){
                error.setText("* Isi terlebih dahulu");
                error.setVisibility(View.VISIBLE);
            }else{
                if (password.getText().toString().equals(password_re.getText().toString())){
                    i.putExtra("email",email.getText().toString().trim());
                    i.putExtra("password",password.getText().toString().trim());
                    startActivity(new Intent(getApplicationContext(),RegisterUser1.class));
                }else{
                    error.setText("* Password Berbeda");
                    error.setVisibility(View.VISIBLE);
                }
            }
            }
        });
    }

    private void intiate_UI() {
        password = (EditText)findViewById(R.id.password);
        password_re = (EditText)findViewById(R.id.password_re);
        email = (EditText)findViewById(R.id.email);
        next = (Button)findViewById(R.id.next);
    }

    private void clear_UI(){
        password.setText("");
        password_re.setText("");
        email.setText("");
    }

//    private void register() {
//        CatroAPI api = RetrofitService.service().create(CatroAPI.class);
//        Call<Value> call = api.registerUser(email.getText().toString().trim(),password.getText().toString().trim());
//        call.enqueue(new Callback<Value>() {
//            @Override
//            public void onResponse(Call<Value> call, Response<Value> response) {
//                String value = response.body().getValue();
//                String message = response.body().getMessage();
//
//                if (value.equals("1")){
//                    error.setVisibility(View.GONE);
//                    Toast.makeText(RegisterUser.this, message, Toast.LENGTH_SHORT).show();
//                    clear_UI();
//                }else if(value.equals("5")){
//                    error.setText("* " + message);
//                    error.setVisibility(View.VISIBLE);
//                    clear_UI();
//                }else{
//                    error.setText("* " + message);
//                    error.setVisibility(View.VISIBLE);
//                    clear_UI();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Value> call, Throwable t) {
//                t.printStackTrace();
//                Log.e("ERROR",t.getMessage());
//            }
//        });
//    }
}
