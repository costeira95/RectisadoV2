/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rectisadov2.Controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import rectisadov2.AppStart;
import rectisadov2.Controller.Cliente.FXMLClientesPrincipalController;
import rectisadov2.Controller.Fornecedor.FXMLFornecedoresPrincipalController;
import rectisadov2.model.Gestor;

/**
 *
 * @author Costeira
 */
public class FXMLMainController {
    
    @FXML
    private Pane menuClientes;

    @FXML
    private Pane menuLancamentos;

    @FXML
    private Pane menuFornecedores;

    @FXML
    private Pane menuSair;
    
    @FXML
    private Label btnSair;
    
    private AppStart appStart;
    

    public FXMLMainController(AppStart appStart) {
        this.appStart = appStart;
    }
    
     @FXML
    void handleClientes(MouseEvent event) {
        new FXMLClientesPrincipalController(appStart);
    }
    
    @FXML
    void handleLancamentos(MouseEvent event) {
        new FXMLLancamentosController(appStart);
    }
    
    @FXML
    void handleFornecedores(MouseEvent event) {
        new FXMLFornecedoresPrincipalController(appStart);
    }

    @FXML
    void handleSair(MouseEvent event) {
        appStart.getStage().close();    
    }
    
}
