package com.ics.catro.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.ics.catro.Object.SearchProfile;
import com.ics.catro.R;
import com.ics.catro.View.DetailOtherUser;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ichsan.Fatiha on 5/8/2018.
 */

public class ListSearchProfileAdapter extends RecyclerView.Adapter<ListSearchProfileAdapter.ViewHolder> {
    List<SearchProfile> searchList;
    Context context;

    public ListSearchProfileAdapter(List<SearchProfile> searchList, Context context) {
        this.searchList = searchList;
        this.context = context;
    }

    @Override
    public ListSearchProfileAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_search,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ListSearchProfileAdapter.ViewHolder holder, int position) {
        SearchProfile searchProfile = searchList.get(position);
        holder.search_username.setText(searchProfile.getUsername());
        holder.image.setText(searchProfile.getImage());
        Picasso.with(context).load("http://10.0.2.2/catro/user_image/"+searchProfile.getImage())
                .into(holder.search_image);
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView search_image;
        TextView search_username,image;
        RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            search_image = (ImageView)itemView.findViewById(R.id.search_image);
            image = (TextView) itemView.findViewById(R.id.image);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayout);
            search_username = (TextView)itemView.findViewById(R.id.search_user);

            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, DetailOtherUser.class);
                    i.putExtra("other_username",search_username.getText().toString());
                    i.putExtra("other_userImage",image.getText().toString());
                    context.startActivity(i);
                }
            });
        }
    }
}
