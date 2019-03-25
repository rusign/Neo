package com.mindorks.framework.mvvm.ui.inscription.Mdp;

import com.mindorks.framework.mvvm.data.DataManager;
import com.mindorks.framework.mvvm.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class InscriptionMdpActivityModule {

    @Provides
    InscriptionMdpViewModel provideInscriptionMdpViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new InscriptionMdpViewModel(dataManager, schedulerProvider);
    }
}
