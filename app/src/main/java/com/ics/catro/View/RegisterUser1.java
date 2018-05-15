package com.ics.catro.View;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ics.catro.API.CatroAPI;
import com.ics.catro.API.RetrofitService;
import com.ics.catro.Object.Value;
import com.ics.catro.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterUser1 extends AppCompatActivity {
    TextView judul,error;
    EditText username,tgl_lahir,no_tlp;
    Button next,prev;
    Calendar myCalendar;
    String optionGender,email,password;
    Intent i;
    RadioGroup radioGroup;
    RadioButton radioGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user1);
        judul = (TextView)findViewById(R.id.judul);
        error = (TextView)findViewById(R.id.error);
        intiate_UI();
        i = getIntent();
        email = i.getStringExtra("email1");
        password = i.getStringExtra("password1");
        myCalendar = Calendar.getInstance();

        //Font
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "font/streetwear.ttf");
        judul.setTypeface(typeFace);

        //Date
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                tgl_lahir.setText(sdf.format(myCalendar.getTime()));
            }

        };


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioGender = (RadioButton)findViewById(selectedId);

                if (username.getText().toString().equals("") || tgl_lahir.getText().toString().equals("") || no_tlp.getText().toString().equals("") || radioGroup.getCheckedRadioButtonId() == -1){
                    error.setText("* Isi terlebih dahulu");
                    error.setVisibility(View.VISIBLE);
                }else{
                    if (selectedId == R.id.male){
                        optionGender = "L";
                    }else{
                        optionGender = "P";
                    }
                    i = new Intent(getApplicationContext(),FinalRegister.class);
                    i.putExtra("username",username.getText().toString());
                    i.putExtra("tgl_lahir",tgl_lahir.getText().toString());
                    i.putExtra("email",email);
                    i.putExtra("password",password);
                    i.putExtra("no_tlp",no_tlp.getText().toString().trim());
                    i.putExtra("jenis_kelamin",optionGender);
                    startActivity(i);
                }
            }
        });

        tgl_lahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(RegisterUser1.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
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
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        tgl_lahir = (EditText)findViewById(R.id.tgl_lahir);
        no_tlp = (EditText)findViewById(R.id.no_tlp);
    }
}
