package com.mindorks.framework.mvvm.ui.call;


import com.mindorks.framework.mvvm.data.DataManager;
import com.mindorks.framework.mvvm.ui.base.BaseViewModel;
import com.mindorks.framework.mvvm.utils.rx.SchedulerProvider;

public class CallViewModel extends BaseViewModel<CallNavigator> {

    public CallViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);

    }

    public String GetToken(){
        return getDataManager().getAccessToken();
    }
}
