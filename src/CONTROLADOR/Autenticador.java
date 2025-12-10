package CONTROLADOR;

public class Autenticador<T extends Autenticable> {

    private T entidad;

    public Autenticador(T entidad) {
        this.entidad = entidad;
    }

    public boolean iniciarSesion(String usuario, String contraseña) {
        return entidad.autenticar(usuario, contraseña);
    }
}

