package com.example.ferreteriaphp.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ferreteriaphp.ArticuloSeleccionado;
import com.example.ferreteriaphp.R;
import com.example.ferreteriaphp.modelo.Articulo;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class AdaptadorPrincipal extends RecyclerView.Adapter<AdaptadorPrincipal.ViewHolder> {

    private List<Articulo> listaArticulos;
    private Context context;

    public AdaptadorPrincipal(List<Articulo> listaArticulos) {
        this.listaArticulos = listaArticulos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.itemprincipal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Articulo articulo = listaArticulos.get(position);

        // Set basic information
        holder.textNombre.setText(articulo.getNombre());
        holder.textCategoria.setText(articulo.getCategoria());
        holder.textDescripcion.setText(articulo.getDescripcion());

        // Set price with currency format
        String precio = articulo.getPrecio() != null ? "$" + articulo.getPrecio() : "N/A";
        holder.textPrecio.setText(precio);

        // Set stock information
        String stock = articulo.getStock() != null ? "Stock: " + articulo.getStock() + " unidades" : "Stock no disponible";
        holder.textStock.setText(stock);

        // Set up the "Ver" button click listener
        holder.buttonVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ArticuloSeleccionado.class);
                intent.putExtra("id", articulo.getId());
                intent.putExtra("nombre", articulo.getNombre());
                intent.putExtra("categoria", articulo.getCategoria());
                intent.putExtra("descripcion", articulo.getDescripcion());
                intent.putExtra("precio", articulo.getPrecio());
                intent.putExtra("stock", articulo.getStock());
                intent.putExtra("origen", articulo.getOrigen());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaArticulos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textNombre;
        TextView textCategoria;
        TextView textDescripcion;
        TextView textPrecio;
        TextView textStock;
        MaterialButton buttonVer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textNombre = itemView.findViewById(R.id.textNombre);
            textCategoria = itemView.findViewById(R.id.textCategoria);
            textDescripcion = itemView.findViewById(R.id.textDescripcion);
            textPrecio = itemView.findViewById(R.id.textPrecio);
            textStock = itemView.findViewById(R.id.textStock);
            buttonVer = itemView.findViewById(R.id.buttonVer);
        }
    }
}
