import javax.swing.*;
import java.awt.*;

public class VentanaIniciar extends JFrame {
    private SistemaBanco sistema;
    private JTextField txtDni;
    private JPasswordField txtPassword;

    public VentanaIniciar(SistemaBanco sistema) {
        this.sistema = sistema;
        setTitle("Iniciar Sesión");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar la ventana automáticamente

        // Panel principal con diseño BorderLayout
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel para el formulario
        JPanel panelFormulario = new JPanel(new GridLayout(2, 2, 10, 10));

        panelFormulario.add(new JLabel("DNI:"));
        txtDni = new JTextField();
        panelFormulario.add(txtDni);

        panelFormulario.add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField();
        panelFormulario.add(txtPassword);

        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);

        // Panel para el botón
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnIniciar = new JButton("Iniciar Sesión");
        panelBoton.add(btnIniciar);

        panelPrincipal.add(panelBoton, BorderLayout.SOUTH);

        add(panelPrincipal);

        // Aplicar validación de solo números al campo DNI
        PatrondeIngreso.soloNumeros(txtDni, 8);

        btnIniciar.addActionListener(e -> {
            try {
                int dni = Integer.parseInt(txtDni.getText());
                String password = new String(txtPassword.getPassword());

                if (ConsolaContraseñas.verificarPassword(dni, password)) {
                    JOptionPane.showMessageDialog(this, "¡Inicio de sesión exitoso!");
                    // Aquí puedes abrir la ventana principal del banco
                    // new VentanaPrincipal(sistema).setVisible(true);
                    // dispose(); // Cerrar ventana de inicio de sesión
                } else {
                    JOptionPane.showMessageDialog(this, "DNI o contraseña incorrectos");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese un DNI válido");
            }
        });
    }
}