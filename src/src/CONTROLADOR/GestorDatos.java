package CONTROLADOR;

import java.util.ArrayList;
import java.util.List;

public class GestorDatos <T>{
    private List<T> lista;

    public GestorDatos(){
        lista=new ArrayList<>();
    }

    public void agregar (T elemento){
        lista.add(elemento);
    }

    public List<T> getLista(){
        return lista;
    }

    public T buscar (java.util.function.Predicate<T> criterio){
        for (T elemento : lista){
            if (criterio.test(elemento)){
                return elemento;
            }
        }
        return null;
    }

    public  void eliminar (T elemento){
        lista.remove(elemento);
    }
}
