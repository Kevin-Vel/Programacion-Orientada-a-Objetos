import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaCliente extends JFrame {
    private SistemaBanco sistema;
    private JTextField txtDni, txtNom, txtApell;
    private JPasswordField txtContraseña;
    private JButton btnCrear, btnVerClientes, btnVolver;

    public VentanaCliente(SistemaBanco sistema) {
        this.sistema = sistema;
        setTitle("Crear Cliente");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Usar BorderLayout como contenedor principal
        setLayout(new BorderLayout(10, 10));

        // Panel principal con márgenes
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel para el formulario (Centro)
        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 10, 10));

        panelFormulario.add(new JLabel("DNI:"));
        txtDni = new JTextField();
        panelFormulario.add(txtDni);

        panelFormulario.add(new JLabel("Nombre:"));
        txtNom = new JTextField();
        panelFormulario.add(txtNom);

        panelFormulario.add(new JLabel("Apellido:"));
        txtApell = new JTextField();
        panelFormulario.add(txtApell);

        panelFormulario.add(new JLabel("Contraseña:"));
        txtContraseña = new JPasswordField();
        panelFormulario.add(txtContraseña);

        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);

        // Panel para botones (Sur)
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        btnCrear = new JButton("Crear Cliente");
        btnVerClientes = new JButton("Ver Clientes");
        btnVolver = new JButton("Regresar");

        panelBotones.add(btnCrear);
        panelBotones.add(btnVerClientes);
        panelBotones.add(btnVolver);

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal, BorderLayout.CENTER);

        // Filtros
        PatrondeIngreso.soloNumeros(txtDni, 8);
        PatrondeIngreso.soloLetras(txtNom);
        PatrondeIngreso.soloLetras(txtApell);

        // Eventos
        btnCrear.addActionListener(e -> {
            try {
                int idclie = (int)(Math.random() * 1000);
                int dni = Integer.parseInt(txtDni.getText());
                String nom = PatrondeIngreso.formatearNombre(txtNom.getText());
                String apell = PatrondeIngreso.formatearNombre(txtApell.getText());
                String contra = new String(txtContraseña.getPassword());

                // Creación Cliente
                Cliente nuevoCliente= sistema.crearCliente(idclie, dni,nom,apell,contra);

                // GUARDAR CONTRASEÑA
                ConsolaContraseñas.guardarContraseña(dni,contra);

                JOptionPane.showMessageDialog(null, "Cliente Creado:\n" + nuevoCliente.toString());
                txtDni.setText("");
                txtNom.setText("");
                txtApell.setText("");
                txtContraseña.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "DNI inválido");
            }
        });

        btnVerClientes.addActionListener(e -> {
            // Establecer criterio de ordenamiento (puedes hacerlo seleccionable por el usuario)
            sistema.getOrdenamiento().setCriterioOrdenamiento("dni"); // o "nombre", "apellido"

            // Obtener lista formateada (usará ordenamiento externo si es necesario)
            String lista = sistema.getOrdenamiento().listaFormateada();
            JOptionPane.showMessageDialog(null, lista);
        });


        btnVolver.addActionListener(e -> {
            Menu inicio = new Menu(sistema);
            inicio.setVisible(true);
            dispose();
        });
    }
}