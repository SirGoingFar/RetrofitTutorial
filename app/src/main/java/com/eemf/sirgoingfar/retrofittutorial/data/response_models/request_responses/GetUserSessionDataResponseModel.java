package com.eemf.sirgoingfar.retrofittutorial.data.response_models.request_responses;

import com.eemf.sirgoingfar.retrofittutorial.data.response_models.BaseErrorModel;
import com.eemf.sirgoingfar.retrofittutorial.data.response_models.BaseResponseModel;
import com.eemf.sirgoingfar.retrofittutorial.data.response_models.BaseSuccessModel;
import com.google.gson.annotations.SerializedName;


public class GetUserSessionDataResponseModel extends BaseResponseModel {

    public class Success extends BaseSuccessModel{

        @SerializedName("status")
        private String status;

        @SerializedName("name")
        private String name;

        @SerializedName("country")
        private String country;

        @SerializedName("dob")
        private String dob;

        @SerializedName("access_token")
        private String accessToken;

        @SerializedName("user_id")
        private String userId;

        public String getStatus() {
            return status;
        }

        public String getName() {
            return name;
        }

        public String getCountry() {
            return country;
        }

        public String getDob() {
            return dob;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public String getUserId() {
            return userId;
        }
    }

    public static class Error extends BaseErrorModel{

        //assuming the error object will be different from the BaseErrorModel's
        //this can be done - but it's advisable to use the general Error response
        //thereby having just one error handler format
        @SerializedName("cause")
        private String errorCause;

        public String getErrorCause() {
            return errorCause;
        }
    }
}
