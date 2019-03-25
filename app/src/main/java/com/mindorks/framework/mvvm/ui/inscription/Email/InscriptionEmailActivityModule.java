package com.mindorks.framework.mvvm.ui.inscription.Email;

import com.mindorks.framework.mvvm.data.DataManager;
import com.mindorks.framework.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class InscriptionEmailActivityModule {

    @Provides
    InscriptionEmailViewModel provideInscriptionEmailViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new InscriptionEmailViewModel(dataManager, schedulerProvider);
    }
}