/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rectisadov2.Controller.Fornecedor;

import rectisadov2.Controller.Cliente.*;
import java.io.IOException;
import java.util.function.Predicate;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import rectisadov2.AppStart;
import rectisadov2.Controller.FXMLDialogErrorController;
import rectisadov2.model.Cliente;
import rectisadov2.model.Gestor;
import rectisadov2.model.NumberTableCellFactory;

/**
 * FXML Controller class
 *
 * @author Costeira
 */
public class FXMLFornecedoresPrincipalController extends Stage {
    
    private AppStart appStart;
    private SortedList<Cliente> sortedClientes;
    @FXML
    private TextField txtSearch;

    @FXML
    private Label LblSelecionado;
    
    @FXML
    private TableView<Cliente> tblClientes;
    @FXML
    private TableColumn<Cliente, Integer> clienteColunaNumero;

    @FXML
    private TableColumn<Cliente, Integer> clienteColunaNome;

    public FXMLFornecedoresPrincipalController(AppStart appStart) {
        if(!Gestor.getInstanceFornecedores().isFornecedoresCarregados()) {
            Gestor.getInstanceFornecedores().load();
            Gestor.getInstanceFornecedores().setFornecedoresCarregados(true);
        }
        this.appStart = appStart;
        FXMLLoader loader = new FXMLLoader(appStart.getAppStart().getClass().getResource("View/FXMLClientesPrincipal.fxml"));
        loader.setController(this);
        AnchorPane anchorPane;
        anchorPane = null;        
        try{
            anchorPane = (AnchorPane)loader.load();
        } catch(IOException e){ 
            System.out.println(e.getMessage());
        }
        
        clienteColunaNumero.setCellFactory(new NumberTableCellFactory<>(1));
        clienteColunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        clienteColunaNome.setSortType(TableColumn.SortType.ASCENDING);
        tblClientes.setItems(FXCollections.observableArrayList(Gestor.getInstanceFornecedores().getElements()));
        tblClientes.getSortOrder().add(clienteColunaNome);
        
        initModality(Modality.WINDOW_MODAL);
        setTitle("Gestor Fornecedores");
        initOwner(appStart.getStage());
        getIcons().add(new Image(AppStart.class.getResourceAsStream("View/Images/icon.png")));
        Scene scene = new Scene(anchorPane);
        setScene(scene);
        
        setResizable(false);
        setFullScreen(false);
        showAndWait();  
    }

    @FXML
    void handleAdicionar(MouseEvent event) {
         new FXMLAdicionarFornecedorController(appStart, tblClientes, this);
         filteredList();
    }

    @FXML
    void handleEditar(MouseEvent event) {
        try {
            new FXMLEditarForecedorController(appStart, tblClientes, this);
        } catch(Exception e) {
            new FXMLDialogErrorController(appStart, this, "Tem que selecionar um fornecedor da lista!");
        }
    }

    @FXML
    void handleSelecionar(MouseEvent event) {
       try {
           Gestor.getInstanceFornecedores().setFornecedorActual(tblClientes.getSelectionModel().getSelectedItem());
           LblSelecionado.setText(Gestor.getInstanceFornecedores().getFornecedorActual().getNome());
       } catch(Exception e) {
           new FXMLDialogErrorController(appStart, this, "Tem que selecionar um fornecedor da lista!"); 
       }
    }
    
     @FXML
    void handleVoltar(MouseEvent event) {
        this.close();
    }
    
    @FXML
    private void initialize() {
        if(Gestor.getInstanceFornecedores().getFornecedorActual() != null)
            LblSelecionado.setText(Gestor.getInstanceFornecedores().getFornecedorActual().getNome());
        
        filteredList();
    }
    
    private void filteredList() {
        FilteredList<Cliente> filteredClientes = new FilteredList<>(
         FXCollections.observableArrayList(Gestor.getInstanceFornecedores().getElements()), e -> true);
        txtSearch.setOnKeyPressed(e -> {
            txtSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filteredClientes.setPredicate((Predicate<? super Cliente>) user-> {
                    if(newValue == null || newValue.isEmpty()) return true;
                    else if(user.getNome().toLowerCase().contains(newValue.toLowerCase())) return true;
                    return false;
                    
                });
            });
                sortedClientes = new SortedList<>(filteredClientes);
                sortedClientes.comparatorProperty().bind(tblClientes.comparatorProperty());
                tblClientes.setItems(sortedClientes);
        });
    }
     
}
