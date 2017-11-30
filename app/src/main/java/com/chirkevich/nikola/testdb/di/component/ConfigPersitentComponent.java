package com.chirkevich.nikola.testdb.di.component;

import com.chirkevich.nikola.testdb.application.App;
import com.chirkevich.nikola.testdb.di.ConfigPersistent;
import com.chirkevich.nikola.testdb.di.module.ActivityModule;

import dagger.Component;

/**
 * Created by Колян on 29.05.2017.
 */
@ConfigPersistent
@Component(dependencies = ApplicationComponent.class)
public interface ConfigPersitentComponent {
    ActivityComponent activityComponent(ActivityModule activityModule);
}
