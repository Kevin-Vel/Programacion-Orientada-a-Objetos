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

        // Panel para los botones
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnIniciar = new JButton("Iniciar Sesión");
        JButton btnRegresar = new JButton("Regresar");

        panelBoton.add(btnIniciar);
        panelBoton.add(btnRegresar);

        panelPrincipal.add(panelBoton, BorderLayout.SOUTH);

        add(panelPrincipal);

        // Aplicar validación de solo números al campo DNI
        PatrondeIngreso.soloNumeros(txtDni, 8);

        // Evento para el botón Iniciar Sesión
        btnIniciar.addActionListener(e -> {
            try {
                int dni = Integer.parseInt(txtDni.getText());
                String password = new String(txtPassword.getPassword());

                if (ConsolaContraseñas.verificarPassword(dni, password)) {
                    // Buscar el cliente en el sistema
                    Cliente cliente = sistema.buscarPorDni(dni);

                    if (cliente != null) {
                        // ✅ VALIDACIÓN CRÍTICA: Verificar que el cliente tenga cuenta
                        if (cliente.getCuenta() == null) {
                            JOptionPane.showMessageDialog(this,
                                    "Error: El cliente no tiene una cuenta asociada.\n" +
                                            "Por favor, contacte con el administrador del sistema.");
                            return; // No continuar
                        }

                        JOptionPane.showMessageDialog(this, "¡Inicio de sesión exitoso!");

                        // Cerrar la ventana de inicio de sesión
                        dispose();

                        // Abrir la ventana principal del cliente
                        VentanaPrincipalCliente ventanaPrincipal = new VentanaPrincipalCliente(sistema, cliente);
                        ventanaPrincipal.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(this, "Cliente no encontrado en el sistema");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "DNI o contraseña incorrectos");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese un DNI válido");
            }
        });

        // Evento para el botón Regresar
        btnRegresar.addActionListener(e -> {
            Menu menu = new Menu(sistema);
            menu.setVisible(true);
            dispose();
        });
    }
}