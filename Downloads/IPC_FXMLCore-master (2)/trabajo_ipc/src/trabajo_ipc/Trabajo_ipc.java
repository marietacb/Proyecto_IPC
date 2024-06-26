/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package trabajo_ipc;

import java.util.Set;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Belén Rodríguez
 */
public class Trabajo_ipc extends Application {
    private static Scene scene;

    
    static void setRoot(Parent root) {
        scene.setRoot(root);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
       //1-creamos un grafo de escena a partir del fichero FXML 
        Application.setUserAgentStylesheet(STYLESHEET_CASPIAN); //estilo caspian
       
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/inicio.fxml"));
        Parent root = loader.load();
        
        //2-Creacion de la escena con el nodo raiz del grafo de escena
        scene = new Scene(root);
        
        

        //3-asignación de la escna al Stage que recibe el metodo        
        stage.setScene(scene);
        stage.setTitle("Mi dinerillo");
        stage.setResizable(true);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
