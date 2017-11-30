package com.chirkevich.nikola.testdb.di.module;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.chirkevich.nikola.testdb.di.ActivityContext;
import com.chirkevich.nikola.testdb.di.ApplicationContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Колян on 29.05.2017.
 */
@Module
public class ApplicationModule {

    protected final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    Application provideApplication() {
        return application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return application;
    }

}
