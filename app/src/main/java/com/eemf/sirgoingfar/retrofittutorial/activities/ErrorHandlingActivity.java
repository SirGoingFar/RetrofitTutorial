package com.eemf.sirgoingfar.retrofittutorial.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.eemf.sirgoingfar.retrofittutorial.R;
import com.eemf.sirgoingfar.retrofittutorial.Util.NetworkIoHelper;
import com.eemf.sirgoingfar.retrofittutorial.data.client_requests.UserClient;
import com.eemf.sirgoingfar.retrofittutorial.data.response_models.BaseErrorModel;
import com.eemf.sirgoingfar.retrofittutorial.data.response_models.request_responses.GetUserSessionDataResponseModel;
import com.eemf.sirgoingfar.retrofittutorial.data.response_models.response_parsers.ResponseParser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ErrorHandlingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_handling);

        downloadSessionUserData();
    }

    private void downloadSessionUserData() {

        Call<GetUserSessionDataResponseModel.Success> call = NetworkIoHelper.getRetrofitInstance("base_url", true)
                .create(UserClient.class).getUserSessionData("email_or_phone_number_of_the_user");

        call.enqueue(new Callback<GetUserSessionDataResponseModel.Success>() {
            @Override
            public void onResponse(Call<GetUserSessionDataResponseModel.Success> call,
                                   Response<GetUserSessionDataResponseModel.Success> response) {

                if(response.isSuccessful()){
                    //do the needful
                } else {

                    //Method 1 - General Error Handler
                    BaseErrorModel errorModelGeneral = ResponseParser.getGeneralErrorResponseObject(response);
                    String errorCode = errorModelGeneral.getErrorCode();
                    String errorMessage = errorModelGeneral.getErrorMessage();

                    //METHOD 2: Specific Error Handling
                    BaseErrorModel errorModelSpecific = ResponseParser.getErrorResponseObject(response,
                            new GetUserSessionDataResponseModel.Error());

                    if(errorModelSpecific instanceof GetUserSessionDataResponseModel.Error){
                        GetUserSessionDataResponseModel.Error errorResponseObject = (GetUserSessionDataResponseModel.Error) errorModelSpecific;
                        //use the object to get
                        String errorCode_ = errorResponseObject.getErrorCode();
                        String errorCause = errorResponseObject.getErrorCause();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetUserSessionDataResponseModel.Success> call, Throwable t) {

            }
        });
    }
}
