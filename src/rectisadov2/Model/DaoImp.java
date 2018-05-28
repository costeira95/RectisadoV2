/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rectisadov2.model;

import com.mongodb.BasicDBObject;
import org.bson.Document;
import rectisadov2.containers.ContainerList;
import rectisadov2.containers.IContainerOperations;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.model.Projections;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author Costeira
 */
public class DaoImp implements IUtilizadorDAO<Cliente, Compras>{

    private final DataSource dataSource;
    
    public DaoImp(String collection) {
        this.dataSource = new DataSource("127.0.0.1", 27017, "rectisado");
        this.dataSource.setCollection(collection);
    }
    
    /***************************************************
     * 
     * Método para ir buscar todos os utilizadores
     * á base de dados
     * @return clientes
     * 
     **************************************************/
    
    @Override
    public IContainerOperations<Cliente> getUtilizadores() {
          IContainerOperations<Cliente> clientes = new ContainerList<>();
          
          List<Document> document = (List<Document>) dataSource.getCollection()
                  .find()
                  .sort(new BasicDBObject("nome", 1))
                  .into(new ArrayList<>());
          
          for(Document d : document) {
              Cliente cliente = new Cliente(d.get("_id").toString(), d.get("nome").toString(), d.getString("morada").toString(), 
                      d.getString("codPost").toString(),Integer.parseInt(d.get("contribuinte").toString()));
              clientes.insert(cliente);
          }
          
          return clientes;
    }
    
    /***************************************************
     * Método que retorna um utilizador
     * 
     * @param utilizador
     * @return Cliente
     **************************************************/

    @Override
    public Cliente getUtilizador(Cliente utilizador) {
        Document document = (Document) dataSource.getCollection()
                  .find(eq("_id", new ObjectId(utilizador.getId())))
                  .projection(Projections.fields(Projections.include("nome"))).first();
        
        Cliente cliente = new Cliente(document.getString("_id"), document.getString("nome"),
        document.getString("morada"), document.getString("codPost"), document.getInteger("contribuinte"));
        return cliente;
    }
    
    /**********************************************************
     * 
     * Método para inserir um utilizador na base de dados
     * @param utilizador
     * @return boolean
     *********************************************************/

    @Override
    public boolean inserirUtilizador(Cliente utilizador) {
        Document cliente = new Document();
        cliente.put("_id", new ObjectId(utilizador.getId()));
        cliente.put("nome", utilizador.getNome());
        cliente.put("morada", utilizador.getMorada());
        cliente.put("contribuinte", utilizador.getNumeroContribuinte());
        cliente.put("codPost", utilizador.getCodPost());
        cliente.put("compras", new ArrayList<>());
        dataSource.getCollection().insertOne(cliente);
        return true;
    }
    
    /************************************************************
     * 
     * Método para actualizar um utilizador, dizendo se foi
     * ou não bem sucedido a inseração
     * @param utilizador
     * @return boolean
     ***********************************************************/

    @Override
    public boolean updateUtilizador(Cliente utilizador) {
        Document find = findUser(utilizador);
        BasicDBObject update = new BasicDBObject();
        update.append("$set", 
                new BasicDBObject().append("nome", utilizador.getNome())
                .append("morada", utilizador.getMorada())
                .append("contribuinte", utilizador.getNumeroContribuinte())
                .append("codPost", utilizador.getCodPost())
        );
        dataSource.getCollection().updateOne(find, update);
        return true;
    }
    
    /***************************************************************
     * Método para remover um utilizador da base de dados
     * @param utilizador
     * @return boolean
     **************************************************************/

    @Override
    public boolean removerUtilizador(Cliente utilizador) {
        Document find = findUser(utilizador);        
        dataSource.getCollection().deleteOne(find);
        return true;
    }  
     /**********************************************************
      * Método para buscar todas as compras de um utilizador
      * 
      * @param utilizador
      * @return IContainerOperations
      ********************************************************/
    @Override
    public IContainerOperations<Compras> getCompras(Cliente utilizador) {
         IContainerOperations<Compras> compras = new ContainerList<>();
         Document find = findUser(utilizador);
         
          Document document = (Document) dataSource.getCollection()
                  .find(find).first();
          List<Document> comprasDB = (List<Document>) document.get("compras");
          for(Document c : comprasDB)
              compras.insert(new Compras(
                      c.getString("_id"),
                      c.getString("descricao"), c.getDouble("valor"), 
                      c.getInteger("requesicao"), ECredito.valueOf(c.getString("tipoCredito")), 
                      LocalDate.parse(c.getString("data"))));
          
          return compras;
    }
    
    /*****************************************************************
     * Procura um determinado utilizador
     * @param utilizador
     * @return Document
     */
    
    private Document findUser(Cliente utilizador) {
        return (Document) dataSource.getCollection()
                  .find(eq("_id", new ObjectId(utilizador.getId()))).first();
    }

    /*************************************************************************
     * Vai buscar todas as compras de todos os utilizadores 
     * @return IContainerOperations
     ************************************************************************/
    @Override
    public IContainerOperations<Compras> todasAsCompras() {
          List<Document> todasAsCompras = (List<Document>) dataSource.getCollection()
                  .find()
                  .projection(Projections.fields(Projections.include("compras"), Projections.excludeId()))
                  .into(new ArrayList<>());
        return cicloCompras(todasAsCompras);
    }
    
    /******************************************************************
     * Adiciona uma compra a um determinado utilizador
     * @param utilizador
     * @param compra
     * @return boolean
     ******************************************************************/

    @Override
    public boolean adicionarCompra(Cliente utilizador, Compras compra) {
        Document user = findUser(utilizador);
        BasicDBObject update = new BasicDBObject();
        BasicDBObject c = new BasicDBObject();
        BasicDBObject novaCompra = new BasicDBObject();
        novaCompra.append("_id", compra.getId());
        novaCompra.append("descricao", compra.getDescricao());
        novaCompra.append("valor", compra.getValor());
        novaCompra.append("requesicao", compra.getRequesicao());
        novaCompra.append("tipoCredito", compra.getTipoCredito().toString());
        novaCompra.append("data", compra.getData().toString());
        c.append("compras", novaCompra);
        update.append("$push", c);
        dataSource.getCollection().updateOne(user, update);
        return true;
    }
    
    /***********************************************************
     * Remove uma compra de um determinado utilizador
     * @param Compras
     * @return 
     **********************************************************/

    @Override
    public boolean removerCompra(Compras compra) {
        BasicDBObject match = new BasicDBObject();
        match.append("compras._id", compra.getId());
        Document d = new Document().append("$pull", 
                        new Document().append("compras", 
                               new Document("_id", compra.getId())
                        )
                );
        dataSource.getCollection().updateOne(match, 
                new Document().append("$pull", 
                        new Document().append("compras", 
                               new Document("_id", compra.getId())
                        )
                )
        );
        return true;
    }
    
    /****************************************************************
     * Retorna uma lista de compras feita por um cliente
     * num determinado dia
     * @param utilizador
     * @param dt1
     * @return IContainerOperations
     */

    @Override
    public IContainerOperations<Compras> comprasDia(Cliente utilizador, LocalDate dt1) {
        List<Document> comprasPorDia = (List<Document>) dataSource.getCollection().aggregate(Arrays.asList(
        new Document("$match", new Document("_id", new ObjectId(utilizador.getId()))),
        new Document("$project", new Document("compras", 
                new Document("$filter",
                       new Document("input", "$compras")
                        .append("as", "compras")
                        .append("cond",
                          new Document("$eq", Arrays.asList("$$compras.data", dt1.toString()))))
                ))
        )).into(new ArrayList<>());
        
        return cicloCompras(comprasPorDia);
    }
    
    /****************************************************************
     * Retorna uma lista de compras feita por um cliente
     * entre datas
     * @param utilizador
     * @param dt1
     * @param dt2
     * @return IContainerOperations
     */

    @Override
    public IContainerOperations<Compras> comprasEntreDias(Cliente utilizador, LocalDate dt1, LocalDate dt2) {
        List<Document> comprasEntreDatas = (List<Document>) dataSource.getCollection().aggregate(Arrays.asList(
        new Document("$match", new Document("_id", new ObjectId(utilizador.getId()))),
        new Document("$project", new Document("compras", 
                new Document("$filter",
                       new Document("input", "$compras")
                        .append("as", "compras")
                        .append("cond",
                          new Document("$and", Arrays.asList(
                                  new Document("$gte", Arrays.asList("$$compras.data", dt1.toString())),
                                  new Document("$lte", Arrays.asList("$$compras.data", dt2.toString())
                          ))))
                ))
        ))).into(new ArrayList<>());
        
        return cicloCompras(comprasEntreDatas);
    }
    
    /************************************************
     * Ciclo para percorrer as compras na
     * base de dados e guardar numa lista
     * @param listaCompras
     * @return IContainerOperations
     */
    
    private IContainerOperations cicloCompras(List<Document> listaCompras) {
        IContainerOperations<Compras> compras = new ContainerList<>();
        for(Document document : listaCompras) {
            List<Document> lista =  (List<Document>) document.get("compras");
            for(Document c : lista)  
                    compras.insert(new Compras(
                          c.getString("_id"),
                          c.getString("descricao"), c.getDouble("valor"), 
                          c.getInteger("requesicao"), ECredito.valueOf(c.getString("tipoCredito")), 
                          LocalDate.parse(c.getString("data"))));
        }
        return compras;
    }
    
    /****************************************
     * 
     * Função para ir buscar uma lista de compras
     * entre datas
     * @param dt1
     * @param dt2
     * @return IContainerOperations
     */

    @Override
    public IContainerOperations<Compras> todasAsComprasEntreDatas(LocalDate dt1, LocalDate dt2) {
        List<Document> todasAsCompras = (List<Document>) dataSource.getCollection().aggregate(Arrays.asList(
        new Document("$project", new Document("compras", 
                new Document("$filter",
                       new Document("input", "$compras")
                        .append("as", "compras")
                        .append("cond",
                          new Document("$and", Arrays.asList(
                                  new Document("$gte", Arrays.asList("$$compras.data", dt1.toString())),
                                  new Document("$lte", Arrays.asList("$$compras.data", dt2.toString())
                          ))))
                ))
        ))).into(new ArrayList<>());
        return cicloCompras(todasAsCompras);
    }
    
    /****************************************
     * 
     * Função para ir buscar uma lista de compras
     * numa data
     * @param dt1
     * @return IContainerOperations
     */

    @Override
    public IContainerOperations<Compras> todasAsComprasNumaDatas(LocalDate dt1) {
          List<Document> todasAsComprasNumaData = (List<Document>) dataSource.getCollection().aggregate(Arrays.asList(
        new Document("$project", new Document("compras", 
                new Document("$filter",
                       new Document("input", "$compras")
                        .append("as", "compras")
                        .append("cond",
                          new Document("$and", Arrays.asList(
                                  new Document("$gte", Arrays.asList("$$compras.data", dt1.toString()))
                                )))
                ))
        ))).into(new ArrayList<>());
        return cicloCompras(todasAsComprasNumaData);
    }
    
    /*********************************
     * Função para actualizar uma
     * compra
     * @param utilizador
     * @param compra
     * @return boolean
     */
    @Override
    public boolean updateCompra(Compras compra) {
        BasicDBObject update = new BasicDBObject();
        update.append("$set", 
                new BasicDBObject()
                .append("compras.$.descricao", compra.getDescricao())
                .append("compras.$.valor", compra.getValor())
                .append("compras.$.requesicao", compra.getRequesicao())
                .append("compras.$.tipoCredito", compra.getTipoCredito().toString())
                .append("compras.$.data", compra.getData().toString())
        );
        dataSource.getCollection().updateOne(eq("compras._id", compra.getId()), update);
        return true;
    }

    @Override
    public boolean removerCompras(Cliente utilizador) {
        Document user = findUser(utilizador);
        dataSource.getCollection().updateOne(user, new Document("$set",
        new Document("compras", Arrays.asList())));
        return true;
    }
}
