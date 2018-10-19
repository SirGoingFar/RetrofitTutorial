package com.eemf.sirgoingfar.retrofittutorial.data.client_requests;

import com.eemf.sirgoingfar.retrofittutorial.data.response_models.request_responses.GetUserSessionDataResponseModel;
import com.eemf.sirgoingfar.retrofittutorial.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface UserClient {

    @POST("create_user_endpoint_relative_path")
    Call<User> createUserAccount(@Body User user);

    //the header is a good place to pass the user_id
    @PUT("edit_user_endpoint_relative_path")
    @Headers({
            "Cache-control: max_age = 3600",
            "User-Agent: Android"
    })
    Call<User> editUser(
            @Header("user_id") int userId,
            @Body User user
    );

    @GET("user_data_endpoint_relative_path")
    Call<GetUserSessionDataResponseModel.Success> getUserSessionData(@Body String userLogin);
}
