package com.eemf.sirgoingfar.retrofittutorial.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.eemf.sirgoingfar.retrofittutorial.R;
import com.eemf.sirgoingfar.retrofittutorial.Util.NetworkIoHelper;
import com.eemf.sirgoingfar.retrofittutorial.data.client_requests.SendDataFormUrlEncoded;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormUrlEncodedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_url_encoded);

        //get these from input sources
        String name = "Norman";
        String age = "23";
        String email = "info@futurestud.io";
        String topics = "Android, PHP, JS";

        //with fixed size
        sendFeedBack(name, age, email, topics);

        //Variable Size: Map and List - If Map<String, Object> and the list of topics is passed, at the server end
        //the list is seen as an array - hence better to send the Map and List different
        Map<String, String> fieldMap = new HashMap<>();
        fieldMap.put("name", name);
        fieldMap.put("age", age);
        fieldMap.put("email", email);
        List<String> topicsList = Arrays.asList(topics.split(","));
        sendFeedBack(fieldMap, topicsList);

        //variable size
        fieldMap.put("topic", topics);
        sendFeedBack(fieldMap);

    }

    private void sendFeedBack(String name, String age, String email, String topics) {

        NetworkIoHelper.getRetrofitInstance("base_url", true)
                .create(SendDataFormUrlEncoded.class)
                .sendFeedbackFixedSize(name, age, email, topics)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //success action
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        //error action
                    }
                });
    }

    private void sendFeedBack(@NonNull Map fieldMap) {
        NetworkIoHelper.getRetrofitInstance("base_url", true)
                .create(SendDataFormUrlEncoded.class)
                .sendFeedbackFixedSize(fieldMap)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //success action
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        //error action
                    }
                });
    }

    private void sendFeedBack(Map<String, String> fieldMap, List<String> topicsList) {

        NetworkIoHelper.getRetrofitInstance("base_url", true)
                .create(SendDataFormUrlEncoded.class)
                .sendFeedbackFixedSize(fieldMap, topicsList)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //success action
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        //error action
                    }
                });
    }
}
