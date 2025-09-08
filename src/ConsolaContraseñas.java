import javax.swing.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ConsolaContraseñas {
    private static final String ARCHIVO = "passwords.txt";

    public static void guardarContraseña(int dni, String contraseña){
        try(PrintWriter pw= new PrintWriter(new FileWriter(ARCHIVO, true))){
            pw.println(dni+ ":"+ contraseña);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Error al guardar contraseña: " + e.getMessage());
        }

    }

    public static Map<Integer, String> cargarPasswords() {
        Map<Integer, String> mapaPasswords = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(":");
                if (partes.length == 2) {
                    int dni = Integer.parseInt(partes[0]);
                    mapaPasswords.put(dni, partes[1]);
                }
            }
        } catch (IOException e) {
            // El archivo no existe aún, se creará cuando se guarde la primera contraseña
        }
        return mapaPasswords;
    }

    // Verificar si una contraseña es correcta para un DNI
    public static boolean verificarPassword(int dni, String password) {
        Map<Integer, String> mapa = cargarPasswords();
        return mapa.containsKey(dni) && mapa.get(dni).equals(password);
    }
}

