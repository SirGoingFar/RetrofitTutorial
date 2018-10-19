package com.eemf.sirgoingfar.retrofittutorial.data.client_requests;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SendDataFormUrlEncoded {

    @FormUrlEncoded
    @POST("endpoint_relative_path")
    Call<ResponseBody> sendFeedbackFixedSize(
            @Field("name") String name,
            @Field("age") String age,
            @Field("email") String email,
            @Field("topic") String topics
    );

    @FormUrlEncoded
    @POST("endpoint_relative_path.....same as above")
    Call<ResponseBody> sendFeedbackFixedSize(
            @FieldMap Map fieldMap
    );

    @FormUrlEncoded
    @POST("endpoint_relative_path.....same as above")
    Call<ResponseBody> sendFeedbackFixedSize(
            @FieldMap() Map fieldMap,
            @Field("topics") List<String> topicsList
    );
}
