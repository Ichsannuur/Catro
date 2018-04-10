package com.ics.catro.View;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ics.catro.R;

public class RegisterUser1 extends AppCompatActivity {
    TextView judul,error;
    EditText username,tgl_lahir,no_tlp;
    Button next,prev;
    Intent i = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user1);
        judul = (TextView)findViewById(R.id.judul);
        error = (TextView)findViewById(R.id.error);
        intiate_UI();
        //Font
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "font/streetwear.ttf");
        judul.setTypeface(typeFace);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().equals("") || tgl_lahir.getText().toString().equals("") || no_tlp.getText().toString().equals("")){
                    error.setText("* Isi terlebih dahulu");
                    error.setVisibility(View.VISIBLE);
                }else{
                    i.putExtra("username",username.getText().toString().trim());
                    i.putExtra("tgl_lahir",tgl_lahir.getText().toString().trim());
                    i.putExtra("no_tlp",no_tlp.getText().toString().trim());
                    startActivity(new Intent(getApplicationContext(),RegisterResult.class));
                }
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void intiate_UI() {
        prev = (Button)findViewById(R.id.prev);
        next = (Button)findViewById(R.id.next);
        username = (EditText)findViewById(R.id.username);
        tgl_lahir = (EditText)findViewById(R.id.tgl_lahir);
        no_tlp = (EditText)findViewById(R.id.no_tlp);
    }
}
