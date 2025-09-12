import javax.swing.*;
import java.awt.*;

public class VentanaPrincipalCliente extends JFrame {
    private SistemaBanco sistema;
    private Cliente cliente;

    public VentanaPrincipalCliente(SistemaBanco sistema, Cliente cliente) {
        this.sistema = sistema;
        this.cliente = cliente;

        setTitle("Ventana Principal - " + cliente.getNom());
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel lblBienvenida = new JLabel("Bienvenido, " + cliente.getNom() + " " + cliente.getApell());
        panel.add(lblBienvenida, BorderLayout.NORTH);

        // Aquí puedes agregar más componentes para las operaciones bancarias

        add(panel);
        setVisible(true); // Asegúrate de hacer visible la ventana
    }
}