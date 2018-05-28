/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rectisadov2.containers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;

/**
 *
 * @author Jose
 * @param <E>
 */
public class ContainerSet<E> implements IContainerOperations<E>, 
                                        Serializable {

    private ObservableSet<E> elementos;


    public ContainerSet() {
        elementos = FXCollections.observableSet();
    }

    @Override
    public boolean isEmpty() {
        return elementos.isEmpty();
    }

    @Override
    public int size() {
        return elementos.size();
    }

    @Override
    public boolean insert(E obj) {
        return elementos.add(obj);
    }

   
    @Override
    public E remove(E obj) {
        E aux = getElement(obj);        
        return (elementos.remove(obj)) ? aux : null;
    }

    @Override
    public List<E> getElements() {
        
        List<E> elementos_aux = new ArrayList<>();

        elementos_aux.addAll(elementos);

        return elementos_aux;
    }

    @Override
    public E getElement(E obj) {      
       
        E aux;
        Iterator<E> it = getIterador();
        while (it.hasNext()) {
            aux = it.next();
            if (aux.equals(obj))
                return aux;
        }

        return null;
    }

    @Override
    public Iterator<E> getIterador() {
        return elementos.iterator();
    }
}
