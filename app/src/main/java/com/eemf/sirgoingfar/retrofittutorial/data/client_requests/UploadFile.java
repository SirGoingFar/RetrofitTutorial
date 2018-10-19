package com.eemf.sirgoingfar.retrofittutorial.data.client_requests;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface UploadFile {

    @Multipart
    @POST("upload_file_end_point_relative_path")
    Call<ResponseBody> uploadSingleFileWithSingleDesc(
            @Part("description") RequestBody description,
            @Part MultipartBody.Part file
    );

    @Multipart
    @POST("upload_file_end_point_relative_path")
    Call<ResponseBody> uploadSingleFileWithMultiplePart(
            @Part("desc") RequestBody desc,
            @Part("location") RequestBody location,
            @Part("photographer") RequestBody photographer,
            @Part("year") RequestBody year,
            @Part MultipartBody.Part file);

    @Multipart
    @POST("upload_file_end_point_relative_path")
    Call<ResponseBody> uploadSingleFileWithMultiplePartUsingPortMap(
            @PartMap Map<String, RequestBody> dataPartMap,
            @Part MultipartBody.Part file);

    //Assume numOfFiles = 2
    @Multipart
    @POST("upload_2_files_relative_end_point_path")
    Call<ResponseBody> uploadFixedNumberOfFiles(
            @Part MultipartBody.Part file1,
            @Part MultipartBody.Part file2);

    @Multipart
    @POST("upload_variable_num_of_file_relative_end_point_path")
    Call<ResponseBody> uploadVaryingNumberOfFiles(
            @Part("files_description") RequestBody description,
            @Part List<MultipartBody.Part> objectList);
}
