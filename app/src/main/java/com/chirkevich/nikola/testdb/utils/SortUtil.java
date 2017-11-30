package com.chirkevich.nikola.testdb.utils;

import com.chirkevich.nikola.testdb.data.remote.model.Article;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by Колян on 30.05.2017.
 */

public class SortUtil {

    public static List<Article> sort(List<Article> list, boolean reverse) {
        Collections.sort(list, new Comparator<Article>() {
            @Override
            public int compare(Article o1, Article o2) {
                Date date1 = DateTimeParser.parse(o1.getPublished_date());
                Date date2 = DateTimeParser.parse(o2.getPublished_date());
                return date1.compareTo(date2);
            }
        });
        if (reverse) Collections.reverse(list);
        return list;
    }
}
