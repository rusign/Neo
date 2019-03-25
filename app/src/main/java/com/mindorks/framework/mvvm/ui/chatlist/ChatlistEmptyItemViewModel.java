package com.mindorks.framework.mvvm.ui.chatlist;


public class    ChatlistEmptyItemViewModel {

    private ChatlistEmptyItemViewModel.ChatlistEmptyItemViewModelListener mListener;

    public ChatlistEmptyItemViewModel(ChatlistEmptyItemViewModel.ChatlistEmptyItemViewModelListener listener) {
        this.mListener = listener;
    }

    public void onRetryClick() {
        mListener.onRetryClick();
    }

    public interface ChatlistEmptyItemViewModelListener {

        void onRetryClick();
    }
}