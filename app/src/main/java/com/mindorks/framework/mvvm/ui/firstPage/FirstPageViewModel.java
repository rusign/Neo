package com.mindorks.framework.mvvm.ui.firstPage;

import com.mindorks.framework.mvvm.data.DataManager;
import com.mindorks.framework.mvvm.ui.base.BaseViewModel;
import com.mindorks.framework.mvvm.utils.rx.SchedulerProvider;

public class FirstPageViewModel extends BaseViewModel<FirstPageNavigator> {

    public FirstPageViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onConnectionClick()
    {
        getNavigator().openLoginActivity();
    }

    public void onInscriptionClick()
    {
        getNavigator().openInscriptionActivity();
    }


}
