/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rectisadov2.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import rectisadov2.AppStart;

/**
 * FXML Controller class
 *
 * @author Costeira
 */
public class FXMLDialogSucessoController extends Stage {

    private AppStart appStart;
    private Stage stage;
    private String msg;
    @FXML
    private Label lblSucesso;
    
    public FXMLDialogSucessoController(AppStart appStart, Stage stage, String msg) {
        this.appStart = appStart;
        this.msg = msg;
        FXMLLoader loader = new FXMLLoader(appStart.getAppStart().getClass().getResource("View/FXMLDialogSucesso.fxml"));
        loader.setController(this);
        AnchorPane anchorPane;
        anchorPane = null;        
        try{
            anchorPane = (AnchorPane)loader.load();
           
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
        
        initModality(Modality.WINDOW_MODAL);
        setTitle("Sucesso!");
        initOwner(stage);
        getIcons().add(new Image(AppStart.class.getResourceAsStream("View/Images/icon.png")));
        Scene scene = new Scene(anchorPane);
        setScene(scene);
        
       setResizable(false);
       setFullScreen(false);
       showAndWait(); 
    }
    
    @FXML
    void handleVoltar(MouseEvent event) {
        close();
    }
    
    @FXML
    public void initialize() {
        if(!msg.isEmpty())
            this.lblSucesso.setText(msg);
    }    
    
}
