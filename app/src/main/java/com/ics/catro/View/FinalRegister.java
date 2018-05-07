package com.ics.catro.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ics.catro.API.CatroAPI;
import com.ics.catro.API.RetrofitService;
import com.ics.catro.Object.Value;
import com.ics.catro.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinalRegister extends AppCompatActivity {
    TextView judul,error;
    ImageView profileImage;
    Intent i;
    Button prev,save;
    String mediaPath,date,time;
    ProgressDialog dialog;
    private static final int PICK_FROM_GALLERY = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_register);
        initView();
        //Get Current Date & Time
        date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        time = new SimpleDateFormat("HH:mm:ss").format(new Date());

        dialog = new ProgressDialog(this);
        //Font
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "font/streetwear.ttf");
        judul.setTypeface(typeFace);

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                i.setType("image/*");
//                i.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(i.createChooser(i,"Select Option"),PICK_FROM_GALLERY);
                i = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, PICK_FROM_GALLERY);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setTitle("Loading");
                dialog.show();
                register(date,time);
            }
        });
    }

    private void initView() {
        judul = (TextView)findViewById(R.id.judul);
        profileImage = (ImageView)findViewById(R.id.image_profile);
        prev = (Button)findViewById(R.id.prev);
        save = (Button)findViewById(R.id.save);
        error = (TextView)findViewById(R.id.error);
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
            profileImage.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
            cursor.close();
        }
    }

    private void register(String date_posting,String time_posting) {
        i = getIntent();

        File file = new File(mediaPath);
        //Parsing any media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"),file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file",file.getName(),requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), i.getStringExtra("email"));
        RequestBody password = RequestBody.create(MediaType.parse("text/plain"), i.getStringExtra("password"));
        RequestBody username = RequestBody.create(MediaType.parse("text/plain"), i.getStringExtra("username"));
        RequestBody jenis_kelamin = RequestBody.create(MediaType.parse("text/plain"), i.getStringExtra("jenis_kelamin"));
        RequestBody tgl_lahir = RequestBody.create(MediaType.parse("text/plain"), i.getStringExtra("tgl_lahir"));
        RequestBody no_tlp = RequestBody.create(MediaType.parse("text/plain"), i.getStringExtra("no_tlp"));
        RequestBody tgl_join = RequestBody.create(MediaType.parse("text/plain"), date_posting);
        RequestBody time_join = RequestBody.create(MediaType.parse("text/plain"), time_posting);

        CatroAPI api = RetrofitService.service().create(CatroAPI.class);
        Call<Value> call = api.registerUser(email,username,password,fileToUpload,filename,tgl_lahir,no_tlp,jenis_kelamin,tgl_join,time_join);

        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")){
                    dialog.dismiss();
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    Toast.makeText(FinalRegister.this, message, Toast.LENGTH_SHORT).show();
                }else if(value.equals("5")){
                    dialog.dismiss();
                    error.setText("* " + message);
                    error.setVisibility(View.VISIBLE);
                }else{
                    dialog.dismiss();
                    error.setText("* " + message);
                    error.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                t.printStackTrace();
                Log.e("ERROR",t.getMessage());
                dialog.dismiss();
            }
        });
    }
}
