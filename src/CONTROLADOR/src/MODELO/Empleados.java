package MODELO;

import CONTROLADOR.Autenticable;

public class Empleados extends Persona implements Autenticable {

    private String cargo;
    private String eDni;

    public Empleados(String nombre, String apellido, String eDni, String password, String cargo) {
        super(nombre, apellido, password);
        this.cargo = cargo;
        this.eDni = eDni;
    }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public String getEDni() { return eDni; }
    public void setEDni(String eDni) { this.eDni = eDni; }

    @Override
    public boolean autenticar(String usuario, String contraseña) {
        return eDni.equals(usuario) && getPassword().equals(contraseña);
    }

    @Override
    public String mostrarInfo() {
        return "Empleado: " + nombre + " " + apellido + " - Cargo: " + cargo;
    }
}


