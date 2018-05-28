/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rectisadov2.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.UUID;

/**
 *
 * @author Costeira
 */
public class Compras {
    
    private static int nrCompras = 0;
    private String id;
    private int numeroCompras;
    private LocalDate data;
    private String descricao;
    private int requesicao;
    private double valor;
    private ECredito tipoCredito;

    public Compras(String descricao, double valor, int requesicao, ECredito tipoCredito) {
        this.id = UUID.randomUUID().toString();
        LocalDate orderDate = LocalDate.now();
        String fDate = orderDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
        this.data = LocalDate.parse(fDate);
        this.descricao = descricao;
        this.valor = valor;
        this.requesicao = requesicao;
        this.tipoCredito = tipoCredito;
    }
    
    public Compras(String descricao, double valor, int requesicao, ECredito tipoCredito, LocalDate data) {
        this.id = UUID.randomUUID().toString();
        LocalDate orderDate = data;
        String fDate = orderDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
        this.data = LocalDate.parse(fDate);
        this.descricao = descricao;
        this.valor = valor;
        this.requesicao = requesicao;
        this.tipoCredito = tipoCredito;
    }
    
    public Compras(String id, String descricao, double valor, int requesicao, ECredito tipoCredito, LocalDate data) {
        this.id = id;
        LocalDate orderDate = data;
        String fDate = orderDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
        this.data = LocalDate.parse(fDate);
        this.descricao = descricao;
        this.valor = valor;
        this.requesicao = requesicao;
        this.tipoCredito = tipoCredito;
    }

    public Compras() {
    }
    
    public int getRequesicao() {
        return requesicao;
    }

    public void setRequesicao(int requesicao) {
        this.requesicao = requesicao;
    }
    
    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public ECredito getTipoCredito() {
        return tipoCredito;
    }

    public void setTipoCredito(ECredito tipoCredito) {
        this.tipoCredito = tipoCredito;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    
    public void editarCompra(String descricao, double valor, int requesicao, ECredito tipoCredito, LocalDate data, String tipoCliente) {
        this.descricao = descricao;
        this.valor = valor;
        this.requesicao = requesicao;
        this.tipoCredito = tipoCredito;
        this.data = data;
        if(tipoCliente == "cliente")
            Gestor.getInstance().getDAO().updateCompra(this);
        else
            Gestor.getInstanceFornecedores().getDAO().updateCompra(this);
    }
    
    public void remover(String tipoUtilizador) {
        if(tipoUtilizador == "clientes")
            Gestor.getInstance().getDAO().removerCompra(this);
        else
            Gestor.getInstanceFornecedores().getDAO().removerCompra(this);
    }
    
    public static Comparator<Compras> comparator = (new Comparator<Compras>() {
        @Override
        public int compare(Compras o1, Compras o2) {
            return (o2.getData().compareTo(o1.getData()));
        }       
    });

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + this.numeroCompras;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this != obj)  return false;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;     
        final Compras other = (Compras) obj;
        
        return (this.id == other.id);
    }

    @Override
    public String toString() {
        return "Compras{" + "data=" + data + ", descricao=" + descricao + ", valor=" + valor + ", tipoCredito=" + tipoCredito + '}';
    }

}
