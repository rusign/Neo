package com.mindorks.framework.mvvm.ui.chat;

import com.mindorks.framework.mvvm.data.DataManager;
import com.mindorks.framework.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class ChatActivityModule {

    @Provides
    ChatViewModel provideChatViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new ChatViewModel(dataManager, schedulerProvider);
    }
}

