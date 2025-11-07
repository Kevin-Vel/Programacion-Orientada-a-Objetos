package VISTA;

import MODELO.Empleados;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaEmpleado extends JFrame {

    private Empleados empleado;

    public VentanaEmpleado(Empleados empleado) {
        this.empleado = empleado;

        setTitle("Panel del Empleado - " + empleado.getNombre());
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panel principal
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 245, 250));

        // 🔹 Encabezado
        JLabel lblTitulo = new JLabel("Bienvenido, " + empleado.getNombre() + " (" + empleado.getCargo() + ")");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.add(lblTitulo, BorderLayout.NORTH);

        // 🔹 Cuerpo con botones
        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));

        JButton btnVerClientes = new JButton("👥 Ver lista de clientes");
        JButton btnRegistrarCliente = new JButton("➕ Registrar nuevo cliente");
        JButton btnCerrarSesion = new JButton("🚪 Cerrar sesión");

        // Estilo de botones
        JButton[] botones = { btnVerClientes, btnRegistrarCliente, btnCerrarSesion };
        for (JButton b : botones) {
            b.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            b.setFocusPainted(false);
        }

        panelBotones.add(btnVerClientes);
        panelBotones.add(btnRegistrarCliente);
        panelBotones.add(btnCerrarSesion);
        panel.add(panelBotones, BorderLayout.CENTER);

        // 🔹 Footer
        JLabel lblInfo = new JLabel("Sistema bancario - Modo Empleado", SwingConstants.CENTER);
        lblInfo.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblInfo.setForeground(Color.DARK_GRAY);
        lblInfo.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
        panel.add(lblInfo, BorderLayout.SOUTH);

        add(panel);

        // 🔹 Eventos de botones
        btnVerClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "📋 Mostrando lista de clientes (pendiente de implementar)");
            }
        });

        btnRegistrarCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "🧾 Formulario de registro de cliente (pendiente de implementar)");
            }
        });

        btnCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cierra esta ventana
                JOptionPane.showMessageDialog(null, "Sesión cerrada correctamente.");
            }
        });
    }
}
