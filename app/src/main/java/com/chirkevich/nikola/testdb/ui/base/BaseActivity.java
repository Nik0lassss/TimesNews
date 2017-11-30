package com.chirkevich.nikola.testdb.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.chirkevich.nikola.testdb.application.App;
import com.chirkevich.nikola.testdb.di.component.ActivityComponent;
import com.chirkevich.nikola.testdb.di.component.ConfigPersitentComponent;
import com.chirkevich.nikola.testdb.di.component.DaggerConfigPersitentComponent;
import com.chirkevich.nikola.testdb.di.module.ActivityModule;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Колян on 29.05.2017.
 */

public class BaseActivity extends AppCompatActivity {
    private static final String KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID";
    private static final AtomicLong NEXT_ID = new AtomicLong(0);
    private static final Map<Long,ConfigPersitentComponent> sComponentMap = new HashMap<>();


    private ActivityComponent activityComponent;
    private long activityId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityId = savedInstanceState !=null ? savedInstanceState.getLong(KEY_ACTIVITY_ID):NEXT_ID.getAndIncrement();

        ConfigPersitentComponent configPersitentComponent;
        if(!sComponentMap.containsKey(activityId))
        {
            configPersitentComponent = DaggerConfigPersitentComponent.builder().applicationComponent(App.get(this).getComponent()).build();
            sComponentMap.put(activityId,configPersitentComponent);
            
        }
        else
        {
            configPersitentComponent = sComponentMap.get(activityId);
        }
        activityComponent = configPersitentComponent.activityComponent(new ActivityModule(this));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_ACTIVITY_ID, activityId);
    }

    @Override
    protected void onDestroy() {

        if(!isChangingConfigurations())
        {
            sComponentMap.remove(activityId);
        }
        super.onDestroy();
    }

    public ActivityComponent activityComponent()
    {
        return activityComponent;
    }
}
