package com.ics.catro.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ics.catro.Object.Article;
import com.ics.catro.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ichsan.Fatiha on 3/21/2018.
 */

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    List<Article> articleList;
    Context context;

    public ArticleAdapter(List<Article> articleList, Context context) {
        this.articleList = articleList;
        this.context = context;
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
        holder.tgl_profile.setText(article.getTgl_posting());
        Picasso.with(context).load(R.drawable.yeezy).into(holder.articleImage);
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView user_profile,tgl_profile;
        ImageView articleImage;
        CheckBox likeIcon;
        public ViewHolder(View itemView) {
            super(itemView);
            likeIcon = (CheckBox)itemView.findViewById(R.id.likeIcon);
            user_profile = (TextView)itemView.findViewById(R.id.user_profile);
            tgl_profile = (TextView)itemView.findViewById(R.id.tgl_profile);
            articleImage = (ImageView) itemView.findViewById(R.id.article_image);

            likeIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(likeIcon.isChecked() == true){
                        Toast.makeText(context, "Yes", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context, "No", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}
