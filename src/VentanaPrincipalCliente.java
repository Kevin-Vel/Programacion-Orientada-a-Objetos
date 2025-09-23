import javax.swing.*;
import java.awt.*;

public class VentanaPrincipalCliente extends JFrame {
    private SistemaBanco sistema;
    private Cliente cliente;
    private JLabel lblSaldo;
    private JTextArea areaTransacciones;

    public VentanaPrincipalCliente(SistemaBanco sistema, Cliente cliente) {
        // ... (código anterior de la Parte 1)

        // Panel central con área de transacciones
        JScrollPane panelCentral = crearPanelCentral();
        add(panelCentral, BorderLayout.CENTER);

        setVisible(true);
    }

    // ... (método crearPanelSuperior de la Parte 1)

    private JScrollPane crearPanelCentral() {
        areaTransacciones = new JTextArea();
        areaTransacciones.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaTransacciones);
        return scrollPane;
    }
}