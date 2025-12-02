package com.example.ferreteriaphp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
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

public class UsuarioSeleccionado extends AppCompatActivity {

    private static final String URL_ACTUALIZAR = "https://reynaldomd.com/phpscript/rescata_usuario.php";
    private static final String PREFS_NAME = "UserPrefs";

    private TextView textId;
    private TextInputEditText inputNombre, inputApellidos, inputEdad;
    private TextInputEditText inputUsuario, inputPassword, inputTipo;
    private ImageButton btnEditar, btnActualizar;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_seleccionado);

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Initialize Volley
        requestQueue = Volley.newRequestQueue(this);

        // Initialize views
        textId = findViewById(R.id.textId);
        inputNombre = findViewById(R.id.inputNombre);
        inputApellidos = findViewById(R.id.inputApellidos);
        inputEdad = findViewById(R.id.inputEdad);
        inputUsuario = findViewById(R.id.inputUsuario);
        inputPassword = findViewById(R.id.inputPassword);
        inputTipo = findViewById(R.id.inputTipo);
        btnEditar = findViewById(R.id.btnEditar);
        btnActualizar = findViewById(R.id.btnActualizar);

        // Get data from intent
        textId.setText(getIntent().getStringExtra("id"));
        inputNombre.setText(getIntent().getStringExtra("nombre"));
        inputApellidos.setText(getIntent().getStringExtra("apellidos"));
        inputEdad.setText(getIntent().getStringExtra("edad"));
        inputUsuario.setText(getIntent().getStringExtra("usuario"));
        inputPassword.setText(getIntent().getStringExtra("password"));
        inputTipo.setText(getIntent().getStringExtra("tipo"));

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
                actualizarUsuario();
            }
        });
    }

    private void actualizarUsuario() {
        final String id = textId.getText().toString();
        final String nombre = inputNombre.getText().toString();
        final String apellidos = inputApellidos.getText().toString();
        final String edad = inputEdad.getText().toString();
        final String password = inputPassword.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ACTUALIZAR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(UsuarioSeleccionado.this, "Usuario actualizado", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UsuarioSeleccionado.this, R.string.error_connection, Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idusuario", id);
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
