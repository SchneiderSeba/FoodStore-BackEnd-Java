package com.tup.programacion3.entities;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import com.tup.programacion3.enums.Rol;

public class Usuario extends Base {
    private String nombre;
    private String apellido;
    private String mail;
    private String celular;
    private String contrasenia;
    private Rol rol;
    private Set<Pedido> pedidos;

    private static final Set<Usuario> usuarios = new LinkedHashSet<>();

    public Usuario() {
        pedidos = new LinkedHashSet<>();

    }

    public Usuario(String nombre, String apellido, String mail, String celular, String contrasenia, Rol rol) {
        this();
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.celular = celular;
        this.contrasenia = contrasenia;
        this.rol = rol;
        agregarUsuario(this);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Set<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(Set<Pedido> pedidos) {
        this.pedidos = pedidos == null ? new LinkedHashSet<>() : new LinkedHashSet<>(pedidos);
    }

    public static Set<Usuario> getUsuarios() {
        return usuarios;
    }

    public static void agregarUsuario(Usuario usuario) {
        if (!usuarios.contains(usuario)) {
            usuarios.add(usuario);
        }
    }

    public void agregarPedido(Pedido pedido) {
        if (!this.pedidos.contains(pedido)) {
            this.pedidos.add(pedido);
            if (pedido.getUsuario() != this) {
                pedido.setUsuario(this);
            }
        }
    }

    @Override
    public String toString() {
        return String.format("USUARIO: %s %s | Mail: %s | Rol: %s", nombre, apellido, mail, rol);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(getNombre(), usuario.getNombre()) && Objects.equals(getApellido(), usuario.getApellido())
                && Objects.equals(getMail(), usuario.getMail()) && Objects.equals(getCelular(), usuario.getCelular())
                && Objects.equals(getContrasenia(), usuario.getContrasenia()) && getRol() == usuario.getRol();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNombre(), getApellido(), getMail(), getCelular(), getContrasenia(), getRol());
    }
}
