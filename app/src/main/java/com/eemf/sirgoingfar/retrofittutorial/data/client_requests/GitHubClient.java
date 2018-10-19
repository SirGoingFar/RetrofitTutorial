package com.eemf.sirgoingfar.retrofittutorial.data.client_requests;

import com.eemf.sirgoingfar.retrofittutorial.model.GitHubRepo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubClient {

    @GET("/users/{user}/repos")
    Call<List<GitHubRepo>> getUserRepos(@Path("user") String user);

}
