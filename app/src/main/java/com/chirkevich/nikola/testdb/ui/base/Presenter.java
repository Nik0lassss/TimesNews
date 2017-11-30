package com.chirkevich.nikola.testdb.ui.base;

/**
 * Created by Колян on 29.05.2017.
 */

public interface Presenter <V extends MvpView> {
    void attachView(V mvpView);
    void detachView();
}
