import javax.swing.*;
import java.awt.*;

public class VentanaPrincipalCliente extends JFrame {
    private SistemaBanco sistema;
    private Cliente cliente;
    private Cuenta cuenta;

    public VentanaPrincipalCliente(SistemaBanco sistema, Cliente cliente) {
        this.sistema = sistema;
        this.cliente = cliente;
        this.cuenta = crearCuentaAutomatica(); // Crear cuenta automáticamente

        setTitle("Banca Digital - Cuenta de " + cliente.getNom());
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel de información del cliente
        JPanel panelInfo = new JPanel(new GridLayout(4, 2, 10, 10));
        panelInfo.setBorder(BorderFactory.createTitledBorder("Información del Cliente"));

        panelInfo.add(new JLabel("Nombre:"));
        panelInfo.add(new JLabel(cliente.getNom() + " " + cliente.getApell()));

        panelInfo.add(new JLabel("DNI:"));
        panelInfo.add(new JLabel(String.valueOf(cliente.getDni())));

        panelInfo.add(new JLabel("Número de Cuenta:"));


        panelInfo.add(new JLabel("Tipo de Cuenta:"));
        

        panelPrincipal.add(panelInfo, BorderLayout.NORTH);

        // Panel de información de la cuenta
        JPanel panelCuenta = new JPanel(new GridLayout(2, 2, 10, 10));
        panelCuenta.setBorder(BorderFactory.createTitledBorder("Información de la Cuenta"));

        panelCuenta.add(new JLabel("Saldo Actual:"));
        panelCuenta.add(new JLabel("S/ " + String.format("%.2f", cuenta.getSaldo())));

        panelCuenta.add(new JLabel("Estado:"));
        panelCuenta.add(new JLabel("Activa"));

        panelPrincipal.add(panelCuenta, BorderLayout.CENTER);

        // Panel de botones (para futuras operaciones)
        JPanel panelBotones = new JPanel(new GridLayout(2, 2, 10, 10));
        panelBotones.setBorder(BorderFactory.createTitledBorder("Operaciones"));

        JButton btnDepositar = new JButton("Depositar");
        JButton btnRetirar = new JButton("Retirar");
        JButton btnTransferir = new JButton("Transferir");
        JButton btnSalir = new JButton("Cerrar Sesión");

        panelBotones.add(btnDepositar);
        panelBotones.add(btnRetirar);
        panelBotones.add(btnTransferir);
        panelBotones.add(btnSalir);

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);

        // Eventos de los botones (por implementar)
        btnDepositar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Función de depósito por implementar");
        });

        btnRetirar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Función de retiro por implementar");
        });

        btnTransferir.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Función de transferencia por implementar");
        });

        btnSalir.addActionListener(e -> {
            // Volver al menú principal
            Menu menu = new Menu(sistema);
            menu.setVisible(true);
            dispose();
        });
    }

    private Cuenta crearCuentaAutomatica() {
        // Generar número de cuenta aleatorio
        int numCuenta = (int) (Math.random() * 1000000000);

        // Asignar tipo de cuenta automáticamente (puedes modificar esta lógica)
        String tipoCuenta = "Ahorros";
        double saldoInicial = 100.0; // Saldo inicial por defecto

        return new Cuenta(numCuenta, tipoCuenta, saldoInicial, cliente);
    }

    // Método para actualizar el saldo en la interfaz (lo usarás después)
    public void actualizarSaldo(double nuevoSaldo) {
        cuenta.setSaldo(nuevoSaldo);
        // Aquí actualizarías los componentes de la interfaz
    }
}