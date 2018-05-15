package com.ics.catro.View;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ics.catro.API.CatroAPI;
import com.ics.catro.API.RetrofitService;
import com.ics.catro.Object.Value;
import com.ics.catro.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfile extends AppCompatActivity {
    TextView judul,error,email;
    EditText username,password,tgl_lahir,no_tlp;
    String optionGender;
    Call<Value> call;
    SharedPreferences preferences;
    ImageView imageprofile;
    Calendar myCalendar;
    Button update;
    Boolean isClicked;
    RadioGroup radioGroup;
    File file;
    RadioButton radioButton,male,female;
    private static final int PICK_FROM_GALLERY = 2;
    String mediaPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        initView();
        myCalendar = Calendar.getInstance();
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        isClicked = false;

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

        tgl_lahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(UpdateProfile.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //Font
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "font/streetwear.ttf");
        judul.setTypeface(typeFace);

        //Get Data From User
        email.setText(preferences.getString("emailId",null));
        username.setText(preferences.getString("getUsername",null));
        tgl_lahir.setText(preferences.getString("getTgl_lahir",null));
        no_tlp.setText(preferences.getString("getNo_Tlp",null));
        if (preferences.getString("getJenisKelamin",null).equals("L")){
            male.setChecked(true);
        }else{
            female.setChecked(true);
        }
        Picasso.with(this).load("http://10.0.2.2/catro/user_image/"+preferences.getString("getImage",null)).into(imageprofile);
        imageprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isClicked = true;
                Intent i = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, PICK_FROM_GALLERY);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton)findViewById(selectedId);
                if (selectedId == R.id.male){
                    optionGender = "L";
                }else{
                    optionGender = "P";
                }
                if(isClicked == true){
                    update_data(optionGender);
                }else{
                    update_data_withoutImage(optionGender);
                }
            }
        });
    }

    private void update_data_withoutImage(final String gender) {
        CatroAPI api = RetrofitService.service().create(CatroAPI.class);
        call = api.update_profile_wihtout_image(preferences.getString("emailId",null),
                username.getText().toString(),
                tgl_lahir.getText().toString(),
                no_tlp.getText().toString(),
                gender);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if(response.body().getValue().equals("1")){
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("getUsername",username.getText().toString().trim());
                    editor.putString("getTgl_lahir",tgl_lahir.getText().toString().trim());
                    editor.putString("getNo_Tlp",no_tlp.getText().toString().trim());
                    editor.putString("getJenisKelamin",gender);
                    editor.apply();
                    startActivity(new Intent(getApplicationContext(),MenuActivity.class));
                    finish();
                    Toast.makeText(UpdateProfile.this, "Success Change Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(UpdateProfile.this, "No Response", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void update_data(final String gender) {
        file = new File(mediaPath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"),file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file",file.getName(),requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), preferences.getString("emailId",null));
        final RequestBody user = RequestBody.create(MediaType.parse("text/plain"), username.getText().toString().trim());
        RequestBody tgl = RequestBody.create(MediaType.parse("text/plain"), tgl_lahir.getText().toString());
        RequestBody jk = RequestBody.create(MediaType.parse("text/plain"), gender);
        RequestBody tlp = RequestBody.create(MediaType.parse("text/plain"), no_tlp.getText().toString().trim());

        CatroAPI api = RetrofitService.service().create(CatroAPI.class);
        call = api.update_profile(email,fileToUpload,filename,user,tgl,tlp,jk);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if(response.body().getValue().equals("1")){
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("getUsername",username.getText().toString().trim());
                    editor.putString("getImage", String.valueOf(file.getName()));
                    editor.putString("getTgl_lahir",tgl_lahir.getText().toString().trim());
                    editor.putString("getNo_Tlp",no_tlp.getText().toString().trim());
                    editor.putString("getJenisKelamin",gender);
                    editor.apply();
                    startActivity(new Intent(getApplicationContext(),MenuActivity.class));
                    finish();
                    Toast.makeText(UpdateProfile.this, "Success Change Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(UpdateProfile.this, "No Response", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        judul = (TextView)findViewById(R.id.judul);
        error = (TextView)findViewById(R.id.error);
        username = (EditText)findViewById(R.id.username);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        male = (RadioButton)findViewById(R.id.male);
        female = (RadioButton)findViewById(R.id.female);
        update = (Button)findViewById(R.id.update);
        password = (EditText)findViewById(R.id.password);
        tgl_lahir = (EditText)findViewById(R.id.tgl_lahir);
        no_tlp = (EditText)findViewById(R.id.no_tlp);
        email = (TextView)findViewById(R.id.email);
        imageprofile = (ImageView)findViewById(R.id.image_profile);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK){

            // Get the Image from data
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            mediaPath = cursor.getString(columnIndex);
            // Set the Image in ImageView for Previewing the Media
            imageprofile.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
            cursor.close();
        }
    }
}
