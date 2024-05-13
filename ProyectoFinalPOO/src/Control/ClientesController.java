package Control;

import Gestion.GestionClientes;
import Gestion.GestionProductos;
import Negocio.Cliente;
import Negocio.Producto;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class ClientesController implements Initializable 
{

    @FXML
    private TextField txt_id;
    @FXML
    private TextField txt_nombre;
    @FXML
    private TextField txt_clave;
    @FXML
    private ImageView img_foto;
    @FXML
    private RadioButton rbt_hombre;
    @FXML
    private RadioButton rbt_mujer;
    @FXML
    private RadioButton rbt_otro;
    @FXML
    private Button btn_buscar;
    @FXML
    private CheckBox chk_ahorros;
    @FXML
    private CheckBox chk_corriente;
    @FXML
    private CheckBox chk_cdt;
    @FXML
    private CheckBox chk_american;
    @FXML
    private CheckBox chk_visa;
    @FXML
    private Button btn_regresar;
    @FXML
    private Button btn_buscarfoto;
    @FXML
    private Button btn_limpiar;
    @FXML
    private Button btn_savenuevo;
    @FXML
    private Button btn_vertodos;
    @FXML
    private Button btn_eliminar;
    @FXML
    private Button btn_patras;
    @FXML
    private Button btn_palante;
    
    //Variables de clase
    private GestionClientes gesCli;
    private GestionProductos gesPro;
    private ArrayList<Cliente> clientes;
    private ArrayList<Producto> productos;
    private int pos;
    private String laFoto;
    private String idActual;
    private String claveActual;
    private String rutaImages;
    private StringBuilder id;
    private StringBuilder nombre;
    private StringBuilder clave;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        this.gesCli=new GestionClientes();
        this.gesPro=new GestionProductos();
        this.pos=0;
        this.rutaImages="././Imagenes/";
        this.laFoto="sinrostro.jpg";
        
        ToggleGroup tg=new ToggleGroup();
        this.rbt_hombre.setToggleGroup(tg);
        this.rbt_mujer.setToggleGroup(tg);
        this.rbt_otro.setToggleGroup(tg);
        this.rbt_hombre.setSelected(true);
        this.poneFoto();
        
        this.id=null;
        this.nombre=null;
        this.clave=null;
        
        this.traeClientes();
    }
    
    @FXML
    private void validaId(KeyEvent event) 
    {
        this.id=new StringBuilder(this.txt_id.getText());
        char c=event.getCharacter().charAt(0);//Capturamos el caracter que se oprimió
        if(!((Character.isDigit(c)||(c==java.awt.event.KeyEvent.VK_BACK_SPACE)||(c==java.awt.event.KeyEvent.VK_DELETE)||(c==java.awt.event.KeyEvent.VK_TAB))))
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
    private void validaNombre(KeyEvent event) 
    {
        this.nombre=new StringBuilder(this.txt_nombre.getText());
        char c=event.getCharacter().charAt(0);//Capturamos el caracter que se oprimió
        if(!((Character.isLetter(c)||(c==java.awt.event.KeyEvent.VK_BACK_SPACE)||(c==java.awt.event.KeyEvent.VK_DELETE)||(c==java.awt.event.KeyEvent.VK_SPACE)||(c==java.awt.event.KeyEvent.VK_TAB))))
        {
            int posCursor=this.txt_nombre.getCaretPosition();//obtiene posición del cursor en el momento de digitar el caracter
            this.manageMensajes("Caracter no válido en este campo", 1);
            this.nombre.deleteCharAt(posCursor-1);
            
            this.txt_nombre.clear();
            this.txt_nombre.setText(this.nombre.toString());
        }
        this.txt_nombre.positionCaret(this.nombre.length());
    }

    @FXML
    private void validaClave(KeyEvent event) 
    {
        this.clave=new StringBuilder(this.txt_clave.getText());
        char c=event.getCharacter().charAt(0);//Capturamos el caracter que se oprimió
        if(!((Character.isDigit(c)||(c==java.awt.event.KeyEvent.VK_BACK_SPACE)||(c==java.awt.event.KeyEvent.VK_DELETE)||(c==java.awt.event.KeyEvent.VK_TAB))))
        {
            int posCursor=this.txt_clave.getCaretPosition();//obtiene posición del cursor en el momento de digitar el caracter
            this.manageMensajes("Caracter no válido en este campo", 1);
            this.clave.deleteCharAt(posCursor-1);
            
            this.txt_clave.clear();
            this.txt_clave.setText(this.clave.toString());
        }
        this.txt_clave.positionCaret(this.clave.length());
    }

    @FXML
    private void doBuscar(ActionEvent event) 
    {
        String id;
        int posi=1;
        boolean existe=false;
        
        id=this.txt_id.getText();
        if(id.isEmpty())
            this.manageMensajes("Debe digitar el ID del cliente a buscar!!!", 1);
        else
        {
            for(Cliente cliente:this.clientes)
            {
                posi++;
                if(cliente.getIdentificacion().equals(id))
                {
                    this.poneCliente(cliente);
                    this.idActual=cliente.getIdentificacion();
                    this.claveActual=cliente.getClave();
                    this.pos=posi;
                    existe=true;
                    break;
                }
            }
            if(!existe)
                this.manageMensajes("No existe un cliente con este ID!!!", 1);
        }
    }

    @FXML
    private void doRegresar(ActionEvent event) 
    {
        try
        {
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/vista/principal.fxml"));
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
    private void doGuardaNuevo(ActionEvent event) 
    {
        String id,name,clave;
        char gender='*';
        boolean produs[]={false,false,false,false,false};
        String errores="";
        int conta=0;
        boolean existe;
        
        id=this.txt_id.getText();
        if(id.isEmpty())
            errores+="El campo ID está vacio!!! \n";
        name=this.txt_nombre.getText();
        if(name.isEmpty())
            errores+="El campo NOMBRE está vacio!!! \n";
        clave=this.txt_clave.getText();
        if(clave.isEmpty())
            errores+="El campo CLAVE está vacio!!! \n";
        
        if(this.rbt_hombre.isSelected())
            gender='M';
        if(this.rbt_mujer.isSelected())
            gender='F';
        if(this.rbt_otro.isSelected())
            gender='O';
        
        if(this.chk_ahorros.isSelected())
            produs[0]=true;
        if(this.chk_corriente.isSelected())
            produs[1]=true;
        if(this.chk_cdt.isSelected())
            produs[2]=true;
        if(this.chk_visa.isSelected())
            produs[3]=true;
        if(this.chk_american.isSelected())
            produs[4]=true;
        
        //Verificar si seleccionó al menos un producto
        for(int i=0;i<produs.length;i++)
        {
            if(produs[i]==true)
                conta++;
        }
        if(conta==0)
            errores+="Debe elegir por lo menos un producto!";
        
        if(errores.length()==0)
        {
            Cliente cliente=new Cliente(id,name,clave,this.laFoto,gender,produs);
            if(this.idActual.equals("Nuevo"))//Creando nuevo
            {
                if(this.gesCli.pruebaExistencia(id))
                    this.manageMensajes("Ese ID ya existe!!", 1);
                if(this.gesCli.pruebaExistenciaClave(clave))
                    this.manageMensajes("Esta clave ya existe!!", 1);
                else
                {
                    if(this.gesCli.guardaCliente(cliente)==true)
                    {
                        this.manageMensajes("El cliente se ha creado exitosamente! :)", 2);
                        this.traeClientes();
                    }
                    else
                        this.manageMensajes("Fallo creando el cliente, por favor revise!", 1);
                }
            }
            else//Modificación
            {
                if (!id.equals(idActual) && gesCli.pruebaExistencia(id)) 
                {
                    manageMensajes("Ese ID ya existe!!", 1);
                    this.txt_id.setText(this.idActual);
                }
                else 
                {
                    Iterator<Cliente> iter=this.clientes.iterator();
                    int pos=-1;
                    while(iter.hasNext())
                    {
                        pos++;
                        if(iter.next().getIdentificacion().equals(this.idActual))
                        {
                            iter.remove();
                            this.clientes.add(pos,cliente);
                            break;
                        }
                    }
                    this.productos=this.gesPro.getTodos();
                    for(Producto producto:this.productos)
                    {
                        if(producto.getIdCliente().equals(this.idActual))
                        {
                            producto.setIdCliente(id);
                        }
                    }
                    this.gesPro.reemplazaArchivo(this.productos);
                    this.gesCli.reemplazaArchivo(this.clientes);
                    this.manageMensajes("El cliente ha sido modificado exitosamente! :)", 2);
                    this.traeClientes();
                }
            }
        }
        else
            this.manageMensajes(errores, 1);
    }
    
    @FXML
    private void doVertodos(ActionEvent event) 
    {
        try
        {
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/Vista/allClientes.fxml"));
            Parent root=loader.load();
            Scene scene=new Scene(root);
            
            //Si necesito mandarle cosas a la otra ventana le programo un método constructor() a la segunda ventana y por allí la mando
            AllClientesController controlVentana2=loader.getController(); //Para mandar información
            controlVentana2.setClientes(this.clientes);
            
            Stage stage=new Stage();
            stage.setOnCloseRequest(even->{even.consume();});
            stage.setResizable(false);
            stage.setTitle("Listado de Clientes");
            stage.setScene(scene);
            stage.show();
            
            Stage myStage=(Stage)this.btn_regresar.getScene().getWindow();
            myStage.close();
        }
        catch(IOException ex)   
        {
            Logger.getLogger(ClientesController.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    @FXML
    private void doEliminar(ActionEvent event) 
    {
        this.productos=this.gesPro.getTodos();
        boolean cuentaexiste=false;
        for(Cliente cliente:this.clientes)
        {
            if(cliente.getIdentificacion().equals(this.idActual))
            {
                cuentaexiste=true;
                break;
            }
        }
        if(cuentaexiste==false)
            {
                this.manageMensajes("No hay cuentas seleccionadas para eliminar!", 1);
                return;
            }
        
        boolean seguro;
        seguro=this.manageMensajes("¿Seguro que desea remover este cliente?", 3);
        if(seguro)
        {
            Iterator<Cliente> iter=this.clientes.iterator();
            while(iter.hasNext())
            {
                if(iter.next().getIdentificacion().equals(this.idActual))
                {
                    Iterator<Producto> iter2=this.productos.iterator();
                    while(iter2.hasNext())
                    {
                        Producto producto =iter2.next();
                        if(this.idActual.equals(producto.getIdCliente()))
                            iter2.remove();
                    }
                    iter.remove();
                    break;
                }
            }
            this.gesCli.reemplazaArchivo(this.clientes);
            this.manageMensajes("Cliente removido con éxito", 2);
            
            if(this.clientes.isEmpty())
                this.doLimpiar(event);
            else
                this.traeClientes();
            
            this.gesPro.reemplazaArchivo(this.productos);
        }
    }

    @FXML
    private void doPatras(ActionEvent event) 
    {
        if(this.pos>0)
        {
            this.pos--;
            Cliente anterior =this.clientes.get(this.pos);
            this.poneCliente(anterior);
            this.idActual=anterior.getIdentificacion();
        }
    }

    @FXML
    private void doPalante(ActionEvent event) 
    {
        if(this.pos<this.clientes.size()-1)
        {
            this.pos++;
            Cliente siguiente =this.clientes.get(this.pos);
            this.poneCliente(siguiente);
            this.idActual=siguiente.getIdentificacion();
        }
    }

    @FXML
    private void doBuscaFoto(ActionEvent event) 
    {
        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("Buscar Imagen");
        fileChooser.setInitialDirectory(new File("././Imagenes/"));
        
        //obtener la imagen seleccionada
        File imgFile=fileChooser.showOpenDialog(new Stage());
        this.laFoto=imgFile.getName();
        
        //Mostrar la imagen
        if(this.laFoto!=null)
        {
            Image image=new Image("file:"+imgFile.getAbsolutePath());
            this.img_foto.setImage(image);
        }
    }

    @FXML
    private void doLimpiar(ActionEvent event) 
    {
        this.txt_id.clear();
        this.txt_nombre.clear();
        this.txt_clave.clear(); 
        this.rbt_hombre.setSelected(true);
        this.rbt_mujer.setSelected(false);
        this.rbt_otro.setSelected(false);
        this.limpiaChecks();
        this.laFoto="sinrostro.jpg";
        this.poneFoto();
        this.txt_id.requestFocus();
        this.idActual="Nuevo";
    }
   
    
    //Métodos locales o creados desde cero para ahorrar líneas de código
    private void traeClientes()
    {
        this.pos=0;
        
        this.clientes=this.gesCli.getTodos();
        
        if(!this.clientes.isEmpty())
        {
            Cliente primero=this.clientes.get(this.pos);
            this.poneCliente(primero);
            this.idActual=primero.getIdentificacion();
        }
        else
            this.idActual="Nuevo";
    }
    private void poneCliente(Cliente cliente)
    {
        boolean productos[];
        this.limpiaChecks();
        
        this.txt_id.setText(cliente.getIdentificacion());
        this.txt_nombre.setText(cliente.getNombre());
        this.txt_clave.setText(cliente.getClave());
        
        if(cliente.getGenero()=='M')
            this.rbt_hombre.setSelected(true);
        if(cliente.getGenero()=='F')
            this.rbt_mujer.setSelected(true);
        if(cliente.getGenero()=='O')
            this.rbt_otro.setSelected(true);
        
        productos=cliente.getProductos();
        if(productos[0]==true)//ahorros
            this.chk_ahorros.setSelected(true);
        if(productos[1]==true)//corriente
            this.chk_corriente.setSelected(true);
        if(productos[2]==true)//CDT
            this.chk_cdt.setSelected(true);
        if(productos[3]==true)//Visa
            this.chk_visa.setSelected(true);
        if(productos[4]==true)//American
            this.chk_american.setSelected(true);
        
        this.laFoto=cliente.getFoto();
        this.poneFoto();
    }
    private void limpiaChecks()
    {
        this.chk_ahorros.setSelected(false);
        this.chk_corriente.setSelected(false);
        this.chk_cdt.setSelected(false);
        this.chk_visa.setSelected(false);
        this.chk_american.setSelected(false);
    }
    private void poneFoto()
    {
        File imgFile=new File(this.rutaImages+this.laFoto);
        String url=imgFile.toURI().toString();
        Image image=new Image(url,true);
        this.img_foto.setImage(image);
    }
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
}