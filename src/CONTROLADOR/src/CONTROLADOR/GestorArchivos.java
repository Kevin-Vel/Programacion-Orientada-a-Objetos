package CONTROLADOR;

import MODELO.Cliente;
import MODELO.Cuenta;

import java.io.*;
import java.util.List;

public class GestorArchivos {

    private static final String ARCHIVO_CLIENTES = "clientes.dat";
    private static final String ARCHIVO_CUENTAS = "cuentas.dat";

    public static void guardarDatos(List<Cliente> clientes, List<Cuenta> cuentas) {
        try (ObjectOutputStream outClientes = new ObjectOutputStream(new FileOutputStream(ARCHIVO_CLIENTES));
             ObjectOutputStream outCuentas = new ObjectOutputStream(new FileOutputStream(ARCHIVO_CUENTAS))) {

            outClientes.writeObject(clientes);
            outCuentas.writeObject(cuentas);
            System.out.println("Datos guardados correctamente.");

        } catch (IOException e) {
            System.err.println("Error al guardar los datos: " + e.getMessage());
        }
    }

    public static void cargarDatos(List<Cliente> clientes, List<Cuenta> cuentas) {
        try (ObjectInputStream inClientes = new ObjectInputStream(new FileInputStream(ARCHIVO_CLIENTES));
             ObjectInputStream inCuentas = new ObjectInputStream(new FileInputStream(ARCHIVO_CUENTAS))) {

            clientes.addAll((List<Cliente>) inClientes.readObject());
            cuentas.addAll((List<Cuenta>) inCuentas.readObject());

            System.out.println("Datos cargados correctamente.");

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar los datos: " + e.getMessage());
        }
    }
}

