package MODELO;

import CONTROLADOR.Autenticable;
import java.io.Serializable;

public class Cliente extends Persona implements Autenticable, Serializable {
    private static final long serialVersionUID = 1L;

    private int idCliente;
    private Cuenta cuenta;
    private int dni;

    // Constructor que espera exactamente (int idclie, int dni, String nom, String apell, String contraseña)
    public Cliente(int idCliente, int dni, String nombre, String apellido, String password) {
        super(nombre, apellido, String.valueOf(dni)); // Persona guarda dni como String
        this.idCliente = idCliente;
        this.cuenta = new Cuenta();
        this.dni=dni;           // cuenta por defecto (saldo 0)
    }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public Cuenta getCuenta() { return cuenta; }
    public void setCuenta(Cuenta cuenta) { this.cuenta = cuenta; }

    public int getDni(){return dni;}
    public void setDni(int dni){this.dni=dni;}

    @Override
    public boolean autenticar(String usuario, String contraseña) {
        // usuario debe ser el dni en String (p. ej. "12345678")
        return false;
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


