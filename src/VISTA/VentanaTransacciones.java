package VISTA;

import CONTROLADOR.SistemaBanco;
import MODELO.Cliente;
import MODELO.Cuenta;

import java.awt.*;
import javax.swing.*;

//Contructor
public class VentanaTransacciones extends JFrame {
    private SistemaBanco sistema;
    private Cliente cliente;

    private JTextField txtDestino, txtMonto;
    private JButton btnTransferir, btnVolver;

    public VentanaTransacciones(SistemaBanco sistema, Cliente cliente) {
        this.sistema = sistema;
        this.cliente = cliente;

        setTitle("Ventana de Transferencia - Cliente: " + cliente.getNombre());
        setSize(400, 250);
        setLocationRelativeTo(null); // CORREGIDO: era "RelativeTO"
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Panel central
        JPanel panelCentro = new JPanel(new GridLayout(2, 2, 10, 10));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panelCentro.add(new JLabel("Monto a Transferir:"));
        txtMonto = new JTextField();
        panelCentro.add(txtMonto);

        add(panelCentro, BorderLayout.CENTER);

        // Panel inferior (botones)
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnTransferir = new JButton("Transferir");
        btnVolver = new JButton("Volver");

        panelBotones.add(btnTransferir);
        panelBotones.add(btnVolver);

        add(panelBotones, BorderLayout.SOUTH); // CORREGIDO: antes estabas agregando "panelCentro"

        // Acción de transferir
        btnTransferir.addActionListener(e -> {
            realizarTransferencia();
            dispose();
        });

        // Acción para volver
        btnVolver.addActionListener(e -> dispose());
    }

    private void realizarTransferencia() {
        try {
            int dniDestino = Integer.parseInt(txtDestino.getText());
            double monto = Double.parseDouble(txtMonto.getText());

            Cliente destinatario = sistema.buscarClientePorDni(dniDestino);

            if (destinatario == null) {
                JOptionPane.showMessageDialog(this, "Cliente destino no encontrado.");
                return;
            }

            Cuenta cuentaOrigen = cliente.getCuenta();
            Cuenta cuentaDestino = destinatario.getCuenta();

            if (cuentaOrigen.getSaldo() < monto) {
                JOptionPane.showMessageDialog(this, "Saldo insuficiente.");
                return;
            }

            cuentaOrigen.retirar(monto);
            cuentaDestino.depositar(monto);
            sistema.guardarDatos();

            JOptionPane.showMessageDialog(this, "Transferencia realizada con éxito.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Verifique los datos ingresados.");
        }
    }
}
