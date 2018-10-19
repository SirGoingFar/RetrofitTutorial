package com.eemf.sirgoingfar.retrofittutorial.data.response_models.response_parsers;

import com.eemf.sirgoingfar.retrofittutorial.data.response_models.BaseErrorModel;
import com.eemf.sirgoingfar.retrofittutorial.data.response_models.BaseResponseModel;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ResponseParser {

    //responseModelClass instanceOf BaseErrorModel = true
    public static BaseErrorModel getErrorResponseObject(Response<?> response, BaseErrorModel responseObject) {


        Converter<ResponseBody, BaseResponseModel> converter = new Retrofit.Builder().build()
                .responseBodyConverter(responseObject.getClass(), new Annotation[0]);

        try {
            responseObject = (BaseErrorModel) converter.convert(response.errorBody());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseObject;
    }

    public static BaseErrorModel getGeneralErrorResponseObject(Response<?> response) {

        Converter<ResponseBody, BaseResponseModel> converter = new Retrofit.Builder().build()
                .responseBodyConverter(BaseErrorModel.class, new Annotation[0]);

        BaseErrorModel responseObject = null;

        try {
            responseObject = (BaseErrorModel) converter.convert(response.errorBody());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseObject;
    }
}
