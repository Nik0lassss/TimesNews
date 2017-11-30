package com.chirkevich.nikola.testdb.exception;

/**
 * Created by Колян on 29.05.2017.
 */

public class MvpViewNotAttachedException extends RuntimeException {
    public MvpViewNotAttachedException() {
        super("Please call Presenter.attachView(MvpView) befor" +
                " requesting data to the Presenter");
    }
}
