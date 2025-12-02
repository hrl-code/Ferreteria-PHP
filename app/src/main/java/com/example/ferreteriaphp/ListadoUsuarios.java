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
import com.example.ferreteriaphp.adaptadores.AdaptadorUsuarios;
import com.example.ferreteriaphp.modelo.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListadoUsuarios extends AppCompatActivity {

    private static final String URL_USUARIOS = "https://reynaldomd.com/phpscript/rescata_usuario.php";

    private RecyclerView recyclerUsuarios;
    private AdaptadorUsuarios adaptador;
    private List<Usuario> listaUsuarios;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_usuarios);

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Initialize Volley
        requestQueue = Volley.newRequestQueue(this);

        // Setup RecyclerView
        recyclerUsuarios = findViewById(R.id.recyclerUsuarios);
        recyclerUsuarios.setLayoutManager(new LinearLayoutManager(this));
        listaUsuarios = new ArrayList<>();
        adaptador = new AdaptadorUsuarios(this, listaUsuarios);
        recyclerUsuarios.setAdapter(adaptador);

        // Load users
        cargarUsuarios();
    }

    private void cargarUsuarios() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_USUARIOS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            listaUsuarios.clear();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                Usuario usuario = new Usuario();
                                // Try "id" first, then "idusuario"
                                String id = obj.optString("id", obj.optString("idusuario", ""));
                                usuario.setId(id);
                                usuario.setNombre(obj.optString("nombre", ""));
                                usuario.setApellidos(obj.optString("apellidos", ""));
                                usuario.setEdad(obj.optString("edad", "0"));
                                usuario.setUsuario(obj.optString("usuario", ""));
                                usuario.setPassword(obj.optString("password", ""));
                                usuario.setTipo(obj.optString("tipo", "user"));
                                listaUsuarios.add(usuario);
                            }

                            adaptador.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ListadoUsuarios.this, "Error al cargar usuarios", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ListadoUsuarios.this, R.string.error_connection, Toast.LENGTH_SHORT).show();
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
