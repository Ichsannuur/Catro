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
    Intent i;
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
                    i = new Intent(RegisterUser.this,RegisterUser1.class);
                    i.putExtra("email1",email.getText().toString().trim());
                    i.putExtra("password1",password.getText().toString().trim());
                    startActivity(i);
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
}
