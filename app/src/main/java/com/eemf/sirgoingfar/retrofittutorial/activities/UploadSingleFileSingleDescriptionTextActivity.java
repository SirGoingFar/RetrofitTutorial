package com.eemf.sirgoingfar.retrofittutorial.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;

import com.eemf.sirgoingfar.retrofittutorial.R;
import com.eemf.sirgoingfar.retrofittutorial.Util.UriUtil;
import com.eemf.sirgoingfar.retrofittutorial.data.client_requests.UploadFile;
import com.eemf.sirgoingfar.retrofittutorial.services.UploadFileService;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UploadSingleFileSingleDescriptionTextActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_singe_file_single_description_text);

        //Get the Description text from the Description EditText
        String description = "photo file";

        //Process and get the File Uri - snap picture/select picture from Gallery through intent
        File imageFile = new File("emulated://public_picture");
        Uri fileUri = FileProvider.getUriForFile(this, "authority_as_added_to_the_Manifest_File", imageFile);

        //upload file asynchronously
        uploadAsynchronously(description, fileUri);

        //upload file synchronously
        uploadSynchronously(description, fileUri);

    }

    private void uploadSynchronously(String description, Uri fileUri) {
        Intent intent = new Intent(this, UploadFileService.class);
    }

    private void uploadAsynchronously(String description, Uri fileUri) {
        uploadImageFileWithDesc(description, fileUri);
    }

    private void uploadImageFileWithDesc(String description, @NonNull Uri fileUri) {

        //Create RequestBody for the Description
        RequestBody descriptionPart = RequestBody.create(MultipartBody.FORM, description);


        /*Create MultiPartBody for File*/

            //Create RequestBody for the File

                //get the File object from the URI appropriately
                File imageFile = new File(UriUtil.getPath(this, fileUri));

                    RequestBody fileRequestBody = RequestBody.create(MediaType.parse(getContentResolver().getType(fileUri)), imageFile);
                    MultipartBody.Part filePart = MultipartBody.Part.createFormData("photo", imageFile.getName(), fileRequestBody);

        //Make the network call
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("base_url")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        UploadFile uploadFile = retrofit.create(UploadFile.class);
        Call<ResponseBody> call = uploadFile.uploadSingleFileWithSingleDesc(descriptionPart, filePart);

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
