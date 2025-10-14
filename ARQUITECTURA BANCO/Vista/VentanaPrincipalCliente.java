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

        // 🔹 Nuevo botón “Actualizar saldo” en la parte inferior
        JButton btnActualizar = new JButton("Actualizar Saldo");
        btnActualizar.addActionListener(e -> actualizarSaldo());

        panel.add(btnActualizar, BorderLayout.SOUTH);

        // Mostrar solo los datos por ahora
        add(panel);
        setVisible(true);
    }

    private void cerrarSesion() {
        dispose();
        SwingUtilities.invokeLater(() -> {
            new VentanaIniciar(sistema).setVisible(true);
        });
    }

    // 🔹 Método para refrescar el saldo en la etiqueta
    private void actualizarSaldo() {
        lblSaldo.setText("Saldo actual: $" + cliente.getCuenta().getSaldo());
    }
}
