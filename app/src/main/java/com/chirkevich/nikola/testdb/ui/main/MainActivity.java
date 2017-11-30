package com.chirkevich.nikola.testdb.ui.main;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.chirkevich.nikola.testdb.R;
import com.chirkevich.nikola.testdb.data.SyncService;
import com.chirkevich.nikola.testdb.data.remote.model.Article;
import com.chirkevich.nikola.testdb.ui.base.BaseActivity;
import com.chirkevich.nikola.testdb.utils.DialogFactory;
import com.chirkevich.nikola.testdb.utils.RxSearch;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends BaseActivity implements MainMvpView {

    private static final String EXTRA_TRIGGER_SYNC_FLAG = "com.chirkevich.nikola.testdb.ui.main.EXTRA_TRIGGER_SYNC_FLAG";

    @Inject
    MainPresenter mMainPresenter;


    @Inject
    ArticlesAdapter mArticlesAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.error_message_text_view)
    TextView errorMessageTextView;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort:
                DialogFactory.showInfoShotToast(this,getString(R.string.sorted_info_message));
                if(mMainPresenter.changeSortState())
                    item.setIcon(getDrawable(R.drawable.icon_toolbal_sort));
                else item.setIcon(getDrawable(R.drawable.icon_toolbal_sort_desc));
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityComponent().inject(this);
        ButterKnife.bind(this);

        mRecyclerView.setAdapter(mArticlesAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMainPresenter.attachView(this);
        mMainPresenter.loadArticles();

        if (getIntent().getBooleanExtra(EXTRA_TRIGGER_SYNC_FLAG, true)) {
            startService(SyncService.getStartIntent(this));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        RxSearch.fromSearchView((SearchView) menu.findItem(R.id.action_search).getActionView())
                .debounce(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(String s) {
                        if (s.isEmpty())
                            mMainPresenter.loadArticles();
                        else
                            mMainPresenter.findArticles(s);
                    }
                });
        return true;
    }

    @Override
    public void showArticles(List<Article> articles) {
        errorMessageTextView.setVisibility(View.GONE);
        mArticlesAdapter.setArticles(articles);
        mArticlesAdapter.notifyDataSetChanged();
    }

    @Override
    public void showArticlesEmpty() {
        showErroreMessage();
        mArticlesAdapter.setArticles(Collections.<Article>emptyList());
        mArticlesAdapter.notifyDataSetChanged();
    }


    @Override
    public void showError() {
        DialogFactory.createGenericErrorDialog(this, getString(R.string.error_loading_articles));

    }

    private void showErroreMessage()
    {
        errorMessageTextView.setVisibility(View.VISIBLE);
        errorMessageTextView.setText(getString(R.string.error_loading_articles));
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainPresenter.detachView();
    }
}
