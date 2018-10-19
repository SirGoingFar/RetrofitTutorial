package com.eemf.sirgoingfar.retrofittutorial.data.client_requests;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface AnotherPlatformClient {

    @GET
    Call<ResponseBody> getProfilePhoto(
            @Url String profilePhotoUrl
    );
}
