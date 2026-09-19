package com.transporte;

public class BoletoNoDisponibleException extends Exception {
    public BoletoNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}