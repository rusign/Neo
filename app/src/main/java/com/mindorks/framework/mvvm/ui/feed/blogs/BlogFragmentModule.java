package com.mindorks.framework.mvvm.ui.feed.blogs;

import android.arch.lifecycle.ViewModelProvider;
import android.support.v7.widget.LinearLayoutManager;

import com.mindorks.framework.mvvm.ViewModelProviderFactory;
import com.mindorks.framework.mvvm.data.DataManager;
import com.mindorks.framework.mvvm.utils.rx.SchedulerProvider;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class BlogFragmentModule {

    @Provides
    BlogViewModel blogViewModel(DataManager dataManager,
                                SchedulerProvider schedulerProvider) {
        return new BlogViewModel(dataManager, schedulerProvider);
    }

    @Provides
    BlogAdapter provideBlogAdapter() {
        return new BlogAdapter(new ArrayList<>());
    }

    @Provides
    ViewModelProvider.Factory provideBlogViewModel(BlogViewModel blogViewModel) {
        return new ViewModelProviderFactory<>(blogViewModel);
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(BlogFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }
}
