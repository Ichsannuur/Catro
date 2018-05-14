package com.ics.catro.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ics.catro.API.CatroAPI;
import com.ics.catro.API.RetrofitService;
import com.ics.catro.Adapter.DetailOtherUserAdapter;
import com.ics.catro.Object.OtherProfile;
import com.ics.catro.Object.Value;
import com.ics.catro.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailOtherUser extends AppCompatActivity {
    Intent i;
    TextView other_user,judul,followers,followed,score;
    Button follow,unfollow;
    List<OtherProfile> otherProfile = new ArrayList<>();
    RecyclerView recyclerOtherUser;
    ImageView other_image_profile;
    SharedPreferences preferences;
    ProgressDialog dialog;
    String time;
    DetailOtherUserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_other_user);
        dialog = new ProgressDialog(this);
        time = new SimpleDateFormat("HH:mm:ss").format(new Date());
        //Action Bar & Font
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.bar_layout_other);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        i = getIntent();
        initView();

        //Init RecyclerView
        recyclerOtherUser.setHasFixedSize(true);
        recyclerOtherUser.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));


        //Set preference for get this user
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        //Get Username
        other_user.setText(i.getStringExtra("other_username"));
        Picasso.with(getApplicationContext()).load("http://10.0.2.2/catro/user_image/"+i.getStringExtra("other_userImage")).into(other_image_profile);

        //Font Style
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "font/streetwear.ttf");
        judul.setTypeface(typeFace);

        //Check whether username is your user or not
        if(i.getStringExtra("other_username").equals(preferences.getString("getUsername",null))){
            follow.setVisibility(View.INVISIBLE);
        }

        check_user_is_followed();
        load_data_user();
        load_data_article_otherUser();

        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setTitle("Loading");
                dialog.show();
                addFollow();
            }
        });

        unfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setTitle("Loading");
                dialog.show();
                addUnfollow();
            }
        });
    }

    private void addUnfollow() {
        CatroAPI api = RetrofitService.service().create(CatroAPI.class);
        Call<Value> call = api.unfollow(preferences.getString("emailId",null),i.getStringExtra("other_username"));
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if (response.body().getValue().equals("1")){
                    dialog.dismiss();
                    follow.setVisibility(View.VISIBLE);
                    unfollow.setVisibility(View.INVISIBLE);
                    followers.setText(String.valueOf(Integer.parseInt(followers.getText().toString()) - 1));
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(DetailOtherUser.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void check_user_is_followed() {
        CatroAPI api = RetrofitService.service().create(CatroAPI.class);
        Call<Value> call = api.check_followed(preferences.getString("emailId",null),i.getStringExtra("other_username"));
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if (response.body().getValue().equals("1")){
                    follow.setVisibility(View.INVISIBLE);
                    unfollow.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(DetailOtherUser.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void load_data_user() {
        CatroAPI api = RetrofitService.service().create(CatroAPI.class);
        Call<Value> call = api.show_other_profile(i.getStringExtra("other_username"));
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                otherProfile = response.body().getOtherProfile();
                followers.setText(otherProfile.get(0).getFollowers() + "");
                followed.setText(otherProfile.get(0).getFollowed() + "");
                score.setText(otherProfile.get(0).getScore());
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(DetailOtherUser.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addFollow() {
        CatroAPI apifollow = RetrofitService.service().create(CatroAPI.class);
        Call<Value> call = apifollow.insert_follow(preferences.getString("emailId",null),i.getStringExtra("other_username")
        ,time);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if (response.body().getValue().equals("1")){
                    dialog.dismiss();
                    follow.setVisibility(View.INVISIBLE);
                    unfollow.setVisibility(View.VISIBLE);
                    followers.setText((Integer.parseInt(followers.getText().toString()) + 1) + "");
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(DetailOtherUser.this, "Failed load data user", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void load_data_article_otherUser() {
        CatroAPI api = RetrofitService.service().create(CatroAPI.class);
        Call<Value> call = api.show_other_profile(i.getStringExtra("other_username"));
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                otherProfile = response.body().getOtherProfile();
                adapter = new DetailOtherUserAdapter(otherProfile,getApplicationContext());
                recyclerOtherUser.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(DetailOtherUser.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        score = (TextView)findViewById(R.id.score);
        other_user = (TextView)findViewById(R.id.other_user);
        judul = (TextView)findViewById(R.id.judul);
        followers = (TextView)findViewById(R.id.followers);
        followed = (TextView)findViewById(R.id.followed);
        follow = (Button)findViewById(R.id.follow);
        unfollow = (Button)findViewById(R.id.unfollow);
        recyclerOtherUser = (RecyclerView)findViewById(R.id.recyclerOtherUser);
        other_image_profile = (ImageView)findViewById(R.id.other_image_profile);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
