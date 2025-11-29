package CONTROLADOR;

import MODELO.Cliente;
import MODELO.Cuenta;
import MODELO.Empleados;
import MODELO.TipoCuenta;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SistemaBanco {

    private Ordenamiento ordenamiento;

    private static final String ARCHIVO_CLIENTES = "clientes.dat";
    private static final String ARCHIVO_CUENTAS = "cuentas.dat";
    private static final String ARCHIVO_EMPLEADOS = "empleados.dat";

    private List<Cuenta> cuentas;
    private List<Empleados> empleados;

    public SistemaBanco() {
        this.ordenamiento = new Ordenamiento();
        this.cuentas = new ArrayList<>();
        this.empleados = new ArrayList<>();
        cargarDatos();
    }

    // ===========================
    //         CARGAR DATOS
    // ===========================
    private void cargarDatos() {
        cuentas = cargarLista(ARCHIVO_CUENTAS);
        empleados = cargarLista(ARCHIVO_EMPLEADOS);

        // Cargar clientes en el ordenamiento (árbol, lista ordenada, etc.)
        List<Cliente> listaCargada = cargarLista(ARCHIVO_CLIENTES);
        for (Cliente c : listaCargada)
            ordenamiento.Implementacion(c);
    }

    @SuppressWarnings("unchecked")
    private <T> List<T> cargarLista(String archivo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return (List<T>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado: " + archivo);
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // ===========================
    //        GESTIÓN CLIENTES
    // ===========================
    public Cliente crearCliente(int idclie, int dni, String nom, String apell, String contraseña) {
        Cliente cliente = new Cliente(idclie, dni, nom, apell, contraseña);
        ordenamiento.Implementacion(cliente);
        guardarDatos();
        return cliente;
    }

    public Cliente buscarClientePorDni(String dniBuscado) {
        for (Cliente c : ordenamiento.getListaCliente()) {
            if (c.getDni().equals(dniBuscado)) {
                return c;
            }
        }
        return null;
    }

    // ===========================
    //        GESTIÓN EMPLEADOS
    // ===========================
    public Empleados buscarEmpleadoPorCodigo(String codigo) {
        for (Empleados e : empleados) {
            if (e.getEDni().equalsIgnoreCase(codigo)) { // ahora usa EDni
                return e;
            }
        }
        return null;
    }

    public void registrarEmpleado(Empleados e) {
        empleados.add(e);
        guardarDatos();
    }

    // ===========================
    //         GESTIÓN CUENTAS
    // ===========================
    public Cuenta crearCuenta(int numCuent, TipoCuenta tipoCuenta, double saldo, Cliente cliente) {
        Cuenta cuenta = new Cuenta(numCuent, tipoCuenta, saldo, cliente);
        cuentas.add(cuenta);
        guardarDatos();
        return cuenta;
    }

    public void agregarCuenta(Cuenta cuenta) {
        if (cuenta != null) {
            cuentas.add(cuenta);
            guardarDatos();
            System.out.println("Cuenta agregada para cliente: " + cuenta.getCliente().getNombre());
        } else {
            System.out.println("Error: cuenta nula.");
        }
    }

    // ===========================
    //   INICIO SESIÓN (BÁSICO)
    // ===========================
    public Object iniciarSesion(String identificador, String password) {

        // Si empieza con letra -> Empleado
        if (Character.isLetter(identificador.charAt(0))) {
            for (Empleados e : empleados) {
                if (e.getEDni().equalsIgnoreCase(identificador)
                        && e.getPassword().equals(password)) {
                    return e;
                }
            }
            return null;
        }

        // Caso contrario -> Cliente
        for (Cliente c : ordenamiento.getListaCliente()) {
            if (c.getDni().equals(identificador)
                    && c.getPassword().equals(password)) {
                return c;
            }
        }
        return null;
    }

    // ===========================
    //      GUARDAR DATOS
    // ===========================
    public void guardarDatos() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_CLIENTES))) {
            oos.writeObject(ordenamiento.getListaCliente());
        } catch (IOException e) {
            System.err.println("Error al guardar clientes: " + e.getMessage());
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_CUENTAS))) {
            oos.writeObject(cuentas);
        } catch (IOException e) {
            System.err.println("Error al guardar cuentas: " + e.getMessage());
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_EMPLEADOS))) {
            oos.writeObject(empleados);
        } catch (IOException e) {
            System.err.println("Error al guardar empleados: " + e.getMessage());
        }
    }

    // ===========================
    //  REGISTRO TRANSACCIONES
    // ===========================
    public void registrarTransaccion(String descripcion) {
        try (FileWriter fw = new FileWriter("transacciones.txt", true)) {
            fw.write(descripcion + "\n");
        } catch (IOException e) {
            System.err.println("Error transacción: " + e.getMessage());
        }
    }

    // ===========================
    //          GETTERS
    // ===========================
    public Ordenamiento getOrdenamiento() { return ordenamiento; }
    public List<Cuenta> getCuentas() { return new ArrayList<>(cuentas); }
    public List<Empleados> getEmpleados() { return new ArrayList<>(empleados); }
}

