package MODELO;

import java.io.Serializable;

public class Cuenta implements Serializable {

    private static final long serialVersionUID = 1L;

    private int numCuenta;
    private TipoCuenta tipCuenta;
    private double saldo;
    private Cliente cliente;

    public Cuenta() {
        this.numCuenta = 0;
        this.tipCuenta = TipoCuenta.DEBITO;
        this.saldo = 0.0;
        this.cliente = null;
    }

    public Cuenta(int numCuenta, TipoCuenta tipCuenta, double saldo, Cliente cliente) {
        this.numCuenta = numCuenta;
        this.tipCuenta = tipCuenta;
        this.saldo = saldo;
        this.cliente = cliente;
    }

    public int getNumCuenta() { return numCuenta; }
    public TipoCuenta getTipCuenta() { return tipCuenta; }
    public double getSaldo() { return saldo; }
    public Cliente getCliente() { return cliente; }

    public void setNumCuenta(int numCuenta) { this.numCuenta = numCuenta; }
    public void setTipCuenta(TipoCuenta tipCuenta) { this.tipCuenta = tipCuenta; }
    public void setSaldo(double saldo) { this.saldo = saldo; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public void depositar(double monto) {
        if (monto > 0) saldo += monto;
    }

    public void retirar(double monto) {
        if (monto > 0 && saldo >= monto) saldo -= monto;
    }
}

