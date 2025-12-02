package com.example.ferreteriaphp;

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
import com.example.ferreteriaphp.adaptadores.AdaptadorArticulos;
import com.example.ferreteriaphp.modelo.Articulo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListadoArticulos extends AppCompatActivity {

    private static final String URL_ARTICULOS = "https://reynaldomd.com/phpscript/listado_articulos.php";

    private RecyclerView recyclerArticulos;
    private AdaptadorArticulos adaptador;
    private List<Articulo> listaArticulos;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_articulos);

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Initialize Volley
        requestQueue = Volley.newRequestQueue(this);

        // Setup RecyclerView
        recyclerArticulos = findViewById(R.id.recyclerArticulos);
        recyclerArticulos.setLayoutManager(new LinearLayoutManager(this));
        listaArticulos = new ArrayList<>();
        adaptador = new AdaptadorArticulos(this, listaArticulos);
        recyclerArticulos.setAdapter(adaptador);

        // Load articles
        cargarArticulos();
    }

    private void cargarArticulos() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_ARTICULOS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            listaArticulos.clear();

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
                                articulo.setOferta(obj.optString("oferta", "0"));
                                articulo.setPrecioOferta(obj.optString("preciooferta", "0"));
                                listaArticulos.add(articulo);
                            }

                            adaptador.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ListadoArticulos.this, "Error al cargar artículos", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ListadoArticulos.this, R.string.error_connection, Toast.LENGTH_SHORT).show();
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
