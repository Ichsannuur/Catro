package com.ics.catro.View;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ics.catro.API.CatroAPI;
import com.ics.catro.API.RetrofitService;
import com.ics.catro.Adapter.ProfileAdapter;
import com.ics.catro.Object.Profile;
import com.ics.catro.Object.Value;
import com.ics.catro.R;

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
    public ProfileActivity() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        recyclerView = (RecyclerView)v.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));

        CatroAPI api = RetrofitService.service().create(CatroAPI.class);
        Call<Value> call = api.show_article_profile();
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
