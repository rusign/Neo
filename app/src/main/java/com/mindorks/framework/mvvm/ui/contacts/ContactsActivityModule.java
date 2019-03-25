package com.mindorks.framework.mvvm.ui.contacts;

import com.mindorks.framework.mvvm.data.DataManager;
import com.mindorks.framework.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class ContactsActivityModule {

    @Provides
    ContactsViewModel provideContactsViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new ContactsViewModel(dataManager, schedulerProvider);
    }
}