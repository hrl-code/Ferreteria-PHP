package com.example.ferreteriaphp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class Cuenta extends AppCompatActivity {

    private static final String URL_ACTUALIZAR = "https://reynaldomd.com/phpscript/rescata_usuario.php";
    private static final String PREFS_NAME = "UserPrefs";

    private TextInputEditText inputNombre, inputApellidos, inputEdad;
    private TextInputEditText inputUsuario, inputPassword, inputTipo;
    private ImageButton btnEditar, btnActualizar;
    private RequestQueue requestQueue;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta);

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Initialize Volley
        requestQueue = Volley.newRequestQueue(this);

        // Initialize views
        inputNombre = findViewById(R.id.inputNombre);
        inputApellidos = findViewById(R.id.inputApellidos);
        inputEdad = findViewById(R.id.inputEdad);
        inputUsuario = findViewById(R.id.inputUsuario);
        inputPassword = findViewById(R.id.inputPassword);
        inputTipo = findViewById(R.id.inputTipo);
        btnEditar = findViewById(R.id.btnEditar);
        btnActualizar = findViewById(R.id.btnActualizar);

        // Get user data from SharedPreferences
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        userId = prefs.getString("user_id", "");
        inputNombre.setText(prefs.getString("user_nombre", ""));
        inputApellidos.setText(prefs.getString("user_apellidos", ""));
        inputEdad.setText(prefs.getString("user_edad", ""));
        inputUsuario.setText(prefs.getString("user_usuario", ""));
        inputTipo.setText(prefs.getString("user_tipo", ""));

        // Disable fields initially
        inputNombre.setEnabled(false);
        inputApellidos.setEnabled(false);
        inputEdad.setEnabled(false);
        inputPassword.setEnabled(false);

        // Setup button listeners
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputNombre.setEnabled(true);
                inputApellidos.setEnabled(true);
                inputEdad.setEnabled(true);
                inputPassword.setEnabled(true);
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarCuenta();
            }
        });
    }

    private void actualizarCuenta() {
        final String nombre = inputNombre.getText().toString();
        final String apellidos = inputApellidos.getText().toString();
        final String edad = inputEdad.getText().toString();
        final String password = inputPassword.getText().toString();

        if (password.isEmpty()) {
            Toast.makeText(this, "Por favor ingrese la contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ACTUALIZAR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Update SharedPreferences
                        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("user_nombre", nombre);
                        editor.putString("user_apellidos", apellidos);
                        editor.putString("user_edad", edad);
                        editor.apply();

                        Toast.makeText(Cuenta.this, "Cuenta actualizada", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Cuenta.this, R.string.error_connection, Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idusuario", userId);
                params.put("nombre", nombre);
                params.put("apellidos", apellidos);
                params.put("edad", edad);
                params.put("password", password);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
