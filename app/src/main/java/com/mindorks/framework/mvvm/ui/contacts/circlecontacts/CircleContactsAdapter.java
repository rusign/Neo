package com.mindorks.framework.mvvm.ui.contacts.circlecontacts;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mindorks.framework.mvvm.R;
import com.mindorks.framework.mvvm.ui.contacts.ContactsAdapter;
import com.mindorks.framework.mvvm.ui.contacts.ContactsCircleItem;
import com.mindorks.framework.mvvm.ui.contacts.contact.ContactActivity;

import java.util.List;

public class CircleContactsAdapter extends RecyclerView.Adapter<CircleContactsAdapter.MyViewHolder> {

    private Context mContext ;
    private List<CircleContactsItem> mData ;


    public CircleContactsAdapter(Context mContext, List<CircleContactsItem> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public CircleContactsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.circle_contact_item,parent,false);
        return new CircleContactsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CircleContactsAdapter.MyViewHolder holder, final int position) {

        holder._title.setText(mData.get(position).getEmail());
        holder._content.setText(mData.get(position).getName());
        //     holder.img_book_thumbnail.setImageResource(mData.get(position).getThumbnail());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("oclik", "click card");
                Intent intent = new Intent(mContext,ContactActivity.class);
                intent.putExtra("name",mData.get(position).getEmail());
                intent.putExtra("email",mData.get(position).getName());
                intent.putExtra("id",mData.get(position).getUser_id());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView _title;
        TextView _content;
        LinearLayout cardView ;

        public MyViewHolder(View itemView) {
            super(itemView);

            _title = (TextView) itemView.findViewById(R.id.emailId) ;
            _content = (TextView) itemView.findViewById(R.id.nameId) ;
            cardView = (LinearLayout) itemView.findViewById(R.id.circle_contact_id);
        }
    }
}