package com.mindorks.framework.mvvm.ui.contacts.circleinvitation;

import com.mindorks.framework.mvvm.data.DataManager;
import com.mindorks.framework.mvvm.ui.contacts.ContactsViewModel;
import com.mindorks.framework.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class CircleInvitationActivityModule {
    @Provides
    CircleInvitationViewModel provideCircleInvitationViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new CircleInvitationViewModel(dataManager, schedulerProvider);
    }
}
