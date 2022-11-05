package com.datechnologies.androidtest.login;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.datechnologies.androidtest.MainActivity;
import com.datechnologies.androidtest.R;
import com.datechnologies.androidtest.utils.API;
import com.datechnologies.androidtest.utils.APIClient;
import com.datechnologies.androidtest.utils.DataWrapper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A screen that displays a login prompt, allowing the user to login to the D & A Technologies Web Server.
 *
 */
public class LoginActivity extends AppCompatActivity {

    private TextInputEditText emailEdt,passwordEdt;
    private MaterialButton loginBtn;
    //==============================================================================================
    // Static Class Methods
    //==============================================================================================

    public static void start(Context context)
    {
        Intent starter = new Intent(context, LoginActivity.class);
        context.startActivity(starter);
    }

    //==============================================================================================
    // Lifecycle Methods
    //==============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        // TODO: Make the UI look like it does in the mock-up. Allow for horizontal screen rotation.
        // TODO: Add a ripple effect when the buttons are clicked
        // TODO: Save screen state on screen rotation, inputted username and password should not disappear on screen rotation

        // TODO: Send 'email' and 'password' to http://dev.rapptrlabs.com/Tests/scripts/login.php
        // TODO: as FormUrlEncoded parameters.

        // TODO: When you receive a response from the login endpoint, display an AlertDialog.
        // TODO: The AlertDialog should display the 'code' and 'message' that was returned by the endpoint.
        // TODO: The AlertDialog should also display how long the API call took in milliseconds.
        // TODO: When a login is successful, tapping 'OK' on the AlertDialog should bring us back to the MainActivity

        // TODO: The only valid login credentials are:
        // TODO: email: info@rapptrlabs.com
        // TODO: password: Test123
        // TODO: so please use those to test the login.

        emailEdt = findViewById(R.id.email_input_edt);
        passwordEdt = findViewById(R.id.password_input_edt);
        loginBtn = findViewById(R.id.login_button);
        loginBtn.setOnClickListener(v->{
            if(validateEmail() && validatePassword()) {
                String emailID = emailEdt.getText().toString();
                String password = passwordEdt.getText().toString();

                API api = APIClient.getClient().create(API.class);
                Call<DataWrapper> loginCall = api.authenticate(emailID,password);
                loginCall.enqueue(new Callback<DataWrapper>() {
                    @Override
                    public void onResponse(Call<DataWrapper> call, Response<DataWrapper> response) {
                        if (response.code()==401) {
                            Toast.makeText(LoginActivity.this,
                                    response.message(),Toast.LENGTH_LONG).show();
                        } else {
                            long responseTime = response.raw().receivedResponseAtMillis()-
                                    response.raw().sentRequestAtMillis();
                            displayAlertDialog(response.code(),response.message(),responseTime);
                        }
                    }

                    @Override
                    public void onFailure(Call<DataWrapper> call, Throwable t) {
                        Log.e("onFailure: ",t.getMessage());
                    }
                });
            }
        });
    }

    private void displayAlertDialog(int code, String message, long responseInMillis) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);
        alertDialog.setTitle(getString(R.string.response_received));
        alertDialog.setMessage(String.format(getString(R.string.response_message),
                code,message,responseInMillis));
        alertDialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onBackPressed();
            }
        });
        alertDialog.create();
        alertDialog.show();
    }

    private boolean validateEmail() {
        if (emailEdt!=null) {
            String email = emailEdt.getText().toString();
            if (email.isEmpty()) {
                emailEdt.setError(getString(R.string.fill_this_field));
                return false;
            } else if (!Pattern.matches(Patterns.EMAIL_ADDRESS.pattern(), email)){
                emailEdt.setError(getString(R.string.fill_valid_email));
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean validatePassword() {
        if (passwordEdt!=null) {
            String password = passwordEdt.getText().toString();
            if (password.isEmpty()) {
                passwordEdt.setError(getString(R.string.fill_this_field));
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
