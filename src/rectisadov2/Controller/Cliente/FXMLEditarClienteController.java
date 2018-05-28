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
import rectisadov2.model.Exceptions.ClienteNuloException;
import rectisadov2.model.Gestor;

/**
 * FXML Controller class
 *
 * @author Costeira
 */
public class FXMLEditarClienteController extends Stage {

    private AppStart appStart;
    
    TableView<Cliente> tblView;
    
    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtMorada;

    @FXML
    private TextField txtContribuinte;

    @FXML
    private TextField txtCodPost;
    
    @FXML
    private void initialize() {
        Cliente cliente  = tblView.getSelectionModel().getSelectedItem();
        if(cliente == null) throw new ClienteNuloException();
        txtNome.setText(cliente.getNome());
        txtMorada.setText(cliente.getMorada());
        txtCodPost.setText(cliente.getCodPost());
        txtContribuinte.setText(String.valueOf(cliente.getNumeroContribuinte()));
    }
    
    public FXMLEditarClienteController(AppStart appStart, TableView<Cliente> tblView, Stage stage) {
        this.appStart = appStart;
        this.tblView = tblView;
        FXMLLoader loader = new FXMLLoader(appStart.getAppStart().getClass().getResource("View/FXMLEditarCliente.fxml"));
        loader.setController(this);
        AnchorPane anchorPane;
        anchorPane = null;
        try{
            anchorPane = (AnchorPane)loader.load();
            
        } catch(IOException e){            
        }
        
        initModality(Modality.WINDOW_MODAL);
        setTitle("Editar Cliente");
        initOwner(stage);
        getIcons().add(new Image(AppStart.class.getResourceAsStream("View/Images/icon.png")));
        Scene scene = new Scene(anchorPane);
        setScene(scene);
        
       setResizable(false);
       setFullScreen(false);
       showAndWait();  
    }

    @FXML
    void handleEditar(MouseEvent event) {
        Cliente cliente  = tblView.getSelectionModel().getSelectedItem();
        cliente.editarCliente(txtNome.getText(), txtMorada.getText(), 
                txtCodPost.getText(), Integer.parseInt(txtContribuinte.getText()));
        Gestor.getInstance().getDAO().updateUtilizador(cliente);
        List<Cliente> clientes = Gestor.getInstance().getElements();
        clientes.sort(Cliente.comparator);
        tblView.setItems(FXCollections.observableArrayList(clientes));
        new FXMLDialogSucessoController(appStart, this, "Cliente editado com sucesso!");
    }

    @FXML
    void handleVoltar(MouseEvent event) {
        this.close();
    }
    
}
