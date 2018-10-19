package com.eemf.sirgoingfar.retrofittutorial.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.eemf.sirgoingfar.retrofittutorial.R;
import com.eemf.sirgoingfar.retrofittutorial.Util.Helper;
import com.eemf.sirgoingfar.retrofittutorial.Util.NetworkIoHelper;
import com.eemf.sirgoingfar.retrofittutorial.data.client_requests.DownloadClient;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DownloadFile extends AppCompatActivity {

    private static final int REQUEST_WRITE_TO_STORAGE = 0;

    @BindView(R.id.btn_download_file)
    Button downloadBtn;

    private boolean isStreamingDownload;
    private String fileUrl;
    private File imageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_file);

        isStreamingDownload = false;
        fileUrl = "amazon_s3_path";

        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadFile(fileUrl, isStreamingDownload);
            }
        });
    }


    private void downloadFile(String fileUrl, boolean isStreamingDownload) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            //check if we have the permission to write to disk
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED)
                download(fileUrl, isStreamingDownload);
            else
                //request permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_WRITE_TO_STORAGE);
        } else
            download(fileUrl, isStreamingDownload);
    }

    private void download(String fileUrl, boolean isStreamingDownload) {

        try {
            imageFile = Helper.getMediaFile(this, Helper.TYPE_PICTURE, true);

            if(imageFile == null){
                Toast.makeText(this, "Download unsuccessful, please try again.", Toast.LENGTH_SHORT).show();
                return;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        //get Retrofit instance
        Retrofit retrofit = NetworkIoHelper.getRetrofitInstance("base_url", false);

        if(!isStreamingDownload) {
            Call<ResponseBody> call = retrofit.create(DownloadClient.class).downloadPhotoFile(fileUrl);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    saveImageToFile(response.body());
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    //Failure Action
                }
            });
        } else {

            Call<ResponseBody> call = retrofit.create(DownloadClient.class).downloadPhotoFileByStreaming(fileUrl);
            call.enqueue(new Callback<ResponseBody>() {

                @SuppressLint("StaticFieldLeak")
                @Override
                public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {

                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            saveImageToFile(response.body());
                            return null;
                        }
                    }.execute();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    //Failure Action
                }
            });
        }

    }

    private void saveImageToFile(ResponseBody body) {

        //write Java boiler plate to serialize the body stream into variable mediaFile

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){

            case REQUEST_WRITE_TO_STORAGE:

                if(grantResults.length >0 && grantResults.length <2){

                    if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                        download(fileUrl, isStreamingDownload);
                    else
                        Toast.makeText(this, getString(R.string.msg_permission_denied), Toast.LENGTH_SHORT).show();

                }else
                    return;
        }
    }
}
