import java.io.*;
import java.util.*;

public class OrdenamientoExterno {

    // Método principal para ordenar archivos grandes
    public static void ordenarArchivoGrande(String archivoEntrada, String archivoSalida, Comparator<String> comparador)
            throws IOException {
        int maxLineasMemoria = 100000; // Líneas que caben en memoria
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

    // Guardar chunk ordenado en archivo temporal
    private static File guardarChunkOrdenado(List<String> chunk, int indice) throws IOException {
        File archivoTemporal = File.createTempFile("chunk_" + indice + "_", ".txt");
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
            int dni1 = Integer.parseInt(partes1[1]); // DNI está en la posición 1
            int dni2 = Integer.parseInt(partes2[1]);
            return Integer.compare(dni1, dni2);
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
