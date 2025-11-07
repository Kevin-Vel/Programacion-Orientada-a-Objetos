package MODELO;

import java.io.Serializable;

public abstract class Persona implements PersonaInterface, Serializable {
    private static final long serialVersionUID = 1L;

    protected String nombre;
    protected String apellido;
    protected String dni;        // ahora dni como String
    protected String password;   // password como String

    public Persona(String nombre, String apellido, String dni, String password){
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.password = password;
    }

    @Override
    public void setNombre(String nombre) { this.nombre = nombre; }
    @Override
    public String getNombre() { return nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public abstract String mostrarInfo();
}




