package com.eemf.sirgoingfar.retrofittutorial.activities;

import com.eemf.sirgoingfar.retrofittutorial.data.client_requests.GitHubClient;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class NormalJavaProjectWithRetrofit {

    /*
        * Because the SimpleXmlConverter is used (unlike GsonConverter where @SerializableName("field_desc") is OPTIONAL
        * if the 'field_desc' is THE SAME as the Java variable name 'field_desc' is to be serialized into)
        * the Pojo has to be annotated - COMPULSORILY
        */
    @Root(name = "task")
    public class Task {

        @Element(name = "id")
        private int id;

        @Element(name = "title")
        private String title;

        @Element(name = "description")
        private String description;

        @Element(name = "language")
        private String language;
    }

    private static Call<Task> call;

    public static void main(String args[]) {


        //If you can make several references to the example you used while introducing a topic (at any point in teaching the topic)
        // and your students can relate,
        //then you made a good introduction
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("base_url")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        call = retrofit.create(GitHubClient.class).getFile();
        call.enqueue(new Callback<Task>() {
            @Override
            public void onResponse(Call<Task> call, Response<Task> response) {
                //success action
            }

            @Override
            public void onFailure(Call<Task> call, Throwable t) {
                /*
                * This check is needed because when a request is cancelled, onFailure callback is called.
                * However, to differentiate a failed request from a cancelled request, the check is needed*/
                if (!call.isCanceled()) {
                    //Failure Action
                } else {
                    //Request Cancel Action
                }
            }
        });
    }

    private void cancelRequest() {
        //To cancel a request
    }
}
