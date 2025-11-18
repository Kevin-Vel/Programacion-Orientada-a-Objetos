package MODELO;

import CONTROLADOR.Autenticable;

public class Empleados extends Persona implements Autenticable{
    private String cargo;
    private String EDni;

    public Empleados(String nombre, String apellido, String Edni, String password, String cargo) {
        super(nombre, apellido, password);
        this.cargo = cargo;
        this.EDni = Edni;
    }


    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public String getEDni(){return EDni;}
    public void setEDni(String EDni){this.EDni=EDni;}

    @Override
    public boolean autenticar(String usuario, String contraseña){
        return nombre.equals(usuario)&& password.equals(contraseña);
    }

    @Override
    public String mostrarInfo() {
        return "Empleado: " + nombre + " " + apellido + " - Cargo: " + cargo;
    }
}


