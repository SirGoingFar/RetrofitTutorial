package com.eemf.sirgoingfar.retrofittutorial.data.client_requests;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SendPlainTextClient {

    @POST("endpoint_relative_path")
    Call<String> sendPlainTextAsString(@Body String plainText);

    @POST("endpoint_relative_path")
    Call<String> sendPlainTextAsRequestBody(@Body RequestBody requestBody);
}
