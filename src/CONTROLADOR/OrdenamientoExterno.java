package CONTROLADOR;

import java.io.*;
import java.util.*;

public class OrdenamientoExterno {

    // Método principal para ordenar archivos grandes
    public static void ordenarArchivoGrande(String archivoEntrada, String archivoSalida, Comparator<String> comparador)
            throws IOException {
        // Calcular el máximo de líneas que caben en memoria (podría ser configurable)
        int maxLineasMemoria = calcularMaxLineasMemoria();
        List<String> chunk = new ArrayList<>(maxLineasMemoria);
        BufferedReader reader = new BufferedReader(new FileReader(archivoEntrada));

        List<File> archivosTemporales = new ArrayList<>();
        String linea;
        int contadorArchivos = 0;

        // Fase 1: Dividir y ordenar chunks
        while ((linea = reader.readLine()) != null) {
            chunk.add(linea);
            if (chunk.size() == maxLineasMemoria) {
                Collections.sort(chunk, comparador);
                File archivoTemporal = guardarChunkOrdenado(chunk, contadorArchivos++);
                archivosTemporales.add(archivoTemporal);
                chunk.clear();
            }
        }

        // Último chunk
        if (!chunk.isEmpty()) {
            Collections.sort(chunk, comparador);
            File archivoTemporal = guardarChunkOrdenado(chunk, contadorArchivos);
            archivosTemporales.add(archivoTemporal);
        }

        reader.close();

        // Fase 2: Mezclar archivos temporales
        mezclarArchivos(archivosTemporales, archivoSalida, comparador);

        // Limpieza: eliminar archivos temporales
        for (File archivo : archivosTemporales) {
            archivo.delete();
        }
    }

    // Método para calcular el máximo de líneas que caben en memoria
    private static int calcularMaxLineasMemoria() {
        // Puedes ajustar este cálculo basado en la memoria disponible
        long maxMemory = Runtime.getRuntime().maxMemory();
        // Asumimos que cada línea tiene en promedio 100 bytes y queremos usar la mitad de la memoria
        int bytesPorLinea = 100;
        int lineas = (int) (maxMemory / (2 * bytesPorLinea));
        return Math.max(10000, lineas); // Mínimo 10000 líneas
    }

    // Guardar chunk ordenado en archivo temporal
    private static File guardarChunkOrdenado(List<String> chunk, int indice) throws IOException {
        File archivoTemporal = File.createTempFile("chunk_" + indice + "_", ".txt");
        archivoTemporal.deleteOnExit(); // Asegurar eliminación al salir
        try (PrintWriter writer = new PrintWriter(new FileWriter(archivoTemporal))) {
            for (String linea : chunk) {
                writer.println(linea);
            }
        }
        return archivoTemporal;
    }

    // Mezclar archivos ordenados
    private static void mezclarArchivos(List<File> archivos, String archivoSalida, Comparator<String> comparador)
            throws IOException {
        PriorityQueue<LineaBuffer> heap = new PriorityQueue<>(
                (a, b) -> comparador.compare(a.linea, b.linea)
        );

        List<BufferedReader> readers = new ArrayList<>();

        // Inicializar heap con la primera línea de cada archivo
        for (File archivo : archivos) {
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            readers.add(reader);
            String linea = reader.readLine();
            if (linea != null) {
                heap.add(new LineaBuffer(linea, reader));
            }
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(archivoSalida))) {
            while (!heap.isEmpty()) {
                LineaBuffer buffer = heap.poll();
                writer.println(buffer.linea);

                String nextLine = buffer.reader.readLine();
                if (nextLine != null) {
                    heap.add(new LineaBuffer(nextLine, buffer.reader));
                }
            }
        }

        // Cerrar todos los readers
        for (BufferedReader reader : readers) {
            reader.close();
        }
    }

    // Clase auxiliar para el buffer de líneas
    private static class LineaBuffer {
        String linea;
        BufferedReader reader;

        LineaBuffer(String linea, BufferedReader reader) {
            this.linea = linea;
            this.reader = reader;
        }
    }

    // Comparadores específicos para clientes
    public static Comparator<String> comparadorPorDNI() {
        return (linea1, linea2) -> {
            String[] partes1 = linea1.split(",");
            String[] partes2 = linea2.split(",");

            String dni1Str = partes1[1];
            String dni2Str = partes2[1];

            Integer dni1Num = null;
            Integer dni2Num = null;

            // 🔹 Intentamos convertir a número
            try {
                dni1Num = Integer.parseInt(dni1Str);
            } catch (NumberFormatException ignored) {}

            try {
                dni2Num = Integer.parseInt(dni2Str);
            } catch (NumberFormatException ignored) {}

            // Si son solo numeros
            if (dni1Num != null && dni2Num != null) {
                return Integer.compare(dni1Num, dni2Num);
            }

            // Si uno es numero
            if (dni1Num != null && dni2Num == null) return -1;
            if (dni1Num == null && dni2Num != null) return 1;

            // Ambos NO son números → comparar como cadenas
            return dni1Str.compareToIgnoreCase(dni2Str);
        };
    }


    public static Comparator<String> comparadorPorNombre() {
        return (linea1, linea2) -> {
            String[] partes1 = linea1.split(",");
            String[] partes2 = linea2.split(",");
            return partes1[2].compareTo(partes2[2]); // Nombre está en posición 2
        };
    }

    public static Comparator<String> comparadorPorApellido() {
        return (linea1, linea2) -> {
            String[] partes1 = linea1.split(",");
            String[] partes2 = linea2.split(",");
            return partes1[3].compareTo(partes2[3]); // Apellido está en posición 3
        };
    }
}