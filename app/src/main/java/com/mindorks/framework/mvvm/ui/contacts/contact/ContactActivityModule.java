package com.mindorks.framework.mvvm.ui.contacts.contact;

import com.mindorks.framework.mvvm.data.DataManager;
import com.mindorks.framework.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class ContactActivityModule {
    @Provides
    ContactViewModel provideContactViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new ContactViewModel(dataManager, schedulerProvider);
    }
}
