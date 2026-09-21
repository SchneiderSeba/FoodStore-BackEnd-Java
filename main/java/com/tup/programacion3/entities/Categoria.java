package com.tup.programacion3.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Categoria extends Base {
    private String nombre;
    private String descripcion;
    private List<Producto> productos;

    public Categoria() {
        productos = new ArrayList<>();
    }

    public Categoria(String nombre, String descripcion) {
        this();
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public void agregarProducto(Producto producto) {
        if (!this.productos.contains(producto)) {
            this.productos.add(producto);
            if (producto.getCategoria() != this) {
                producto.setCategoria(this);
            }
        }
    }

    @Override
    public String toString() {
        return String.format("Categoria[id=%d, nombre='%s', descripcion='%s', productos=%d]", getId(), nombre,
                descripcion, productos.size());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Categoria categoria = (Categoria) o;
        return Objects.equals(getNombre(), categoria.getNombre()) && Objects.equals(getDescripcion(), categoria.getDescripcion()) && Objects.equals(getProductos(), categoria.getProductos());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNombre(), getDescripcion(), getProductos());
    }
}