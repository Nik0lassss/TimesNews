package com.chirkevich.nikola.testdb.data.local;

import android.content.ContentValues;
import android.database.Cursor;

import com.chirkevich.nikola.testdb.data.remote.model.Article;
import com.chirkevich.nikola.testdb.data.remote.model.Media;
import com.chirkevich.nikola.testdb.data.remote.model.Mediametadata;

/**
 * Created by Колян on 29.05.2017.
 */

public class Db {


    public abstract static class ArticleTable {

        public static final String TABLE_NAME = "article";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_COUNT_TYPE = "count_type";
        public static final String COLUMN_COLUMN = "column";
        public static final String COLUMN_SECTION = "section";
        public static final String COLUMN_BYLINE = "byline";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_PUBLISHED_DATE = "published_date";
        public static final String COLUMN_SOURCE = "source";


        public static final String CREATE = "CREATE TABLE " + TABLE_NAME + " (" +

                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_URL + " TEXT, " +
                COLUMN_COUNT_TYPE + " TEXT, " +
                COLUMN_COLUMN + " TEXT, " +
                COLUMN_SECTION + " TEXT, " +
                COLUMN_BYLINE + " TEXT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_PUBLISHED_DATE + " TEXT, " +
                COLUMN_SOURCE + " TEXT " +
                " ); ";


        public static ContentValues toContentValues(Article article) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_URL, article.url);
            values.put(COLUMN_COUNT_TYPE, article.count_type);
            values.put(COLUMN_COLUMN, article.column);
            values.put(COLUMN_BYLINE, article.byline);
            values.put(COLUMN_TITLE, article.title);
            values.put(COLUMN_PUBLISHED_DATE, article.published_date);
            values.put(COLUMN_SOURCE, article.source);
            values.put(COLUMN_SECTION, article.section);
            return values;
        }

        public static Article parseCursor(Cursor cursor) {
            Article article = new Article();
            article.setUrl(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_URL)));
            article.setCount_type(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COUNT_TYPE)));
            article.setColumn(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COLUMN)));
            article.setByline(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BYLINE)));
            article.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)));
            article.setPublished_date(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PUBLISHED_DATE)));
            article.setSource(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SOURCE)));
            article.setSection(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SECTION)));
            article.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
            return article;
        }

    }


    public abstract static class MediaTable {
        public static final String TABLE_NAME = "media_table";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_ARTICLE_ID = "article_id";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_SUBTYPE = "subtype";
        public static final String COLUMN_CAPTION = "caption";
        public static final String COLUMN_COPYRIGHT = "copyright";

        public static final String CREATE = " CREATE TABLE " + TABLE_NAME + " ( " +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TYPE + " TEXT, " +
                COLUMN_SUBTYPE + " TEXT, " +
                COLUMN_CAPTION + " TEXT, " +
                COLUMN_COPYRIGHT + " TEXT, " +
                COLUMN_ARTICLE_ID + " INTEGER, " +
                " FOREIGN KEY ( " + COLUMN_ARTICLE_ID + " ) REFERENCES " + ArticleTable.TABLE_NAME + " ( " + ArticleTable.COLUMN_ID + " )" +
                " ); ";


        public static ContentValues toContentValues(Media media, long articleId) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ARTICLE_ID, articleId);
            values.put(COLUMN_TYPE, media.type);
            values.put(COLUMN_SUBTYPE, media.subtype);
            values.put(COLUMN_CAPTION, media.caption);
            values.put(COLUMN_COPYRIGHT, media.copyright);
            return values;
        }

        public static Media parseCursor(Cursor cursor) {
            Media media = new Media();
            media.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
            media.setType(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE)));
            media.setCaption(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CAPTION)));
            media.setCopyright(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COPYRIGHT)));
            media.setSubtype(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SUBTYPE)));
            return media;
        }

    }


    public abstract static class MediaMetadataTable {
        public static final String TABLE_NAME = "media_meta_table";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_MEDIA_ID = "media_id";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_FORMAT = "format";
        public static final String COLUMN_HEIGHT = "height";
        public static final String COLUMN_WIDTH = "width";


        public static final String CREATE = " CREATE TABLE " + TABLE_NAME + " ( " +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_MEDIA_ID + " INTEGER, " +
                COLUMN_URL + " TEXT, " +
                COLUMN_FORMAT + " TEXT, " +
                COLUMN_HEIGHT + " INTEGER, " +
                COLUMN_WIDTH + " INTEGER, " +
                " FOREIGN KEY ( " + COLUMN_MEDIA_ID + " ) REFERENCES " + MediaMetadataTable.TABLE_NAME + " ( " + MediaTable.COLUMN_ID + " )" +
                " ); ";

        public static ContentValues toContentValues(Mediametadata mediaMetadata, long mediaId) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_MEDIA_ID, mediaId);
            values.put(COLUMN_URL, mediaMetadata.getUrl());
            values.put(COLUMN_FORMAT, mediaMetadata.getFormat());
            values.put(COLUMN_HEIGHT, mediaMetadata.getHeight());
            values.put(COLUMN_WIDTH, mediaMetadata.getWidth());
            return values;
        }

        public static Mediametadata parseCursor(Cursor cursor) {
            Mediametadata mediametadata = new Mediametadata();
            mediametadata.setFormat(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FORMAT)));
            mediametadata.setUrl(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_URL)));
            mediametadata.setHeight(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_HEIGHT)));
            mediametadata.setWidth(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_WIDTH)));
            return mediametadata;
        }
    }
}
