package com.example.ferreteriaphp;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
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

public class NuevoArticulo extends AppCompatActivity {

    private static final String URL_REGISTRAR = "https://reynaldomd.com/phpscript/registro_articulo.php";

    private TextInputEditText inputNombre, inputDescripcion, inputPrecio, inputStock;
    private Spinner spinnerCategoria, spinnerOrigen;
    private RadioButton radioDestacado, radioOferta;
    private MaterialButton btnRegistrar;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_articulo);

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
        inputDescripcion = findViewById(R.id.inputDescripcion);
        inputPrecio = findViewById(R.id.inputPrecio);
        inputStock = findViewById(R.id.inputStock);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        spinnerOrigen = findViewById(R.id.spinnerOrigen);
        radioDestacado = findViewById(R.id.radioDestacado);
        radioOferta = findViewById(R.id.radioOferta);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        // Setup spinners
        ArrayAdapter<CharSequence> adapterCategoria = ArrayAdapter.createFromResource(this,
                R.array.categorias, android.R.layout.simple_spinner_item);
        adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapterCategoria);

        ArrayAdapter<CharSequence> adapterOrigen = ArrayAdapter.createFromResource(this,
                R.array.origenes, android.R.layout.simple_spinner_item);
        adapterOrigen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOrigen.setAdapter(adapterOrigen);

        // Set button listener
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarArticulo();
            }
        });
    }

    private void registrarArticulo() {
        final String nombre = inputNombre.getText().toString().trim();
        final String categoria = spinnerCategoria.getSelectedItem().toString();
        final String descripcion = inputDescripcion.getText().toString().trim();
        final String precio = inputPrecio.getText().toString().trim();
        final String stock = inputStock.getText().toString().trim();
        final String origen = spinnerOrigen.getSelectedItem().toString();
        final String destacado = radioDestacado.isChecked() ? "1" : "0";
        final String oferta = radioOferta.isChecked() ? "1" : "0";

        if (nombre.isEmpty() || descripcion.isEmpty() || precio.isEmpty() || stock.isEmpty()) {
            Toast.makeText(this, R.string.error_empty_fields, Toast.LENGTH_SHORT).show();
            return;
        }

        btnRegistrar.setEnabled(false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGISTRAR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        btnRegistrar.setEnabled(true);
                        Toast.makeText(NuevoArticulo.this, "Artículo registrado", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        btnRegistrar.setEnabled(true);
                        Toast.makeText(NuevoArticulo.this, R.string.error_connection, Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nombre", nombre);
                params.put("categoria", categoria);
                params.put("descripcion", descripcion);
                params.put("precio", precio);
                params.put("stock", stock);
                params.put("origen", origen);
                params.put("destacado", destacado);
                params.put("oferta", oferta);
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
