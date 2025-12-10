package VISTA;

import CONTROLADOR.SistemaBanco;

import javax.swing.*;
import java.awt.*;

public class Menu extends JFrame {

    private SistemaBanco sistema;

    public Menu(SistemaBanco sistema) {
        this.sistema = sistema; // ✔ CORREGIDO

        //Interfaz de Pantalla
        setTitle("Hola, bienvenido a la Banca Digital");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10)); // ahora 4 filas porque tienes 4 botones

        // Botones
        JButton btnCliente = new JButton("Crear Cliente");
        JButton btnIniciar = new JButton("Iniciar Sesión");
        JButton btnVerClientes = new JButton("Ver Clientes");
        JButton btnSalir = new JButton("Salir");

        //Agregar botones al panel
        panel.add(btnCliente);
        panel.add(btnIniciar);
        panel.add(btnVerClientes);
        panel.add(btnSalir);

        add(panel, BorderLayout.CENTER);

        // acciones
        btnCliente.addActionListener(e -> {
            VentanaCliente ventana = new VentanaCliente(this.sistema);
            ventana.setVisible(true);
            dispose();
        });

        btnIniciar.addActionListener(e -> {
            VentanaIniciar iniciar = new VentanaIniciar(this.sistema);
            iniciar.setVisible(true);
            dispose();
        });

        btnVerClientes.addActionListener(e -> {
            // ✔ Ahora se pasa criterio: nombre / apellido / dni
            String lista = this.sistema.getOrdenamiento().listaFormateadaOrdenada("nombre");
            JOptionPane.showMessageDialog(this, lista, "Lista de Clientes", JOptionPane.INFORMATION_MESSAGE);
        });

        btnSalir.addActionListener(e -> System.exit(0));
    }
}
