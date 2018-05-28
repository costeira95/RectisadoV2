/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rectisadov2.containers;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Jose
 * @param <E>
 */
public class ContainerList<E> implements IContainerOperations<E>, 
                                              Serializable {

    private ObservableList<E> elementos;

    public ContainerList() {
        elementos = FXCollections.observableArrayList();
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

        if (elementos.contains(obj)) {
            return false;
        }

        this.elementos.add(obj);
        return true;
    }


    @Override
    public E remove(E obj) {

        E object_temp = getElement(obj);
        
        if (object_temp!=null)
            this.elementos.remove(obj);

        return object_temp;
    }

    @Override
    public List<E> getElements() {
        return this.elementos;
    }

    @Override
    public E getElement(E obj) {

        E obj_temp=null;        

        int idx=elementos.indexOf(obj);
        if (idx!=-1)
            obj_temp=elementos.get(idx);        
        
        return obj_temp;
    }

    @Override
    public Iterator<E> getIterador() {
        return elementos.iterator();
    }
}
