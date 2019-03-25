package com.mindorks.framework.mvvm.ui.call;

import com.mindorks.framework.mvvm.data.DataManager;
import com.mindorks.framework.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class CallActivityModule {

    @Provides
    CallViewModel provideCallViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new CallViewModel(dataManager, schedulerProvider);
    }
}
