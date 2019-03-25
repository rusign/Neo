package com.mindorks.framework.mvvm.ui.inscription;

import com.mindorks.framework.mvvm.data.DataManager;
import com.mindorks.framework.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class InscriptionActivityModule {

    @Provides
    InscriptionViewModel provideInscriptionViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new InscriptionViewModel(dataManager, schedulerProvider);
    }
}