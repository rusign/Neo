package com.mindorks.framework.mvvm.ui.contacts.circlecontacts;

import com.mindorks.framework.mvvm.data.DataManager;
import com.mindorks.framework.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class CircleContactsActivityModule {
    @Provides
    CircleContactsViewModel provideCircleContactsViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new CircleContactsViewModel(dataManager, schedulerProvider);
    }
}
