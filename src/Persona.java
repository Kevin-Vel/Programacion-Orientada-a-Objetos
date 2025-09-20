import java.io.Serializable;

public abstract class Persona implements Serializable {
    private  static final   long serialVersionUID=1L;
    protected  String nombre;
    protected  String apellido;

    public Persona(String nombre, String apellido){
        this.nombre=nombre;
        this.apellido=apellido;
    }

    public String getNombre(){return nombre;}
    public String getApellido(){return apellido;}

    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setApellido(String apellido) {this.apellido = apellido;}

    public abstract String mostrarInfo();
}

