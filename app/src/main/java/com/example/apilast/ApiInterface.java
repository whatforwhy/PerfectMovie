package com.example.apilast;

import com.example.apilast.models.InfoFilm;
import com.example.apilast.models.Root;
import com.example.apilast.models.RootTrailer;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface ApiInterface {
    @Headers({"X-API-KEY: c75eff76-f10d-456c-9d69-69e2900efda3"})
    @GET("films/{id}")
    Call<InfoFilm> getFilmById(@Path("id") Integer id);

    @Headers({"X-API-KEY: c75eff76-f10d-456c-9d69-69e2900efda3"})
    @GET("films/top?type=TOP_250_BEST_FILMS&page=1")
    Call<Root> getFilmListTop();

    @Headers({"X-API-KEY: c75eff76-f10d-456c-9d69-69e2900efda3"})
    @GET("films/{id}/videos")
    Call<RootTrailer> getFilmTrailer(@Path("id") Integer id);

    @Headers({"X-API-KEY: c75eff76-f10d-456c-9d69-69e2900efda3"})
    @GET("films/top?type=TOP_AWAIT_FILMS&page=1")

    Call<Root> getFilmListPremieres();


}

