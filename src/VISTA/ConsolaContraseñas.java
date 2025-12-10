package VISTA;

import javax.swing.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ConsolaContraseñas {
    private static final String ARCHIVO = "passwords.txt";

    public static void guardarContraseña(int dni, String contraseña) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO, true))) {
            pw.println(dni + ":" + contraseña);
            System.out.println("DEBUG: Contraseña guardada - DNI: " + dni + ", Contraseña: " + contraseña);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar contraseña: " + e.getMessage());
            System.out.println("ERROR: " + e.getMessage());
        }

    }

    public static Map<Integer, String> cargarPasswords() {
        Map<Integer, String> mapaPasswords = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            System.out.println("DEBUG: Cargando contraseñas desde " + new File(ARCHIVO).getAbsolutePath());

            while ((linea = br.readLine()) != null) {
                System.out.println("DEBUG: Leyendo línea: " + linea);
                String[] partes = linea.split(":");
                if (partes.length == 2) {
                    int dni = Integer.parseInt(partes[0].trim());
                    String password = partes[1].trim();
                    mapaPasswords.put(dni, password);
                    System.out.println("DEBUG: Añadido al mapa - DNI: " + dni + ", Password: " + password);
                } else {
                    System.out.println("DEBUG: Línea ignorada (formato incorrecto): " + linea);
                }
            }
        } catch (IOException e) {
            System.out.println("DEBUG: El archivo no existe aún o no se puede leer: " + e.getMessage());
        }

        System.out.println("DEBUG: Mapa cargado con " + mapaPasswords.size() + " contraseñas");
        return mapaPasswords;
    }

    // Verificar si una contraseña es correcta para un DNI
    public static boolean verificarPassword(int dni, String password) {
        System.out.println("DEBUG: Verificando - DNI: " + dni + ", Password: " + password);

        Map<Integer, String> mapa = cargarPasswords();
        boolean existeDNI = mapa.containsKey(dni);
        boolean passwordCorrecto = existeDNI && mapa.get(dni).equals(password);

        System.out.println("DEBUG: Existe DNI: " + existeDNI);
        if (existeDNI) {
            System.out.println("DEBUG: Password almacenado: '" + mapa.get(dni) + "'");
            System.out.println("DEBUG: Password ingresado: '" + password + "'");
            System.out.println("DEBUG: Coinciden: " + mapa.get(dni).equals(password));
        }
        System.out.println("DEBUG: Resultado verificación: " + passwordCorrecto);

        return passwordCorrecto;
    }

    // Método adicional para ver todas las contraseñas (útil para depuración)
    public static String mostrarTodasContraseñas() {
        Map<Integer, String> mapa = cargarPasswords();
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<Integer, String> entry : mapa.entrySet()) {
            sb.append("DNI: ").append(entry.getKey())
                    .append(" - Password: ").append(entry.getValue())
                    .append("\n");
        }

        return sb.toString();
    }
}