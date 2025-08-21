public class Cuenta {
    private String numCuenta;
    private String tipCuenta;
    private double saldo;
    private Cliente cliente;

    public Cuenta (String numCuenta, String tipCuenta, double saldo, Cliente cliente){
        this.numCuenta=numCuenta;
        this.tipCuenta=tipCuenta;
        this.saldo=saldo;
        this.cliente=cliente;
    }

    public String getNumCuenta(){return numCuenta;}
    public String getTipCuenta(){return tipCuenta;}
    public double getSaldo(){return saldo;}
    public Cliente getCliente(){return cliente;}

    public void setNumCuenta(String numCuenta){this.numCuenta;}
    public void setTipCuenta(String tipCuenta){this.tipCuenta;}
    public void setSaldo(double saldo){this.saldo;}
    public void setCliente(Cliente cliente){this.cliente;}
    
}
