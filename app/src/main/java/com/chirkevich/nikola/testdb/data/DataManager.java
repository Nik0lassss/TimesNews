package com.chirkevich.nikola.testdb.data;

import com.chirkevich.nikola.testdb.data.local.DatabaseHelper;
import com.chirkevich.nikola.testdb.data.remote.NYService;
import com.chirkevich.nikola.testdb.data.remote.model.Article;
import com.chirkevich.nikola.testdb.data.remote.model.ArticleResponse;


import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Колян on 29.05.2017.
 */
@Singleton
public class DataManager {

    private final NYService mNyService;
    private final DatabaseHelper mDatabaseHelper;

    private final String typeMostPopular = "Sports";
    private final String timePerison = "1";

    @Inject
    public DataManager(NYService nyService, DatabaseHelper databaseHelper) {
        this.mNyService = nyService;
        this.mDatabaseHelper = databaseHelper;
    }


    public Observable<Article> syncArticls() {
        return mNyService.getArticleMostPopular(typeMostPopular, timePerison, NYService.API_KEY).concatMap(new Func1<ArticleResponse, rx.Observable<? extends Article>>() {
            @Override
            public rx.Observable<? extends Article> call(ArticleResponse articleResponse) {
                return mDatabaseHelper.setArticles(articleResponse.results);
            }
        });
    }



    public Observable<List<Article>> getArticls() {
        return mDatabaseHelper.getArticles().distinct();
    }

    public Observable<List<Article>> findArticls(String search) {
        return mDatabaseHelper.findArticles(search).distinct();
    }
}
