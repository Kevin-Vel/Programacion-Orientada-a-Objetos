import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;

public class Menu {
    public static void main(String [] args){

        JFrame frame= new JFrame("Banca movil");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,200);
        frame.setVisible(true);

        while (true){
            String opcion=JOptionPane.showInputDialog(
                    frame,
                    "Seleccione una opcion:\n" +
                            "1. Crear cuenta\n"+
                            "2.Inciar sesion\n"+
                            "3.Salir"
            );
            if(opcion==null) break;
            switch (opcion){
                case "1":
                    JOptionPane.showMessageDialog(frame,"Opcion: Crear Cliente");
                    break;
            }
        }
    }
}
