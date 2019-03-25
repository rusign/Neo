package com.mindorks.framework.mvvm.ui.inscription.Birth;

import com.mindorks.framework.mvvm.data.DataManager;
import com.mindorks.framework.mvvm.ui.base.BaseViewModel;
import com.mindorks.framework.mvvm.utils.rx.SchedulerProvider;

public class InscriptionBirthViewModel  extends BaseViewModel<InscriptionBirthNavigator> {

        public InscriptionBirthViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
            super(dataManager, schedulerProvider);
        }


        public void onBirthInscriptionClick(){
            getNavigator().next();
        }
}

