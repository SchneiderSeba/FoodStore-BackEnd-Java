package com.tup.programacion3.entities;

import com.tup.programacion3.exceptions.ValorInvalidoException;

import java.util.Objects;

public class Producto extends Base {
    private String nombre;
    private Double precio;
    private String descripcion;
    private int stock;
    private String imagen;
    private boolean disponible;
    private Categoria categoria;

    public Producto() {
    }

    public Producto(String nombre, Double precio, String descripcion, int stock, String imagen, boolean disponible,
                    Categoria categoria) {
        setNombre(nombre);
        setPrecio(precio);
        setDescripcion(descripcion);
        setStock(stock);
        setImagen(imagen);
        setDisponible(disponible);
        setCategoria(categoria);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
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
        return String.format("Producto[id=%d, nombre='%s', $%.2f, stock=%d, disponible=%s]", getId(), nombre, precio,
                stock, disponible ? "Sí" : "No");
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return getStock() == producto.getStock() && isDisponible() == producto.isDisponible() && Objects.equals(getNombre(), producto.getNombre()) && Objects.equals(getPrecio(), producto.getPrecio()) && Objects.equals(getDescripcion(), producto.getDescripcion()) && Objects.equals(getImagen(), producto.getImagen()) && Objects.equals(getCategoria(), producto.getCategoria());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNombre(), getPrecio(), getDescripcion(), getStock(), getImagen(), isDisponible(), getCategoria());
    }
}
