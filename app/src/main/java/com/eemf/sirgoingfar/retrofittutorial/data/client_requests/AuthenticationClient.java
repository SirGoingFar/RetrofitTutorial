package com.eemf.sirgoingfar.retrofittutorial.data.client_requests;

import com.eemf.sirgoingfar.retrofittutorial.model.Login;
import com.eemf.sirgoingfar.retrofittutorial.model.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AuthenticationClient {

    /*Basic Authentication*/
    @POST("login")
    Call<User> authenticateUserBasic(
            @Header("Authentication") String authHeader
    );

    /*Token Authentication*/
    @POST("login")
    Call<User> authenticateUserToken(Login login);

    @GET("secret_question")
    Call<ResponseBody> getSecretQuestion(
            @Header("Authentication") String accessToken
    );
}
