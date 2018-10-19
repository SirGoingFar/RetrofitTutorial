package com.eemf.sirgoingfar.retrofittutorial.data.response_models;

import com.google.gson.annotations.SerializedName;

public class BaseErrorModel extends BaseResponseModel {

    @SerializedName("status")
    private String status;

    @SerializedName("error_code")
    private String errorCode;

    @SerializedName("error_message")
    private String errorMessage;

    @SerializedName("exception_message")
    private String exceptionMessage;

    public String getStatus() {
        return status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }
}
