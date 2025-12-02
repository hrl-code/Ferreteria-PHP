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

public class AdaptadorOfertas extends RecyclerView.Adapter<AdaptadorOfertas.ViewHolder> {

    private List<Articulo> listaOfertas;

    public AdaptadorOfertas(List<Articulo> listaOfertas) {
        this.listaOfertas = listaOfertas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemofertas, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Articulo articulo = listaOfertas.get(position);
        holder.textNombre.setText(articulo.getNombre());
        holder.textCategoria.setText(articulo.getCategoria());
        holder.textDescripcion.setText(articulo.getDescripcion());
        holder.textPrecioOferta.setText(articulo.getPrecioOferta());
        holder.textStock.setText(articulo.getStock());
    }

    @Override
    public int getItemCount() {
        return listaOfertas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textNombre;
        TextView textCategoria;
        TextView textDescripcion;
        TextView textPrecioOferta;
        TextView textStock;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textNombre = itemView.findViewById(R.id.textNombre);
            textCategoria = itemView.findViewById(R.id.textCategoria);
            textDescripcion = itemView.findViewById(R.id.textDescripcion);
            textPrecioOferta = itemView.findViewById(R.id.textPrecioOferta);
            textStock = itemView.findViewById(R.id.textStock);
        }
    }
}
