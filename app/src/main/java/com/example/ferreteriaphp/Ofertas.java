package com.example.ferreteriaphp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ferreteriaphp.adaptadores.AdaptadorOfertas;
import com.example.ferreteriaphp.adaptadores.AdaptadorOfertas_admin;
import com.example.ferreteriaphp.modelo.Articulo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Ofertas extends AppCompatActivity {

    private static final String URL_OFERTAS = "https://reynaldomd.com/phpscript/listado_articulos_oferta.php";
    private static final String PREFS_NAME = "UserPrefs";

    private RecyclerView recyclerOfertas;
    private List<Articulo> listaOfertas;
    private RequestQueue requestQueue;
    private boolean isAdmin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ofertas);

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

        // Setup RecyclerView
        recyclerOfertas = findViewById(R.id.recyclerOfertas);
        recyclerOfertas.setLayoutManager(new LinearLayoutManager(this));
        listaOfertas = new ArrayList<>();

        // Load ofertas
        cargarOfertas();
    }

    private void cargarOfertas() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_OFERTAS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            listaOfertas.clear();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                Articulo articulo = new Articulo();
                                // Try "id" first, then "idarticulo"
                                String id = obj.optString("id", obj.optString("idarticulo", ""));
                                articulo.setId(id);
                                articulo.setNombre(obj.optString("nombre", ""));
                                articulo.setCategoria(obj.optString("categoria", ""));
                                articulo.setDescripcion(obj.optString("descripcion", ""));
                                articulo.setPrecio(obj.optString("precio", "0"));
                                articulo.setStock(obj.optString("stock", "0"));
                                articulo.setOrigen(obj.optString("origen", ""));
                                articulo.setDestacado(obj.optString("destacado", "0"));
                                articulo.setOferta(obj.optString("oferta", "1"));
                                articulo.setPrecioOferta(obj.optString("preciooferta", "0"));
                                listaOfertas.add(articulo);
                            }

                            // Set adapter based on user type
                            if (isAdmin) {
                                AdaptadorOfertas_admin adaptador = new AdaptadorOfertas_admin(listaOfertas);
                                recyclerOfertas.setAdapter(adaptador);
                            } else {
                                AdaptadorOfertas adaptador = new AdaptadorOfertas(listaOfertas);
                                recyclerOfertas.setAdapter(adaptador);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Ofertas.this, "Error al cargar ofertas", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Ofertas.this, R.string.error_connection, Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
