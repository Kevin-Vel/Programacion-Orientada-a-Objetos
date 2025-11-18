import MODELO.Persona;
import MODELO.Cliente;
import MODELO.Empleados;
import java.util.List;

public class GestorUsuarios<T extends Persona> {

    private List<T> listaUsuarios;

    public GestorUsuarios(List<T> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public T verificarAcceso(String dniIngresado, String passwordIngresado) {

        for (T usuario : listaUsuarios) {

            String dniReal = null;

            // ====== Si es Cliente ======
            if (usuario instanceof Cliente) {
                dniReal = ((Cliente) usuario).getDni(); // Cliente usa DNI normal
            }

            // ====== Si es Empleado ======
            if (usuario instanceof Empleados) {
                dniReal = ((Empleados) usuario).geteDni(); // Empleado usa eDni
            }

            // Verificamos que exista DNI
            if (dniReal != null &&
                    dniReal.equalsIgnoreCase(dniIngresado) &&
                    usuario.getPassword().equals(passwordIngresado)) {

                return usuario;   // acceso válido
            }
        }

        return null; // no coincide nadie
    }
}
