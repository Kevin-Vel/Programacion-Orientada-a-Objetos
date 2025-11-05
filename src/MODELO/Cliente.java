package MODELO;

public class Cliente extends Persona {
    private Cuenta cuenta;

    public Cliente(String nombre, String apellido, String dni, String password) {
        super(nombre, apellido, dni, password);
    }

    public Cuenta getCuenta() { return cuenta; }
    public void setCuenta(Cuenta cuenta) { this.cuenta = cuenta; }

    @Override
    public String mostrarInfo() {
        return "Cliente: " + nombre + " " + apellido + " - DNI: " + dni;
    }
}

