package com.mindorks.framework.mvvm.ui.chatlist;

import com.mindorks.framework.mvvm.data.model.api.ChatlListResponse;

import java.util.List;

public interface ChatlistNavigator {

    void updateBlog(List<ChatlListResponse.Chatlist> blogList);

    void displayDialogAddConv();

    void displayEmail(List<String> email, String nom, int conv_id);

    void toastMessage(String message);

    void addCircleToMap(String name, int id);

    void openHome();

}
