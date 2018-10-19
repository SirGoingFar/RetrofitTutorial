package com.eemf.sirgoingfar.retrofittutorial.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.eemf.sirgoingfar.retrofittutorial.Util.NetworkIoHelper;
import com.eemf.sirgoingfar.retrofittutorial.data.client_requests.UploadFile;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UploadFileService extends IntentService {

    public static final String EXTRA_DESCRIPTION = "file_description";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public UploadFileService() {
        super(UploadFileService.class.getName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if(intent != null && intent.getData() != null && intent.hasExtra(EXTRA_DESCRIPTION)){

            Retrofit retrofit = NetworkIoHelper.getRetrofitInstance("base_url", true);

            if(retrofit == null){
                //notify user of failure
                return;
            }

            String fileDesc = intent.getStringExtra(EXTRA_DESCRIPTION);

            try {
                Response<ResponseBody> result = retrofit.create(UploadFile.class)
                        .uploadSingleFileWithSingleDesc(
                                NetworkIoHelper.createPartFromString(fileDesc),
                                NetworkIoHelper.createPartFromFile(
                                        this.getApplicationContext(),
                                        fileDesc,
                                        intent.getData()))
                        .execute();

                if(result != null){
                    //notify of success
                    //perform next operation that should be done on upload success
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
