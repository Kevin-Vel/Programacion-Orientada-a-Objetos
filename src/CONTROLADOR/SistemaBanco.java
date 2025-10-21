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
    private List<Cliente> clientes;

    public SistemaBanco() {
        this.ordenamiento = new Ordenamiento();
        this.cuentas = new ArrayList<>();
        this.empleados = new ArrayList<>();
        cargarDatos(); // Cargar datos al inicializar
    }

    //CARGAR DATOS
    private void cargarDatos() {
        cuentas = cargarLista(ARCHIVO_CUENTAS);
        empleados = cargarLista(ARCHIVO_EMPLEADOS);
        clientes = cargarLista(ARCHIVO_CLIENTES);
    }

    public Cliente crearCliente(int idclie, int dni, String nom, String apell, String contraseña) {
        Cliente cliente = new Cliente(idclie, dni, nom, apell, contraseña);
        
        ordenamiento.Implementacion(cliente);
        guardarDatos();
        return cliente;
    }

    public Cliente buscarClientePorDni(int dni) {
        for (Cliente c : ordenamiento.getListaCliente()) {
            if (c.getDni() == dni) {
                return c;
            }
        }
        return null;
    }



    public Cuenta crearCuenta(int numCuent, TipoCuenta tipoCuenta, double saldo, Cliente cliente) {
        Cuenta cuenta = new Cuenta(numCuent, tipoCuenta, saldo, cliente);
        cuentas.add(cuenta);
        guardarDatos(); // Guardar después de agregar
        return cuenta;
    }

    //Metodo para implementar lo predeterminado
    public void agregarCuenta(Cuenta cuenta) {
        if (cuenta != null) {
            cuentas.add(cuenta);
            guardarDatos();
            System.out.println("MODELO.Cuenta agregada correctamente para el cliente: " + cuenta.getCliente().getNombre());
        } else {
            System.out.println("Error: la cuenta no puede ser nula.");
        }
    }


    public int iniciarSesion(int dni) {
        return dni;
    }

    public Cliente buscarPorDni(int dni) {
        for (Cliente c : ordenamiento.getListaCliente()) {
            if (c.getDni() == dni) {
                return c;
            }
        }
        return null;
    }

    public Ordenamiento getOrdenamiento() {
        return ordenamiento;
    }

    public List<Cuenta> getCuentas() {
        return new ArrayList<>(cuentas); // Devolver copia
    }

    //Guardar Transacciones (HISTORIAL)
    public void registrarTransaccion(String descripcion) {
        try (FileWriter fw = new FileWriter("transacciones.txt", true)) {
            fw.write(descripcion + "\n");
        } catch (IOException e) {
            System.err.println("Error al registrar transacción: " + e.getMessage());
        }
    }


    public void guardarDatos() {
        // Guardar clientes
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_CLIENTES))) {
            oos.writeObject(ordenamiento.getListaCliente());
            System.out.println("Clientes guardados: " + ordenamiento.getListaCliente().size());
        } catch (IOException e) {
            System.err.println("Error al guardar clientes: " + e.getMessage());
        }

        // Guardar cuentas
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_CUENTAS))) {
            oos.writeObject(cuentas);
            System.out.println("Cuentas guardadas: " + cuentas.size());
        } catch (IOException e) {
            System.err.println("Error al guardar cuentas: " + e.getMessage());
        }
    }

    //Mejora de cargarDatos
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
}


