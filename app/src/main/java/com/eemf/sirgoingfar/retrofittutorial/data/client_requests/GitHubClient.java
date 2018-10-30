package com.eemf.sirgoingfar.retrofittutorial.data.client_requests;

import com.eemf.sirgoingfar.retrofittutorial.activities.NormalJavaProjectWithRetrofit;
import com.eemf.sirgoingfar.retrofittutorial.model.AccessToken;
import com.eemf.sirgoingfar.retrofittutorial.model.GitHubRepo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GitHubClient {

    @GET("/users/{user}/repos")
    Call<List<GitHubRepo>> getUserRepos(@Path("user") String user);

    @Headers("Accept: application/json")
    @POST("login/oauth/access_token")
    @FormUrlEncoded
    Call<AccessToken> getAccessToken(
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("code") String authToken
    );

    @GET("endpoint_relative_path")
    Call<NormalJavaProjectWithRetrofit.Task> getFile();

}
