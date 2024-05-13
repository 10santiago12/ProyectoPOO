/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Start;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Start extends Application
{
    public static void main(String[] args) 
    {
        launch(args);        
    }

    @Override
    public void start(Stage ventana) throws Exception 
    {
        Parent root=FXMLLoader.load(getClass().getResource("/Vista/Principal.fxml")); //Arma la ventana con el código XML
        Scene scene=new Scene(root);
        ventana.setScene(scene);
        
        ventana.setTitle("Principal"); //Agrega título a la ventana
        ventana.setResizable(false); //no permite redimensionar la ventana  
        //ventana.setOnCloseRequest(event->{event.consume();}); //Deshabilita la X de cerrar
        ventana.show(); //La muestra
    }
}
