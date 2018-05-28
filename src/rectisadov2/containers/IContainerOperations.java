/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rectisadov2.containers;

import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Jose
 * @param <E>
 */
public interface IContainerOperations<E> {
    
    public boolean isEmpty();
    public int size();
    public boolean insert(E obj);
    public E remove(E obj);
    public List<E> getElements();    
    public E getElement(E obj);
    public Iterator<E> getIterador();
    
}
