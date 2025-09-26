import java.io.Serializable;

public class Cliente extends Persona implements Serializable {
    private  static final   long serialVersionUID=1L;
    private Cuenta cuenta;
    private int idcli;
    private int dni;
    private String contraseña;

    // Constructor
    public Cliente(int idclie, int dni, String nom, String apell, String contraseña) {
        super(nom, apell);
        this.idcli = idclie;
        this.dni = dni;
        this.contraseña = this.contraseña;
        this.cuenta = new Cuenta();
    }

    public Cuenta getCuenta(){return cuenta;}
    public int getIdclie() { return idcli; }
    public int getDni() { return dni; }
    public String getContraseña() { return contraseña; }

    public void setContraseña(String contraseña) { this.contraseña = contraseña; }
    public void setCuenta(Cuenta cuenta){this.cuenta=cuenta;}
    @Override
    public String mostrarInfo() {
        return "Cliente: " + idcli + " - DNI: " + dni + " - " + nombre + " " + apellido;
    }

    @Override
    public String toString() {
        return idcli + " " + dni + " - " + nombre + " - " + apellido;
    }
}


