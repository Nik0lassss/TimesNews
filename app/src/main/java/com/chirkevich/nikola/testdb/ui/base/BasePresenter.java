package com.chirkevich.nikola.testdb.ui.base;

import com.chirkevich.nikola.testdb.exception.MvpViewNotAttachedException;

/**
 * Created by Колян on 29.05.2017.
 */

public class BasePresenter<T extends MvpView> implements Presenter<T> {
    private T mvpView;


    @Override
    public void attachView(T mvpView) {
        this.mvpView = mvpView;
    }

    @Override
    public void detachView() {
        mvpView = null;
    }

    public boolean isViewattached() {
        return mvpView != null;
    }

    public T getMvpView()
    {
        return mvpView;
    }


    public void checkViewAttached()
    {
        if(!isViewattached()) throw new MvpViewNotAttachedException();
    }
}
