import java.io.Serializable;

public class Cliente extends Persona implements Serializable {
    private  static final   long serialVersionUID=1L;
    private int idcli;
    private int dni;
    private String contraseña;

    // Constructor
    public Cliente(int idclie, int dni, String nom, String apell) {
        super(nom, apell);
        this.idcli = idclie;
        this.dni = dni;
        this.contraseña = "";
    }

    public int getIdclie() { return idcli; }
    public int getDni() { return dni; }
    public String getContraseña() { return contraseña; }

    public void setContraseña(String contraseña) { this.contraseña = contraseña; }

    @Override
    public String mostrarInfo() {
        return "Cliente: " + idcli + " - DNI: " + dni + " - " + nombre + " " + apellido;
    }

    @Override
    public String toString() {
        return idcli + " " + dni + " - " + nombre + " - " + apellido;
    }
}


