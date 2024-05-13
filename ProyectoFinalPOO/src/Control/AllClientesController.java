/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Control;

import Negocio.Cliente;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author surre
 */
public class AllClientesController implements Initializable {
    
    @FXML
    private Button btn_regresar;
    @FXML
    private TableView<Cliente> tbl_Listado;
    @FXML
    private TableColumn<?, ?> col_id;
    @FXML
    private TableColumn<?, ?> col_nombre;
    @FXML
    private TableColumn<?, ?> col_genero;
    @FXML
    private ImageView img_foto;
    @FXML
    private ComboBox<String> cbx_genero;
    @FXML
    private ComboBox<String> cbx_producto;
    @FXML
    private TextField txt_BuscaNombre;
    private ArrayList<Cliente> clientes;
    private ObservableList<Cliente> obsClientes;
    private ObservableList<Cliente> filtrados;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        this.obsClientes=FXCollections.observableArrayList();
        this.filtrados=FXCollections.observableArrayList();
        this.llenaCombos();
        this.modelaTabla(); 
    }    

    @FXML
    private void do_Regresar(ActionEvent event) 
    {
        try
        {
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/Vista/clientes.fxml"));
            Parent root=loader.load();
            Scene scene=new Scene(root);
            
            Stage stage=new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Manejo de Clientes");
            stage.show();
            
            Stage myStage=(Stage)this.btn_regresar.getScene().getWindow();
            myStage.close();
        }
        catch(IOException ex)   
        {
            Logger.getLogger(AllClientesController.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    @FXML
    private void doPoneFoto(MouseEvent event) 
    {   
        Cliente perso=this.tbl_Listado.getSelectionModel().getSelectedItem();
        if(perso!=null)
        {
            File imgFile=new File("././Imagenes/"+perso.getFoto());
            String url=imgFile.toURI().toString();
            Image image=new Image(url,true);
            this.img_foto.setImage(image);
        }
    }
    
    @FXML
    private void doBuscaNombre(KeyEvent event) 
    {
        String filtroName;
        
        filtroName=this.txt_BuscaNombre.getText();
        
        if(filtroName.isEmpty())
            this.tbl_Listado.setItems(this.obsClientes);
        else
        {
            this.filtrados.clear();
            for(Cliente p:this.obsClientes)
            {
                if((p.getNombre().toLowerCase()).contains(filtroName.toLowerCase()))
                    this.filtrados.add(p);
            }
            this.tbl_Listado.setItems(this.filtrados);
        }
    }

    @FXML
    private void doFiltroGenero(ActionEvent event) 
    {
        int index;
        
        this.filtrados.clear();
        index=this.cbx_genero.getSelectionModel().getSelectedIndex();
        
        if (index==0)
            this.tbl_Listado.setItems(this.obsClientes);
        else
        {
            if(index>0)
            {
                for(Cliente cliente:this.obsClientes)
                {
                    if(cliente.getGenero()==this.cbx_genero.getSelectionModel().getSelectedItem().charAt(0))
                    {
                        this.filtrados.add(cliente);
                    }
                }
            }
            this.tbl_Listado.setItems(this.filtrados);
        }
    }

    @FXML
    private void doFiltroProducto(ActionEvent event) 
    {
        int index;
        
        this.filtrados.clear();
        index=this.cbx_producto.getSelectionModel().getSelectedIndex();
        
        if (index==0)
            this.tbl_Listado.setItems(this.obsClientes);
        else
        {
            if(index>0)
            {
                for(Cliente cliente:this.obsClientes)
                {
                    boolean products[]=cliente.getProductos();
                    if(products[index-1]==true)
                    {
                        this.filtrados.add(cliente);
                    }
                }
            }
            this.tbl_Listado.setItems(this.filtrados);
        }
    }
    
    //Métodos locales o creados desde cero para ahorrar líneas de código

    public void setClientes(ArrayList<Cliente> clientes)
    {
        this.clientes=clientes;
        this.obsClientes=FXCollections.observableArrayList(this.clientes);
        this.tbl_Listado.setItems(this.obsClientes);
    }
    private void llenaCombos()
    {
        this.cbx_genero.getItems().add("Todos");
        this.cbx_genero.getItems().add("Masculino");
        this.cbx_genero.getItems().add("Femenino");
        this.cbx_genero.getItems().add("Otro");
        
        this.cbx_producto.getItems().add("Todos");
        this.cbx_producto.getItems().add("Cta. Ahorros");
        this.cbx_producto.getItems().add("Cta. Corriente");
        this.cbx_producto.getItems().add("CDT");
        this.cbx_producto.getItems().add("Tarj. Visa");
        this.cbx_producto.getItems().add("Tarj. American");
        
    }
    private void modelaTabla()
    {
        this.col_id.setCellValueFactory(new PropertyValueFactory("identificacion"));
        this.col_nombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.col_genero.setCellValueFactory(new PropertyValueFactory("genero"));
    }
}
