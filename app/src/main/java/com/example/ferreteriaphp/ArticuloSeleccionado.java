package com.example.ferreteriaphp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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

public class ArticuloSeleccionado extends AppCompatActivity {

    private static final String URL_ACTUALIZAR = "https://reynaldomd.com/phpscript/actualiza_articulo.php";
    private static final String URL_ELIMINAR = "https://reynaldomd.com/phpscript/elimina_articulo.php";
    private static final String PREFS_NAME = "UserPrefs";

    private TextView textId;
    private TextInputEditText inputNombre, inputCategoria, inputDescripcion;
    private TextInputEditText inputPrecio, inputStock, inputOrigen;
    private ImageButton btnEliminar, btnEditar, btnActualizar;
    private LinearLayout layoutBotonesAdmin;
    private RequestQueue requestQueue;
    private boolean isAdmin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articulo_seleccionado);

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Initialize Volley
        requestQueue = Volley.newRequestQueue(this);

        // Check if user is admin
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String userTipo = prefs.getString("user_tipo", "user");
        isAdmin = "admin".equalsIgnoreCase(userTipo);

        // Initialize views
        textId = findViewById(R.id.textId);
        inputNombre = findViewById(R.id.inputNombre);
        inputCategoria = findViewById(R.id.inputCategoria);
        inputDescripcion = findViewById(R.id.inputDescripcion);
        inputPrecio = findViewById(R.id.inputPrecio);
        inputStock = findViewById(R.id.inputStock);
        inputOrigen = findViewById(R.id.inputOrigen);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnEditar = findViewById(R.id.btnEditar);
        btnActualizar = findViewById(R.id.btnActualizar);
        layoutBotonesAdmin = findViewById(R.id.layoutBotonesAdmin);

        // Show admin buttons if admin
        if (isAdmin) {
            layoutBotonesAdmin.setVisibility(View.VISIBLE);
        }

        // Get data from intent
        textId.setText(getIntent().getStringExtra("id"));
        inputNombre.setText(getIntent().getStringExtra("nombre"));
        inputCategoria.setText(getIntent().getStringExtra("categoria"));
        inputDescripcion.setText(getIntent().getStringExtra("descripcion"));
        inputPrecio.setText(getIntent().getStringExtra("precio"));
        inputStock.setText(getIntent().getStringExtra("stock"));
        inputOrigen.setText(getIntent().getStringExtra("origen"));

        // Setup button listeners
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputPrecio.setEnabled(true);
                inputStock.setEnabled(true);
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarArticulo();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoEliminar();
            }
        });
    }

    private void actualizarArticulo() {
        final String id = textId.getText().toString();
        final String precio = inputPrecio.getText().toString();
        final String stock = inputStock.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ACTUALIZAR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ArticuloSeleccionado.this);
                        builder.setTitle(R.string.actualizacion_articulos);
                        builder.setMessage(R.string.actualizacion_realizada);
                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        builder.show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ArticuloSeleccionado.this, R.string.error_connection, Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idarticulo", id);
                params.put("precio", precio);
                params.put("stock", stock);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void mostrarDialogoEliminar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.eliminacion_articulos);
        builder.setMessage(R.string.confirmacion_eliminar_articulo);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                eliminarArticulo();
            }
        });
        builder.setNegativeButton(R.string.cancelar, null);
        builder.show();
    }

    private void eliminarArticulo() {
        final String id = textId.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ELIMINAR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(ArticuloSeleccionado.this, "Artículo eliminado", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ArticuloSeleccionado.this, R.string.error_connection, Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idarticulo", id);
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
