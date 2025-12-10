package CONTROLADOR;

import MODELO.*;
import VISTA.ConsolaContraseñas;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class SistemaBanco {

    // 🔹 CAMBIO: Ahora es genérico y específico para Clientes
    private Ordenamiento<Cliente> ordenamientoClientes;
    private Ordenamiento<Empleados> ordenamientoEmpleados;

    private static final String ARCHIVO_CLIENTES = "clientes.dat";
    private static final String ARCHIVO_CUENTAS = "cuentas.dat";
    private static final String ARCHIVO_EMPLEADOS = "empleados.dat";
    private static final String ARCHIVO_EMPLEADOS_TXT = "empleados.txt";

    private List<Cuenta> cuentas;
    private List<Empleados> empleados;

    public SistemaBanco() {
        this.ordenamientoClientes = new Ordenamiento<>();
        this.ordenamientoEmpleados = new Ordenamiento<>();
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
            ordenamientoClientes.agregar(c);

        // Cargar empleados en su ordenamiento
        for (Empleados e : empleados)
            ordenamientoEmpleados.agregar(e);
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
        ordenamientoClientes.agregar(cliente);
        guardarDatos();
        ConsolaContraseñas.guardarContraseña(dni, contraseña);
        return cliente;
    }

    public Cliente buscarClientePorDni(int dniBuscado) {
        // 🔹 LAMBDA 1: Uso de stream y Optional
        Optional<Cliente> clienteOpt = ordenamientoClientes.getLista().stream()
                .filter(c -> c.getDni() == dniBuscado)
                .findFirst();

        return clienteOpt.orElse(null);
    }

    // 🔹 MÉTODOS CON LAMBDAS

    // LAMBDA 2: Filtrar clientes por apellido
    public List<Cliente> buscarClientesPorApellido(String apellido) {
        return ordenamientoClientes.filtrar(c ->
                c.getApellido().equalsIgnoreCase(apellido)
        );
    }

    // LAMBDA 3: Contar clientes con DNI mayor a
    public long contarClientesConDniMayorA(int dniMinimo) {
        return ordenamientoClientes.contar(c -> c.getDni() > dniMinimo);
    }

    // LAMBDA 4: Obtener nombres únicos de clientes
    public List<String> getNombresUnicosClientes() {
        return ordenamientoClientes.getLista().stream()
                .map(Cliente::getNombre)
                .distinct()
                .collect(Collectors.toList());
    }

    // LAMBDA 5: Clientes ordenados por DNI descendente
    public List<Cliente> getClientesOrdenadosPorDniDesc() {
        return ordenamientoClientes.getLista().stream()
                .sorted((c1, c2) -> Integer.compare(c2.getDni(), c1.getDni()))
                .collect(Collectors.toList());
    }

    // ===========================
    //        GESTIÓN EMPLEADOS
    // ===========================
    public Empleados buscarEmpleadoPorCodigo(String codigo) {
        // 🔹 LAMBDA 6: Búsqueda con stream
        Optional<Empleados> empleadoOpt = empleados.stream()
                .filter(e -> e.getEDni().equalsIgnoreCase(codigo))
                .findFirst();

        return empleadoOpt.orElse(null);
    }

    public void registrarEmpleado(Empleados e) {
        empleados.add(e);
        ordenamientoEmpleados.agregar(e);
        guardarDatos();
        guardarEmpleadoTxt(e);
    }

    // 🔹 LAMBDA 7: Filtrar empleados por cargo
    public List<Empleados> buscarEmpleadosPorCargo(String cargo) {
        return ordenamientoEmpleados.filtrar(e ->
                e.getCargo().equalsIgnoreCase(cargo)
        );
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
            // 🔹 Constructor corregido: (nombre, apellido, eDni, password, cargo)
            Empleados nuevoEmpleado = new Empleados(
                    "Empleado_" + codigo,
                    "Apellido",
                    codigo,
                    password,
                    "Nuevo_Empleado"
            );

            registrarEmpleado(nuevoEmpleado);
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

    // 🔹 LAMBDA 8: Filtrar cuentas por saldo mínimo
    public List<Cuenta> getCuentasConSaldoMayorA(double saldoMinimo) {
        return cuentas.stream()
                .filter(c -> c.getSaldo() > saldoMinimo)
                .collect(Collectors.toList());
    }

    // ===========================
    //   INICIO SESIÓN
    // ===========================
    public Persona iniciarSesion(String identificador, String password) {
        // Empleado
        if (Character.isLetter(identificador.charAt(0))) {
            // Buscar empleado existente
            Empleados empleado = buscarEmpleadoPorCodigo(identificador);

            if (empleado != null) {
                // Verificar contraseña
                if (empleado.autenticar(identificador, password)) {
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

            if (cliente != null && cliente.autenticar(identificador, password)) {
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
            oos.writeObject(ordenamientoClientes.getLista());
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

    // 🔹 Para compatibilidad con código existente que usa getOrdenamiento()
    public Ordenamiento<Cliente> getOrdenamiento() {
        return ordenamientoClientes;
    }

    // 🔹 Getters específicos
    public List<Cliente> getListaClientes() {
        return ordenamientoClientes.getLista();
    }

    public List<Cuenta> getCuentas() {
        return new ArrayList<>(cuentas);
    }

    public List<Empleados> getEmpleados() {
        return new ArrayList<>(empleados);
    }

    // 🔹 Método para usar ordenamiento externo (opcional)
    public String getClientesOrdenadosExternamente(String criterio) {
        return ordenamientoClientes.listaFormateadaOrdenada(criterio);
    }

    // 🔹 Método para activar/desactivar ordenamiento externo
    public void configurarOrdenamientoExterno(boolean activar) {
        ordenamientoClientes.activarOrdenamientoExterno(activar);
        ordenamientoEmpleados.activarOrdenamientoExterno(activar);
    }
}