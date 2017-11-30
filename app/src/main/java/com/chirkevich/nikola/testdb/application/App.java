package com.chirkevich.nikola.testdb.application;

import android.app.Application;
import android.content.Context;

import com.chirkevich.nikola.testdb.di.component.ApplicationComponent;
import com.chirkevich.nikola.testdb.di.component.DaggerApplicationComponent;
import com.chirkevich.nikola.testdb.di.module.ApplicationModule;

/**
 * Created by Колян on 29.05.2017.
 */

public class App  extends Application{
    ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static App get(Context context){
        return (App) context.getApplicationContext();
    }

    public ApplicationComponent getComponent()
    {
        if(applicationComponent == null)
        {
            applicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
        }
        return applicationComponent;
    }
}
