import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Ordenamiento {
    private List<Cliente> listaCliente=new ArrayList<>();

    public void Implementacion(Cliente nuevoCliente){
        int i= 0;
        while (i< listaCliente.size() && listaCliente.get(i).getNom().compareToIgnoreCase(nuevoCliente.getNom())<0){
            i++;
        }
        listaCliente.add(i, nuevoCliente);
    }
    public List<Cliente>getListaCliente(){
        return listaCliente;
    }
    public String listaFormateada(){
        if (listaCliente.isEmpty()){
            return "No hay clientes registrados. ";
        }
        StringBuilder sb= new StringBuilder();
        for (Cliente c: listaCliente){
            sb.append((c.toString())).append("\n");
        }
        return sb.toString();
    }
    public Cliente buscarPorDni(int dni) {
        for (Cliente c : listaCliente) {
            if (c.getDni() == dni) {
                return c; // retorna el cliente si lo encuentra
            }
        }
        return null; // si no lo encuentra
    }
}