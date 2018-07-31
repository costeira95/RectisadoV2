/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rectisadov2.model;

import rectisadov2.model.Exceptions.ClienteExistenteException;
import java.util.Iterator;
import java.util.List;
import rectisadov2.containers.ContainerList;
import rectisadov2.containers.IContainerOperations;

/**
 *
 * @author Costeira
 */
public class Gestor extends ContainerList<Cliente> {
    
    private static Gestor instance = new Gestor("clientes");
    private static Gestor instanceFornecedores = new Gestor("fornecedores");
    
    private Cliente clienteActual;
    private Cliente fornecedorActual;
    private boolean clientesCarregados;
    private boolean fornecedoresCarregados;

    private DaoImp DAO;

    public Gestor(String collection) {
        clienteActual = null;
        clientesCarregados = false;
        fornecedoresCarregados = false;
        DAO = new DaoImp(collection);
    }

    //ir buscar a instancia, criação de instancia unica
    public static Gestor getInstance() {
        return instance;
    }

    public Cliente getClienteActual() {
        return clienteActual;
    }

    public void setClienteActual(Cliente clienteActual) {
        this.clienteActual = clienteActual;
    }

    public Cliente getFornecedorActual() {
        return fornecedorActual;
    }

    public void setFornecedorActual(Cliente fornecedorActual) {
        this.fornecedorActual = fornecedorActual;
    }
    
    //adiciona clientes ao hashmap cliente
    public void addCliente(Cliente cliente) {
        if(this.getElements().contains(cliente)) throw new ClienteExistenteException(cliente);
        this.insert(cliente);
        DAO.inserirUtilizador(cliente);
    }
    
    //remove cliente do hashmap
    public boolean removerCliente() {
        this.remove(clienteActual);
        return true;
    }
    
    //obter cliente por numero
    public Cliente getClientePorNumero(Cliente cliente) {
        return this.getElement(cliente);
    }
    
    //obter todos os clientes
    public List<Cliente> todosClientes() {
        return this.getElements();
    }

    public static void setInstance(Gestor instance) {
        Gestor.instance = instance;
    }

    public DaoImp getDAO() {
        return DAO;
    }

    public static Gestor getInstanceFornecedores() {
        return instanceFornecedores;
    }

    public static void setInstanceFornecedores(Gestor instanceFornecedores) {
        Gestor.instanceFornecedores = instanceFornecedores;
    }

    public boolean isClientesCarregados() {
        return clientesCarregados;
    }

    public void setClientesCarregados(boolean clientesCarregados) {
        this.clientesCarregados = clientesCarregados;
    }

    public boolean isFornecedoresCarregados() {
        return fornecedoresCarregados;
    }

    public void setFornecedoresCarregados(boolean fornecedoresCarregados) {
        this.fornecedoresCarregados = fornecedoresCarregados;
    } 
    
    public void load() {
        IContainerOperations clientes = DAO.getUtilizadores();
        Iterator it = clientes.getIterador();
           while(it.hasNext()) {
               Cliente cliente = (Cliente) it.next();
               this.insert(cliente);
            }
               
    }
}
