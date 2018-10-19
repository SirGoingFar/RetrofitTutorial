package com.eemf.sirgoingfar.retrofittutorial.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.eemf.sirgoingfar.retrofittutorial.R;
import com.eemf.sirgoingfar.retrofittutorial.Util.NetworkIoHelper;
import com.eemf.sirgoingfar.retrofittutorial.data.client_requests.GitHubClient;
import com.eemf.sirgoingfar.retrofittutorial.model.GitHubRepo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GetApiJsonDataActivity extends AppCompatActivity {

    TextView resultTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTv = findViewById(R.id.tv_result);

        makeNetWorkRequest();
    }

    private void makeNetWorkRequest() {

        Retrofit retrofit = NetworkIoHelper.getRetrofitInstance("http://api.github.com", false);

        Call<List<GitHubRepo>> call = retrofit.create(GitHubClient.class).getUserRepos("Nezspencer");

        call.enqueue(new Callback<List<GitHubRepo>>() {
            @Override
            public void onResponse(Call<List<GitHubRepo>> call, Response<List<GitHubRepo>> response) {

                if(response.body() == null)
                    return;

                Log.d("TAG", response.toString());

                for (GitHubRepo repo : response.body()){
                    resultTv.append("\n" + repo.getRepoName());
                }
            }

            @Override
            public void onFailure(Call<List<GitHubRepo>> call, Throwable t) {
                Toast.makeText(GetApiJsonDataActivity.this, "Error :(", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
