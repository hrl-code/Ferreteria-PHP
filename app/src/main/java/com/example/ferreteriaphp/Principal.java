package com.example.ferreteriaphp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ferreteriaphp.adaptadores.AdaptadorPrincipal;
import com.example.ferreteriaphp.modelo.Articulo;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Principal extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String URL_DESTACADOS = "https://reynaldomd.com/phpscript/listado_articulos_destacados.php";
    private static final String PREFS_NAME = "UserPrefs";

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private RecyclerView recyclerPrincipal;
    private AdaptadorPrincipal adaptador;
    private List<Articulo> listaArticulos;
    private RequestQueue requestQueue;
    private String userTipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        // Initialize Volley
        requestQueue = Volley.newRequestQueue(this);

        // Get user data
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String userName = prefs.getString("user_nombre", "Usuario");
        userTipo = prefs.getString("user_tipo", "user");

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Setup drawer
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Setup navigation header
        View headerView = navigationView.getHeaderView(0);
        TextView textNavUsername = headerView.findViewById(R.id.textNavUsername);
        textNavUsername.setText(userName);

        // Show/hide admin menu items
        if ("admin".equalsIgnoreCase(userTipo)) {
            navigationView.getMenu().findItem(R.id.nav_nuevo_articulo).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_listado_usuarios).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_nuevo_usuario).setVisible(true);
        }

        // Setup RecyclerView
        recyclerPrincipal = findViewById(R.id.recyclerPrincipal);
        recyclerPrincipal.setLayoutManager(new LinearLayoutManager(this));
        listaArticulos = new ArrayList<>();
        adaptador = new AdaptadorPrincipal(listaArticulos);
        recyclerPrincipal.setAdapter(adaptador);

        // Load destacados
        cargarArticulosDestacados();
    }

    private void cargarArticulosDestacados() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DESTACADOS,
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
                            Toast.makeText(Principal.this, "Error al cargar artículos", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Principal.this, R.string.error_connection, Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_listado_articulos) {
            startActivity(new Intent(this, ListadoArticulos.class));
        } else if (id == R.id.nav_ofertas) {
            startActivity(new Intent(this, Ofertas.class));
        } else if (id == R.id.nav_nuevo_articulo) {
            startActivity(new Intent(this, NuevoArticulo.class));
        } else if (id == R.id.nav_listado_usuarios) {
            startActivity(new Intent(this, ListadoUsuarios.class));
        } else if (id == R.id.nav_nuevo_usuario) {
            startActivity(new Intent(this, NuevoUsuario.class));
        } else if (id == R.id.nav_mi_cuenta) {
            startActivity(new Intent(this, Cuenta.class));
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
