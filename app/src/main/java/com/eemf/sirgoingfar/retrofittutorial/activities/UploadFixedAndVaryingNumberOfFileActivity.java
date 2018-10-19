package com.eemf.sirgoingfar.retrofittutorial.activities;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.eemf.sirgoingfar.retrofittutorial.R;
import com.eemf.sirgoingfar.retrofittutorial.Util.NetworkIoHelper;
import com.eemf.sirgoingfar.retrofittutorial.data.client_requests.UploadFile;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UploadFixedAndVaryingNumberOfFileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_fixed_number_of_file);

        //get the 2 files' Uris
        Uri file1Uri = null;
        Uri file2Uri = null;
        Uri file3Uri = null;
        Uri file4Uri = null;
        Uri file5Uri = null;
        Uri file6Uri = null;

        String fileDesc = "Family Photo"; //you can get this from thr Description EditText field

        //fixed number of photo
        uploadFixedNumberOfFiles(file1Uri, file2Uri);

        //varying and un-predefined number of photo
        uploadVaryingNumberOfFiles(fileDesc, file1Uri, file2Uri);
        uploadVaryingNumberOfFiles(fileDesc, file1Uri, file2Uri, file3Uri);
        uploadVaryingNumberOfFiles(fileDesc, file1Uri, file2Uri, file3Uri, file4Uri);
        uploadVaryingNumberOfFiles(fileDesc, file1Uri, file2Uri, file3Uri, file4Uri, file5Uri);
    }

    private void uploadFixedNumberOfFiles(@NonNull Uri file1Uri, @NonNull Uri file2Uri) {

        Retrofit retrofit = NetworkIoHelper.getRetrofitInstance("base_url", true);

        if(retrofit == null){
            //Toast an unsuccessful message
            Toast.makeText(this, "Upload unsuccessful", Toast.LENGTH_SHORT).show();
            return;
        }

        Call<ResponseBody> call = retrofit.create(UploadFile.class)
                .uploadFixedNumberOfFiles(
                        NetworkIoHelper.createPartFromFile(this, "Photo 1", file1Uri),
                        NetworkIoHelper.createPartFromFile(this, "Photo 1", file2Uri)
                );

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

    private void uploadVaryingNumberOfFiles(@NonNull String fileDesc, @NonNull Uri... fileUri) {

        Retrofit retrofit = NetworkIoHelper.getRetrofitInstance("base_url", true);

        //DO a Null check
        if(retrofit == null){
            //Toast an unsuccessful message
            Toast.makeText(this, "Upload unsuccessful", Toast.LENGTH_SHORT).show();
            return;
        } else if(fileUri.length <= 0){
            //Toast "NOTHING TO UPLOAD" message
            Toast.makeText(this, "Nothing to upload", Toast.LENGTH_SHORT).show();
            return;
        }

        //get List of MultiPartBody.Part objects
        List<MultipartBody.Part> objectList = new ArrayList<>();

        for (int i = 0; i < fileUri.length; i++)
            objectList.add(NetworkIoHelper.createPartFromFile(this, "photo" + i, fileUri[i]));



        Call<ResponseBody> call = retrofit.create(UploadFile.class)
                .uploadVaryingNumberOfFiles(NetworkIoHelper.createPartFromString(fileDesc), objectList);

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
