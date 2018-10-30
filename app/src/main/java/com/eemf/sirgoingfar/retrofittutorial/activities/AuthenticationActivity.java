package com.eemf.sirgoingfar.retrofittutorial.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;

import com.eemf.sirgoingfar.retrofittutorial.R;
import com.eemf.sirgoingfar.retrofittutorial.Util.NetworkIoHelper;
import com.eemf.sirgoingfar.retrofittutorial.data.client_requests.AuthenticationClient;
import com.eemf.sirgoingfar.retrofittutorial.model.Login;
import com.eemf.sirgoingfar.retrofittutorial.model.User;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AuthenticationActivity extends AppCompatActivity {

    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_authentication);

        String username = "";
        String password = "";

        retrofit = NetworkIoHelper.getRetrofitInstance("base_url", true);

        //Basic Authentication
        basicAuthentication(username, password);

        //Token Authentication
        tokenAuthentication(username, password);
    }

    private void basicAuthentication(String username, String password) {
        String userDetail = username + ":" + password;
        String authHeader = "Basic " + Base64.encodeToString(userDetail.getBytes(), Base64.NO_WRAP);

        try {
            User user = retrofit.create(AuthenticationClient.class)
                    .authenticateUserBasic(authHeader)
                    .execute().body();

            //use the 'user' object
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void tokenAuthentication(String username, String password) {

        Login login = new Login(username, password);

        retrofit.create(AuthenticationClient.class).authenticateUserToken(login)
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        //Success Action - E.g. To Dashboard
                        //It's advisable to save the TOKEN in SharedPreference for easy access
                        getSecretQuestion(response.body().getAccessToken());
                    }

                    private void getSecretQuestion(String accessToken) {
                        retrofit.create(AuthenticationClient.class).getSecretQuestion(accessToken)
                                .enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        //Success Action
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        //Failure Action
                                    }
                                });
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        //Failure Action
                    }
                });
    }
}
