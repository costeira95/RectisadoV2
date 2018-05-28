/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rectisadov2.containers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

/**
 *
 * @author Jose António
 * @param <K>
 * @param <V>
 */
public class ContainerMap<K, V> implements IContainerOperationsMap<K, V> {
    
    private ObservableMap<K, V> elementos;

    public ContainerMap() {
        elementos = FXCollections.observableHashMap();
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
    public boolean insert(K key, V obj) {
        if (elementos.containsKey(key))
            return false;
        
        elementos.put(key, obj);
        return true;        
    }

    @Override
    public V remove(K key) {
        return elementos.remove(key);
    }

    @Override
    public List<V> getElements() {
        List<V> values = new ArrayList<>();
        values.addAll(elementos.values());
        return values;
    }

    @Override
    public List<K> getKeys() {
        List<K> keys = new ArrayList<>();
        keys.addAll(elementos.keySet());
        return keys;
    }

    @Override
    public V getElement(K key) {
        return elementos.get(key);
    }

    @Override
    public Iterator<V> getIteradorValues() {
        return elementos.values().iterator();
    }

    @Override
    public Iterator<K> getIteradorKeys() {
        return elementos.keySet().iterator();
    }
    
}
