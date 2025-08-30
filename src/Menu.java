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
        JButton btnVerClientes= new JButton(("Ver Clientes"));

        //implementacion
        panel.add(btnCliente);
        panel.add(btnIniciar);
        panel.add(btnSalir);

        add(panel, BorderLayout.CENTER);

        //acciones
        btnCliente.addActionListener(e ->{
            VentanaCliente ventana=new VentanaCliente(sistema);
            ventana.setVisible(true);
            dispose();
        });

        btnIniciar.addActionListener(e -> {
            VentanaIniciar iniciar = new VentanaIniciar(sistema);
            iniciar.setVisible(true);
            dispose(); // para cerrar la ventana actual si quieres
        });
        btnSalir.addActionListener( e ->
                System.exit(0));
        btnVerClientes.addActionListener(e -> {
            String lista = sistema.getOrdenamiento().listaFormateada();
            JOptionPane.showMessageDialog(this, lista, "Lista de Clientes", JOptionPane.INFORMATION_MESSAGE);
        });
    }
}