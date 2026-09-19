package com.transporte;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner; // Importamos Scanner para leer del teclado

public class SistemaTransporte {

    // Colecciones: ArrayList para la lista general y HashMap para búsqueda rápida por DNI
    private final ArrayList<Pasajero> listaPasajeros = new ArrayList<>();
    private final HashMap<String, String> rutasDisponibles = new HashMap<>();
    private final HashMap<String, Pasajero> registroPorDni = new HashMap<>();

    public SistemaTransporte() {
        // Inicializamos rutas de la empresa de transporte
        rutasDisponibles.put("R01", "Lima - Ica");
        rutasDisponibles.put("R02", "Lima - Arequipa");
        rutasDisponibles.put("R03", "Lima - Trujillo");
    }

    // Método para registrar pasajeros
    public void registrarPasajero(Pasajero pasajero) {
        listaPasajeros.add(pasajero);
        registroPorDni.put(pasajero.getDni(), pasajero); 
    }

    // Búsqueda automática por DNI
    public void buscarPasajeroPorDni(String dniBuscado) {
        System.out.println("\n[BÚSQUEDA] Consultando DNI: " + dniBuscado);
        if (registroPorDni.containsKey(dniBuscado)) {
            Pasajero pEncontrado = registroPorDni.get(dniBuscado);
            System.out.println("¡Pasajero Encontrado!");
            System.out.println("   -> Nombres completos: " + pEncontrado.getNombre());
            System.out.println("   -> Tipo de Asiento: " + pEncontrado.getTipoAsiento());
        } else {
            System.out.println("❌ No se encontró ningún pasajero registrado con el DNI: " + dniBuscado);
        }
    }

    // Sobrecarga de métodos para inscribir
    public void inscribir(Pasajero pasajero, String codigoRuta) {
        String nombreRuta = rutasDisponibles.get(codigoRuta);
        registrarPasajero(pasajero);
        System.out.println("-> Registrado con éxito: " + pasajero.getNombre() + " en la ruta: " + nombreRuta);
    }

    public void inscribir(Pasajero pasajero, String codigoRuta, String observacion) {
        String nombreRuta = rutasDisponibles.get(codigoRuta);
        registrarPasajero(pasajero);
        System.out.println("-> Registrado VIP con éxito: " + pasajero.getNombre() + " en la ruta: " + nombreRuta + " [" + observacion + "]");
    }

    // Precios
    public double calcularPrecio(double tarifaBase) {
        return tarifaBase;
    }

    public double calcularPrecio(double tarifaBase, boolean esEstudiante) {
        if (esEstudiante) {
            return tarifaBase * 0.5;
        }
        return tarifaBase;
    }

    // Manejo de errores con try/catch/finally
    public void verificarYVenderBoleto(String codigoRuta, int cantidadAsientos) throws BoletoNoDisponibleException {
        try {
            System.out.println("\n[Proceso] Verificando ruta: " + codigoRuta);
            if (!rutasDisponibles.containsKey(codigoRuta)) {
                throw new BoletoNoDisponibleException("Error: La ruta '" + codigoRuta + "' no existe en el sistema.");
            }
            if (cantidadAsientos > 15) {
                throw new BoletoNoDisponibleException("Error: No hay suficientes asientos disponibles (máximo 15).");
            }
            System.out.println("¡Venta de boletos exitosa! Asientos reservados: " + cantidadAsientos);
        } catch (BoletoNoDisponibleException e) {
            System.out.println("[EXCEPCIÓN CAPTURADA]: " + e.getMessage());
        } finally {
            System.out.println("[FIN DE TRANSACCIÓN]: Operación finalizada de forma segura.");
        }
    }

    public void mostrarPasajeros() {
        System.out.println("\n--- LISTA GENERAL DE PASAJEROS (ArrayList) ---");
        for (Pasajero p : listaPasajeros) {
            System.out.println("- " + p.toString());
        }
    }

    // --- MENÚ INTERACTIVO CON EL TECLADO ---
    public static void main(String[] args) {
        SistemaTransporte sistema = new SistemaTransporte();
        try (Scanner scanner = new Scanner(System.in)) {
            int opcion = 0;
            
            // Precargamos un pasajero por defecto para que la lista no empiece vacía
            Pasajero pasajeroBase = new Pasajero("Juan Pérez", "71234567", "Ventana");
            sistema.registrarPasajero(pasajeroBase);
            
            do {
                System.out.println("\n========================================");
                System.out.println("   SISTEMA DE TRANSPORTE - MENÚ");
                System.out.println("========================================");
                System.out.println("1. Registrar nuevo pasajero por teclado");
                System.out.println("2. Buscar pasajero por DNI");
                System.out.println("3. Ver lista completa de pasajeros");
                System.out.println("4. Probar venta de boletos y excepciones");
                System.out.println("5. Salir");
                System.out.print("Elija una opción: ");
                
                if (scanner.hasNextInt()) {
                    opcion = scanner.nextInt();
                    scanner.nextLine(); // Limpiar el buffer de entrada
                    
                    switch (opcion) {
                        case 1 -> {
                            System.out.println("\n--- REGISTRO DE NUEVO PASAJERO ---");
                            System.out.print("Ingrese nombres y apellidos: ");
                            String nombre = scanner.nextLine();
                            
                            System.out.print("Ingrese número de DNI: ");
                            String dni = scanner.nextLine();
                            
                            System.out.print("Ingrese tipo de asiento (Ventana/Pasillo/VIP): ");
                            String asiento = scanner.nextLine();
                            
                            System.out.print("Ingrese código de ruta (R01, R02 o R03): ");
                            String ruta = scanner.nextLine();
                            
                            // Creamos y registramos el objeto
                            Pasajero nuevo = new Pasajero(nombre, dni, asiento);
                            sistema.inscribir(nuevo, ruta);
                        }
                            
                        case 2 -> {
                            System.out.println("\n--- CONSULTA POR DNI ---");
                            System.out.print("Ingrese el DNI a buscar: ");
                            String dniBuscado = scanner.nextLine();
                            sistema.buscarPasajeroPorDni(dniBuscado);
                        }
                            
                        case 3 -> sistema.mostrarPasajeros();
                            
                        case 4 -> {
                            System.out.println("\n--- PROBANDO EXCEPCIONES Y RUTAS ---");
                            try {
                                sistema.verificarYVenderBoleto("R01", 3); // Válido
                                sistema.verificarYVenderBoleto("R99", 2); // Generará excepción a propósito
                            } catch (BoletoNoDisponibleException e) {
                            }
                        }
                            
                        case 5 -> System.out.println("Saliendo del sistema. ¡Hasta luego!");
                            
                        default -> System.out.println("Opción inválida. Intente de nuevo.");
                    }
                } else {
                    System.out.println("Por favor, ingrese un número válido.");
                    scanner.next(); // Limpiar entrada incorrecta
                }
                
            } while (opcion != 5);
        }
    }
}