package MODELO;

import CONTROLADOR.Autenticable;

public class Empleados extends Persona implements Autenticable{
    private String cargo;

    public Empleados(String nombre, String apellido, String dni, String password, String cargo) {
        super(nombre, apellido, dni, password);
        this.cargo = cargo;
    }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    @Override
    public boolean autenticar(String usuario, String contraseña){
        return nombre.equals(usuario)&& password.equals(contraseña);
    }

    @Override
    public String mostrarInfo() {
        return "Empleado: " + nombre + " " + apellido + " - Cargo: " + cargo;
    }
}


