package com.eemf.sirgoingfar.retrofittutorial.data.client_requests;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface AddQueryParameter {

    Call<ResponseBody> addStaticQueryParameters(
            @Query("user_location") String location,
            @Query("search_keyword") String keyword,
            @Query("request_id") int id
    );

    //You can put Query Parameters that you're not sure to be involved in a request in the Map
    //if you are sure, you can specify it as part of the method parameter
    Call<ResponseBody> addDynamicQueryParameters(
            @Query("search_keyword") String keyword,
            @Query("request_id") int id,
            @QueryMap Map<String, Object> map
    );

    //If any of the arguments has a value NULL from the caller, Retrofit does not bother to send it
    //with the Request payload
    Call<ResponseBody> addOptionalQueryParameters(
            @Query("user_location") String location,
            @Query("search_keyword") String keyword,
            @Query("request_id") Integer id);

    Call<ResponseBody> addMultipleValuesForAQueryParameter(
            @Query("user_location") String location,
            @Query("search_keyword") List<String> keyword,
            @Query("request_id") Integer id
    );
}
