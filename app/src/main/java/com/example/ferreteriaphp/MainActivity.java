package com.example.ferreteriaphp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String URL_LOGIN = "https://reynaldomd.com/phpscript/login.php";
    private static final String PREFS_NAME = "UserPrefs";

    private TextInputEditText inputUsername;
    private TextInputEditText inputPassword;
    private MaterialButton buttonLogin;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        inputUsername = findViewById(R.id.inputUsername);
        inputPassword = findViewById(R.id.inputPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        // Initialize Volley
        requestQueue = Volley.newRequestQueue(this);

        // Set login button click listener
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        final String username = inputUsername.getText().toString().trim();
        final String password = inputPassword.getText().toString().trim();

        // Validate input
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, R.string.error_empty_fields, Toast.LENGTH_SHORT).show();
            return;
        }

        // Disable button during login
        buttonLogin.setEnabled(false);

        // Make login request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        buttonLogin.setEnabled(true);
                        try {
                            // Try to parse as JSONObject first
                            JSONObject jsonResponse = new JSONObject(response);

                            // Check if login was successful
                            if (jsonResponse.has("status") && "success".equals(jsonResponse.getString("status"))) {
                                // Save user data
                                SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();

                                // Get user data from response or use defaults
                                editor.putString("user_id", jsonResponse.optString("idusuario", "1"));
                                editor.putString("user_nombre", jsonResponse.optString("nombre", username));
                                editor.putString("user_apellidos", jsonResponse.optString("apellidos", ""));
                                editor.putString("user_edad", jsonResponse.optString("edad", ""));
                                editor.putString("user_usuario", username);
                                editor.putString("user_tipo", jsonResponse.getString("tipo"));
                                editor.apply();

                                // Navigate to Principal activity
                                Intent intent = new Intent(MainActivity.this, Principal.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(MainActivity.this, R.string.error_login, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, R.string.error_login, Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        buttonLogin.setEnabled(true);
                        Toast.makeText(MainActivity.this, R.string.error_connection, Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("usuario", username);
                params.put("password", password);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}
