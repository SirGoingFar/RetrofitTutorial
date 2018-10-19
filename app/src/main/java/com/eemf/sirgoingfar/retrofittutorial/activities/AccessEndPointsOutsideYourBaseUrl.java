package com.eemf.sirgoingfar.retrofittutorial.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.eemf.sirgoingfar.retrofittutorial.R;
import com.eemf.sirgoingfar.retrofittutorial.Util.NetworkIoHelper;
import com.eemf.sirgoingfar.retrofittutorial.data.client_requests.AnotherPlatformClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class AccessEndPointsOutsideYourBaseUrl extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_end_points_outside_your_base_url);

        //The API with the normal app BASE_URL sends the URL text as a response to a request
        //to access a file on another platform other than the BASE URL, e.g. on Amazon S3
        String profilePhotoUrl = "response_text_from_my_app_server";

        fetchProfilePhoto(profilePhotoUrl);
    }

    private void fetchProfilePhoto(String profilePhotoUrl) {

        Retrofit retrofit = NetworkIoHelper.getRetrofitInstance("base_url", true);
        retrofit.create(AnotherPlatformClient.class).getProfilePhoto(profilePhotoUrl).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                //perform success action
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //perform failure action
            }
        }
    );


    }
}
