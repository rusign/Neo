package com.mindorks.framework.mvvm.ui.contacts;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.mindorks.framework.mvvm.R;
import com.mindorks.framework.mvvm.ui.contacts.circlecontacts.CircleContactsActivity;
import com.mindorks.framework.mvvm.ui.home.HomeActivity;

import java.util.List;

public class ContactsAdapter  extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> {

    private Context mContext ;
    private List<ContactsCircleItem> mData ;
    public ContactsActivity activity;


    public ContactsAdapter(Context mContext, List<ContactsCircleItem> mData) {
            this.mContext = mContext;
            this.mData = mData;
            }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view ;
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            view = mInflater.inflate(R.layout.card,parent,false);
            return new MyViewHolder(view);
            }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder._title.setText(mData.get(position).getTitle());
        holder._content.setText(mData.get(position).getContent());holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.i("onLongClick", "click card");
                dialogConfirm(mData.get(position).getId());
               // Intent intent = CircleContactsActivity.newIntent(mContext);
                //intent.putExtra("id",mData.get(position).getId());
                //mContext.startActivity(intent);
                return true;
              }
             });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("oclik", "click card");
                    Intent intent = CircleContactsActivity.newIntent(mContext);
                    intent.putExtra("id",mData.get(position).getId());
                    mContext.startActivity(intent);
                }

            });
    }

    private void dialogConfirm(int circle_id)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Voulez vous quittez le cercle")
                .setPositiveButton("Accepter", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        activity._ContactsVM.quitCircle(circle_id);;
                    }
                })
                .setNegativeButton("Refuser", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
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