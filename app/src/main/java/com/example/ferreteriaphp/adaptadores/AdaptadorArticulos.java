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

import com.example.ferreteriaphp.ArticuloSeleccionado;
import com.example.ferreteriaphp.R;
import com.example.ferreteriaphp.modelo.Articulo;

import java.util.List;

public class AdaptadorArticulos extends RecyclerView.Adapter<AdaptadorArticulos.ViewHolder> {

    private List<Articulo> listaArticulos;
    private Context context;

    public AdaptadorArticulos(Context context, List<Articulo> listaArticulos) {
        this.context = context;
        this.listaArticulos = listaArticulos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemlistadoarticulos, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Articulo articulo = listaArticulos.get(position);
        holder.textNombre.setText(articulo.getNombre());
        holder.textCategoria.setText(articulo.getCategoria());
        holder.textPrecio.setText(articulo.getPrecio());
        holder.imageCategoria.setImageResource(articulo.getImagenCategoria());

        holder.cardArticulo.setOnClickListener(new View.OnClickListener() {
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
        CardView cardArticulo;
        TextView textNombre;
        TextView textCategoria;
        TextView textPrecio;
        ImageView imageCategoria;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardArticulo = itemView.findViewById(R.id.cardArticulo);
            textNombre = itemView.findViewById(R.id.textNombre);
            textCategoria = itemView.findViewById(R.id.textCategoria);
            textPrecio = itemView.findViewById(R.id.textPrecio);
            imageCategoria = itemView.findViewById(R.id.imageCategoria);
        }
    }
}
