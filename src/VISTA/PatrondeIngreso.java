package VISTA;

import javax.swing.*;
import javax.swing.text.*;
import java.util.regex.Pattern;

public class PatrondeIngreso {

    //Patron Numeros
    public static void soloNumeros(JTextField campo, int maxDigitos){
        ((AbstractDocument)campo.getDocument()).setDocumentFilter(new DocumentFilter(){
            private final Pattern patron = Pattern.compile("\\d*");

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException{
                if(text.matches("\\d*")&&(fb.getDocument().getLength()+text.length()-length)<= maxDigitos){
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
    }

    //Patron Letras
    public static void soloLetras(JTextField campo) {
        ((AbstractDocument) campo.getDocument()).setDocumentFilter(new DocumentFilter() {
            private final Pattern patron = Pattern.compile("[a-zA-ZáéíóúÁÉÍÓÚñÑ]*");

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                if (text != null && patron.matcher(text).matches()) {
                    if (offset == 0 && !text.isEmpty()) {
                        text = text.substring(0, 1).toUpperCase() + (text.length() > 1 ? text.substring(1) : "");
                    }
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
    }


    // Verificacion de campo Empleado
    public static void soloDNI(JTextField campo) {
        ((AbstractDocument) campo.getDocument()).setDocumentFilter(new DocumentFilter() {
            private final Pattern patron = Pattern.compile("[Ee]?\\d{0,8}");

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                String textoActual = fb.getDocument().getText(0, fb.getDocument().getLength());
                String nuevoTexto = textoActual.substring(0, offset) + text + textoActual.substring(offset + length);

                if (patron.matcher(nuevoTexto).matches()) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
    }


    //Validacion de campo Letras
    public static boolean essoloLetras(JTextField campo){
        return campo.getText().matches("[a-zA-ZAáéíóúñÑ]*");
    }

    //Verficiacion de primera letra en mayuscula
    public static String formatearNombre(String texto){
        if (texto==null || texto.isEmpty()) return texto;
        return texto.substring(0,1). toUpperCase()+
                texto.substring(1).toLowerCase();
    }

    //Verificar si es Cuenta Empleado
    public static boolean esEmpleado(String texto){
        return texto.matches("^E\\d+$");
    }

    //Verifica si Corresponde aun Cliente
    public static boolean esCliente(String texto){
        return texto.matches("^\\d{8}$");
    }
}
