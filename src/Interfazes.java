public class Interfazes {
    public interface PersonaInterface {
        void setNombre(String nombre);

        String getNombre();
    }

    public interface Autenticable {
        boolean autenticar(String usuario, String contraseña);
    }
}
