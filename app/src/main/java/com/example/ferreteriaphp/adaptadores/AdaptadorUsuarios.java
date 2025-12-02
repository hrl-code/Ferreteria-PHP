package com.example.ferreteriaphp.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ferreteriaphp.R;
import com.example.ferreteriaphp.UsuarioSeleccionado;
import com.example.ferreteriaphp.modelo.Usuario;

import java.util.List;

public class AdaptadorUsuarios extends RecyclerView.Adapter<AdaptadorUsuarios.ViewHolder> {

    private Context context;
    private List<Usuario> listaUsuarios;

    public AdaptadorUsuarios(Context context, List<Usuario> listaUsuarios) {
        this.context = context;
        this.listaUsuarios = listaUsuarios;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemlistadousuarios, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Usuario usuario = listaUsuarios.get(position);
        holder.textNombre.setText(usuario.getNombre());
        holder.textApellidos.setText(usuario.getApellidos());
        holder.textTipo.setText(usuario.getTipo());
        holder.imageTipo.setImageResource(usuario.getImagenTipo());

        holder.cardUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UsuarioSeleccionado.class);
                intent.putExtra("id", usuario.getId());
                intent.putExtra("nombre", usuario.getNombre());
                intent.putExtra("apellidos", usuario.getApellidos());
                intent.putExtra("edad", usuario.getEdad());
                intent.putExtra("usuario", usuario.getUsuario());
                intent.putExtra("password", usuario.getPassword());
                intent.putExtra("tipo", usuario.getTipo());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardUsuario;
        TextView textNombre;
        TextView textApellidos;
        TextView textTipo;
        ImageView imageTipo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardUsuario = itemView.findViewById(R.id.cardUsuario);
            textNombre = itemView.findViewById(R.id.textNombre);
            textApellidos = itemView.findViewById(R.id.textApellidos);
            textTipo = itemView.findViewById(R.id.textTipo);
            imageTipo = itemView.findViewById(R.id.imageTipo);
        }
    }
}
