package com.example.ferreteriaphp.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ferreteriaphp.R;
import com.example.ferreteriaphp.modelo.Articulo;

import java.util.List;

public class AdaptadorPrincipal extends RecyclerView.Adapter<AdaptadorPrincipal.ViewHolder> {

    private List<Articulo> listaArticulos;

    public AdaptadorPrincipal(List<Articulo> listaArticulos) {
        this.listaArticulos = listaArticulos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemprincipal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Articulo articulo = listaArticulos.get(position);
        holder.textNombre.setText(articulo.getNombre());
        holder.textCategoria.setText(articulo.getCategoria());
        holder.textDescripcion.setText(articulo.getDescripcion());
    }

    @Override
    public int getItemCount() {
        return listaArticulos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textNombre;
        TextView textCategoria;
        TextView textDescripcion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textNombre = itemView.findViewById(R.id.textNombre);
            textCategoria = itemView.findViewById(R.id.textCategoria);
            textDescripcion = itemView.findViewById(R.id.textDescripcion);
        }
    }
}
