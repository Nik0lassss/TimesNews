package com.chirkevich.nikola.testdb.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Колян on 29.05.2017.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigPersistent {
}
