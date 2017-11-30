package com.chirkevich.nikola.testdb.di.component;

import android.app.Application;
import android.content.Context;

import com.chirkevich.nikola.testdb.data.DataManager;
import com.chirkevich.nikola.testdb.data.SyncService;
import com.chirkevich.nikola.testdb.data.local.DatabaseHelper;
import com.chirkevich.nikola.testdb.data.remote.NYApiModule;
import com.chirkevich.nikola.testdb.data.remote.NYService;
import com.chirkevich.nikola.testdb.di.ApplicationContext;
import com.chirkevich.nikola.testdb.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Колян on 29.05.2017.
 */

@Singleton
@Component(modules = {ApplicationModule.class, NYApiModule.class})
public interface ApplicationComponent {

    void inject(SyncService syncService);

    @ApplicationContext
    Context context();

    Application application();
    NYService nyServcie();
    DatabaseHelper databaseHelper();
    DataManager dataManager();
}
