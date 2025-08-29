public class SistemaBanco {

    public Cliente crearCliente(int idclie, int dni, String nom, String apell) {
        Cliente cliente = new Cliente(idclie, dni, nom, apell);
        ordenamiento.Implementacion(cliente);  // agrega ordenado
        return cliente;
    }

    public Cuenta crearCuenta(int numCuent, String tipCuenta, double saldo, Cliente cliente) {
        return new Cuenta(numCuent, tipCuenta, saldo, cliente);

    }

    public int iniciarSesion(Cliente cliente, int dni){
        int dni1 = dni;
        return (dni);
    }
    private Ordenamiento ordenamiento = new Ordenamiento();

    public Ordenamiento getOrdenamiento() {
        return ordenamiento;
    }
}
