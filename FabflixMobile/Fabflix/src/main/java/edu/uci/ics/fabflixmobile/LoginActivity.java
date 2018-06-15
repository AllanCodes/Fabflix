package edu.uci.ics.fabflixmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_form);
    }

    public void redirectLogin(View view) {

        /*
            Go to Search page after successful login
         */

        Intent goToIntent = new Intent(this, SearchActivity.class);

        startActivity(goToIntent);
    }

    public void connectToTomcat(View view) {

        // Post request form data
        final Map<String, String> params = new HashMap<String, String>();
        EditText userEditText = (EditText)findViewById(R.id.editText);
        EditText passEditText = (EditText)findViewById(R.id.editText2);
        params.put("username", userEditText.getText().toString());
        params.put("password", passEditText.getText().toString());

        final RequestQueue queue = NetworkManager.sharedManager(this).queue;
        final View currentView = view;

        // 10.0.2.2 is the host machine when running the android emulator
        final StringRequest afterLoginRequest = new StringRequest(Request.Method.GET, "https://ec2-54-219-171-102.us-west-1.compute.amazonaws.com:8443/Fabflix/usercheck",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        /*
                            Only redirect to Search Page after we know we have a successful session stored.
                         */
                        Log.d("response2", response);

                        redirectLogin(currentView);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("security.error", error.toString());
                    }
                }
        );


        final StringRequest loginRequest = new StringRequest(Request.Method.POST, "https://ec2-54-219-171-102.us-west-1.compute.amazonaws.com:8443/Fabflix/AndroidLogin",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("response", response);
                        JSONObject jsonResponse;
                        try {
                            jsonResponse = new JSONObject(response);
                            if (!jsonResponse.get("message").toString().equals("success")) {
                                ((TextView) findViewById(R.id.http_response)).setText(jsonResponse.get("message").toString());
                            } else {
                                queue.add(afterLoginRequest);
                            }
                        } catch (JSONException e) {
                            Log.d("json.error", e.toString());
                        }
                        // Add the request to the RequestQueue.
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("security.error", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }  // HTTP POST Form Data
        };
        queue.add(loginRequest);
//        SafetyNet.getClient(this).verifyWithRecaptcha("6LfqflsUAAAAALpipz_wlztQR47r8M_nsVPDBHUD")
//                .addOnSuccessListener(this, new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
//                    @Override
//                    public void onSuccess(SafetyNetApi.RecaptchaTokenResponse response) {
//                        if (!response.getTokenResult().isEmpty()) {
//                            // Add the request to the RequestQueue.
//                            params.put("g-recaptcha-response", response.getTokenResult());
//                            queue.add(loginRequest);
//                        }
//                    }
//                })
//                .addOnFailureListener(this, new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        if (e instanceof ApiException) {
//                            ApiException apiException = (ApiException) e;
//                            Log.d("Login", "Error message: " +
//                                    CommonStatusCodes.getStatusCodeString(apiException.getStatusCode()));
//                        } else {
//                            Log.d("Login", "Unknown type of error: " + e.getMessage());
//                        }
//                    }
//                });

    }

}
