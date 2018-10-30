package com.eemf.sirgoingfar.retrofittutorial.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.eemf.sirgoingfar.retrofittutorial.R;
import com.eemf.sirgoingfar.retrofittutorial.data.client_requests.GitHubClient;
import com.eemf.sirgoingfar.retrofittutorial.model.AccessToken;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GitHubOAuthActivity extends AppCompatActivity {

    /*NB: Some of these parameters mary vary with OAuth Providers*/
    private String clientId = "register your app with the OAuth Provider to get it";
    private String clientSecret = "register your app with the OAuth Provider to get it";
    String redirectUri = "futurestudio://callback"; //- added with an <intent-filter> in the Manifest file - It's registered with the OAuth Provider too
    private String authToken;
    private String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_git_hub_oauth);

        //open the OAuth Provider Login page
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String githubOAuthLoginUrl = "https://github.com/login/oauth/authorize?client_id=" + clientId + "&scope=repos&redirect_uri=" + redirectUri;
        intent.setData(Uri.parse(githubOAuthLoginUrl));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();

        if (intent != null && intent.getData() != null) {

            Uri intentDataUri = intent.getData();

            if (intentDataUri != null && intentDataUri.toString().startsWith(redirectUri)) {
                authToken = intentDataUri.getQueryParameter("code");

                //get the accessToken from the authToken
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://github.com")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                retrofit.create(GitHubClient.class).getAccessToken(clientId, clientSecret, authToken)
                        .enqueue(new Callback<AccessToken>() {
                            @Override
                            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                                if (response.isSuccessful()) {
                                    accessToken = response.body().getAccessToken();
                                    /*The accessToken can then be used to make API calls*/
                                }
                            }

                            @Override
                            public void onFailure(Call<AccessToken> call, Throwable t) {
                                //failure action
                            }
                        });
            }

        }
    }
}
