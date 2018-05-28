/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rectisadov2.containers;

import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Jose António
 * @param <K>
 * @param <V>
 */
public interface IContainerOperationsMap<K, V> {
     
    public boolean isEmpty();
    public int size();
    public boolean insert(K key,V obj);
    public V remove(K key);
    public List<V> getElements();
    public List<K> getKeys();
    public V getElement(K key);
    public Iterator<V> getIteradorValues();
    public Iterator<K> getIteradorKeys();    
    
}
