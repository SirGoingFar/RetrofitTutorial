package com.eemf.sirgoingfar.retrofittutorial.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.eemf.sirgoingfar.retrofittutorial.R;
import com.eemf.sirgoingfar.retrofittutorial.Util.NetworkIoHelper;
import com.eemf.sirgoingfar.retrofittutorial.data.client_requests.UserClient;
import com.eemf.sirgoingfar.retrofittutorial.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        //Extract Username from the username EditText field
        String username = "SirGoingFar";

        //Extract Password from the password EditText field
        String password = "*******";

        //Extract Age from the age EditText field
        int age = 14;

        //Extract Email from the email EditText field
        String email = "a@b.com";

        //Extract Topics of interest
        String[] topicOfInterest = "Health, Technology, Medicine".split(",");

        //create a User object
        User user = new User(username, password, email, age, topicOfInterest);

            //Make Network Request
            createUser(user);

        //Edit a user
        editUser(user.getUserId(), user);
    }

    private void createUser(User user) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("base_url")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserClient userClient = retrofit.create(UserClient.class);
        userClient.createUserAccount(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Toast.makeText(CreateAccountActivity.this,
                        getString(R.string.user_created_success_msg, String.valueOf(response.body().getUserId())),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(CreateAccountActivity.this, "Sorry! Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void editUser(int userId, @NonNull User user) {

        Retrofit retrofit = NetworkIoHelper.getRetrofitInstance("base_url", true);

        UserClient userClient = retrofit.create(UserClient.class);
        userClient.editUser(userId, user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Toast.makeText(CreateAccountActivity.this,
                        getString(R.string.user_created_success_msg, String.valueOf(response.body().getUserId())),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(CreateAccountActivity.this, "Sorry! Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
