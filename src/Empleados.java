public class Empleados extends Persona {
    private String cargo;

    public Empleados(String nombre, String apellido, String cargo) {
        super(nombre, apellido);
        this.cargo = cargo;
    }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    @Override
    public String mostrarInfo() {
        return "Empleado: " + nombre + " " + apellido + " - Cargo: " + cargo;
    }
}

