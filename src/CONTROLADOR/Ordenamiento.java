package CONTROLADOR;

import MODELO.Cliente;
import MODELO.Persona;

import java.io.*;
import java.util.*;

public class Ordenamiento<T extends Persona> {
    private Comparator<T> comparador;
    private List<Cliente> listaCliente = new ArrayList<>();
    private static final int UMBRAL_ORDENAMIENTO_EXTERNO = 10000;

    // 🔹 Inserta un nuevo cliente manteniendo orden alfabético por nombre
    public void Implementacion(Cliente nuevoCliente) {
        int i = 0;
        while (i < listaCliente.size() &&
                listaCliente.get(i).getNombre().compareToIgnoreCase(nuevoCliente.getNombre()) < 0) {
            i++;
        }
        listaCliente.add(i, nuevoCliente);
    }

    public List<Cliente> getListaCliente() {
        return listaCliente;
    }

    // 🔹 Método con expresión lambda mejorada para permitir DNI String
    public void ordenarPor(String criterio) {
        Comparator<Cliente> comparador;

        switch (criterio.toLowerCase()) {
            case "dni":
                // ✅ Intenta ordenar numéricamente si se puede, si no, alfabéticamente
                comparador = Comparator.comparingInt(c -> {
                    try {
                        return Integer.parseInt(c.getDni());
                    } catch (NumberFormatException e) {
                        return Integer.MAX_VALUE; // Si no es número, lo manda al final
                    }
                });
                break;

            case "apellido":
                comparador = Comparator.comparing(Cliente::getApellido, String.CASE_INSENSITIVE_ORDER);
                break;

            case "nombre":
            default:
                comparador = Comparator.comparing(Cliente::getNombre, String.CASE_INSENSITIVE_ORDER);
                break;
        }

        listaCliente.sort(comparador);
    }

    // 🔹 Devuelve una cadena con los clientes ordenados según el criterio
    public String listaFormateada(String criterio) {
        if (listaCliente.isEmpty()) {
            return "No hay clientes registrados.";
        }

        if (listaCliente.size() <= UMBRAL_ORDENAMIENTO_EXTERNO) {
            ordenarPor(criterio);
            return formatearListaMemoria();
        } else {
            try {
                return formatearListaOrdenamientoExterno(criterio);
            } catch (IOException e) {
                return "Error al ordenar: " + e.getMessage();
            }
        }
    }

    // 🔹 Mostrar lista directamente desde memoria
    private String formatearListaMemoria() {
        StringBuilder sb = new StringBuilder();
        for (Cliente c : listaCliente) {
            sb.append(c.toString()).append("\n");
        }
        return sb.toString();
    }

    // 🔹 Si la lista es grande, usar la otra clase OrdenamientoExterno
    private String formatearListaOrdenamientoExterno(String criterio) throws IOException {
        File archivoEntrada = File.createTempFile("clientes_", ".txt");
        File archivoSalida = File.createTempFile("clientes_ordenados_", ".txt");
        archivoEntrada.deleteOnExit();
        archivoSalida.deleteOnExit();

        guardarEnArchivo(archivoEntrada.getAbsolutePath());

        Comparator<String> comparador;
        switch (criterio.toLowerCase()) {
            case "dni":
                comparador = OrdenamientoExterno.comparadorPorDNI();
                break;
            case "apellido":
                comparador = OrdenamientoExterno.comparadorPorApellido();
                break;
            default:
                comparador = OrdenamientoExterno.comparadorPorNombre();
        }

        OrdenamientoExterno.ordenarArchivoGrande(
                archivoEntrada.getAbsolutePath(),
                archivoSalida.getAbsolutePath(),
                comparador
        );

        String resultado = leerYFormatearArchivo(archivoSalida);
        archivoEntrada.delete();
        archivoSalida.delete();

        return resultado;
    }

    // 🔹 Guardar en archivo CSV
    public void guardarEnArchivo(String nombreArchivo) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))) {
            for (Cliente cliente : listaCliente) {
                writer.println(clienteToCSV(cliente));
            }
        }
    }

    // 🔹 Cargar desde archivo CSV
    public void cargarDesdeArchivo(String nombreArchivo) throws IOException {
        listaCliente.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                Cliente cliente = csvToCliente(linea);
                if (cliente != null) listaCliente.add(cliente);
            }
        }
    }

    // 🔹 Conversión Cliente ↔ CSV
    private String clienteToCSV(Cliente cliente) {
        return cliente.getIdCliente() + "," +
                cliente.getDni() + "," +
                cliente.getNombre() + "," +
                cliente.getApellido() + "," +
                cliente.getPassword();
    }

    private Cliente csvToCliente(String csvLine) {
        try {
            String[] partes = csvLine.split(",");
            if (partes.length == 5) {
                int id = Integer.parseInt(partes[0]);
                String dni = partes[1];
                String nombre = partes[2];
                String apellido = partes[3];
                String contraseña = partes[4];
                Cliente cliente = new Cliente(id, dni, nombre, apellido, contraseña);
                cliente.setPassword(contraseña);
                return cliente;
            }
        } catch (Exception e) {
            System.err.println("Error al parsear línea: " + csvLine);
        }
        return null;
    }

    private String leerYFormatearArchivo(File archivo) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                Cliente cliente = csvToCliente(linea);
                if (cliente != null) sb.append(cliente.toString()).append("\n");
            }
        }
        return sb.toString();
    }
}

