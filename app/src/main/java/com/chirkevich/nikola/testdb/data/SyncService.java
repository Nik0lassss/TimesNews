package com.chirkevich.nikola.testdb.data;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

import com.chirkevich.nikola.testdb.application.App;
import com.chirkevich.nikola.testdb.data.remote.model.Article;
import com.chirkevich.nikola.testdb.utils.AndroidComponentUtil;
import com.chirkevich.nikola.testdb.utils.NetworkUtil;
import com.chirkevich.nikola.testdb.utils.RxUtil;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by Колян on 29.05.2017.
 */

public class SyncService extends Service {
    @Inject
    DataManager mDataManager;

    private Subscription mSubscription;


    public static Intent getStartIntent(Context context) {
        return new Intent(context, SyncService.class);
    }

    public static boolean isRunning(Context context) {
        return AndroidComponentUtil.isServiceRunning(context, SyncService.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        App.get(this).getComponent().inject(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        if (!NetworkUtil.isNetworkConnected(this)) {
            AndroidComponentUtil.toggleComponent(this, SyncOnConnectionAvailable.class, true);
            stopSelf(startId);
            return START_NOT_STICKY;
        }

        RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.syncArticls().subscribeOn(Schedulers.io())
                .subscribe(new Observer<Article>() {
                    @Override
                    public void onCompleted() {
                        stopSelf(startId);
                    }

                    @Override
                    public void onError(Throwable e) {

                        stopSelf(startId);
                    }

                    @Override
                    public void onNext(Article article) {

                    }
                });
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (mSubscription != null) mSubscription.unsubscribe();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public static class SyncOnConnectionAvailable extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)
                    && NetworkUtil.isNetworkConnected(context)) {
                {
                    AndroidComponentUtil.toggleComponent(context, this.getClass(), false);
                    context.startService(getStartIntent(context));
                }
            }
        }
    }
}
