package com.mindorks.framework.mvvm.ui.settings;

import com.mindorks.framework.mvvm.data.DataManager;
import com.mindorks.framework.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;


@Module
public class SettingsActivityModule {

    @Provides
    SettingsViewModel provideSettingsViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new SettingsViewModel(dataManager, schedulerProvider);
    }
}