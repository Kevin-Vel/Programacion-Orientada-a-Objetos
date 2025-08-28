import javax.swing.*;
import java.awt.event.*;

public class VentanaCliente extends JFrame {
    private JTextField txtDni, txtNom, txtApell;
    private JButton btnCrear;

    public VentanaCliente() {
        setTitle("Crear Cliente");
        setSize(300, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel lblDni = new JLabel("DNI:");
        lblDni.setBounds(20, 20, 100, 25);
        add(lblDni);

        txtDni = new JTextField();
        txtDni.setBounds(120, 20, 120, 25);
        add(txtDni);

        JLabel lblNom = new JLabel("Nombre:");
        lblNom.setBounds(20, 60, 100, 25);
        add(lblNom);

        txtNom = new JTextField();
        txtNom.setBounds(120, 60, 120, 25);
        add(txtNom);

        JLabel lblApell = new JLabel("Apellido:");
        lblApell.setBounds(20, 100, 100, 25);
        add(lblApell);

        txtApell = new JTextField();
        txtApell.setBounds(120, 100, 120, 25);
        add(txtApell);

        btnCrear = new JButton("Crear Cliente");
        btnCrear.setBounds(80, 140, 140, 30);
        add(btnCrear);

        // Evento del botón
        btnCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int idcli =  (int) (Math.random()*1000);
                    int dni = Integer.parseInt(txtDni.getText());
                    String nom = txtNom.getText();
                    String apell = txtApell.getText();

                    Cliente nuevoCliente = new Cliente(idcli, dni, nom, apell);

                    JOptionPane.showMessageDialog(null,
                            "Cliente creado:\n" + nuevoCliente.toString());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "DNI inválido");
                }
            }
        });

        setVisible(true);
    }
}
