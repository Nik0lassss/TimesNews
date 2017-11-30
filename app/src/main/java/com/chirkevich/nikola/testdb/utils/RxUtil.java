package com.chirkevich.nikola.testdb.utils;

import rx.Subscription;

/**
 * Created by Колян on 29.05.2017.
 */

public class RxUtil {

    public static void unsubscribe(Subscription subscription) {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
