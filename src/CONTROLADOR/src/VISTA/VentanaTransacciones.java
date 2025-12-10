package VISTA;

import CONTROLADOR.SistemaBanco;
import MODELO.Cliente;
import javax.swing.*;
import java.awt.*;

public class VentanaTransacciones extends JFrame {
    private SistemaBanco sistema;
    private Cliente cliente;
    private JTextField txtMonto;
    private JButton btnTransferir, btnVolver;
    int saldo = 3000;

    public VentanaTransacciones(SistemaBanco sistema, Cliente cliente) {
        this.sistema = sistema;
        this.cliente = cliente;

        setTitle("Transferencia - Cliente: " + cliente.getNombre());
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Panel principal
        JPanel panelCentro = new JPanel(new GridLayout(3, 2, 10, 10));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panelCentro.add(new JLabel("Saldo actual:"+saldo));
        JLabel lblSaldo = new JLabel("S/ " + cliente.getCuenta().getSaldo());
        panelCentro.add(lblSaldo);

        panelCentro.add(new JLabel("Monto a transferir:"));
        txtMonto = new JTextField();
        panelCentro.add(txtMonto);

        add(panelCentro, BorderLayout.CENTER);

        // Panel botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnTransferir = new JButton("Transferir");
        btnVolver = new JButton("Volver");
        panelBotones.add(btnTransferir);
        panelBotones.add(btnVolver);
        add(panelBotones, BorderLayout.SOUTH);

        // Acción Transferir
        btnTransferir.addActionListener(e -> {
            try {
                String dniDestinoStr = JOptionPane.showInputDialog(this, "Ingrese el DNI del destinatario:");
                if (dniDestinoStr == null) return;
                int dniDestino = Integer.parseInt(dniDestinoStr);

                Cliente destinatario = sistema.buscarClientePorDni(dniDestino);
                if (destinatario == null) {
                    JOptionPane.showMessageDialog(this, "Cliente no encontrado.");
                    return;
                }

                double monto = Double.parseDouble(txtMonto.getText());
                if (monto <= 0) {
                    JOptionPane.showMessageDialog(this, "⚠El monto debe ser mayor que cero.");
                    return;
                }

                if (cliente.getCuenta().getSaldo() < monto) {
                    JOptionPane.showMessageDialog(this, " Saldo insuficiente.");
                    return;
                }

                int confirmar = JOptionPane.showConfirmDialog(this,
                        "¿Confirmar transferencia de S/ " + monto + " a " + destinatario.getNombre() + "?",
                        "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirmar != JOptionPane.YES_OPTION) return;

                // Realizar transferencia
                cliente.getCuenta().setSaldo(cliente.getCuenta().getSaldo() - monto);
                destinatario.getCuenta().setSaldo(destinatario.getCuenta().getSaldo() + monto);
                sistema.guardarDatos();

                JOptionPane.showMessageDialog(this,
                        " Transferencia realizada con éxito.\n" +
                                "Nuevo saldo: S/ " + cliente.getCuenta().getSaldo());

                lblSaldo.setText("S/ " + cliente.getCuenta().getSaldo());
                txtMonto.setText("");
                
                //Guardar Registro
                sistema.registrarTransaccion(
                        "Transferencia de S/ " + monto + " de " + cliente.getNombre() +
                                " a " + destinatario.getNombre()
                );

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, " Ingrese valores válidos.");
            }
        });

        // Acción Volver
        btnVolver.addActionListener(e -> dispose());
    }
}

