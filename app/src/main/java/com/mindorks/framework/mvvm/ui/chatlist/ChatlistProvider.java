package com.mindorks.framework.mvvm.ui.chatlist;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ChatlistProvider {

    @ContributesAndroidInjector(modules = ChatlistActivityModule.class)
    abstract ChatlistActivity provideChatlistActivityFactory();
}