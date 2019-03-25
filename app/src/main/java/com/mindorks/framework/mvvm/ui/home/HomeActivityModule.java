package com.mindorks.framework.mvvm.ui.home;


import com.mindorks.framework.mvvm.data.DataManager;
import com.mindorks.framework.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeActivityModule {

    @Provides
    HomeViewModel provideHomeViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new HomeViewModel(dataManager, schedulerProvider);
    }
}