package CONTROLADOR;

import MODELO.Cliente;
import MODELO.Cuenta;
import MODELO.TipoCuenta;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SistemaBanco {
    private Ordenamiento ordenamiento;
    private static final String ARCHIVO_CLIENTES = "clientes.dat";
    private static final String ARCHIVO_CUENTAS = "cuentas.dat";
    private List<Cuenta> cuentas;

    public SistemaBanco() {
        this.ordenamiento = new Ordenamiento();
        this.cuentas = new ArrayList<>();
        cargarDatos(); // Cargar datos al inicializar
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

    // Métodos para persistencia de datos
    @SuppressWarnings("unchecked")
    private void cargarDatos() {
        // Cargar clientes
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO_CLIENTES))) {
            List<Cliente> clientesCargados = (List<Cliente>) ois.readObject();
            for (Cliente cliente : clientesCargados) {
                ordenamiento.Implementacion(cliente);
            }
            System.out.println("Clientes cargados: " + clientesCargados.size());
        } catch (FileNotFoundException e) {
            System.out.println("Archivo de clientes no encontrado. Se creará uno nuevo.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar clientes: " + e.getMessage());
        }

        // Cargar cuentas
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO_CUENTAS))) {
            cuentas = (List<Cuenta>) ois.readObject();
            System.out.println("Cuentas cargadas: " + cuentas.size());
        } catch (FileNotFoundException e) {
            System.out.println("Archivo de cuentas no encontrado. Se creará uno nuevo.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar cuentas: " + e.getMessage());
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
    }}
