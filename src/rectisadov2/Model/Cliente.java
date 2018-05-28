/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rectisadov2.model;

import rectisadov2.model.Exceptions.NomeVazioException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import rectisadov2.containers.ContainerList;
import rectisadov2.containers.IContainerOperations;
import rectisadov2.model.Exceptions.DescricaoVazioException;

/**
 *
 * @author Costeira
 */
public class Cliente {
    private String id;
    private String nome;
    private String morada;
    private String codPost;
    private int numeroContribuinte;
    private boolean carregado;
    
    private IContainerOperations<Compras> compras;
    
    public Cliente(String nome, String morada, String codPost, int numeroContribuinte) {
        if(nome.isEmpty()) throw new NomeVazioException();
        this.nome = nome;
        this.morada = morada;
        this.codPost = codPost;
        this.numeroContribuinte = numeroContribuinte;
        this.carregado = false;
        this.compras = new ContainerList<>();
    }
    
    public Cliente(String id, String nome, String morada, String codPost, int numeroContribuinte) {
        if(nome.isEmpty()) throw new NomeVazioException();
        this.id = id;
        this.nome = nome;
        this.morada = morada;
        this.codPost = codPost;
        this.numeroContribuinte = numeroContribuinte;
        this.carregado = false;
        this.compras = new ContainerList<>();
    }

    public Cliente() {
    }
    
    public Cliente(String id, String nome) {
        this.id = id;
        this.nome = nome;
        this.carregado = false;
        this.compras = new ContainerList<>();
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getCodPost() {
        return codPost;
    }

    public void setCodPost(String codPost) {
        this.codPost = codPost;
    }

    public int getNumeroContribuinte() {
        return numeroContribuinte;
    }

    public void setNumeroContribuinte(int numeroContribuinte) {
        this.numeroContribuinte = numeroContribuinte;
    }

    public void editarCliente(String nome, String morada, String codPost, int numeroContribuinte) {
        if(nome.isEmpty()) throw new NomeVazioException();
        this.nome = nome;
        this.morada = morada;
        this.codPost = codPost;
        this.numeroContribuinte = numeroContribuinte;
    }
    
    //Todas as compras feitas pelo cliente
    public List<Compras> getListaCompras(String tipoCliente) {
        if (!carregado) loadCompras(tipoCliente);
        return this.compras.getElements();
    }
    
    //Todas as compras feitas pelo cliente
    public Double SaldoCliente() {
        double saldo = 0;
        List<Compras> compra = compras.getElements();
        for(Compras c : compra) {
            if(c.getTipoCredito().equals(ECredito.CREDITO))
                saldo += c.getValor();
            else
                saldo -=c.getValor();
        }
        return saldo;
    }
    
    //Compras feitas num determinado dia
    public List<Compras> getListaComprasDia(LocalDate dia, String tipoUtilizador) {
        IContainerOperations<Compras> compraAux;
        if(tipoUtilizador == "clientes")
            compraAux = Gestor.getInstance().getDAO().comprasDia(this, dia);
        else
            compraAux = Gestor.getInstanceFornecedores().getDAO().comprasDia(this, dia);
        return compraAux.getElements();
    }
    
    //entre datas
    public List<Compras> getListaComprasEntreDatas(LocalDate diaInicio, LocalDate diaFim, String tipoUtilizador) {
        IContainerOperations comprasAux;
        if(tipoUtilizador =="clientes")
            comprasAux = Gestor.getInstance().getDAO().comprasEntreDias(this, diaInicio, diaFim);
        else
            comprasAux = Gestor.getInstanceFornecedores().getDAO().comprasEntreDias(this, diaInicio, diaFim);
        return comprasAux.getElements();
    }
    
    //Remover todas as compras
    public void removerTodasCompras(String tipoUtilizador) {
        if(tipoUtilizador == "clientes")
            Gestor.getInstance().getDAO().removerCompras(this);
        else
            Gestor.getInstanceFornecedores().getDAO().removerCompras(this);
        this.compras.getElements().clear();
    }
    
    //Remover todas as compras
    public void removerUmaCompra(Compras compra, String tipoUtilizador) {
        compra.remover(tipoUtilizador);
        this.compras.remove(compra);
    }
 
    //adiciona uma compra ao hasmap compras
    public void adicionarCompra(Compras compra, String tipoUtilizador) {
        if(compra.getDescricao().isEmpty()) throw new DescricaoVazioException();
        this.compras.insert(compra);
        if(tipoUtilizador == "cliente")
            Gestor.getInstance().getDAO().adicionarCompra(this, compra);
        else
            Gestor.getInstanceFornecedores().getDAO().adicionarCompra(this, compra);
    }
    
    
    public boolean validarCodigoPostal(String codigoPostal) {
        return codigoPostal.matches("\\d{4}\\-\\d{3}");
    }
    
    public boolean validarContribuinte(int contribuinte) {
        String contibuinteString = Integer.toString(contribuinte);
        return contibuinteString.matches("\\d{9}");
    }
    
    private void loadCompras(String tipoCliente) {
        if(tipoCliente == "fornecedores") {
            System.out.println("sadas");
            this.compras = Gestor.getInstanceFornecedores().getDAO().getCompras(this);
        }
        else
            this.compras = Gestor.getInstance().getDAO().getCompras(this);
        this.carregado = true;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public static Comparator<Cliente> comparator = (new Comparator<Cliente>() {
        @Override
        public int compare(Cliente o1, Cliente o2) {
            return (o1.getNome().compareToIgnoreCase(o2.getNome()));
        }       
    });
    
    @Override
    public int hashCode() {
        return this.getId().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(this != obj) return false;
        if(obj == null) return false;
        if(obj.getClass() != this.getClass()) return false;
        
        final Cliente other = (Cliente) obj;
        return (other.getId().equals(this.getId()));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id).append("\n");
        sb.append("Nome: ").append(nome).append("\n");
        sb.append("Morada: ").append(morada).append("\n");
        sb.append("Codigo Postal: ").append(codPost).append("\n");
        sb.append("Contribuinte: ").append(numeroContribuinte).append("\n");
        return sb.toString(); //To change body of generated methods, choose Tools | Templates.
    }
    
}
