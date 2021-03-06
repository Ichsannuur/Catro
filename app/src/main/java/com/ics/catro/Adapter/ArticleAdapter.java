package com.ics.catro.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ics.catro.API.CatroAPI;
import com.ics.catro.API.RetrofitService;
import com.ics.catro.Object.Article;
import com.ics.catro.Object.Value;
import com.ics.catro.R;
import com.ics.catro.View.DetailOtherUser;
import com.ics.catro.View.MenuActivity;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ichsan.Fatiha on 3/21/2018.
 */

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    List<Article> articleList;
    Context context;
    String sessionUser;

    public ArticleAdapter(List<Article> articleList, Context context, String sessionUser) {
        this.articleList = articleList;
        this.context = context;
        this.sessionUser = sessionUser;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Article article = articleList.get(position);
        holder.user_profile.setText(article.getUsername());
        holder.id_like.setText(article.getId_like() + "");
        holder.id_article.setText(article.getId_article() + "");
        holder.email.setText(article.getUsername());
        holder.tgl_profile.setText(article.getTgl_posting() + " " +article.getTime_posting());
        holder.article_detail.setText(article.getArticle());
        holder.countLiked.setText(article.getCountLiked() + "");
        holder.textImage.setText(article.getUser_image());
        if(article.getIsLiked() == 1){
            holder.likeIcon.setChecked(true);
        }else{
            holder.likeIcon.setChecked(false);
        }
        Picasso.with(context).load("http://10.0.2.2/catro/image/"+article.getGambar()).into(holder.articleImage);
        Picasso.with(context).load("http://10.0.2.2/catro/user_image/"+article.getUser_image()).into(holder.imageProfile);

    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView user_profile,tgl_profile,countLiked,id_article,email,id_like,article_detail,textImage;
        ImageView articleImage,imageProfile;
        CheckBox likeIcon;
        public ViewHolder(View itemView) {
            super(itemView);
            likeIcon = (CheckBox)itemView.findViewById(R.id.likeIcon);
            user_profile = (TextView)itemView.findViewById(R.id.user_profile);
            email = (TextView)itemView.findViewById(R.id.email);
            imageProfile = (ImageView)itemView.findViewById(R.id.image_profile);
            id_like = (TextView)itemView.findViewById(R.id.id_like);
            id_article = (TextView)itemView.findViewById(R.id.id_article);
            article_detail = (TextView)itemView.findViewById(R.id.article_detail);
            countLiked = (TextView)itemView.findViewById(R.id.count);
            tgl_profile = (TextView)itemView.findViewById(R.id.tgl_profile);
            articleImage = (ImageView) itemView.findViewById(R.id.article_image);
            textImage = (TextView)itemView.findViewById(R.id.textImage);

            //Show Profile Other User & Follow
            user_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    show_or_follow();
                }
            });

            likeIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                    String time = new SimpleDateFormat("HH:mm:ss").format(new Date());

                    if (likeIcon.isChecked()){
                        CatroAPI api = RetrofitService.service().create(CatroAPI.class);
                        Call<Value> call = api.insert_like("1",id_article.getText().toString(),
                                sessionUser,
                                Integer.parseInt(id_like.getText().toString()),
                                date,time);
                        call.enqueue(new Callback<Value>() {
                            @Override
                            public void onResponse(Call<Value> call, Response<Value> response) {
                                if (response.body().getValue().equals("1")){
                                    countLiked.setText(Integer.parseInt(countLiked.getText().toString()) + 1 + "");
                                    likeIcon.setChecked(true);
                                }else{
                                    Toast.makeText(context, "Gagal", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Value> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
                    }else{
                        CatroAPI api = RetrofitService.service().create(CatroAPI.class);
                        Call<Value> call = api.insert_like("0",id_article.getText().toString(),
                                sessionUser,
                                Integer.parseInt(id_like.getText().toString()),date,time);
                        call.enqueue(new Callback<Value>() {
                            @Override
                            public void onResponse(Call<Value> call, Response<Value> response) {
                                if (response.body().getValue().equals("1")){
                                    countLiked.setText(Integer.parseInt(countLiked.getText().toString()) - 1 + "");
                                    likeIcon.setChecked(false);
                                }else{
                                    Toast.makeText(context, "Gagal", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Value> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
                    }
                }
            });
        }

        private void show_or_follow() {
            Intent i = new Intent(context, DetailOtherUser.class);
            i.putExtra("other_username",user_profile.getText().toString());
            i.putExtra("other_userImage",textImage.getText().toString());
            context.startActivity(i);
        }
    }
}
