package CONTROLADOR;

import MODELO.Cliente;

import java.io.*;
import java.util.*;

public class Ordenamiento {

    private final List<Cliente> listaCliente = new ArrayList<>();
    private String criterioPorDefecto = "nombre";
    private static final int UMBRAL_ORDENAMIENTO_EXTERNO = 10000;

    // =====================================================
    // 🔹 Inserción ordenada por nombre
    // =====================================================
    public void agregarOrdenado(Cliente nuevoCliente) {
        int i = Collections.binarySearch(
                listaCliente,
                nuevoCliente,
                Comparator.comparing(Cliente::getNombre, String.CASE_INSENSITIVE_ORDER)
        );

        if (i < 0) i = -(i + 1);
        listaCliente.add(i, nuevoCliente);
    }

    public List<Cliente> getListaCliente() {
        return listaCliente;
    }

    // =====================================================
    // 🔹 Comparadores corregidos
    // =====================================================
    private Comparator<Cliente> getComparador(String criterio) {

        switch (criterio.toLowerCase()) {

            case "dni":
                return Comparator.comparingInt(Cliente::getDni);

            case "apellido":
                return Comparator.comparing(Cliente::getApellido, String.CASE_INSENSITIVE_ORDER);

            case "nombre":
            default:
                return Comparator.comparing(Cliente::getNombre, String.CASE_INSENSITIVE_ORDER);
        }
    }

    // =====================================================
    // 🔹 Ordenar lista interna
    // =====================================================
    public void ordenarPor(String criterio) {
        listaCliente.sort(getComparador(criterio));
    }


    // =====================================================
    // 🔹 Lista formateada
    // =====================================================
    public String listaFormateada(String criterio) {
        if (listaCliente.isEmpty()) return "No hay clientes registrados.";

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

    // =====================================================
    // Formateo en memoria
    // =====================================================
    private String formatearListaMemoria() {
        StringBuilder sb = new StringBuilder();
        for (Cliente c : listaCliente) sb.append(c).append("\n");
        return sb.toString();
    }

    // =====================================================
    // Ordenamiento externo
    // =====================================================
    private String formatearListaOrdenamientoExterno(String criterio) throws IOException {

        File in = File.createTempFile("clientes_", ".txt");
        File out = File.createTempFile("clientes_ordenados_", ".txt");

        in.deleteOnExit();
        out.deleteOnExit();

        guardarEnArchivo(in.getAbsolutePath());

        // ⚠ ESTE MÉTODO LO TIENES QUE TENER EN TU CLASE OrdenamientoExterno
        Comparator<String> compString;
        switch (criterio.toLowerCase()) {
            case "dni": compString = OrdenamientoExterno.comparadorPorDNI(); break;
            case "apellido": compString = OrdenamientoExterno.comparadorPorApellido(); break;
            default: compString = OrdenamientoExterno.comparadorPorNombre(); break;
        }

        OrdenamientoExterno.ordenarArchivoGrande(
                in.getAbsolutePath(),
                out.getAbsolutePath(),
                compString
        );

        return leerYFormatearArchivo(out);
    }

    // =====================================================
    // Guardar CSV
    // =====================================================
    public void guardarEnArchivo(String ruta) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ruta))) {
            for (Cliente c : listaCliente) {
                pw.println(clienteToCSV(c));
            }
        }
    }

    public void Implementacion(Cliente c) {
        agregarOrdenado(c);
    }

    // =====================================================
    // Cargar CSV
    // =====================================================
    public void cargarDesdeArchivo(String ruta) throws IOException {
        listaCliente.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String line;
            while ((line = br.readLine()) != null) {
                Cliente c = csvToCliente(line);
                if (c != null) listaCliente.add(c);
            }
        }
    }

    // =====================================================
    // Conversión a CSV corregida
    // =====================================================
    private String clienteToCSV(Cliente c) {
        return c.getIdCliente() + "," +
                c.getDni() + "," +
                c.getNombre() + "," +
                c.getApellido();
    }

    private Cliente csvToCliente(String line) {
        try {
            String[] p = line.split(",");
            if (p.length != 5) return null;

            return new Cliente(
                    Integer.parseInt(p[0]), // idCliente
                    Integer.parseInt(p[1]), // dni
                    p[2],                   // nombre
                    p[3],                   // apellido
                    p[4]                    // password
            );

        } catch (Exception e) {
            System.err.println("Error al parsear CSV: " + line);
            return null;
        }
    }


    // =====================================================
    // 🔹 Leer archivo ordenado
    // =====================================================
    private String leerYFormatearArchivo(File f) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                Cliente c = csvToCliente(line);
                if (c != null) sb.append(c).append("\n");
            }
        }
        return sb.toString();
    }
}
