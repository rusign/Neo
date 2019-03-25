package com.mindorks.framework.mvvm.ui.inscription.Name;

import com.mindorks.framework.mvvm.data.DataManager;
import com.mindorks.framework.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class InscriptionNameActivityModule {

    @Provides
    InscriptionNameViewModel provideInscriptionNameViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new InscriptionNameViewModel(dataManager, schedulerProvider);
    }
}