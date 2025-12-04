package MODELO;

public class Transacciones {
    private int Monto;
    private String Tipo, Fecha;

    public Transacciones(int Monto, String Tipo, String Fecha){
        this.Monto=Monto;
        this.Tipo=Tipo;
        this.Fecha=Fecha;
    }

    public int getMonto(){return Monto;}
    public String getTipo(){return Tipo;}
    public String getFecha(){return Fecha;}

    public void setMonto(int Monto){this.Monto=Monto;}
    public void setTipo(String Tipo){this.Tipo=Tipo;}
    public void setFecha(String Fecha){this.Fecha=Fecha;}
}
