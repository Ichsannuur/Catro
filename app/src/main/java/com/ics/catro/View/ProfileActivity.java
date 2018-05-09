package com.ics.catro.View;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ics.catro.API.CatroAPI;
import com.ics.catro.API.RetrofitService;
import com.ics.catro.Adapter.ProfileAdapter;
import com.ics.catro.Object.Profile;
import com.ics.catro.Object.Value;
import com.ics.catro.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileActivity extends Fragment {
    RecyclerView recyclerView;
    List<Profile> profileList = new ArrayList<>();
    ProfileAdapter adapter;
    TextView nama_pengguna;
    Button logout;
    ImageView image_profile;
    SharedPreferences preferences;
    public ProfileActivity() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Preference
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        recyclerView = (RecyclerView)v.findViewById(R.id.recyclerview);
        nama_pengguna = (TextView)v.findViewById(R.id.nama_pengguna);
        image_profile = (ImageView)v.findViewById(R.id.image_profile);
        logout = (Button)v.findViewById(R.id.logout);
        //Set data from login user
        nama_pengguna.setText(preferences.getString("getUsername",null));
        Picasso.with(getContext()).load("http://10.0.2.2/catro/user_image/"+preferences.getString("getImage",null))
                .into(image_profile);
        recyclerView.setHasFixedSize(true);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Logout Function
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity().getApplicationContext(),LoginActivity.class));
                SharedPreferences.Editor edit = preferences.edit();
                edit.clear();
                edit.commit();
                getActivity().finish();
            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));

        CatroAPI api = RetrofitService.service().create(CatroAPI.class);
        Call<Value> call = api.show_article_profile(preferences.getString("emailId",""));
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                if(value.equals("1")){
                    profileList = response.body().getProfileList();
                    adapter = new ProfileAdapter(getActivity(),profileList);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                Toast.makeText(getContext(), "Gagal", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}
