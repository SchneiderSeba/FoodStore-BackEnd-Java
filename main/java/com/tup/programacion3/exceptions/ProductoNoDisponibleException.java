package com.tup.programacion3.exceptions;

import com.tup.programacion3.entities.Producto;

public class ProductoNoDisponibleException extends NegocioException {
    public ProductoNoDisponibleException(Producto producto) {
        super("El producto '" + producto.getNombre() + "' no esta disponible.");
    }
}
