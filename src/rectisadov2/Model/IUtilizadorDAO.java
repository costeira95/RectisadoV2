/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rectisadov2.model;

import java.time.LocalDate;
import rectisadov2.containers.IContainerOperations;
/**
 *
 * @author Costeira
 */
public interface IUtilizadorDAO <E , V> {  
    IContainerOperations<E> getUtilizadores();
    E getUtilizador(E utilizador);
    boolean inserirUtilizador(E utilizador);
    boolean updateUtilizador(E utilizador);
    boolean removerUtilizador(E utilizador); 
    IContainerOperations<V> getCompras(E utilizador);
    IContainerOperations<V> todasAsCompras();
    IContainerOperations<V> todasAsComprasEntreDatas(LocalDate dt1, LocalDate dt2);
    boolean adicionarCompra(E utilizador, V compra);
    boolean updateCompra(V compra);
    boolean removerCompra(V compra);
    boolean removerCompras(E utilizador);
    IContainerOperations<V> comprasDia(E utilizador, LocalDate dt1);
    IContainerOperations<V> comprasEntreDias(E utilizador, LocalDate dt1, LocalDate dt2);
    IContainerOperations<Compras> todasAsComprasNumaDatas(LocalDate dt1);
}
