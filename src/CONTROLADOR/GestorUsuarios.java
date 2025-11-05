package CONTROLADOR;

import MODELO.Persona;
import java.util.List;

public class GestorUsuarios<T extends Persona> {

    private List<T> listaUsuarios;

    public GestorUsuarios(List<T> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public T verificarAcceso(String dni, String password) {
        for (T usuario : listaUsuarios) {
            if (usuario.getDni().equalsIgnoreCase(dni)
                    && usuario.getPassword().equals(password)) {
                return usuario;
            }
        }
        return null;
    }
}
