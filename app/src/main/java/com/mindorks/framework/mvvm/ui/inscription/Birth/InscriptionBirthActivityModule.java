package com.mindorks.framework.mvvm.ui.inscription.Birth;

import com.mindorks.framework.mvvm.data.DataManager;
import com.mindorks.framework.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class InscriptionBirthActivityModule {

    @Provides
    InscriptionBirthViewModel provideInscriptionBirthViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new InscriptionBirthViewModel(dataManager, schedulerProvider);
    }
}
