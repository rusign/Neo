package com.mindorks.framework.mvvm.ui.contacts.circleinvitation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mindorks.framework.mvvm.R;
import com.mindorks.framework.mvvm.ui.contacts.circleinvitation.CircleInvitationItem;

import java.util.List;

public class CircleInvitationAdapter extends RecyclerView.Adapter<CircleInvitationAdapter.MyViewHolder> {

    private Context mContext ;
    private List<CircleInvitationItem> mData ;
    public CircleInvitationActivity activity;


    public CircleInvitationAdapter(Context mContext, List<CircleInvitationItem> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public CircleInvitationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.card,parent,false);
        return new CircleInvitationAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CircleInvitationAdapter.MyViewHolder holder, final int position) {

        holder._title.setText(mData.get(position).getTitle());
        holder._content.setText(mData.get(position).getContent());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("oclik", "click card");
                dialogConfirm(mData.get(position).getId());
              //  Intent intent = CircleInvitationActivity.newIntent(mContext);
                //intent.putExtra("id",mData.get(position).getId());
                //mContext.startActivity(intent);
            }
        });
    }

    private void dialogConfirm(int circle_id)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Voulez vous acceptez l'invitation")
                .setPositiveButton("Accepter", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        activity.getViewModel().joinCircle(circle_id);
                    }
                })
                .setNegativeButton("Refuser", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        activity.getViewModel().refectCircle(circle_id);
                    }
                });
        // Create the AlertDialog object and return it
         builder.show();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView _title;
        TextView _content;
        CardView cardView ;

        public MyViewHolder(View itemView) {
            super(itemView);

            _title = (TextView) itemView.findViewById(R.id.circle_title_id) ;
            _content = (TextView) itemView.findViewById(R.id.circle_content_id) ;
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
        }
    }
}