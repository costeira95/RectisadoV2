/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rectisadov2.Controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import rectisadov2.AppStart;
import rectisadov2.Controller.Cliente.FXMLAdicionarCompraController;
import rectisadov2.Controller.Cliente.FXMLEditarCompraController;
import rectisadov2.model.Compras;
import rectisadov2.model.ECredito;
import rectisadov2.model.Gestor;
import rectisadov2.model.PDF.GerarPDF;

/**
 * FXML Controller class
 *
 * @author Costeira
 */
public class FXMLVisualizarCompraController extends Stage {

    private AppStart appStart;
    private Compras compra;
    
    @FXML
    private TextField txtTransacao;

    @FXML
    private TextField txtRequesicao;

    @FXML
    private TextField txtValor;

    @FXML
    private TextField txtData;

    @FXML
    private TextArea txtDescricao;
    
    
    public FXMLVisualizarCompraController(AppStart appStart, Compras compra) {
        this.appStart = appStart;
        this.compra = compra;
        FXMLLoader loader = new FXMLLoader(appStart.getAppStart().getClass().getResource("View/FXMLVisualizarCompra.fxml"));
        loader.setController(this);
        AnchorPane anchorPane;
        anchorPane = null;        
        try{
            anchorPane = (AnchorPane)loader.load();
           
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
        
        
        initModality(Modality.WINDOW_MODAL);
        setTitle("Visualizar lançamento");
        initOwner(appStart.getStage());
        getIcons().add(new Image(AppStart.class.getResourceAsStream("View/Images/icon.png")));
        Scene scene = new Scene(anchorPane);
        setScene(scene);
        
       setResizable(false);
       showAndWait();  
    }
    
    @FXML
    public void initialize() {
        txtData.setText(compra.getData().toString());
        txtDescricao.setText(compra.getDescricao());
        txtRequesicao.setText(String.valueOf(compra.getRequesicao()));
        txtTransacao.setText(compra.getTipoCredito().toString());
        txtValor.setText(String.valueOf(compra.getValor()));
    }
 
    @FXML
    void handleVoltar(MouseEvent event) {
        this.close();
    }
    
    
}
