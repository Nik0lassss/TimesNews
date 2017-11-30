package com.chirkevich.nikola.testdb.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.chirkevich.nikola.testdb.di.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

/**
 * Created by Колян on 29.05.2017.
 */

@Singleton
public class DbOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "testDb.db";
    public static final int DATABASE_VERSION = 1;

    @Inject
    public DbOpenHelper(@ApplicationContext Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.beginTransaction();
        try {
            db.execSQL(Db.ArticleTable.CREATE);
            db.execSQL(Db.MediaTable.CREATE);
            db.execSQL(Db.MediaMetadataTable.CREATE);
            db.setTransactionSuccessful();
        }
        catch (Exception e)
        {
            Timber.e(e,e.getMessage());
        }finally {
            db.endTransaction();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
