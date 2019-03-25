package com.mindorks.framework.mvvm.ui.chatlist;

import android.databinding.ObservableField;
import android.util.Log;

import com.mindorks.framework.mvvm.data.model.api.ChatlListResponse;
import com.mindorks.framework.mvvm.ui.feed.blogs.BlogItemViewModel;

public class ChatlistItemViewModel {
    public final ObservableField<String> name;

    public final ObservableField<String> content;


    public final ChatlistItemViewModel.ChatlistItemViewModelListener mListener;

    private final ChatlListResponse.Chatlist _chatlist;

    public ChatlistItemViewModel(ChatlListResponse.Chatlist chatlist, ChatlistItemViewModel.ChatlistItemViewModelListener listener) {
        this._chatlist = chatlist;
        this.mListener = listener;
        name = new ObservableField<>(_chatlist.getName());
        content = new ObservableField<>(_chatlist.getContent());
    }

    public void onItemClick() {
        Log.i("TestClick 1", _chatlist.getName());
        Log.i("TestClick 1", String.valueOf(_chatlist.getId()));
        Log.i("TestClick 1", String.valueOf(_chatlist.getCircle_id()));
        mListener.onItemClick(_chatlist.getName(), _chatlist.getId(), _chatlist.getCircle_id());

    }

    public boolean onItemLongClick() {
        mListener.onItemLongClick(_chatlist.getId(), _chatlist.getCircle_id());
        return true;
    }

    public interface ChatlistItemViewModelListener {

        void onItemClick(String blogUrl, Integer id, Integer circle_id);

        void onItemLongClick(Integer id, Integer circle_id);
    }


}
