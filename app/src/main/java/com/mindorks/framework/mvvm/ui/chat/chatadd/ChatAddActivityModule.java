package com.mindorks.framework.mvvm.ui.chat.chatadd;

import com.mindorks.framework.mvvm.data.DataManager;
import com.mindorks.framework.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class ChatAddActivityModule {
    @Provides
    ChatAddViewModel provideChatAddViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new ChatAddViewModel(dataManager, schedulerProvider);
    }
}
