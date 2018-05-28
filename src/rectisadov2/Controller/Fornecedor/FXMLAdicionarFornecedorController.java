/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rectisadov2.Controller.Fornecedor;

import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.bson.types.ObjectId;
import rectisadov2.AppStart;
import rectisadov2.Controller.FXMLDialogErrorController;
import rectisadov2.Controller.FXMLDialogSucessoController;
import rectisadov2.model.Cliente;
import rectisadov2.model.Exceptions.NomeVazioException;
import rectisadov2.model.Gestor;

/**
 * FXML Controller class
 *
 * @author Costeira
 */
public class FXMLAdicionarFornecedorController extends Stage {

    private AppStart appStart;
    private TableView tblView;
        
    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtMorada;

    @FXML
    private TextField txtContribuinte;

    @FXML
    private TextField txtPostal;

    public FXMLAdicionarFornecedorController(AppStart appStart, TableView tblView, Stage stage) {
        this.appStart = appStart;
        this.tblView = tblView;
        
        FXMLLoader loader = new FXMLLoader(appStart.getAppStart().getClass().getResource("View/FXMLAdicionarCliente.fxml"));
        loader.setController(this);
        AnchorPane anchorPane;
        anchorPane = null;        
        try{
            anchorPane = (AnchorPane)loader.load();
        } catch(IOException e){            
        }
        
        initModality(Modality.WINDOW_MODAL);
        setTitle("Adicionar Fornecedor");
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
            int contribuinte = (txtContribuinte.getText().isEmpty()) ? 0 :
                            Integer.parseInt(txtContribuinte.getText());
          Gestor.getInstanceFornecedores().addCliente(new Cliente(new ObjectId().toString(),
                  txtNome.getText(), txtMorada.getText(), txtPostal.getText(), 
                  contribuinte));
          List<Cliente> cliente = Gestor.getInstanceFornecedores().getElements();
          cliente.sort(Cliente.comparator);
          tblView.setItems(FXCollections.observableArrayList(cliente));
          new FXMLDialogSucessoController(appStart, this, "Fornecedor adicionado com sucesso!");
        } catch(NomeVazioException e) {
            new FXMLDialogErrorController(appStart, this, e.getMessage());
        }
    }

    @FXML
    void handleVoltar(MouseEvent event) {
        this.close();
    }
    
}
