package com.ics.catro.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ics.catro.Object.Profile;
import com.ics.catro.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ichsan.Fatiha on 3/12/2018.
 */

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {
    Context context;
    List<Profile> profileList;

    public ProfileAdapter(Context context, List<Profile> profileList) {
        this.context = context;
        this.profileList = profileList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile,parent,false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Profile object = profileList.get(position);
        holder.text.setText(object.getGambar());
        Picasso.with(context).load("http://10.0.2.2/catro/image/"+object.getGambar()).into(holder.articleImage);
    }

    @Override
    public int getItemCount() {
        return profileList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        ImageView articleImage;
        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView)itemView.findViewById(R.id.text);
            articleImage = (ImageView)itemView.findViewById(R.id.articleImage);
        }
    }
}
