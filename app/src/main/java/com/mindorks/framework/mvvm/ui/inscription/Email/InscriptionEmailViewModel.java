package com.mindorks.framework.mvvm.ui.inscription.Email;

import com.mindorks.framework.mvvm.data.DataManager;
import com.mindorks.framework.mvvm.ui.base.BaseViewModel;
import com.mindorks.framework.mvvm.utils.rx.SchedulerProvider;

public class InscriptionEmailViewModel extends BaseViewModel<InscriptionEmailNavigator> {

    public InscriptionEmailViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onEmailInscriptionClick(){
        getNavigator().next();
    }
}
