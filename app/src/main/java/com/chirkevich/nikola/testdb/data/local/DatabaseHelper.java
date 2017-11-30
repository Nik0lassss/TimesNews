package com.chirkevich.nikola.testdb.data.local;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.chirkevich.nikola.testdb.data.remote.model.Article;
import com.chirkevich.nikola.testdb.data.remote.model.Media;
import com.chirkevich.nikola.testdb.data.remote.model.Mediametadata;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Колян on 29.05.2017.
 */

@Singleton
public class DatabaseHelper {

    private final BriteDatabase mDb;


    @Inject
    public DatabaseHelper(DbOpenHelper dbOpenHelper) {
        SqlBrite.Builder briteBuilder = new SqlBrite.Builder();
        mDb = briteBuilder.build().wrapDatabaseHelper(dbOpenHelper, Schedulers.immediate());
    }

    public BriteDatabase getBriteDb() {
        return mDb;
    }


    public Observable<Article> setArticles(final Collection<Article> newArticles) {
        return Observable.create(new Observable.OnSubscribe<Article>() {
            @Override
            public void call(Subscriber<? super Article> subscriber) {
                if (subscriber.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    mDb.delete(Db.ArticleTable.TABLE_NAME, null);
                    mDb.delete(Db.MediaTable.TABLE_NAME,null);
                    mDb.delete(Db.MediaMetadataTable.TABLE_NAME,null);
                    for (Article article : newArticles) {
                        long result = mDb.insert(Db.ArticleTable.TABLE_NAME, Db.ArticleTable.toContentValues(article), SQLiteDatabase.CONFLICT_REPLACE);
                        List<Media> medias;
                        medias = article.getMedia();
                        for (Media media : medias) {
                            List<Mediametadata> mediametadataList;
                            mediametadataList = media.getMedia_metadata();
                            long insertMediaResult = mDb.insert(Db.MediaTable.TABLE_NAME, Db.MediaTable.toContentValues(media, result), SQLiteDatabase.CONFLICT_REPLACE);
                            for(Mediametadata mediametadata: mediametadataList)
                            {
                                long insertMediametaDataResult = mDb.insert(Db.MediaMetadataTable.TABLE_NAME,Db.MediaMetadataTable.toContentValues(mediametadata,insertMediaResult),SQLiteDatabase.CONFLICT_REPLACE);
                            }
                        }
                        if (result >= 0) subscriber.onNext(article);
                    }
                    transaction.markSuccessful();
                    subscriber.onCompleted();
                }
                finally {
                    transaction.end();
                }
            }
        });
    }

    public Observable<List<Article>> getArticles() {
        return mDb.createQuery(Db.ArticleTable.TABLE_NAME, "SELECT * FROM " + Db.ArticleTable.TABLE_NAME).mapToList(new Func1<Cursor, Article>() {
            @Override
            public Article call(Cursor cursor) {
                final Article article = Db.ArticleTable.parseCursor(cursor);
                mDb.createQuery(Db.MediaTable.TABLE_NAME, "SELECT * FROM " + Db.MediaTable.TABLE_NAME + " WHERE " + Db.MediaTable.COLUMN_ARTICLE_ID + " = " + article.getId()).mapToList(new Func1<Cursor, Media>() {
                    @Override
                    public Media call(Cursor cursor) {
                        final Media media = Db.MediaTable.parseCursor(cursor);

                        mDb.createQuery(Db.MediaMetadataTable.TABLE_NAME, "SELECT * FROM " + Db.MediaMetadataTable.TABLE_NAME + " WHERE " + Db.MediaMetadataTable.COLUMN_MEDIA_ID + " = " + media.getId()).mapToList(new Func1<Cursor, Mediametadata>() {
                            @Override
                            public Mediametadata call(Cursor cursor) {
                                return Db.MediaMetadataTable.parseCursor(cursor);
                            }
                        }).subscribe(new Subscriber<List<Mediametadata>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(List<Mediametadata> mediametadatas) {
                                media.setMedia_metadata(mediametadatas);
                            }
                        });
                        return media;
                    }
                }).subscribe(new Observer<List<Media>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Media> medias) {
                        article.setMedia(medias);
                    }
                });
                //mDb.createQuery(Db.MediaTable.TABLE_NAME,"SELECT * FROM "+Db.MediaTable.TABLE_NAME)
                return article;
            }
        });


    }
    public Observable<List<Article>> findArticles(String search) {
        return mDb.createQuery(Db.ArticleTable.TABLE_NAME, "SELECT * FROM " + Db.ArticleTable.TABLE_NAME + " WHERE " + Db.ArticleTable.COLUMN_TITLE + " LIKE '%" + search + "%' ").mapToList(new Func1<Cursor, Article>() {
            @Override
            public Article call(Cursor cursor) {
                final Article article = Db.ArticleTable.parseCursor(cursor);
                mDb.createQuery(Db.MediaTable.TABLE_NAME, "SELECT * FROM " + Db.MediaTable.TABLE_NAME + " WHERE " + Db.MediaTable.COLUMN_ARTICLE_ID + " = " + article.getId()).mapToList(new Func1<Cursor, Media>() {
                    @Override
                    public Media call(Cursor cursor) {
                        final Media media = Db.MediaTable.parseCursor(cursor);

                        mDb.createQuery(Db.MediaMetadataTable.TABLE_NAME, "SELECT * FROM " + Db.MediaMetadataTable.TABLE_NAME + " WHERE " + Db.MediaMetadataTable.COLUMN_MEDIA_ID + " = " + media.getId()).mapToList(new Func1<Cursor, Mediametadata>() {
                            @Override
                            public Mediametadata call(Cursor cursor) {
                                return Db.MediaMetadataTable.parseCursor(cursor);
                            }
                        }).subscribe(new Subscriber<List<Mediametadata>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(List<Mediametadata> mediametadatas) {
                                media.setMedia_metadata(mediametadatas);
                            }
                        });
                        return media;
                    }
                }).subscribe(new Observer<List<Media>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Media> medias) {
                        article.setMedia(medias);
                    }
                });
                //mDb.createQuery(Db.MediaTable.TABLE_NAME,"SELECT * FROM "+Db.MediaTable.TABLE_NAME)
                return article;
            }
        });
    }
}

