package com.tup.programacion3.exceptions;

public class ValorInvalidoException extends NegocioException {
    public ValorInvalidoException(String campo, String detalle) {
        super("Valor invalido para " + campo + ": " + detalle);
    }
}
