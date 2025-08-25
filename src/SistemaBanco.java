public class SistemaBanco {

    public Cliente crearCliente(int dni, String nom, String apell) {
        return new Cliente(dni, nom, apell);
    }

    public Cuenta crearCuenta(int numCuent, String tipCuenta, double saldo, Cliente cliente) {
        return new Cuenta(numCuent, tipCuenta, saldo, cliente);

    }

    public int iniciarSesion(Cliente cliente, int dni){
        int dni1 = dni;
        return (dni);
    }
}

