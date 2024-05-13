/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Control;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


public class PrincipalController implements Initializable 
{

    @FXML
    private ImageView img_portada;
    @FXML
    private ImageView img_clientes;
    @FXML
    private ImageView img_productos;
    @FXML
    private Label txt_clientes;
    @FXML
    private Label txt_clientes1;
    @FXML
    private Label txt_clientes2;
    @FXML
    private ImageView img_transacciones;


    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO
    }    

    @FXML
    private void goClientes(MouseEvent event) 
    {
        try
        {
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/vista/clientes.fxml"));
            Parent root=loader.load();
            Scene scene=new Scene(root);
            
            Stage stage=new Stage();
            stage.setScene(scene);
            stage.setOnCloseRequest(even->{even.consume();});
            stage.setResizable(false);
            stage.setTitle("Manejo de Clientes");
            stage.show();
            
            Stage myStage=(Stage)this.img_clientes.getScene().getWindow();
            myStage.close();
        }
        catch(IOException ex)   
        {
            Logger.getLogger(ClientesController.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    @FXML
    private void goProductos(MouseEvent event) 
    {
        try
        {
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/vista/productos.fxml"));
            Parent root=loader.load();
            Scene scene=new Scene(root);
            
            Stage stage=new Stage();
            stage.setScene(scene);
            stage.setOnCloseRequest(even->{even.consume();});
            stage.setResizable(false);
            stage.setTitle("Manejo de Productos");
            stage.show();
            
            Stage myStage=(Stage)this.img_clientes.getScene().getWindow();
            myStage.close();
        }
        catch(IOException ex)   
        {
            Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    @FXML
    private void goTransacciones(MouseEvent event) 
    {
        try
        {
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/vista/transacciones.fxml"));
            Parent root=loader.load();
            Scene scene=new Scene(root);
            
            Stage stage=new Stage();
            stage.setScene(scene);
            stage.setOnCloseRequest(even->{even.consume();});
            stage.setResizable(false);
            stage.setTitle("Gestión de transacciones");
            stage.show();
            
            Stage myStage=(Stage)this.img_clientes.getScene().getWindow();
            myStage.close();
        }
        catch(IOException ex)   
        {
            Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
}
