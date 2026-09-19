package com.transporte;

public class Pasajero {
    private final String nombre;
    private final String dni;
    private final String tipoAsiento;

    public Pasajero(String nombre, String dni, String tipoAsiento) {
        this.nombre = nombre;
        this.dni = dni;
        this.tipoAsiento = tipoAsiento;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDni() {
        return dni;
    }

    public String getTipoAsiento() {
        return tipoAsiento;
    }

    @Override
    public String toString() {
        return "Pasajero: " + nombre + " [DNI: " + dni + "] - Asiento: " + tipoAsiento;
    }
}