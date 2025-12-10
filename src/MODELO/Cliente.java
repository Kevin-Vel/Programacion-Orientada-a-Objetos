package MODELO;

import CONTROLADOR.Autenticable;
import java.io.Serializable;

public class Cliente extends Persona implements Autenticable, Serializable {
    private static final long serialVersionUID = 1L;

    private int idCliente;
    private Cuenta cuenta;
    private int dni;

    public Cliente(int idCliente, int dni, String nombre, String apellido, String password) {
        super(nombre, apellido, password);
        this.idCliente = idCliente;
        this.cuenta = new Cuenta();
        this.dni = dni;
    }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public Cuenta getCuenta() { return cuenta; }
    public void setCuenta(Cuenta cuenta) { this.cuenta = cuenta; }

    public int getDni() { return dni; }
    public void setDni(int dni) { this.dni = dni; }

    @Override
    public boolean autenticar(String usuario, String contraseña) {
        return usuario.equals(String.valueOf(this.dni))
                && contraseña.equals(getPassword());
    }

    @Override
    public String mostrarInfo() {
        return "Cliente: " + nombre + " " + apellido + " - DNI: " + dni;
    }

    @Override
    public String toString() {
        return idCliente + " " + dni + " - " + nombre + " - " + apellido;
    }
}
