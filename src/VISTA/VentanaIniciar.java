package VISTA;

import MODELO.Cliente;
import MODELO.Empleados;
import CONTROLADOR.SistemaBanco;
import MODELO.Persona;

import javax.swing.*;
import java.awt.*;

public class VentanaIniciar extends JFrame {
    private SistemaBanco sistema;
    private JTextField txtUsuario; // puede ser DNI o código de empleado (E###)
    private JPasswordField txtPassword;

    public VentanaIniciar(SistemaBanco sistema) {
        this.sistema = sistema;
        setTitle("Iniciar Sesión");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel formulario
        JPanel panelFormulario = new JPanel(new GridLayout(2, 2, 10, 10));
        panelFormulario.add(new JLabel("Usuario (DNI o E..):"));
        txtUsuario = new JTextField();
        panelFormulario.add(txtUsuario);

        panelFormulario.add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField();
        panelFormulario.add(txtPassword);
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);

        // Panel botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnIniciar = new JButton("Iniciar Sesión");
        JButton btnRegresar = new JButton("Regresar");
        panelBotones.add(btnIniciar);
        panelBotones.add(btnRegresar);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);

        // Validaciones
        PatrondeIngreso.soloNumeros(txtPassword, 4);
        PatrondeIngreso.soloDni(txtUsuario,8);

        // Acción del botón Iniciar Sesión
        btnIniciar.addActionListener(e -> iniciarSesion());

        // Acción del botón Regresar
        btnRegresar.addActionListener(e -> {
            Menu menu = new Menu(sistema);
            menu.setVisible(true);
            dispose();
        });
    }

    private void iniciarSesion() {
        String usuario = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (usuario.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.");
            return;
        }

        // Convertir a mayúsculas para consistencia
        String usuarioUpper = usuario.toUpperCase();

        // 🔹 LLAMAR AL MÉTODO DEL SISTEMA QUE YA TIENE REGISTRO AUTOMÁTICO
        Persona persona = sistema.iniciarSesion(usuarioUpper, password);

        if (persona != null) {
            // 🔹 USO POLIMÓRFICO: mostrarInfo() se comporta diferente según Cliente o Empleado
            String mensajeBienvenida = "¡Inicio de sesión exitoso!\n" + persona.mostrarInfo();
            JOptionPane.showMessageDialog(this, mensajeBienvenida);

            dispose(); // Cerrar ventana de login

            // 🔹 Dependiendo del tipo, abrimos una ventana u otra
            if (persona instanceof Empleados) {
                Empleados empleado = (Empleados) persona;
                VentanaEmpleado ventanaEmpleado = new VentanaEmpleado(empleado, sistema);
                ventanaEmpleado.setVisible(true);
            } else if (persona instanceof Cliente) {
                Cliente cliente = (Cliente) persona;
                VentanaPrincipalCliente ventanaCliente = new VentanaPrincipalCliente(sistema, cliente);
                ventanaCliente.setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Usuario o contraseña incorrectos",
                    "Error de autenticación", JOptionPane.ERROR_MESSAGE);
        }
    }
}
