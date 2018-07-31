/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rectisadov2;

import java.io.IOException;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import rectisadov2.Controller.FXMLMainController;
import rectisadov2.model.Cliente;
import rectisadov2.model.Gestor;

/**
 *
 * @author Costeira
 */
public class AppStart extends Application {
    private Stage stage;
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        FXMLLoader loader;        
        loader = new FXMLLoader(getClass().getResource("View/FXMLMainView.fxml"));
        loader.setController(new FXMLMainController(this));
        AnchorPane anchorPane;
        anchorPane = null;
        try{
            anchorPane = (AnchorPane)loader.load();
        }catch(IOException e){
               System.out.println(e.getMessage());
        }
        
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.getIcons().add(new Image(AppStart.class.getResourceAsStream("View/Images/icon.png")));
        stage.setTitle("Rectisado 2.0");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public AppStart getAppStart() {
        return this;
    }
    
    public Stage getStage() {
        return stage;
    }
    
}
