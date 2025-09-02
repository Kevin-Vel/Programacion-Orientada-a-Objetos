public class Empleados {
    private String nombre, apellido, cargo;

    public Empleados(String nombre, String apellido, String cargo){
        this.nombre=nombre;
        this.apellido=apellido;
        this.cargo=cargo;
    }

    public String getNombre(){return nombre;}
    public String getApellido(){return apellido;}
    public String getCargo(){return cargo;}

    public void setNombre(String nombre){this.nombre=nombre;}
    public void setApellido(String apellido){this.apellido=apellido;}
    public void setCargo(String cargo){this.cargo=cargo;}
}
