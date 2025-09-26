import javax.swing.*;
import java.awt.*;


public class VentanaPrincipalCliente extends JFrame {
    private SistemaBanco sistema;
    private Cliente cliente;
    private JLabel lblSaldo;
    private JTextArea areaTransacciones;

    public VentanaPrincipalCliente(SistemaBanco sistema, Cliente cliente) {
        this.sistema = sistema;
        this.cliente = cliente;

        setTitle("Banco XYZ - Cliente: " + cliente.getNombre());
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal con BorderLayout
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));

        // Panel superior con información del cliente
        JPanel panelSuperior = new JPanel(new GridLayout(2, 1));
        JLabel lblBienvenida = new JLabel("Bienvenido, " + cliente.getNombre() + " " + cliente.getApellido());
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 16));

        lblSaldo.setFont(new Font("Arial", Font.PLAIN, 14));

        panelSuperior.add(lblBienvenida);
        panelSuperior.add(lblSaldo);
        panel.add(panelSuperior, BorderLayout.NORTH);

        // Panel central con área de transacciones
        areaTransacciones = new JTextArea();
        areaTransacciones.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaTransacciones);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Panel inferior con botones de operaciones
        JPanel panelBotones = new JPanel(new GridLayout(2, 3, 10, 10));

        JButton btnDepositar = new JButton("Depositar");
        JButton btnRetirar = new JButton("Retirar");
        JButton btnTransferir = new JButton("Transferir");
        JButton btnHistorial = new JButton("Ver Historial");
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        JButton btnActualizar = new JButton("Actualizar Saldo");

        panelBotones.add(btnDepositar);
        panelBotones.add(btnRetirar);
        panelBotones.add(btnTransferir);
        panelBotones.add(btnHistorial);
        panelBotones.add(btnCerrarSesion);
        panelBotones.add(btnActualizar);

        panel.add(panelBotones, BorderLayout.SOUTH);

        // Listeners para los botones
        // btnActualizar.addActionListener(e -> actualizarSaldo());
        // btnDepositar.addActionListener(e -> abrirVentanaDeposito());
        // btnRetirar.addActionListener(e -> abrirVentanaRetiro());
        // btnCerrarSesion.addActionListener(e -> cerrarSesion());
        //btnHistorial.addActionListener(e -> mostrarHistorial());
        // btnTransferir.addActionListener(e -> abrirVentanaTransferencia());

        // Agregar el panel al frame
        add(panel);

        // Cargar información inicial
        //actualizarSaldo();
        setVisible(true);
    }

    // Los métodos restantes (actualizarSaldo, abrirVentanaDeposito, etc.) se mantienen igual
    // ... (por brevedad, no se repiten aquí, pero en el código completo deben ir)
}