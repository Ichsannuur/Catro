package com.ics.catro.View;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.ics.catro.API.CatroAPI;
import com.ics.catro.API.RetrofitService;
import com.ics.catro.Adapter.ListSearchProfileAdapter;
import com.ics.catro.Object.Article;
import com.ics.catro.Object.SearchProfile;
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
public class SearchFragment extends Fragment {
    List<SearchProfile> searchlist = new ArrayList<>();
    ListSearchProfileAdapter adapter;
    RecyclerView recyclerView;
    EditText search;
    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        search = (EditText)v.findViewById(R.id.search);
        recyclerView = (RecyclerView)v.findViewById(R.id.recyclerview);
        //Definisi UI
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (search.getText().toString().equals("")){
                    recyclerView.setVisibility(View.INVISIBLE);
                }else{
                    recyclerView.setVisibility(View.VISIBLE);
                    showList(search.getText().toString(),recyclerView);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return v;
    }

    private void showList(String search, final RecyclerView rv) {
        CatroAPI api = RetrofitService.service().create(CatroAPI.class);
        Call<Value> call = api.search_profile(search);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if(response.body().getValue().equals("1")){
                    searchlist = response.body().getSearchProfile();
                    adapter = new ListSearchProfileAdapter(searchlist,getContext());
                    rv.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getContext(), "Failed load data...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
