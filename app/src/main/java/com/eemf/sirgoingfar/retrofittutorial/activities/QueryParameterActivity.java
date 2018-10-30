package com.eemf.sirgoingfar.retrofittutorial.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.eemf.sirgoingfar.retrofittutorial.R;
import com.eemf.sirgoingfar.retrofittutorial.data.client_requests.AddQueryParameter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class QueryParameterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_aconstant_query_parameter);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        HttpUrl httpUrl = request.url();

                        HttpUrl newHttpUrl = httpUrl.newBuilder().addQueryParameter("api_key", "API_KEY_VALUE").build();
                        Request newRequest = request.newBuilder().url(newHttpUrl).build();

                        return chain.proceed(newRequest);
                    }
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("base_url")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        AddQueryParameter clientObject = retrofit.create(AddQueryParameter.class);

        //static Query Parameter addition
        clientObject.addStaticQueryParameters("SUB, OAU, Ile-Ife", "asylumn", 1)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                        //success action
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        //failure response
                    }
                });

        //dynamic Query Parameter addition
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("user_location", "Abuja");
        clientObject.addDynamicQueryParameters("Computer Centre, OAU, Ile-Ife", 1, queryMap)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                        //success action
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        //failure action
                    }
                });

        //optional Query Parameter addition
        //If a Query Parameter is optional, pass NULL to it. This can only be achieved when type class is used instead
        //of the corresponding primitive type, i.e. 'Integer' in place of 'int'
        clientObject.addOptionalQueryParameters("Awolowo Hall", null, null)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                        //success action
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        //failure action
                    }
                });

        clientObject.addMultipleValuesForAQueryParameter(
                "Moremi Hall, OAU, Ile-Ife",
                Arrays.asList("Block 3", "Room 321"),
                3)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                        //success action
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        //failure action
                    }
                });
    }
}
