package com.chirkevich.nikola.testdb.ui.main;

import com.chirkevich.nikola.testdb.data.remote.model.Article;
import com.chirkevich.nikola.testdb.ui.base.MvpView;

import java.util.List;

/**
 * Created by Колян on 29.05.2017.
 */

public interface MainMvpView extends MvpView {

    void showArticles(List<Article> articles);
    void showArticlesEmpty();
    void showError();
}
