import javax.swing.*;
import java.awt.*;


public class VentanaIniciar extends JFrame {
    private SistemaBanco sistema;

    public VentanaIniciar(SistemaBanco sistema) {
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

        });
    }
}