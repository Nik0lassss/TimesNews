package com.chirkevich.nikola.testdb.di.module;

import android.app.Activity;
import android.content.Context;

import com.chirkevich.nikola.testdb.di.ActivityContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Колян on 29.05.2017.
 */

@Module
public class ActivityModule {
    private Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    Activity provideActivity() {
        return activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return activity;
    }
}
