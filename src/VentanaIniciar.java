import javax.swing.*;
import java.awt.*;


public class VentanaIniciar extends JFrame {
    private SistemaBanco sistema;

    public VentanaIniciar() {
        this.sistema = sistema;
        setTitle("Iniciar Sesión");
        setSize(300,200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3,2));

        JLabel lblDni = new JLabel("DNI:");
        JTextField txtDni = new JTextField();
        JButton btnIniciar = new JButton("Iniciar Sesión");

        add(lblDni);
        add(txtDni);
        add(btnIniciar);

        btnIniciar.addActionListener(e -> {
            try {
                int dni = Integer.parseInt(txtDni.getText());
                Cliente cliente = sistema.buscarPorDni(dni);

                if (cliente != null) {
                    JOptionPane.showMessageDialog(this,
                            "Bienvenido " + cliente.getNom());
                    // Aquí puedes abrir VentanaCliente
                    new VentanaCliente(true).setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "DNI no encontrado.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "DNI inválido.");
            }
        });
    }
}
