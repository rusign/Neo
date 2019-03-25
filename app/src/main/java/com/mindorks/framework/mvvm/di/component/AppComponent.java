package com.mindorks.framework.mvvm.di.component;

import android.app.Application;

import com.mindorks.framework.mvvm.MvvmApp;
import com.mindorks.framework.mvvm.di.builder.ActivityBuilder;
import com.mindorks.framework.mvvm.di.module.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {AndroidInjectionModule.class, AppModule.class, ActivityBuilder.class})
public interface AppComponent {

    void inject(MvvmApp app);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
