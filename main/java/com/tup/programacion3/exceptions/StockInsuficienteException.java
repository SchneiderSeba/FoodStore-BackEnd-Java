package com.tup.programacion3.exceptions;

import com.tup.programacion3.entities.Producto;

public class StockInsuficienteException extends NegocioException {
    public StockInsuficienteException(Producto producto, int cantidadSolicitada) {
        super("Stock insuficiente para '" + producto.getNombre() + "'. Solicitado: "
                + cantidadSolicitada + ", disponible: " + producto.getStock() + ".");
    }
}
