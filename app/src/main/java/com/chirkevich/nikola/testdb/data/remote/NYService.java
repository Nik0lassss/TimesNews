package com.chirkevich.nikola.testdb.data.remote;

import com.chirkevich.nikola.testdb.data.remote.model.ArticleResponse;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Колян on 29.05.2017.
 */

public interface NYService {


    String ENDPOINT = "http://api.nytimes.com/svc/mostpopular/v2/";

    String API_KEY = "cb8500ad214c455ca3f1ad8a2ccabba2";

    @GET("mostemailed/{section}/{time-period}.json")
    Observable<ArticleResponse> getArticleMostPopular(@Path("section") String section, @Path("time-period") String timePeriod, @Header("api-key") String apiKey);


}
