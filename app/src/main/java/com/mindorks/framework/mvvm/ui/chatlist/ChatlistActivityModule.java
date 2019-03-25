package com.mindorks.framework.mvvm.ui.chatlist;

import android.support.v7.widget.LinearLayoutManager;

import com.mindorks.framework.mvvm.data.DataManager;
import com.mindorks.framework.mvvm.ui.feed.blogs.BlogFragment;
import com.mindorks.framework.mvvm.utils.rx.SchedulerProvider;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;


@Module
public class ChatlistActivityModule {


    @Provides
    ChatlistAdapter provideChatlistAdapter(ChatlistActivity activity) {
        return new ChatlistAdapter(new ArrayList<>());
    }

    @Provides
    ChatlistViewModel provideChatlistViewModel(DataManager dataManager,
                                               SchedulerProvider schedulerProvider) {
        return new ChatlistViewModel(dataManager, schedulerProvider);
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(ChatlistActivity activity) {
        return new LinearLayoutManager(activity);
    }
}