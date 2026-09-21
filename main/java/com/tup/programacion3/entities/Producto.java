package com.tup.programacion3.entities;

import com.tup.programacion3.exceptions.ValorInvalidoException;

import java.util.Objects;
import java.util.Set;
import java.util.LinkedHashSet;

public class Producto extends Base {
    private String nombre;
    private Double precio;
    private String imagen;
    private String descripcion;
    private int stock;
    private boolean disponible;
    private Categoria categoria;

    private static final Set<Producto> productos = new LinkedHashSet<>();

    public Producto() {

    }

    public Producto(String nombre, Double precio, String imagen, String descripcion, int stock, boolean disponible,
            Categoria categoria) {
        setNombre(nombre);
        setPrecio(precio);
        setImagen(imagen);
        setDescripcion(descripcion);
        setStock(stock);
        setDisponible(disponible);
        setCategoria(categoria);
        addProducto(this);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void addProducto(Producto producto) {
        if (producto != null) {
            productos.add(producto);
        }
    }

    public static Set<Producto> getProductos() {
        return productos;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        if (precio == null || precio < 0) {
            throw new ValorInvalidoException("precio", "debe ser mayor o igual a cero");
        }
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        if (stock < 0) {
            throw new ValorInvalidoException("stock", "no puede ser negativo");
        }
        this.stock = stock;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
        if (categoria != null && !categoria.getProductos().contains(this)) {
            categoria.agregarProducto(this);
        }
    }

    @Override
    public String toString() {
        return String.format("Producto[id=%d, nombre='%s', $%.2f, stock=%d, categoria='%s', disponible=%s]", getId(),
                nombre, precio,
                stock, categoria != null ? categoria.getNombre() : "N/A", disponible ? "Sí" : "No");
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        Producto producto = (Producto) o;
        return Objects.equals(getId(), producto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
