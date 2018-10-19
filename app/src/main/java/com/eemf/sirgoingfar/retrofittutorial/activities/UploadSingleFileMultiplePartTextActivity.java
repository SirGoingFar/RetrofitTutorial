package com.eemf.sirgoingfar.retrofittutorial.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.eemf.sirgoingfar.retrofittutorial.R;
import com.eemf.sirgoingfar.retrofittutorial.Util.NetworkIoHelper;
import com.eemf.sirgoingfar.retrofittutorial.data.client_requests.UploadFile;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UploadSingleFileMultiplePartTextActivity extends AppCompatActivity {

    private static Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_single_file_multiple_part_text);

        //get data as appropriate
        String desc = null;
        String location = null;
        String photographer = null;
        String year = null;
        Uri photoUri = null;

        //upload by specifying the supplied values
        uploadSingleFileMultiplePartText(desc, location, photographer, year, photoUri);

        //upload using PortMap and adding extra Part
        uploadSingleFileAndPartTextMap(desc, location, photographer, year, photoUri);
    }

    private void uploadSingleFileMultiplePartText(String desc, String location, String photographer, String year, Uri photoUri) {

        RequestBody descPart = null;
        RequestBody locationPart = null;
        RequestBody photographerPart = null;
        RequestBody yearPart = null;

        if (!TextUtils.isEmpty(desc)) {
            descPart = NetworkIoHelper.createPartFromString(desc);
        }

        if (!TextUtils.isEmpty(location)) {
            locationPart = NetworkIoHelper.createPartFromString(desc);
        }

        if (!TextUtils.isEmpty(photographer)) {
            photographerPart = NetworkIoHelper.createPartFromString(desc);
        }

        if (!TextUtils.isEmpty(year)) {
            yearPart = NetworkIoHelper.createPartFromString(desc);
        }

        MultipartBody.Part filePart = NetworkIoHelper.createPartFromFile(this, "photo", photoUri);

        Retrofit retrofit = NetworkIoHelper.getRetrofitInstance("base_url", true);

        UploadFile uploadFile = retrofit.create(UploadFile.class);

        Call<ResponseBody> call = uploadFile.uploadSingleFileWithMultiplePart(descPart, locationPart, photographerPart, yearPart, filePart);

        call.enqueue(new Callback<ResponseBody>() {
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

    private void uploadSingleFileAndPartTextMap(String desc, String location, String photographer, String year, Uri photoUri) {

        Map<String, RequestBody> dataPartMap = new HashMap<>();

        //additional part can be added, hardcoded
        dataPartMap.put("cameraType", NetworkIoHelper.createPartFromString("Samsung"));
        dataPartMap.put("Country", NetworkIoHelper.createPartFromString("Nigeria"));

        dataPartMap.put(desc, NetworkIoHelper.createPartFromString(desc));
        dataPartMap.put(location, NetworkIoHelper.createPartFromString(location));
        dataPartMap.put(photographer, NetworkIoHelper.createPartFromString(photographer));
        dataPartMap.put(year, NetworkIoHelper.createPartFromString(year));

        MultipartBody.Part filePart = NetworkIoHelper.createPartFromFile(this, "photo", photoUri);

        Retrofit retrofit = NetworkIoHelper.getRetrofitInstance("base_url", true);

        UploadFile uploadFile = retrofit.create(UploadFile.class);

        Call<ResponseBody> call = uploadFile.uploadSingleFileWithMultiplePartUsingPortMap(dataPartMap, filePart);

        call.enqueue(new Callback<ResponseBody>() {
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
}
