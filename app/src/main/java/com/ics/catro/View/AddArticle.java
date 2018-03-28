package com.ics.catro.View;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

public class AddArticle extends AppCompatActivity {
    Button save;
    ImageView image;
    EditText article;
    String mediaPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_article);
        renderUI();

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 0);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveArticle();
            }
        });

    }

    private void renderUI() {
        save = (Button)findViewById(R.id.save);
        image = (ImageView)findViewById(R.id.image_article);
        article = (EditText)findViewById(R.id.article);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == 0 && resultCode == RESULT_OK && null != data) {

                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);
                // Set the Image in ImageView for Previewing the Media
                image.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                cursor.close();

            } else {
                Toast.makeText(this, "You haven't picked Image/Video", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }

    private void saveArticle() {
        File file = new File(mediaPath);
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        //Parsing any media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"),file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file",file.getName(),requestBody);

        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
        RequestBody fileArticle = RequestBody.create(MediaType.parse("text/plain"), article.getText().toString());
        RequestBody fileDate = RequestBody.create(MediaType.parse("text/plain"), date);

        CatroAPI api = RetrofitService.service().create(CatroAPI.class);
        Call<Value> call = api.add_article(fileArticle,fileToUpload,filename,fileDate);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();
                if(value.equals("1")){
                    Toast.makeText(AddArticle.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(AddArticle.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                Toast.makeText(AddArticle.this, "Gagal", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}
