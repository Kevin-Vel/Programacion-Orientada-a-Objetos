import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class Menu extends JFrame {
    private SistemaBanco sistema;

    public Menu() {
        sistema = new SistemaBanco();
        //Interfaz de Pantalla
        setTitle("Hola, biienvenido ala Banca Digital");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);//Centra la ventana
//logica de Opciones
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));// 3 filas y 10px

        //opciones
        JButton btnCliente = new JButton("Creaar Cliente");
        JButton btnIniciar = new JButton("Iniciar Sesion");
        JButton btnSalir = new JButton("Salir");

        //implementacion
        panel.add(btnCliente);
        panel.add(btnIniciar);
        panel.add(btnSalir);

        add(panel, BorderLayout.CENTER);

        //acciones
        btnCliente.addActionListener(e ->
                JOptionPane.showMessageDialog(this, new VentanaCliente()));

        btnIniciar.addActionListener(e ->
                JOptionPane.showMessageDialog(this,"Ventana Cuenta"));

        btnSalir.addActionListener( e ->
                System.exit(0));
    }
}
