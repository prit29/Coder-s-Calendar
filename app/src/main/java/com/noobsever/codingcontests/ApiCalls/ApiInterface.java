package com.noobsever.codingcontests.ApiCalls;

import com.noobsever.codingcontests.Models.Shedule;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("/api/v1/json/contest/")
    Call<Shedule> getShedule(@Query("username") String username, @Query("api_key") String api_key, @Query("resource__name") String recorse_name,@Query("start__gte") String start_gte,@Query("start__lte") String start__lte,@Query("order_by") String order_by );

    @GET("/api/v1/json/contest/")
    Call<Shedule> getLive(@Query("username") String username, @Query("api_key") String api_key, @Query("resource__name") String resourse_name,@Query("start__lte") String start_gte,@Query("end__gte") String start__lte,@Query("order_by") String order_by );
}
