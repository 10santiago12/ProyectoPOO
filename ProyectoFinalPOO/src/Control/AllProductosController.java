/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Control;

import Gestion.GestionClientes;
import Gestion.GestionProductos;
import Negocio.Producto;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
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
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author surre
 */
public class AllProductosController implements Initializable {

    @FXML
    private Button btn_regresar;
    @FXML
    private TableView<Producto> tbl_Listado;
    @FXML
    private TableColumn<?, ?> col_idProdu;
    @FXML
    private TableColumn<?, ?> col_idCliente;
    @FXML
    private TableColumn<?, ?> col_Produ;
    @FXML
    private TableColumn<?, ?> col_Apertura;
    @FXML
    private TableColumn<?, ?> col_Dispo;
    @FXML
    private ComboBox<String> cbx_Idcliente;
    @FXML
    private ComboBox<String> cbx_producto;
    @FXML
    private TextField txt_BuscaId;

    private GestionClientes gesCli;
    private GestionProductos gesPro;
    private ArrayList<Producto> productos;
    private ObservableList<Producto> obsProductos;
    private ObservableList<Producto> filtrados;
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        this.gesCli=new GestionClientes();
        this.gesPro=new GestionProductos();
        this.obsProductos=FXCollections.observableArrayList();
        this.filtrados=FXCollections.observableArrayList();
        this.llenaCombos();
        this.modelaTabla();
    }    

    @FXML
    private void do_Regresar(ActionEvent event) 
    {
        try
        {
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/Vista/productos.fxml"));
            Parent root=loader.load();
            Scene scene=new Scene(root);
            
            Stage stage=new Stage();
            stage.setOnCloseRequest(even->{even.consume();});
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Manejo de Productos");
            stage.show();
            
            Stage myStage=(Stage)this.btn_regresar.getScene().getWindow();
            myStage.close();
        }
        catch(IOException ex)   
        {
            Logger.getLogger(AllProductosController.class.getName()).log(Level.SEVERE,null,ex);
        }
    }


    @FXML
private void doFiltroIdCliente(ActionEvent event) 
{
    String idCliente = this.cbx_Idcliente.getSelectionModel().getSelectedItem();
    
    if (idCliente != null) 
    {
        this.filtrados.clear();

        for (Producto producto : this.obsProductos) 
        {
            if (producto.getIdCliente().equals(idCliente)) 
            {
                this.filtrados.add(producto);
            }
        }
        this.tbl_Listado.setItems(this.filtrados);
    }
}


    @FXML
    private void doFiltroProducto(ActionEvent event) {
        int index;

        this.filtrados.clear();
        index = this.cbx_producto.getSelectionModel().getSelectedIndex();

        if (index == 0) 
        {
            this.tbl_Listado.setItems(this.obsProductos);
        } 
        else 
        {
            if (index > 0) 
            {
                for (Producto producto : this.obsProductos) 
                {
                    int productIndex = Integer.parseInt(producto.getTipoProducto());
                    if (productIndex == index) 
                    {
                        this.filtrados.add(producto);
                    }
                }
                this.tbl_Listado.setItems(this.filtrados);
            }
        }
    }

    @FXML
    private void doBuscaNombre(KeyEvent event) 
    {
        String filtroName;
        
        filtroName=this.txt_BuscaId.getText();
        
        if(filtroName.isEmpty())
            this.tbl_Listado.setItems(this.obsProductos);
        else
        {
            this.filtrados.clear();
            for(Producto p:this.obsProductos)
            {
                if((p.getIdProducto()).contains(filtroName))
                    this.filtrados.add(p);
            }
            this.tbl_Listado.setItems(this.filtrados);
        }
    } 
    //Métodos locales
    public void setProductos(ArrayList<Producto> productos)
    {
        this.productos=productos;
        this.obsProductos=FXCollections.observableArrayList(this.productos);
        this.tbl_Listado.setItems(this.obsProductos);
    }
    
    private void llenaCombos() 
    {
    Set<String> clientesUnicos = new HashSet<>();

    this.productos = this.gesPro.getTodos();
    
    for (Producto producto : this.productos) 
    {
        String idCliente = producto.getIdCliente();
        
        if (!clientesUnicos.contains(idCliente)) 
        {
            this.cbx_Idcliente.getItems().add(idCliente);
            clientesUnicos.add(idCliente);
        }
    }

    this.cbx_producto.getItems().add("Todos");
    this.cbx_producto.getItems().add("Cta. Ahorros");
    this.cbx_producto.getItems().add("Cta. Corriente");
    this.cbx_producto.getItems().add("CDT");
    this.cbx_producto.getItems().add("Tarj. Visa");
    this.cbx_producto.getItems().add("Tarj. American");
}

    private void modelaTabla()
    {
        this.col_idProdu.setCellValueFactory(new PropertyValueFactory("idProducto"));
        this.col_idCliente.setCellValueFactory(new PropertyValueFactory("idCliente"));
        this.col_Produ.setCellValueFactory(new PropertyValueFactory("producto"));
        this.col_Apertura.setCellValueFactory(new PropertyValueFactory("fecha"));
        this.col_Dispo.setCellValueFactory(new PropertyValueFactory("valorUno"));
    }
}