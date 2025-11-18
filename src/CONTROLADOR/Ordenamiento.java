package CONTROLADOR;

import MODELO.Cliente;
import MODELO.Persona;

import java.io.*;
import java.util.*;

public class Ordenamiento<T extends Persona> {

    private final List<Cliente> listaCliente = new ArrayList<>();
    private static final int UMBRAL_ORDENAMIENTO_EXTERNO = 10000;

    // =====================================================
    // 🔹 Inserta de forma ordenada por nombre (optimizado)
    // =====================================================
    public void Implementacion(Cliente nuevoCliente) {
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
    // 🔹 Comparadores en un solo método (óptimo)
    // =====================================================
    private Comparator<Cliente> getComparador(String criterio) {

        switch (criterio.toLowerCase()) {

            case "dni":
                // Ordena DNI numérico si se puede, si no alfabético
                return Comparator.comparing(c -> {
                    String dni = c.getDni();
                    try {
                        return Integer.parseInt(dni);
                    } catch (NumberFormatException e) {
                        return Integer.MAX_VALUE; // manda al final
                    }
                });

            case "apellido":
                return Comparator.comparing(Cliente::getApellido, String.CASE_INSENSITIVE_ORDER);

            case "nombre":
            default:
                return Comparator.comparing(Cliente::getNombre, String.CASE_INSENSITIVE_ORDER);
        }
    }

    // =====================================================
    // 🔹 Ordenar lista interna (rápido)
    // =====================================================
    public void ordenarPor(String criterio) {
        listaCliente.sort(getComparador(criterio));
    }

    // =====================================================
    // 🔹 Devolver lista ordenada (memoria o archivo)
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
    // 🔹 Formateo simple desde memoria
    // =====================================================
    private String formatearListaMemoria() {
        StringBuilder sb = new StringBuilder();
        for (Cliente c : listaCliente) {
            sb.append(c).append("\n");
        }
        return sb.toString();
    }

    // =====================================================
    // 🔹 Ordenamiento externo (optimizado)
    // =====================================================
    private String formatearListaOrdenamientoExterno(String criterio) throws IOException {

        File in = File.createTempFile("clientes_", ".txt");
        File out = File.createTempFile("clientes_ordenados_", ".txt");

        in.deleteOnExit();
        out.deleteOnExit();

        guardarEnArchivo(in.getAbsolutePath());

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

        String resultado = leerYFormatearArchivo(out);

        return resultado;
    }

    // =====================================================
    // 🔹 Guardar CSV
    // =====================================================
    public void guardarEnArchivo(String ruta) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ruta))) {
            for (Cliente c : listaCliente) {
                pw.println(clienteToCSV(c));
            }
        }
    }

    // =====================================================
    // 🔹 Cargar CSV
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
    // 🔹 Conversión Cliente ↔ CSV
    // =====================================================
    private String clienteToCSV(Cliente c) {
        return c.getIdCliente() + "," + c.getDni() + "," +
                c.getNombre() + "," + c.getApellido() + "," +
                c.getPassword();
    }

    private Cliente csvToCliente(String line) {
        try {
            String[] p = line.split(",");
            if (p.length != 5) return null;

            return new Cliente(
                    Integer.parseInt(p[0]),
                    p[1], p[2], p[3], p[4]
            );

        } catch (Exception e) {
            System.err.println("Error al parsear CSV: " + line);
            return null;
        }
    }

    // =====================================================
    // 🔹 Leer archivo ya ordenado
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
