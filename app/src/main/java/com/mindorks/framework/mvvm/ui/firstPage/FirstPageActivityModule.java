package com.mindorks.framework.mvvm.ui.firstPage;

import com.mindorks.framework.mvvm.data.DataManager;
import com.mindorks.framework.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;


@Module
public class FirstPageActivityModule {

    @Provides
    FirstPageViewModel provideFirstPageViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new FirstPageViewModel(dataManager, schedulerProvider);
    }
}