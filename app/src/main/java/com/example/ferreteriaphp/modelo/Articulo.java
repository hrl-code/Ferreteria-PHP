package com.example.ferreteriaphp.modelo;

public class Articulo {
    private String id;
    private String nombre;
    private String categoria;
    private String descripcion;
    private String precio;
    private String stock;
    private String origen;
    private String destacado;
    private String oferta;
    private String precioOferta;

    public Articulo() {
    }

    public Articulo(String id, String nombre, String categoria, String descripcion,
                   String precio, String stock, String origen, String destacado,
                   String oferta, String precioOferta) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.origen = origen;
        this.destacado = destacado;
        this.oferta = oferta;
        this.precioOferta = precioOferta;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestacado() {
        return destacado;
    }

    public void setDestacado(String destacado) {
        this.destacado = destacado;
    }

    public String getOferta() {
        return oferta;
    }

    public void setOferta(String oferta) {
        this.oferta = oferta;
    }

    public String getPrecioOferta() {
        return precioOferta;
    }

    public void setPrecioOferta(String precioOferta) {
        this.precioOferta = precioOferta;
    }

    public int getImagenCategoria() {
        switch (categoria) {
            case "Menaje":
                return android.R.drawable.ic_menu_compass; // Placeholder
            case "Herramientas":
                return android.R.drawable.ic_menu_manage; // Placeholder
            case "Decoración":
                return android.R.drawable.ic_menu_gallery; // Placeholder
            default:
                return android.R.drawable.ic_menu_help;
        }
    }
}
