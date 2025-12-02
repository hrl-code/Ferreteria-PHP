package com.example.ferreteriaphp.modelo;

public class Usuario {
    private String id;
    private String nombre;
    private String apellidos;
    private String edad;
    private String usuario;
    private String password;
    private String tipo;

    public Usuario() {
    }

    public Usuario(String id, String nombre, String apellidos, String edad,
                  String usuario, String password, String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
        this.usuario = usuario;
        this.password = password;
        this.tipo = tipo;
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

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getImagenTipo() {
        if ("admin".equalsIgnoreCase(tipo)) {
            return android.R.drawable.ic_menu_manage; // Placeholder for admin icon
        } else {
            return android.R.drawable.ic_menu_myplaces; // Placeholder for user icon
        }
    }

    public boolean isAdmin() {
        return "admin".equalsIgnoreCase(tipo);
    }
}
