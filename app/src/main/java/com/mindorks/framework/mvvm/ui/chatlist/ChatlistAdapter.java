package com.mindorks.framework.mvvm.ui.chatlist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mindorks.framework.mvvm.data.model.api.ChatlListResponse;
import com.mindorks.framework.mvvm.databinding.ActivityChatlistBinding;
import com.mindorks.framework.mvvm.databinding.ItemChatlistEmptyViewBinding;
import com.mindorks.framework.mvvm.databinding.ItemChatlistViewBinding;
import com.mindorks.framework.mvvm.ui.base.BaseViewHolder;
import com.mindorks.framework.mvvm.ui.chat.ChatActivity;
import com.mindorks.framework.mvvm.ui.home.HomeActivity;

import java.util.List;

public class ChatlistAdapter extends RecyclerView.Adapter<BaseViewHolder>  {

    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;

    private List<ChatlListResponse.Chatlist> _chatlistReponseList;

    private ChatlistAdapterListener mListener;

    public ChatlistActivity activity;

    public ChatlistAdapter(List<ChatlListResponse.Chatlist> chatlListResponseList) {
        _chatlistReponseList = chatlListResponseList;
    }

    @Override
    public int getItemCount() {
        if (_chatlistReponseList != null && _chatlistReponseList.size() > 0) {
            return _chatlistReponseList.size();
        } else {
            return 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (_chatlistReponseList != null && !_chatlistReponseList.isEmpty()) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                ItemChatlistViewBinding blogViewBinding = ItemChatlistViewBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new ChatlistAdapter.ChatlistViewHolder(blogViewBinding);
            case VIEW_TYPE_EMPTY:
            default:
                ItemChatlistEmptyViewBinding emptyViewBinding = ItemChatlistEmptyViewBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new ChatlistAdapter.EmptyViewHolder(emptyViewBinding);
        }
    }

    public void addItems(List<ChatlListResponse.Chatlist> chatlistList) {
        _chatlistReponseList.addAll(chatlistList);
        notifyDataSetChanged();
    }

    public void clearItems() {
        _chatlistReponseList.clear();
    }

    public void setListener(ChatlistAdapterListener listener) {
        this.mListener = listener;
    }

    public interface ChatlistAdapterListener {

        void onRetryClick();

        void onChatActivity(Integer id, Integer circle_id);
    }

    public class ChatlistViewHolder extends BaseViewHolder implements ChatlistItemViewModel.ChatlistItemViewModelListener {

        private ItemChatlistViewBinding _binding;

        private ChatlistItemViewModel _chatlistItemViewModel;

        public ChatlistViewHolder(ItemChatlistViewBinding binding) {
            super(binding.getRoot());
            this._binding = binding;
        }

        @Override
        public void onBind(int position) {

            Log.i("onBind","onBind adapter");
            final ChatlListResponse.Chatlist chatlist = _chatlistReponseList.get(position);
            _chatlistItemViewModel = new ChatlistItemViewModel(chatlist, this);
           _binding.setViewModel(_chatlistItemViewModel);

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            _binding.executePendingBindings();
            Log.i("onBind","onBind end adapter");
        }

        @Override
        public void onItemClick(String blogUrl, Integer id, Integer circle_id) {

            Log.i("TestClick 2",blogUrl);
            Log.i("TestClick 2", String.valueOf(id));
            Log.i("TestClick 2", String.valueOf(circle_id));
            mListener.onChatActivity(id, circle_id);
        }

        @Override
        public void onItemLongClick(Integer id, Integer circle_id) {
            Log.i("TestClick 2", String.valueOf(id));
            Log.i("TestClick 2", String.valueOf(circle_id));
            dialogConfirm(id);
        }

        private void dialogConfirm(int conv_id)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setMessage("Voulez vous quittez la conversation")
                    .setPositiveButton("Accepter", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            activity.getViewModel().quitConv(conv_id);;
                        }
                    })
                    .setNegativeButton("Refuser", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            // Create the AlertDialog object and return it
            builder.show();
        }

    }
    public class EmptyViewHolder extends BaseViewHolder implements ChatlistEmptyItemViewModel.ChatlistEmptyItemViewModelListener {

        private ItemChatlistEmptyViewBinding mBinding;

        public EmptyViewHolder(ItemChatlistEmptyViewBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            ChatlistEmptyItemViewModel emptyItemViewModel = new ChatlistEmptyItemViewModel(this);
            mBinding.setViewModel(emptyItemViewModel);
        }

        @Override
        public void onRetryClick() {
            mListener.onRetryClick();

        }
    }

}
