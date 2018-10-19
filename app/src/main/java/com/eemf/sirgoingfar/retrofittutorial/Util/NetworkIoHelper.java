package com.eemf.sirgoingfar.retrofittutorial.Util;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.eemf.sirgoingfar.retrofittutorial.BuildConfig;

import java.io.File;
import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkIoHelper {

    private static Retrofit retrofit;

    public static RequestBody createPartFromString(String stringValue) {

        RequestBody valuePart = null;

        if (!TextUtils.isEmpty(stringValue))
            valuePart = RequestBody.create(MultipartBody.FORM, stringValue);

        return valuePart;
    }

    public static MultipartBody.Part createPartFromFile(Context context, String fileDesc, Uri fileUri) {

        //get File object from the fileUri - equate file object to the result of the operation
        File file = new File(UriUtil.getPath(context, fileUri));

        MultipartBody.Part filePart;

        RequestBody fileRequestBody = RequestBody.create(MediaType.parse(context.getContentResolver().getType(fileUri)), file);

        filePart = MultipartBody.Part.createFormData(fileDesc, file.getName(), fileRequestBody);

        return filePart;

    }

    public static Retrofit getRetrofitInstance(@NonNull String baseUrl, boolean addUserIdToRequest) {

        //Create OkHttpClient Builder and HttpLoggingInterceptor
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(HttpLoggingInterceptor.Level.BODY);

        //Add logger to client if it's DEBUG
        if(BuildConfig.DEBUG)
            clientBuilder.addInterceptor(logger);

        if(addUserIdToRequest) {
            //Todo: Here's the best place to put 'user_id'
            //Add an Authorization (e.g. user_id) Header Interceptior
            clientBuilder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {

                    //get the built request
                    Request newRequest = chain.request();

                    //get a builder on the request
                    Request.Builder newRequestBuilder = newRequest.newBuilder();

                    //Todo: add the header - if there's any 'user_id' header in the request
                    //prior this point, #header will replace it
                    //however, to add another header 'user_id' - without replacing it - should it exists prior this point
                    //use function #addHeader
                    newRequestBuilder.header("user_id", "user_id");

                    //continue the flow
                    return chain.proceed(newRequestBuilder.build());
                }
            });
        }

        if (retrofit == null)
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(clientBuilder.build())
                    .build();

        return retrofit;
    }

}
