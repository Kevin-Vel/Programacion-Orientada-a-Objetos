package CONTROLADOR;

import MODELO.Cliente;
import MODELO.Cuenta;
import MODELO.Empleados;
import MODELO.TipoCuenta;
import VISTA.ConsolaContraseñas;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SistemaBanco {

    private Ordenamiento ordenamiento;
    private static final String ARCHIVO_CLIENTES = "clientes.dat";
    private static final String ARCHIVO_CUENTAS = "cuentas.dat";
    private static final String ARCHIVO_EMPLEADOS = "empleados.dat";
    private static final String ARCHIVO_EMPLEADOS_TXT = "empleados.txt";

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

        // Cargar clientes en el ordenamiento
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
        ConsolaContraseñas.guardarContraseña(dni, contraseña);
        return cliente;
    }

    public Cliente buscarClientePorDni(int dniBuscado) {
        for (Cliente c : ordenamiento.getListaCliente()) {
            if (c.getDni() == dniBuscado) {
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
            if (e.getEDni().equalsIgnoreCase(codigo)) {
                return e;
            }
        }
        return null;
    }

    public void registrarEmpleado(Empleados e) {
        empleados.add(e);
        guardarDatos();
        guardarEmpleadoTxt(e); // Guarda también en .txt
    }

    // ===========================
    //   GUARDAR EMPLEADOS
    // ===========================
    private void guardarEmpleadoTxt(Empleados empleado) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO_EMPLEADOS_TXT, true))) {
            pw.println("Código: " + empleado.getEDni() +
                    ", Nombre: " + empleado.getNombre() +
                    ", Apellido: " + empleado.getApellido() +
                    ", Cargo: " + empleado.getCargo());
            System.out.println("Empleado guardado en txt: " + empleado.getEDni());
        } catch (IOException e) {
            System.err.println("Error al guardar empleado en txt: " + e.getMessage());
        }
    }

    // ===========================
    //   REGISTRO AUTOMÁTICO EMPLEADOS
    // ===========================
    private Empleados registrarEmpleadoAutomatico(String codigo, String password) {
        try {
            // Crear empleado con datos básicos
            Empleados nuevoEmpleado = new Empleados(
                    codigo,
                    "Empleado_" + codigo,  // Nombre temporal
                    "Apellido",            // Apellido temporal
                    "Nuevo_Empleado",      // Cargo temporal
                    password
            );

            empleados.add(nuevoEmpleado);
            guardarDatos();
            guardarEmpleadoTxt(nuevoEmpleado);

            System.out.println("Empleado registrado automáticamente: " + codigo);
            return nuevoEmpleado;

        } catch (Exception e) {
            System.err.println("Error al registrar empleado automático: " + e.getMessage());
            return null;
        }
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
        }
    }

    // ===========================
    //   INICIO SESIÓN
    // ===========================
    public Object iniciarSesion(String identificador, String password) {
        // Empleado
        if (Character.isLetter(identificador.charAt(0))) {
            // Buscar empleado existente
            Empleados empleado = buscarEmpleadoPorCodigo(identificador);

            if (empleado != null) {
                // Verificar contraseña
                if (empleado.getPassword().equals(password)) {
                    return empleado;
                }
                return null; // Contraseña incorrecta
            }

            // Si no existe, registrarlo automáticamente
            return registrarEmpleadoAutomatico(identificador, password);
        }

        // Cliente
        try {
            int dni = Integer.parseInt(identificador);
            Cliente cliente = buscarClientePorDni(dni);

            if (cliente != null && cliente.getPassword().equals(password)) {
                return cliente;
            }

        } catch (NumberFormatException e) {
            return null;
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