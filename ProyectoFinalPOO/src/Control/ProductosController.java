
package Control;

import Gestion.GestionClientes;
import Gestion.GestionProductos;
import Negocio.Cliente;
import Negocio.Producto;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class ProductosController implements Initializable {

    @FXML
    private Button btn_vertodos;
    @FXML
    private ComboBox<String> cbx_productos;
    @FXML
    private TextField txt_numProd;
    @FXML
    private Label lbl_saldo;
    @FXML
    private TextField txt_saldo;
    @FXML
    private Label lbl_meses;
    @FXML
    private TextField txt_meses;
    @FXML
    private DatePicker fec_apertura;
    @FXML
    private Button btn_crear;
    @FXML
    private Button btn_modificar;
    @FXML
    private Button btn_eliminar;
    @FXML
    private Button btn_regresar;
    @FXML
    private TextField txt_id;
    @FXML
    private Button btn_buscar;
    
    //Variables de clase
    private GestionClientes gesCli;
    private GestionProductos gesPro;
    private ArrayList<Producto> productos;
    private ArrayList<Cliente> clientes;
    private Cliente cliente;
    private StringBuilder id;
    private StringBuilder saldo;
    private StringBuilder meses;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        this.gesPro=new GestionProductos();
        this.gesCli=new GestionClientes();
        this.id=null;
        this.saldo=null;
        this.meses=null;
        this.fec_apertura.setEditable(false);
        this.traeProductos();
    }    
    
    @FXML
    private void validaSaldo(KeyEvent event) 
    {
        this.saldo=new StringBuilder(this.txt_saldo.getText());
        char c=event.getCharacter().charAt(0);//Capturamos el caracter que se oprimió
        if(!((Character.isDigit(c)||(c==java.awt.event.KeyEvent.VK_BACK_SPACE)||(c==java.awt.event.KeyEvent.VK_DELETE))))
        {
            int posCursor=this.txt_saldo.getCaretPosition();//obtiene posición del cursor en el momento de digitar el caracter
            this.manageMensajes("Caracter no válido en este campo", 1);
            this.saldo.deleteCharAt(posCursor-1);
            
            this.txt_saldo.clear();
            this.txt_saldo.setText(this.saldo.toString());
        }
        this.txt_saldo.positionCaret(this.saldo.length());
    }

    @FXML
    private void validaMeses(KeyEvent event) 
    {
        this.meses=new StringBuilder(this.txt_meses.getText());
        char c=event.getCharacter().charAt(0);//Capturamos el caracter que se oprimió
        if(!((Character.isDigit(c)||(c==java.awt.event.KeyEvent.VK_BACK_SPACE)||(c==java.awt.event.KeyEvent.VK_DELETE))))
        {
            int posCursor=this.txt_meses.getCaretPosition();//obtiene posición del cursor en el momento de digitar el caracter
            this.manageMensajes("Caracter no válido en este campo", 1);
            this.meses.deleteCharAt(posCursor-1);
            
            this.txt_meses.clear();
            this.txt_meses.setText(this.meses.toString());
        }
        this.txt_meses.positionCaret(this.meses.length());
    }

    @FXML
    private void validaId(KeyEvent event) 
    {
        this.id=new StringBuilder(this.txt_id.getText());
        char c=event.getCharacter().charAt(0);//Capturamos el caracter que se oprimió
        if(!((Character.isDigit(c)||(c==java.awt.event.KeyEvent.VK_BACK_SPACE)||(c==java.awt.event.KeyEvent.VK_DELETE))))
        {
            int posCursor=this.txt_id.getCaretPosition();//obtiene posición del cursor en el momento de digitar el caracter
            this.manageMensajes("Caracter no válido en este campo", 1);
            this.id.deleteCharAt(posCursor-1);
            
            this.txt_id.clear();
            this.txt_id.setText(this.id.toString());
        }
        this.txt_id.positionCaret(this.id.length());
    }

    @FXML
    private void doBuscar(ActionEvent event) 
    {
        String id;
        boolean existe=false;
        
        id=this.txt_id.getText();
        this.clientes=this.gesCli.getTodos();
        if(id.isEmpty())
            this.manageMensajes("Debe digitar el ID del cliente a buscar!!!", 1);
        else
        {
            for(Cliente cliente:this.clientes)
            {
                if(cliente.getIdentificacion().equals(id))
                {
                    this.llenaCombos(cliente);
                    this.cliente=cliente;
                    existe=true;
                    this.manageMensajes("Seleccione la lista desplegable para ver y crear sus productos", 2);
                    break;
                }
            }
            if(!existe)
                this.manageMensajes("No existe un cliente con este ID!!!", 1);
        }
    }
    @FXML
    private void doVertodos(ActionEvent event) 
    {
        try
        {
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/Vista/allProductos.fxml"));
            Parent root=loader.load();
            Scene scene=new Scene(root);
            this.productos=this.gesPro.getTodos();
            
            AllProductosController ouController=loader.getController();
            ouController.setProductos(this.productos);
            
            Stage stage=new Stage();
            stage.setOnCloseRequest(even->{even.consume();});
            stage.setResizable(false);
            stage.setTitle("Listado de Productos");
            stage.setScene(scene);
            stage.show();
            
            Stage myStage=(Stage)this.btn_regresar.getScene().getWindow();
            myStage.close();
        }
        catch(IOException ex)   
        {
            Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    @FXML
    private void doFiltroProducto(ActionEvent event) 
    {
        this.traeProductos();
        String idCliente, item;
        boolean existe = false;

        item = this.cbx_productos.getSelectionModel().getSelectedItem();
        idCliente = this.txt_id.getText();

        // Restablece el estado de los campos
        this.limpiaTodo();

        for (Producto producto : this.productos) 
        {
            if (producto.getIdCliente().equals(idCliente) && producto.getTipoProducto().equals(String.valueOf(getTipoProductoIndex(item)))) 
            {
                existe = true;
                this.poneProducto(producto);
            }
        }
        if (!existe) 
        {
            this.nuevoProducto();
        }
    }

    @FXML
    private void doApertura(ActionEvent event) 
    {
        LocalDate selectedDate = this.fec_apertura.getValue();

        if (selectedDate != null) 
        {
            LocalDate currentDate = LocalDate.now();

            if (selectedDate.isBefore(currentDate)) 
            {
                this.manageMensajes("La fecha de creación no puede ser anterior a la de hoy!!!", 1);
                this.fec_apertura.setValue(null);
            }
        }
    }

    @FXML
    private void doCrear(ActionEvent event) {
        String idprodu, fecha = "", uno = "0", dos = "0", text;
        String errores = "";
        int tipoprodu;

        idprodu = this.txt_numProd.getText();
        if (idprodu.isEmpty())
            errores += "El campo N° de cuenta está vacío!!! \n";

        text = this.cbx_productos.getSelectionModel().getSelectedItem();

        uno = this.txt_saldo.getText();
        if (uno.isEmpty()) {
            errores += "El campo de SALDO/CUPO está vacío!!! \n";
        }

        tipoprodu = getTipoProducto(text);

        if (tipoprodu == 3) {
            dos = this.txt_meses.getText();
            if (dos.isEmpty()) {
                errores += "El campo de PLAZO está vacío!!!\n";
            }
        }

        String id = this.txt_id.getText();
        if (id.isEmpty())
            errores += "El campo ID está vacío!!! \n";

        LocalDate selectedDate = this.fec_apertura.getValue();

        if (selectedDate == null) {
            errores += "El campo FECHA no se ha completado.\n";
        } else {
            fecha = selectedDate.toString();
        }

        if (errores.length() == 0) {
            Producto producto = new Producto(idprodu, String.valueOf(tipoprodu), id, fecha, Float.parseFloat(uno),
                    Integer.parseInt(dos));

            if (this.gesPro.guardaProducto(producto)) 
            {
                this.manageMensajes("El producto se ha creado exitosamente! :)", 2);
                this.btn_crear.setDisable(true);
                this.btn_eliminar.setDisable(false);
                this.btn_modificar.setDisable(false);
            } 
            else 
            {
                this.manageMensajes("Fallo creando el producto, por favor revise!", 1);
            }
        } 
        else 
        {
            this.manageMensajes(errores, 1);
        }
    }

    @FXML
    private void doModificar(ActionEvent event) 
    {
        String idprodu,tipoprodu="0",id,fecha="",uno="0",dos="0",text;
        String errores="";
        
        idprodu=this.txt_numProd.getText();
        if(idprodu.isEmpty())
            errores+="El campo N° de cuenta está vacio!!! \n";
        
        text=this.cbx_productos.getSelectionModel().getSelectedItem();
        
        uno=this.txt_saldo.getText();
        if(uno.isEmpty())
        {
            errores+="El campo de SALDO/CUPO está vacio!!! \n";
        }
        
        if(text.equals("Cta. Ahorros"))
            tipoprodu="1";
        if(text.equals("Cta. Corriente"))
            tipoprodu="2";
        if(text.equals("CDT"))
        {
            tipoprodu="3";
            dos=this.txt_meses.getText();
            if(dos.isEmpty())
            {
                errores+="El campo de PLAZO está vacio!!!\n";
            }
        }
        if(text.equals("Tarj. Visa"))
            tipoprodu="4";
        if(text.equals("Tarj. American"))
            tipoprodu="5";
        
        
        id=this.txt_id.getText();
        if(id.isEmpty())
            errores+="El campo ID está vacio!!! \n";
        
        LocalDate selectedDate = this.fec_apertura.getValue();
        
        if(selectedDate==null)
        {
             errores+="El campo FECHA no se ha completado.\n";
        }
        else
            fecha=selectedDate.toString();

        if(errores.length()==0)
        {
            Producto producto=new Producto(idprodu,tipoprodu,id,fecha,Float.parseFloat(uno),Integer.parseInt(dos));
            this.productos=this.gesPro.getTodos();
             Iterator<Producto> iter = this.productos.iterator();
            int pos = -1;                
                while(iter.hasNext())
                {
                    pos++;
                    if(iter.next().getIdProducto().equals(this.txt_numProd.getText()))
                    {
                        iter.remove();
                        this.productos.add(pos,producto);
                        break;
                    }
                }
                this.gesPro.reemplazaArchivo(this.productos);
                this.manageMensajes("El producto ha sido modificado exitosamente! :)", 2);
                this.productos = this.gesPro.getTodos();
                this.traeProductos();
        }
        else
            this.manageMensajes(errores, 1);
    }

    @FXML
    private void doEliminar(ActionEvent event) 
    {
        boolean seguro, seguro2;
        String id, item;
        item = this.cbx_productos.getSelectionModel().getSelectedItem();
        seguro = this.manageMensajes("¿Seguro que quiere eliminar este producto?", 3);

        if (seguro) 
        {
            Iterator<Producto> iter = this.productos.iterator();
            while (iter.hasNext()) 
            {
                if (iter.next().getIdProducto().equals(this.txt_numProd.getText())) 
                {
                    iter.remove();
                    break;
                }
            }

            // Verificar si todos los productos del cliente están en false
            id = this.txt_id.getText();
            boolean clienteEliminado = true;

            for (Cliente cliente : this.clientes) 
            {
                if (cliente.getIdentificacion().equals(id)) 
                {
                    boolean[] produs = cliente.getProductos();
                    switch (getTipoProductoIndex(item)) 
                    {
                        case 1:
                            produs[0] = false;
                            break;
                        case 2:
                            produs[1] = false;
                            break;
                        case 3:
                            produs[2] = false;
                            break;
                        case 4:
                            produs[3] = false;
                            break;
                        case 5:
                            produs[4] = false;
                            break;
                    }
                    for (boolean producto : produs) 
                    {
                        if (producto) 
                        {
                            clienteEliminado = false;
                            break;
                        }
                    }
                    break;
                }
            }

            if (clienteEliminado) 
            {
                this.manageMensajes("El cliente será eliminado del banco ya que no contará con productos", 2);
                Iterator<Cliente> clienteIter = this.clientes.iterator();
                while (clienteIter.hasNext())
                {
                    if (clienteIter.next().getIdentificacion().equals(id)) 
                    {
                        clienteIter.remove();
                        this.txt_id.setText("");
                        break;
                    }
                }
            }

            // Actualizar archivos
            this.gesPro.reemplazaArchivo(this.productos);
            this.gesCli.reemplazaArchivo(this.clientes);

            this.manageMensajes("Producto removido con éxito!!", 2);
            this.limpiaTodo();
            this.traeProductos();

            for (Cliente cliente : this.clientes) 
            {
                if (cliente.getIdentificacion().equals(id)) 
                {
                    this.llenaCombos(cliente);
                    break;
                }  
            }
        }
    }

    @FXML
    private void doRegresar(ActionEvent event) 
    {
        try
        {
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/Vista/principal.fxml"));
            Parent root=loader.load();
            Scene scene=new Scene(root);
            
            Stage stage=new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Principal");
            stage.show();
            
            Stage myStage=(Stage)this.btn_regresar.getScene().getWindow();
            myStage.close();
        }
        catch(IOException ex)   
        {
            Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE,null,ex);
        }
    } 
    
    ////////////////////////////////////////////////////////////////////////////////////////Métodos locales////////////////////////////////////////////////////////////////////////////////////////
    
    private boolean manageMensajes(String mesg, int caso)
    {
        Alert msg;
        boolean ok=false;
        
        if(caso==1)//error
        {
            msg=new Alert(Alert.AlertType.ERROR);
            msg.setTitle("ERROR");
            
            msg.setHeaderText(null);
            msg.setContentText(mesg);
            msg.showAndWait();
        }
        if(caso==2)//Notificación
        {
            msg=new Alert(Alert.AlertType.INFORMATION);
            msg.setTitle("OK");
            
            msg.setHeaderText(null);
            msg.setContentText(mesg);
            msg.showAndWait();
        }
        if(caso==3)//Confirmación
        {
            msg=new Alert(Alert.AlertType.CONFIRMATION);
            msg.setTitle("Petición Eliminación");
            
            msg.setHeaderText(null);
            msg.setContentText(mesg);
            msg.initStyle(StageStyle.UTILITY);
            
            Optional<ButtonType> result=msg.showAndWait();
            if(result.get()==ButtonType.OK)
                ok=true;
        }
        return ok; 
    }
    private void llenaCombos(Cliente cliente)
    {
        this.cbx_productos.getItems().clear();
        boolean productos[];
        productos=cliente.getProductos();
        if(productos[0]==true)
            this.cbx_productos.getItems().add("Cta. Ahorros");
        if(productos[1]==true)
            this.cbx_productos.getItems().add("Cta. Corriente");
        if(productos[2]==true)
            this.cbx_productos.getItems().add("CDT");
        if(productos[3]==true)
            this.cbx_productos.getItems().add("Tarj. Visa");
        if(productos[4]==true)
            this.cbx_productos.getItems().add("Tarj. American");
    }
    private void poneProducto(Producto producto)
    {
        this.txt_numProd.setDisable(false);
        this.txt_numProd.setText(producto.getIdProducto());
        String item = this.cbx_productos.getSelectionModel().getSelectedItem();        
 
        switch (item) 
        {
            case "Cta. Ahorros":
            case "Cta. Corriente":
                this.lbl_saldo.setText("Saldo(Miles)");
                this.txt_saldo.setDisable(false);
                this.txt_saldo.setText(String.valueOf(producto.getValorUno()));
                this.txt_meses.setDisable(true);
                this.lbl_meses.setText("");
                break;
            case "CDT":
                this.lbl_saldo.setText("Inversión(Millones):");
                this.lbl_meses.setText("Plazo(Meses):");
                this.txt_saldo.setDisable(false);
                this.txt_saldo.setText(String.valueOf(producto.getValorUno()));
                this.txt_meses.setDisable(false);
                this.txt_meses.setText(String.valueOf(producto.getValorDos()));
                break;
            case "Tarj. Visa":
            case "Tarj. American":
                this.lbl_saldo.setText("Cupo(Millones):");
                this.txt_saldo.setDisable(false);
                this.txt_saldo.setText(String.valueOf(producto.getValorUno()));
                this.txt_meses.setDisable(true);
                this.lbl_meses.setText("");
                break;
            default:
                break;
        }
        this.fec_apertura.setDisable(false);
        this.fec_apertura.setValue(LocalDate.parse(producto.getFecha()));
        this.btn_crear.setDisable(true);
        this.btn_eliminar.setDisable(false);
        this.btn_modificar.setDisable(false);
    }

    private void traeProductos()
    {
        this.productos=this.gesPro.getTodos();
    }
    
    private void nuevoProducto() 
    {
        String item = this.cbx_productos.getSelectionModel().getSelectedItem();

        if (item != null) 
        {
            String numprodu = String.valueOf(1000000000 + (long) (Math.random() * 9000000000L));

            switch (item) 
            {
                case "Cta. Ahorros":
                case "Cta. Corriente":
                    limpiaTodo();
                    manageMensajes("El producto no ha sido creado aún!!!", 2);
                    btn_crear.setDisable(false);
                    fec_apertura.setDisable(false);
                    fec_apertura.getEditor().clear();
                    txt_numProd.setDisable(false);
                    txt_saldo.setDisable(false);
                    btn_modificar.setDisable(true);
                    btn_eliminar.setDisable(true);
                    txt_numProd.setText(numprodu);
                    lbl_saldo.setText("Saldo(Miles)");
                    lbl_meses.setText("");
                    txt_meses.setText("");
                    txt_meses.setDisable(true);
                    break;
                case "CDT":
                    limpiaTodo();
                    manageMensajes("El producto no ha sido creado aún!!!", 2);
                    btn_crear.setDisable(false);
                    fec_apertura.getEditor().clear();
                    fec_apertura.setDisable(false);
                    txt_numProd.setDisable(false);
                    txt_meses.setDisable(false);
                    txt_saldo.setDisable(false);
                    btn_modificar.setDisable(true);
                    btn_eliminar.setDisable(true);
                    txt_numProd.setText(numprodu);
                    lbl_saldo.setText("Inversión(Millones):");
                    lbl_meses.setText("Plazo(Meses):");
                    break;
                case "Tarj. Visa":
                case "Tarj. American":
                    limpiaTodo();
                    manageMensajes("El producto no ha sido creado aún!!!", 2);
                    btn_crear.setDisable(false);
                    fec_apertura.getEditor().clear();
                    fec_apertura.setDisable(false);
                    txt_numProd.setDisable(false);
                    txt_numProd.setText(numprodu);
                    btn_modificar.setDisable(true);
                    btn_eliminar.setDisable(true);
                    lbl_saldo.setText("Cupo(Millones):");
                    txt_saldo.setDisable(false);
                    lbl_meses.setText("");
                    txt_meses.setText("");
                    txt_meses.setDisable(true);
                    break;
                default:
                    break;
            }
        } 
        else 
        {
            System.out.println("");
        }
    }


    private void limpiaTodo()
    {
        this.txt_saldo.setText("");
        this.txt_meses.setText("");
        this.txt_numProd.setText("");
        this.fec_apertura.getEditor().clear();
        this.btn_crear.setDisable(false);
        this.btn_modificar.setDisable(true);
        this.btn_eliminar.setDisable(true);
    }
    private int getTipoProductoIndex(String tipoProducto) 
    {
        if (tipoProducto != null) 
        {
            switch (tipoProducto) 
            {
                case "Cta. Ahorros":
                    return 1;
                case "Cta. Corriente":
                    return 2;
                case "CDT":
                    return 3;
                case "Tarj. Visa":
                    return 4;
                case "Tarj. American":
                    return 5;
                default:
                    return -1; // Valor predeterminado si no coincide con ningún tipo conocido
            }
        } 
        else 
        {
            return -1; // Manejar el caso cuando tipoProducto es null
        }
    }
    private int getTipoProducto(String text) 
    {
        switch (text) 
        {   
            case "Cta. Ahorros":
                return 1;
            case "Cta. Corriente":
                return 2;
            case "CDT":
                return 3;
            case "Tarj. Visa":
                return 4;
            case "Tarj. American":
                return 5;
            default:
                return -1; // Valor por defecto o error
        }
    }
}