package VISTA;

import MODELO.Empleados;
import CONTROLADOR.SistemaBanco;
import javax.swing.*;
import java.awt.*;

public class VentanaEmpleado extends JFrame {

    private Empleados empleado;
    private SistemaBanco sistema;

    // CORREGIDO: Constructor ahora recibe dos parámetros
    public VentanaEmpleado(Empleados empleado, SistemaBanco sistema) {
        this.empleado = empleado;
        this.sistema = sistema;  // <-- AQUÍ SE ASIGNA EL SISTEMA

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
        btnVerClientes.addActionListener(e -> {
            // Ahora puede usar 'sistema' porque está asignado
            String lista = this.sistema.getOrdenamiento().listaFormateadaOrdenada("nombre");
            JOptionPane.showMessageDialog(this, lista, "Lista de Clientes", JOptionPane.INFORMATION_MESSAGE);
        });

        btnRegistrarCliente.addActionListener(e -> {
            // Puede abrir VentanaCliente pasando el sistema
            VentanaCliente ventanaCliente = new VentanaCliente(sistema);
            ventanaCliente.setVisible(true);
            dispose(); // Cierra esta ventana
        });

        btnCerrarSesion.addActionListener(e -> {
            dispose(); // Cierra esta ventana
            JOptionPane.showMessageDialog(null, "Sesión cerrada correctamente.");
        });
    }
}