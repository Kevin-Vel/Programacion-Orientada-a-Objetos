public class Main {
    public static void main(String[] args) {
        SistemaBanco sistema = new SistemaBanco();
        Menu menu = new Menu(sistema);
        menu.setVisible(true);
    }
}