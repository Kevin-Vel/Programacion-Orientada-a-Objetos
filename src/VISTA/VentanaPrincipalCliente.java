package VISTA;

import MODELO.Cliente;
import MODELO.Cuenta;
import CONTROLADOR.SistemaBanco;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipalCliente extends JFrame {
    private SistemaBanco sistema;
    private Cliente cliente;
    private JLabel lblSaldo;

    public VentanaPrincipalCliente(SistemaBanco sistema, Cliente cliente) {
        this.sistema = sistema;
        this.cliente = cliente;

        setTitle("Banco XYZ - Cliente: " + cliente.getNombre());
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 🔹 Verificar cuenta: si no existe, crear una por defecto
        if (cliente.getCuenta() == null) {
            cliente.setCuenta(new Cuenta()); // saldo 0
        }

        // Panel principal con BorderLayout
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        // Panel superior con información del cliente
        JPanel panelSuperior = new JPanel(new GridLayout(4, 1));
        JLabel lblBienvenida = new JLabel("Bienvenido, " + cliente.getNombre() + " " + cliente.getApellido());
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel lblDni = new JLabel("DNI: " + cliente.getDni());
        lblDni.setFont(new Font("Arial", Font.PLAIN, 14));

        lblSaldo = new JLabel("Saldo actual: $" + cliente.getCuenta().getSaldo());
        lblSaldo.setFont(new Font("Arial", Font.PLAIN, 14));

        // Botón de cerrar sesión
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.addActionListener(e -> cerrarSesion());

        panelSuperior.add(btnCerrarSesion);
        panelSuperior.add(lblBienvenida);
        panelSuperior.add(lblDni);
        panelSuperior.add(lblSaldo);

        panel.add(panelSuperior, BorderLayout.NORTH);

        // 🔹 Panel central con botones de acciones
        JPanel panelCentro = new JPanel(new GridLayout(1, 2, 10, 10));

        JButton btnTransferir = new JButton("Transferir Dinero");
        JButton btnActualizar = new JButton("Actualizar Saldo");

        panelCentro.add(btnTransferir);
        panelCentro.add(btnActualizar);

        panel.add(panelCentro, BorderLayout.CENTER);

        // Eventos
        btnActualizar.addActionListener(e -> actualizarSaldo());

        btnTransferir.addActionListener(e -> {
            try {
                // Pedir DNI del destinatario
                String dniDestinoStr = JOptionPane.showInputDialog("Ingrese el DNI del destinatario:");
                if (dniDestinoStr == null) return; // Cancelar
                int dniDestino = Integer.parseInt(dniDestinoStr);

                // Buscar cliente destino
                Cliente destinatario = sistema.buscarClientePorDni(dniDestino);
                if (destinatario == null) {
                    JOptionPane.showMessageDialog(this, "Cliente no encontrado");
                    return;
                }

                // Pedir monto
                String montoStr = JOptionPane.showInputDialog("Ingrese el monto a transferir:");
                if (montoStr == null) return; // Cancelar
                double monto = Double.parseDouble(montoStr);

                // Validar saldo
                if (cliente.getCuenta().getSaldo() < monto) {
                    JOptionPane.showMessageDialog(this, "Saldo insuficiente");
                    return;
                }

                // Realizar transferencia
                cliente.getCuenta().setSaldo(cliente.getCuenta().getSaldo() - monto);
                destinatario.getCuenta().setSaldo(destinatario.getCuenta().getSaldo() + monto);

                sistema.guardarDatos(); // 🔹 Guardar cambios

                JOptionPane.showMessageDialog(this, "Transferencia realizada con éxito");

                actualizarSaldo();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Entrada inválida");
            }
        });

        add(panel);
        setVisible(true);
    }

    private void cerrarSesion() {
        dispose();
        SwingUtilities.invokeLater(() -> new VentanaIniciar(sistema).setVisible(true));
    }

    private void actualizarSaldo() {
        lblSaldo.setText("Saldo actual: $" + cliente.getCuenta().getSaldo());
    }
}


