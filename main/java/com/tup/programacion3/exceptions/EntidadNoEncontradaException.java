package com.tup.programacion3.exceptions;

public class EntidadNoEncontradaException extends NegocioException {
    public EntidadNoEncontradaException(String entidad) {
        super(entidad + " no encontrado/a.");
    }
}