import java.io.*;
import java.util.*;

public class Ordenamiento {
    private List<Cliente> listaCliente = new ArrayList<>();
    private static final int UMBRAL_ORDENAMIENTO_EXTERNO = 10000; // Umbral para usar ordenamiento externo
    private Comparator<String> criterioOrdenamiento = OrdenamientoExterno.comparadorPorNombre(); // Criterio por defecto

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

        // Decidir si usar ordenamiento en memoria o externo
        if (listaCliente.size() <= UMBRAL_ORDENAMIENTO_EXTERNO) {
            // Ordenamiento en memoria
            return formatearListaMemoria();
        } else {
            // Ordenamiento externo para grandes volúmenes
            try {
                return formatearListaOrdenamientoExterno();
            } catch (IOException e) {
                return "Error al ordenar: " + e.getMessage();
            }
        }
    }

    // Método para ordenamiento en memoria
    private String formatearListaMemoria() {
        StringBuilder sb = new StringBuilder();
        for (Cliente c : listaCliente) {
            sb.append(c.toString()).append("\n");
        }
        return sb.toString();
    }

    // Método para ordenamiento externo
    private String formatearListaOrdenamientoExterno() throws IOException {
        // Crear archivos temporales
        File archivoTemporalEntrada = File.createTempFile("clientes_", ".txt");
        File archivoTemporalSalida = File.createTempFile("clientes_ordenados_", ".txt");

        // Configurar eliminación automática al salir
        archivoTemporalEntrada.deleteOnExit();
        archivoTemporalSalida.deleteOnExit();

        // Guardar datos en archivo temporal
        guardarEnArchivo(archivoTemporalEntrada.getAbsolutePath());

        // Ejecutar ordenamiento externo
        OrdenamientoExterno.ordenarArchivoGrande(
                archivoTemporalEntrada.getAbsolutePath(),
                archivoTemporalSalida.getAbsolutePath(),
                criterioOrdenamiento
        );

        // Leer y formatear resultado
        String resultado = leerYFormatearArchivo(archivoTemporalSalida);

        // Eliminar archivos temporales
        archivoTemporalEntrada.delete();
        archivoTemporalSalida.delete();

        return resultado;
    }

    // Establecer criterio de ordenamiento
    public void setCriterioOrdenamiento(String criterio) {
        switch (criterio.toLowerCase()) {
            case "dni":
                this.criterioOrdenamiento = OrdenamientoExterno.comparadorPorDNI();
                break;
            case "apellido":
                this.criterioOrdenamiento = OrdenamientoExterno.comparadorPorApellido();
                break;
            case "nombre":
            default:
                this.criterioOrdenamiento = OrdenamientoExterno.comparadorPorNombre();
        }
    }

    // Métodos auxiliares existentes (sin cambios)
    public void guardarEnArchivo(String nombreArchivo) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))) {
            for (Cliente cliente : listaCliente) {
                writer.println(clienteToCSV(cliente));
            }
        }
    }

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

    private String clienteToCSV(Cliente cliente) {
        return cliente.getIdclie() + "," +
                cliente.getDni() + "," +
                cliente.getNom() + "," +
                cliente.getApell();
    }

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

    private String leerYFormatearArchivo(File archivo) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                Cliente cliente = csvToCliente(linea);
                if (cliente != null) {
                    sb.append(cliente.toString()).append("\n");
                }
            }
        }
        return sb.toString();
    }
}
