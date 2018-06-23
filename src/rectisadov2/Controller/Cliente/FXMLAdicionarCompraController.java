/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rectisadov2.Controller.Cliente;

import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import rectisadov2.AppStart;
import rectisadov2.Controller.FXMLDialogSucessoController;
import rectisadov2.model.Cliente;
import rectisadov2.model.Compras;
import rectisadov2.model.ECredito;
import rectisadov2.model.Gestor;

/**
 * FXML Controller class
 *
 * @author Costeira
 */
public class FXMLAdicionarCompraController extends Stage {

    private AppStart appStart;
    private TableView tblView;
        
     @FXML
    private TextField txtDescricao;

    @FXML
    private TextField txtRequesicao;

    @FXML
    private TextField txtValor;

    @FXML
    private DatePicker dtCompra;

    @FXML
    private ComboBox<ECredito> cbTransacao;

    public FXMLAdicionarCompraController(AppStart appStart, TableView tblView, Stage stage) {
        this.appStart = appStart;
        this.tblView = tblView;
        FXMLLoader loader = new FXMLLoader(appStart.getAppStart().getClass().getResource("View/FXMLAdicionarCompra.fxml"));
        loader.setController(this);
        AnchorPane anchorPane;
        anchorPane = null;        
        try{
            anchorPane = (AnchorPane)loader.load();
        } catch(IOException e){            
        }
        
        initModality(Modality.WINDOW_MODAL);
        setTitle("Adicionar Compra");
        initOwner(stage);
        getIcons().add(new Image(AppStart.class.getResourceAsStream("View/Images/icon.png")));
        Scene scene = new Scene(anchorPane);
        setScene(scene);
        
       setResizable(false);
       setFullScreen(false);
       showAndWait();  
    }

    @FXML
    void handleAdicionar(MouseEvent event) {
        try {
          if(dtCompra.getValue() == null) {
            Gestor.getInstance().getClienteActual().adicionarCompra(new Compras(
                    txtDescricao.getText(), 
                    Double.parseDouble(txtValor.getText())
                    ,Integer.parseInt(txtRequesicao.getText()), 
                    cbTransacao.getSelectionModel().getSelectedItem(), Gestor.getInstance().getClienteActual().getNome()), "cliente");
          }
          
          else {
              Gestor.getInstance().getClienteActual().adicionarCompra(new Compras(
                    txtDescricao.getText(), 
                    Double.parseDouble(txtValor.getText())
                    ,Integer.parseInt(txtRequesicao.getText()), 
                    cbTransacao.getSelectionModel().getSelectedItem(),
                    dtCompra.getValue(), Gestor.getInstance().getClienteActual().getNome()), "cliente");
          }
          List<Compras> compras = Gestor.getInstance().getClienteActual().getListaCompras("clientes");
          compras.sort(Compras.comparator);
          tblView.setItems(FXCollections.observableArrayList(compras));
          new FXMLDialogSucessoController(appStart, this, "Lançamento adicionado com sucesso!");
        } catch(Exception e) {
            
        }
    }

    @FXML
    void handleVoltar(MouseEvent event) {
        this.close();
    }
    
    @FXML
    public void initialize() {
        cbTransacao.setItems(FXCollections.observableArrayList(ECredito.values()));
    }
    
}
