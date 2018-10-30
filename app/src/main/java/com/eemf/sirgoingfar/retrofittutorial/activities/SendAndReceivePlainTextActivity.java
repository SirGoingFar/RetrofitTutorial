package com.eemf.sirgoingfar.retrofittutorial.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.eemf.sirgoingfar.retrofittutorial.R;
import com.eemf.sirgoingfar.retrofittutorial.Util.NetworkIoHelper;
import com.eemf.sirgoingfar.retrofittutorial.data.client_requests.SendPlainTextClient;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendAndReceivePlainTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_and_receive_plain_text);

        //sending plain text (String)
        sendPlainText("plain text");

        //sending plain text (Requestbody)
        sendPlainText(NetworkIoHelper.createPartFromString("plain text"));
    }

    private void sendPlainText(String text) {
        NetworkIoHelper.getRetrofitInstance("base_url", true)
                .create(SendPlainTextClient.class)
                .sendPlainTextAsString(text)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        //success action
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        //failure action
                    }
                });
    }

    private void sendPlainText(RequestBody requestBody) {
        NetworkIoHelper.getRetrofitInstance("base_url", true)
                .create(SendPlainTextClient.class)
                .sendPlainTextAsRequestBody(requestBody)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        //success action
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        //failure action
                    }
                });
    }
}
