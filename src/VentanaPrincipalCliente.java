import javax.swing.*;
import java.awt.*;

public class VentanaPrincipalCliente extends JFrame {
    private SistemaBanco sistema;
    private Cliente cliente;

    public VentanaPrincipalCliente(SistemaBanco sistema, Cliente cliente) {
        this.sistema = sistema;
        this.cliente = cliente;

        setTitle("Banco XYZ - Cliente: " + cliente.getNombre() + " " + cliente.getApellido());
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel superior con información del cliente
        JPanel panelSuperior = crearPanelSuperior();
        add(panelSuperior, BorderLayout.NORTH);

        setVisible(true);
    }

    private JPanel crearPanelSuperior() {
        JPanel panelSuperior = new JPanel(new GridLayout(4, 1, 5, 5));

        JLabel lblBienvenida = new JLabel("Bienvenido, " + cliente.getNombre() + " " + cliente.getApellido());
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel lblId = new JLabel("ID Cliente: " + cliente.getIdclie());
        JLabel lblDni = new JLabel("DNI: " + cliente.getDni());

        // 
        JLabel lblContra = new JLabel("Contraseña: " + cliente.getContraseña());

        panelSuperior.add(lblBienvenida);
        panelSuperior.add(lblId);
        panelSuperior.add(lblDni);
        panelSuperior.add(lblContra);

        return panelSuperior;
    }
}
