package com.example.ferreteriaphp;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class NuevoUsuario extends AppCompatActivity {

    private static final String URL_REGISTRAR = "https://reynaldomd.com/phpscript/registro_usuario.php";

    private TextInputEditText inputNombre, inputApellidos, inputEdad;
    private TextInputEditText inputUsuario, inputPassword;
    private Spinner spinnerTipo;
    private MaterialButton btnRegistrar;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_usuario);

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
        spinnerTipo = findViewById(R.id.spinnerTipo);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        // Setup spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tipos_usuario, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipo.setAdapter(adapter);

        // Set button listener
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });
    }

    private void registrarUsuario() {
        final String nombre = inputNombre.getText().toString().trim();
        final String apellidos = inputApellidos.getText().toString().trim();
        final String edad = inputEdad.getText().toString().trim();
        final String usuario = inputUsuario.getText().toString().trim();
        final String password = inputPassword.getText().toString().trim();
        final String tipo = spinnerTipo.getSelectedItem().toString();

        if (nombre.isEmpty() || apellidos.isEmpty() || edad.isEmpty() ||
            usuario.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, R.string.error_empty_fields, Toast.LENGTH_SHORT).show();
            return;
        }

        btnRegistrar.setEnabled(false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGISTRAR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        btnRegistrar.setEnabled(true);
                        Toast.makeText(NuevoUsuario.this, "Usuario registrado", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        btnRegistrar.setEnabled(true);
                        Toast.makeText(NuevoUsuario.this, R.string.error_connection, Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nombre", nombre);
                params.put("apellidos", apellidos);
                params.put("edad", edad);
                params.put("usuario", usuario);
                params.put("password", password);
                params.put("tipo", tipo);
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
