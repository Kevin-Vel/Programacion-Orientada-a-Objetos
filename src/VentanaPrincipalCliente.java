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
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        // Panel superior con información del cliente
        JPanel panelSuperior = new JPanel(new GridLayout(3, 1,5,5));
        JLabel lblBienvenida = new JLabel("Bienvenido, " + cliente.getNombre() + " " + cliente.getApellido());
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel lblDni = new JLabel("DNI: " + cliente.getDni());
        lblDni.setFont(new Font("Arial", Font.PLAIN, 14));

        lblSaldo = new JLabel("Saldo actual: $" + cliente.getCuenta().getSaldo());
        lblSaldo.setFont(new Font("Arial", Font.PLAIN, 14));

        panelSuperior.add(lblBienvenida);
        panelSuperior.add(lblDni);
        panelSuperior.add(lblSaldo);

        panel.add(panelSuperior, BorderLayout.NORTH);

        // 🔹 Panel inferior con botón Cerrar Sesión
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
        panelInferior.add(btnCerrarSesion);

        panel.add(panelInferior, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

    private void cerrarSesion() {
        dispose();
        SwingUtilities.invokeLater(() -> {
            new VentanaIniciar(sistema).setVisible(true);
        });
    }
}
