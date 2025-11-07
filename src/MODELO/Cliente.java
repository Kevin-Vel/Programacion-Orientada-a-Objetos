package MODELO;

import CONTROLADOR.Autenticable;
import java.io.Serializable;

public class Cliente extends Persona implements Autenticable, Serializable {
    private static final long serialVersionUID = 1L;

    private int idCliente;
    private Cuenta cuenta;

    // Constructor que espera exactamente (int idclie, int dni, String nom, String apell, String contraseña)
    public Cliente(int idCliente, String dni, String nombre, String apellido, String password) {
        super(nombre, apellido, String.valueOf(dni), password); // Persona guarda dni como String
        this.idCliente = idCliente;
        this.cuenta = new Cuenta(); // cuenta por defecto (saldo 0)
    }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public Cuenta getCuenta() { return cuenta; }
    public void setCuenta(Cuenta cuenta) { this.cuenta = cuenta; }

    @Override
    public boolean autenticar(String usuario, String contraseña) {
        // usuario debe ser el dni en String (p. ej. "12345678")
        return getDni() != null && getDni().equals(usuario) && getPassword() != null && getPassword().equals(contraseña);
    }

    @Override
    public String mostrarInfo() {
        return "Cliente: " + getNombre() + " " + getApellido() + " - DNI: " + getDni();
    }

    @Override
    public String toString() {
        return idCliente + " " + getDni() + " - " + getNombre() + " - " + getApellido();
    }
}


