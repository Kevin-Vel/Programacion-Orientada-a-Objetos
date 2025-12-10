package CONTROLADOR;

import MODELO.Persona;
import MODELO.Cliente;

import java.io.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Ordenamiento<T extends Persona> {

    private final List<T> lista = new ArrayList<>();
    private static final int UMBRAL_ORDENAMIENTO_EXTERNO = 10000;
    private boolean usarOrdenamientoExterno = false; // 🔹 CONTROL

    // =====================================================
    // 🔹 CONSTRUCTOR Y CONFIGURACIÓN
    // =====================================================
    public Ordenamiento() {
        this.usarOrdenamientoExterno = false; // Por defecto: NO usar ordenamiento externo
    }

    public void activarOrdenamientoExterno(boolean activar) {
        this.usarOrdenamientoExterno = activar;
    }

    // =====================================================
    // 🔹 MÉTODOS BÁSICOS
    // =====================================================
    public void agregar(T persona) {
        lista.add(persona);
    }

    public List<T> getLista() {
        return new ArrayList<>(lista);
    }

    // =====================================================
    // 🔹 ORDENAMIENTO EN MEMORIA (simple)
    // =====================================================
    public void ordenarPorNombre() {
        lista.sort(Comparator.comparing(Persona::getNombre, String.CASE_INSENSITIVE_ORDER));
    }

    public void ordenarPorApellido() {
        lista.sort(Comparator.comparing(Persona::getApellido, String.CASE_INSENSITIVE_ORDER));
    }

    public void ordenarPor(Comparator<T> comparador) {
        lista.sort(comparador);
    }

    // =====================================================
    // 🔹 MÉTODO PRINCIPAL QUE DECIDE QUÉ USAR
    // =====================================================
    public String listaFormateadaOrdenada(String criterio) {
        if (lista.isEmpty()) return "No hay registros.";

        // 🔹 DECISIÓN: ¿Usar ordenamiento externo?
        boolean debeUsarExterno = usarOrdenamientoExterno &&
                lista.size() > UMBRAL_ORDENAMIENTO_EXTERNO;

        if (!debeUsarExterno) {
            // 🔹 ORDENAMIENTO SIMPLE EN MEMORIA
            return ordenarEnMemoriaYFormatear(criterio);
        } else {
            // 🔹 ORDENAMIENTO EXTERNO (solo si está activado y es necesario)
            try {
                return ordenarConExterno(criterio);
            } catch (IOException e) {
                return "Error en ordenamiento externo: " + e.getMessage();
            }
        }
    }

    // =====================================================
    // 🔹 ORDENAMIENTO SIMPLE EN MEMORIA
    // =====================================================
    private String ordenarEnMemoriaYFormatear(String criterio) {
        switch (criterio.toLowerCase()) {
            case "apellido":
                ordenarPorApellido();
                break;
            case "nombre":
            default:
                ordenarPorNombre();
                break;
        }

        StringBuilder sb = new StringBuilder();
        lista.forEach(p -> sb.append(p.mostrarInfo()).append("\n"));
        return sb.toString();
    }

    // =====================================================
    // 🔹 ORDENAMIENTO EXTERNO (solo para Clientes)
    // =====================================================
    private String ordenarConExterno(String criterio) throws IOException {
        // Verificar que sea lista de Clientes
        if (lista.isEmpty() || !(lista.get(0) instanceof Cliente)) {
            return "Ordenamiento externo solo disponible para Clientes";
        }

        File archivoTemp = File.createTempFile("clientes_", ".txt");
        archivoTemp.deleteOnExit();

        File archivoOrdenado = File.createTempFile("clientes_ordenados_", ".txt");
        archivoOrdenado.deleteOnExit();

        // Guardar clientes en archivo temporal
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivoTemp))) {
            for (T persona : lista) {
                if (persona instanceof Cliente) {
                    Cliente c = (Cliente) persona;
                    pw.println(c.getIdCliente() + "," + c.getDni() + "," +
                            c.getNombre() + "," + c.getApellido() + "," +
                            c.getPassword());
                }
            }
        }

        // Determinar comparador
        Comparator<String> comparador;
        switch (criterio.toLowerCase()) {
            case "dni":
                comparador = OrdenamientoExterno.comparadorPorDNI();
                break;
            case "apellido":
                comparador = OrdenamientoExterno.comparadorPorApellido();
                break;
            case "nombre":
            default:
                comparador = OrdenamientoExterno.comparadorPorNombre();
                break;
        }

        // Llamar a OrdenamientoExterno
        OrdenamientoExterno.ordenarArchivoGrande(
                archivoTemp.getAbsolutePath(),
                archivoOrdenado.getAbsolutePath(),
                comparador
        );

        // Leer y formatear resultado
        return leerArchivoOrdenado(archivoOrdenado);
    }

    private String leerArchivoOrdenado(File archivo) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 5) {
                    sb.append("ID: ").append(partes[0])
                            .append(", DNI: ").append(partes[1])
                            .append(", Nombre: ").append(partes[2])
                            .append(" ").append(partes[3])
                            .append("\n");
                }
            }
        }
        return sb.toString();
    }

    // =====================================================
    // 🔹 LAMBDAS Y STREAMS (Unidad 3)
    // =====================================================
    public List<T> filtrar(Predicate<T> condicion) {
        return lista.stream()
                .filter(condicion)
                .collect(Collectors.toList());
    }

    public long contar(Predicate<T> condicion) {
        return lista.stream()
                .filter(condicion)
                .count();
    }

    public List<String> getNombresUnicos() {
        return lista.stream()
                .map(Persona::getNombre)
                .distinct()
                .collect(Collectors.toList());
    }

    // =====================================================
    // 🔹 MÉTODOS DE COMPATIBILIDAD
    // =====================================================
    @SuppressWarnings("unchecked")
    public List<Cliente> getListaCliente() {
        List<Cliente> clientes = new ArrayList<>();
        for (T persona : lista) {
            if (persona instanceof Cliente) {
                clientes.add((Cliente) persona);
            }
        }
        return clientes;
    }

    public void Implementacion(Cliente c) {
        agregar((T) c);
    }
}