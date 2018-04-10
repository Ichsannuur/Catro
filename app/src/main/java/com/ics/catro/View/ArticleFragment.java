package com.ics.catro.View;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ics.catro.API.CatroAPI;
import com.ics.catro.API.RetrofitService;
import com.ics.catro.Adapter.ArticleAdapter;
import com.ics.catro.Object.Article;
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
public class ArticleFragment extends Fragment {
    RecyclerView recyclerViewArticle;
    List<Article> articleList = new ArrayList<>();
    ArticleAdapter adapter;
    SharedPreferences preferences;
    public ArticleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Preference
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_article, container, false);
        //Definisi UI
        recyclerViewArticle = (RecyclerView)v.findViewById(R.id.recyclerviewArticle);
        recyclerViewArticle.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewArticle.setLayoutManager(layoutManager);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadArticle();
    }

    public void loadArticle() {
        CatroAPI api = RetrofitService.service().create(CatroAPI.class);
        Call<Value> call = api.show_article(preferences.getString("emailId",""));
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                if(value.equals("1")){
                    articleList = response.body().getArticle();
                    adapter = new ArticleAdapter(articleList,getContext(),preferences.getString("emailId",""));
                    recyclerViewArticle.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                Toast.makeText(getContext(), "Ga dapat respond", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}
