package com.chirkevich.nikola.testdb.ui.main;

import com.chirkevich.nikola.testdb.data.DataManager;
import com.chirkevich.nikola.testdb.data.remote.model.Article;
import com.chirkevich.nikola.testdb.di.ConfigPersistent;
import com.chirkevich.nikola.testdb.ui.base.BasePresenter;
import com.chirkevich.nikola.testdb.utils.DialogFactory;
import com.chirkevich.nikola.testdb.utils.RxUtil;
import com.chirkevich.nikola.testdb.utils.SortUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Колян on 29.05.2017.
 */
@ConfigPersistent
public class MainPresenter extends BasePresenter<MainMvpView> {


    private final DataManager dataManager;
    private Subscription subscription;
    private ArrayList<Article> cacheArticles;
    private boolean ASC_ORDER_BY = true;


    @Inject
    public MainPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
        cacheArticles = new ArrayList<>();
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);

    }

    @Override
    public void detachView() {
        super.detachView();
        if (subscription != null) subscription.unsubscribe();
    }


    public boolean changeSortState() {
        if (ASC_ORDER_BY) {
            ASC_ORDER_BY = false;
            getMvpView().showArticles(SortUtil.sort(cacheArticles, ASC_ORDER_BY));
            return ASC_ORDER_BY;
        } else {
            ASC_ORDER_BY = true;
            getMvpView().showArticles(SortUtil.sort(cacheArticles, ASC_ORDER_BY));
            return ASC_ORDER_BY;
        }
    }

    public void loadArticles() {
        checkViewAttached();
        RxUtil.unsubscribe(subscription);
        subscription = dataManager.getArticls().
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Article>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Article> articles) {
                        showArticles(articles);
                    }
                });
    }

    public void findArticles(String query) {
        checkViewAttached();
        RxUtil.unsubscribe(subscription);
        subscription = dataManager.findArticls(query).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Article>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Article> articles) {
                        showArticles(articles);
                    }
                });
    }

    private void showArticles(List<Article> articles) {
        if (articles.isEmpty()) {
            cacheArticles = new ArrayList<>();
            getMvpView().showArticlesEmpty();
        } else {
            cacheArticles = (ArrayList<Article>) SortUtil.sort(articles, ASC_ORDER_BY);
            getMvpView().showArticles(articles);
        }
    }
}
