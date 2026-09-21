package com.tup.programacion3.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.tup.programacion3.enums.Rol;

public class Usuario extends Base {
    private String nombre;
    private String apellido;
    private String mail;
    private String celular;
    private String contrasenia;
    private Rol rol;
    private List<Pedido> pedidos;

    public Usuario() {
        pedidos = new ArrayList<>();
    }

    public Usuario(String nombre, String apellido, String mail, String celular, String contrasenia, Rol rol) {
        this();
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.celular = celular;
        this.contrasenia = contrasenia;
        this.rol = rol;
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

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
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
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(getNombre(), usuario.getNombre()) && Objects.equals(getApellido(), usuario.getApellido()) && Objects.equals(getMail(), usuario.getMail()) && Objects.equals(getCelular(), usuario.getCelular()) && Objects.equals(getContrasenia(), usuario.getContrasenia()) && getRol() == usuario.getRol() && Objects.equals(getPedidos(), usuario.getPedidos());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNombre(), getApellido(), getMail(), getCelular(), getContrasenia(), getRol(), getPedidos());
    }
}