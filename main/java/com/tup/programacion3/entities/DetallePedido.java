package com.tup.programacion3.entities;

import com.tup.programacion3.exceptions.EntidadNoEncontradaException;
import com.tup.programacion3.exceptions.ValorInvalidoException;

import java.util.Objects;

public class DetallePedido extends Base {
    private int cantidad;
    private Double subtotal;
    private Producto producto;

    public DetallePedido() {
    }

    private static Long nextId = 1L;

    public DetallePedido(int cantidad, Producto producto) {
        setId(nextId++);
        setProducto(producto);
        setCantidad(cantidad);
        this.subtotal = calcularSubtotal();
    }

    private Double calcularSubtotal() {
        if (producto != null) {
            return cantidad * producto.getPrecio();
        } else {
            return 0.0;
        }
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        if (cantidad <= 0) {
            throw new ValorInvalidoException("cantidad", "debe ser mayor a cero");
        }
        this.cantidad = cantidad;
        this.subtotal = calcularSubtotal();
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        if (producto == null) {
            throw new EntidadNoEncontradaException("Producto");
        }
        this.producto = producto;
        this.subtotal = calcularSubtotal();
    }

    @Override
    public String toString() {
        return String.format("- DetallePedido #%d: %s x %d => Subtotal: $%.2f", getId(),
                producto != null ? producto.getNombre() : "", cantidad, subtotal);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        DetallePedido that = (DetallePedido) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
