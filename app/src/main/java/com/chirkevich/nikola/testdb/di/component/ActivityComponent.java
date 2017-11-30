package com.chirkevich.nikola.testdb.di.component;

import com.chirkevich.nikola.testdb.ui.main.MainActivity;
import com.chirkevich.nikola.testdb.di.PerActivity;
import com.chirkevich.nikola.testdb.di.module.ActivityModule;

import dagger.Subcomponent;

/**
 * Created by Колян on 29.05.2017.
 */

@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity mainActivity);
}
