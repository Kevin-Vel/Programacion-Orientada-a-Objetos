import java.util.ArrayList;
import java.util.List;

public class Ordenamiento {
    private List<Cliente> listaCliente = new ArrayList<>();

    // Inserta el cliente en orden alfabético según el nombre
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
        StringBuilder sb = new StringBuilder();
        for (Cliente c : listaCliente) {
            sb.append(c.toString()).append("\n");
        }
        return sb.toString();
    }
}
