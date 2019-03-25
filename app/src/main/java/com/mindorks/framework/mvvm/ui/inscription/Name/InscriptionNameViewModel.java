package com.mindorks.framework.mvvm.ui.inscription.Name;

import com.mindorks.framework.mvvm.data.DataManager;
import com.mindorks.framework.mvvm.ui.base.BaseViewModel;
import com.mindorks.framework.mvvm.utils.rx.SchedulerProvider;

public class InscriptionNameViewModel extends BaseViewModel<InscriptionNameNavigator> {

    public InscriptionNameViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onNameInscriptionClick(){
        getNavigator().next();
    }
}