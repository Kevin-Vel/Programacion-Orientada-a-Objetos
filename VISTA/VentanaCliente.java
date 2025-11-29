package VISTA;

import MODELO.Cliente;
import MODELO.Cuenta;
import CONTROLADOR.SistemaBanco;
import MODELO.TipoCuenta;

import javax.swing.*;
import java.awt.*;

public class VentanaCliente extends JFrame {
    private SistemaBanco sistema;
    private JTextField txtDni, txtNom, txtApell;
    private JPasswordField txtContraseña;
    private JComboBox<String> comboTipoCueneta;
    private JButton btnCrear, btnVerClientes, btnVolver;

    public VentanaCliente(SistemaBanco sistema) {
        this.sistema = sistema;
        setTitle("Ventana Cliente");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Usar BorderLayout como contenedor principal
        setLayout(new BorderLayout(10, 10));

        // Panel principal con márgenes
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel para el formulario (Centro)
        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 10, 10));

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

        panelFormulario.add(new JLabel("Tipo de cuenta"));
        String[] tipos={"DEBITO", "CREDITO"};
        comboTipoCueneta= new JComboBox<>(tipos);
        panelFormulario.add(comboTipoCueneta);

        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);

        // Panel para botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        btnCrear = new JButton("Crear Cliente");
        btnVolver = new JButton("Regresar");

        panelBotones.add(btnCrear);
        panelBotones.add(btnVolver);

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal, BorderLayout.CENTER);

        // Validadores (Patrón de Ingreso)
        PatrondeIngreso.soloNumeros(txtDni, 8);
        PatrondeIngreso.soloLetras(txtNom);
        PatrondeIngreso.soloLetras(txtApell);

        // Eventos
        btnCrear.addActionListener(e -> {
            try {
                int idclie = (int) (Math.random() * 1000);
                int dni = Integer.parseInt(txtDni.getText());
                String nom = PatrondeIngreso.formatearNombre(txtNom.getText());
                String apell = PatrondeIngreso.formatearNombre(txtApell.getText());
                String contra = new String(txtContraseña.getPassword());

                //Tipo de MODELO.Cuenta selecionado
                String tipoSelecionao = (String) comboTipoCueneta.getSelectedItem();
                TipoCuenta tipoCuenta = tipoSelecionao.equals("CREDITO")
                    ?TipoCuenta.CREDITO: TipoCuenta.DEBITO;

                // Creación MODELO.Cliente
                Cliente nuevoCliente = sistema.crearCliente(idclie, dni, nom, apell, contra);
                Cuenta nuevaCuenta= new Cuenta((int)(Math.random()*100000), tipoCuenta,0.0, nuevoCliente);
                sistema.agregarCuenta(nuevaCuenta);

                // Guardar contraseña
                ConsolaContraseñas.guardarContraseña(dni, contra);

                JOptionPane.showMessageDialog(null, "Cliente Creado:\n" + nuevoCliente.toString());

                // Limpiar campos
                txtDni.setText("");
                txtNom.setText("");
                txtApell.setText("");
                txtContraseña.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "DNI inválido");
            }
        });

        btnVolver.addActionListener(e -> {
            VISTA.Menu inicio = new Menu(sistema);
            inicio.setVisible(true);
            dispose();
        });
    }
}
