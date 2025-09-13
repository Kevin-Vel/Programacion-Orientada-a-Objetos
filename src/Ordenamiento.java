import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Ordenamiento {
    private List<Cliente> listaCliente = new ArrayList<>();

    // Método existente - ordenamiento en memoria por nombre
    public void Implementacion(Cliente nuevoCliente) {
        int i = 0;
        while (i < listaCliente.size() &&
                listaCliente.get(i).getNom().compareToIgnoreCase(nuevoCliente.getNom()) < 0) {
            i++;
        }
        listaCliente.add(i, nuevoCliente);
    }

    public List<Cliente> getListaCliente() {
        return listaCliente;
    }

    // Devuelve la lista como texto formateado
    public String listaFormateada() {
        if (listaCliente.isEmpty()) {
            return "No hay clientes registrados.";
        }
        StringBuilder sb = new StringBuilder();
        for (Cliente c : listaCliente) {
            sb.append(c.toString()).append("\n");
        }
        return sb.toString();
    }

    // NUEVOS MÉTODOS PARA ORDENAMIENTO EXTERNO

    /**
     * Guarda todos los clientes en un archivo para ordenamiento externo
     */
    public void guardarEnArchivo(String nombreArchivo) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))) {
            for (Cliente cliente : listaCliente) {
                writer.println(clienteToCSV(cliente));
            }
        }
    }

    /**
     * Carga clientes desde un archivo (después de ordenamiento externo)
     */
    public void cargarDesdeArchivo(String nombreArchivo) throws IOException {
        listaCliente.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                Cliente cliente = csvToCliente(linea);
                if (cliente != null) {
                    listaCliente.add(cliente);
                }
            }
        }
    }

    /**
     * Convierte un cliente a formato CSV
     */
    private String clienteToCSV(Cliente cliente) {
        return cliente.getIdclie() + "," +
                cliente.getDni() + "," +
                cliente.getNom() + "," +
                cliente.getApell();
    }

    /**
     * Convierte una línea CSV a objeto Cliente
     */
    private Cliente csvToCliente(String csvLine) {
        try {
            String[] partes = csvLine.split(",");
            if (partes.length == 4) {
                int id = Integer.parseInt(partes[0]);
                int dni = Integer.parseInt(partes[1]);
                String nombre = partes[2];
                String apellido = partes[3];
                return new Cliente(id, dni, nombre, apellido);
            }
        } catch (NumberFormatException e) {
            System.err.println("Error al parsear línea: " + csvLine);
        }
        return null;
    }

    /**
     * Método para ordenamiento externo - delega a la clase especializada
     */
    public void ordenamientoExterno(String archivoEntrada, String archivoSalida, String criterio)
            throws IOException {
        Comparator<String> comparador;

        switch (criterio.toLowerCase()) {
            case "dni":
                comparador = OrdenamientoExterno.comparadorPorDNI();
                break;
            case "nombre":
                comparador = OrdenamientoExterno.comparadorPorNombre();
                break;
            case "apellido":
                comparador = OrdenamientoExterno.comparadorPorApellido();
                break;
            default:
                throw new IllegalArgumentException("Criterio de ordenamiento no válido: " + criterio);
        }

        OrdenamientoExterno.ordenarArchivoGrande(archivoEntrada, archivoSalida, comparador);
    }
}
