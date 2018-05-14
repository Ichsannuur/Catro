package com.ics.catro.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ics.catro.Object.OtherProfile;
import com.ics.catro.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ichsan.Fatiha on 5/11/2018.
 */

public class DetailOtherUserAdapter extends RecyclerView.Adapter<DetailOtherUserAdapter.ViewHolder> {
    List<OtherProfile> otherProfile;
    Context context;

    public DetailOtherUserAdapter(List<OtherProfile> otherProfile, Context context) {
        this.otherProfile = otherProfile;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_other_profile,parent,false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OtherProfile profile = otherProfile.get(position);
        Picasso.with(context).load("http://10.0.2.2/catro/image/"+profile.getImage()).into(holder.articleOtherImage);
    }

    @Override
    public int getItemCount() {
        return otherProfile.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView articleOtherImage;
        public ViewHolder(View itemView) {
            super(itemView);
            articleOtherImage = (ImageView)itemView.findViewById(R.id.articleOtherImage);
        }
    }
}
