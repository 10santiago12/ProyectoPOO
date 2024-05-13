/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Control;

import Gestion.GestionClientes;
import Gestion.GestionProductos;
import Negocio.Cliente;
import Negocio.Producto;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author surre
 */
public class TransaccionesController implements Initializable {

    @FXML
    private Button btn_regresar;
    @FXML
    private Button btn_continuar;
    @FXML
    private Pane pane_1;
    @FXML
    private Pane pane_2;
    @FXML
    private PasswordField psw_clave;
    @FXML
    private Label lbl_nombre;
    @FXML
    private Label lbl_pagos;
    @FXML
    private Label lbl_saldos;
    @FXML
    private Label lbl_avance;
    @FXML
    private Label lbl_retiro;
    @FXML
    private Label lbl_deposito;
    @FXML
    private Label lbl_compra;
    @FXML
    private Label lbl_cambioClave;
    @FXML
    private Label lbl_salir;
    @FXML
    private Button btn_retiro;
    @FXML
    private Button btn_avance;
    @FXML
    private Button btn_saldos;
    @FXML
    private Button btn_pagos;
    @FXML
    private Button btn_deposito;
    @FXML
    private Button btn_compra;
    @FXML
    private Button btn_cambioClave;
    @FXML
    private Button btn_salir;
    @FXML
    private ComboBox<String> cbx_productos;

    private StringBuilder clave;
    private GestionClientes gesCli;
    private GestionProductos gesPro;
    private Cliente cliente;
    private Producto producto;
    private Producto selected;
    private ArrayList<Cliente> clientes;
    private ArrayList<Producto> productos;
    private TextInputDialog inputDialog;
    private String tipoProducto;
    private Float nuevoSaldo;

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        this.gesCli = new GestionClientes();
        this.gesPro= new GestionProductos();
        this.inputDialog = new TextInputDialog();
    }    
    @FXML
    private void validaClave(KeyEvent event) 
    {
        this.clave=new StringBuilder(this.psw_clave.getText());
        char c=event.getCharacter().charAt(0);//Capturamos el caracter que se oprimió
        if(!((Character.isDigit(c)||(c==java.awt.event.KeyEvent.VK_BACK_SPACE)||(c==java.awt.event.KeyEvent.VK_DELETE)|(c==java.awt.event.KeyEvent.VK_ENTER))))
        {
            int posCursor=this.psw_clave.getCaretPosition();//obtiene posición del cursor en el momento de digitar el caracter
            this.manageMensajes("Caracter no válido en este campo", 1);
            this.clave.deleteCharAt(posCursor-1);

            this.psw_clave.clear();
            this.psw_clave.setText(this.clave.toString());
        }
        this.psw_clave.positionCaret(this.clave.length());
    }
    @FXML
    private void do_regresar(MouseEvent event) 
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
            Logger.getLogger(TransaccionesController.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    @FXML
    private void do_continuar(MouseEvent event) 
    {
        String clave;
        clave=this.psw_clave.getText();
        boolean existe=false;
        this.clientes=this.gesCli.getTodos();

        if(clave.isEmpty())
            this.manageMensajes("Por favor digite su clave!!!", 1);
        else
        {
            this.cliente=this.gesCli.buscarClave(clave);
            if (this.cliente != null) 
            {
                this.pane_1.setVisible(false);
                this.pane_2.setVisible(true);
                this.inicial();
                this.llenaCombos();
                existe = true;
            }
            if (!existe) 
            {
                this.manageMensajes("Clave errónea.", 1);
                this.psw_clave.clear();
                this.psw_clave.requestFocus();
            } 
        }
        for(Cliente cliente:this.clientes)
        {
            if(cliente.getClave().equals(clave))
            {
               this.lbl_nombre.setText(cliente.getNombre().toUpperCase());
            }
        }
    }

    @FXML
    private void do_retiro(MouseEvent event) 
    {
        this.buscaProducto();
        Float saldo,retiro;
        tipoProducto=this.cbx_productos.getSelectionModel().getSelectedItem();
        
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Retiro");
        dialog.setHeaderText("Por favor, ingrese el monto a retirar (En miles):");
        Optional<String> result = dialog.showAndWait();
        
        if (result.isPresent()) 
        {
            String retiroInput = result.get();

            if (!validarEntrada(retiroInput)) 
            {
                this.manageMensajes("Transacción rechazada! Caracter no válido en este campo", 1);
                return;
            }
            saldo=this.producto.getValorUno();
            retiro = Float.parseFloat(result.get());
            
            if(retiro>saldo)
            {
                this.manageMensajes("Transacción rechazada! Fondos insuficientes :(", 1);
            }
            else
            {
                this.nuevoSaldo=saldo-retiro;
                this.producto.setValorUno(nuevoSaldo);
                this.manageMensajes("Retiro exitoso!!!", 2);
                this.gesPro.reemplazaArchivo(this.productos);
            }
        }
    }

    @FXML
    private void do_avance(MouseEvent event) 
    {
        this.buscaProducto();
        Float saldo,retiro;
        tipoProducto=this.cbx_productos.getSelectionModel().getSelectedItem();
        
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Avance");
        dialog.setHeaderText("Por favor, ingrese el monto a retirar (En millones):");
        Optional<String> result = dialog.showAndWait();
        
        if (result.isPresent()) 
        {
            String avanceInput = result.get();

            if (!validarEntrada(avanceInput)) 
            {
                this.manageMensajes("Transacción rechazada! Caracter no válido en este campo", 1);
                return;
            }
            saldo=this.producto.getValorUno();
            retiro = Float.parseFloat(result.get());
            
            if(retiro>saldo)
            {
                this.manageMensajes("Transacción rechazada! Fondos insuficientes :(", 1);
            }
            else
            {
                this.nuevoSaldo=saldo-retiro; 
                this.producto.setValorUno(nuevoSaldo);
                this.manageMensajes("Transacción aprobada!! Por favor, retire su dinero.", 2);
                this.gesPro.reemplazaArchivo(this.productos);
            }
        }
    }

    @FXML
    private void do_saldos(MouseEvent event) 
    {
        this.buscaProducto();
        nuevoSaldo=this.producto.getValorUno();
        tipoProducto=this.cbx_productos.getSelectionModel().getSelectedItem();
        String mensaje = "";
        if ((this.tipoProducto.equals("Cta. Ahorros")) || (this.tipoProducto.equals("Cta. Corriente"))) 
        {
            mensaje += "N° de Producto: " + this.producto.getIdProducto() + "\n";

            if (this.nuevoSaldo > 999) 
            {
                float saldoEnMillones = this.nuevoSaldo / 1000.0f;
                if (saldoEnMillones == 1.0f) 
                {
                    mensaje += "Saldo actual: $"+saldoEnMillones+ " Millón de pesos";
                } 
                else 
                {
                    mensaje += "Saldo actual: $" + String.format("%.2f", saldoEnMillones) + " Millones de pesos";
                }
            }
            else if(this.nuevoSaldo==0.0)
                mensaje += "Saldo actual: $"+this.nuevoSaldo+" Pesos";
            else 
            {
                mensaje += "Saldo actual: $" + this.nuevoSaldo + " Mil pesos";
            }
        }
        else if((this.tipoProducto.equals("Tarj. Visa")) || (this.tipoProducto.equals("Tarj. American")))
        {
            this.buscaProducto();
            mensaje += "N° de Producto: "+this.producto.getIdProducto()+"\n";
            if(this.nuevoSaldo==0.0)
                mensaje += "Saldo actual: $"+this.nuevoSaldo+" Pesos";
            else if(this.nuevoSaldo==1.0)
                mensaje += "Saldo actual: $"+this.nuevoSaldo+" Millón de pesos";
            else
                mensaje += "Saldo actual: $"+this.nuevoSaldo+" Millones de pesos";
        }
        this.manageMensajes(mensaje, 2);
    }

    @FXML
    private void do_pagos(MouseEvent event) 
    {
        this.buscaProducto();
        Float saldo;
        String retiro;
        tipoProducto=this.cbx_productos.getSelectionModel().getSelectedItem();
        
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Pagos");
        dialog.setHeaderText("Por favor, ingrese del pago a realizar (En millones):");
        Optional<String> result = dialog.showAndWait();
        
        if (result.isPresent()) 
        {
            String pagoInput = result.get();

            if (!validarEntrada(pagoInput)) 
            {
                this.manageMensajes("Transacción rechazada! Caracter no válido en este campo", 1);
                return;
            }
            saldo=this.producto.getValorUno();
            retiro = result.get();
            
            this.nuevoSaldo=saldo+Float.parseFloat(retiro);
            
            this.producto.setValorUno(nuevoSaldo);
            this.manageMensajes("Pago exitoso!!!", 2);
            this.gesPro.reemplazaArchivo(this.productos);
        }
    }

    @FXML
    private void do_deposito(MouseEvent event) 
    {
        this.buscaProducto();
        Float saldo;
        String retiro;
        tipoProducto=this.cbx_productos.getSelectionModel().getSelectedItem();
        
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Depósito");
        dialog.setHeaderText("Por favor, ingrese el monto que va a depositar (En miles):");
        Optional<String> result = dialog.showAndWait();
        
        if (result.isPresent()) 
        {
            String depositoInput = result.get();

            if (!validarEntrada(depositoInput)) 
            {
                this.manageMensajes("Transacción rechazada! Caracter no válido en este campo", 1);
                return;
            }
            saldo=this.producto.getValorUno();
            retiro = result.get();
            
            this.nuevoSaldo=saldo+Float.parseFloat(retiro);
            
            this.producto.setValorUno(nuevoSaldo);
            this.manageMensajes("Depósito exitoso!!!", 2);
            this.gesPro.reemplazaArchivo(this.productos);
        }
    }

    @FXML
    private void do_compra(MouseEvent event) 
    {
        this.buscaProducto();
        Float saldo,retiro;
        tipoProducto=this.cbx_productos.getSelectionModel().getSelectedItem();
        
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Compra");
        dialog.setHeaderText("Por favor, ingrese el valor de la compra (En millones):");
        Optional<String> result = dialog.showAndWait();
        
        if (result.isPresent()) 
        {
            String compraInput = result.get();

            if (!validarEntrada(compraInput)) 
            {
                this.manageMensajes("Transacción rechazada! Caracter no válido en este campo", 1);
                return;
            }
            saldo=this.producto.getValorUno();
            retiro = Float.parseFloat(result.get());
            
            if(retiro>saldo)
            {
                this.manageMensajes("Transacción rechazada! Fondos insuficientes :(", 1);
            }
            else
            {
                this.nuevoSaldo=saldo-retiro;
                this.producto.setValorUno(nuevoSaldo);
                this.manageMensajes("Transacción aprobada!! Compra exitosa :)", 2);
                this.gesPro.reemplazaArchivo(this.productos);
            }
        }
    }

    @FXML
    private void do_cambioClave(MouseEvent event) 
    {
        String newclave, clave;

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Cambio de Clave");
        dialog.setHeaderText("Digite la nueva clave");
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) 
        {
            
            String claveInput = result.get();

            if (!validarEntrada(claveInput)) 
            {
                this.manageMensajes("Operación rechazada! Caracter no válido en este campo", 1);
                return;
            }
            clave = this.psw_clave.getText();
            newclave = result.get();
            this.clientes = this.gesCli.getTodos();

            for (Cliente cliente : this.clientes) 
            {
               if(cliente.getClave().equals(clave))
               {
                    if (this.gesCli.pruebaExistenciaClave(newclave)) 
                    {
                        this.manageMensajes("Esta clave ya existe!!!", 1);
                    } 
                    else 
                    {
                        cliente.setClave(newclave);
                        this.manageMensajes("Clave modificada exitosamente!! :)", 2);
                    }
                    break;
               }
            }
            this.gesCli.reemplazaArchivo(this.clientes);
        }
    }


    @FXML
    private void doFiltroProducto(ActionEvent event) 
    {
        this.productos=this.gesPro.getTodos();
        tipoProducto=this.cbx_productos.getSelectionModel().getSelectedItem();
        if(tipoProducto!=null)
        {
            if(tipoProducto.equals("Cta. Ahorros")||tipoProducto.equals("Cta. Corriente"))
                this.cuentas();
            if(tipoProducto.equals("CDT"))
            {
                this.manageMensajes("No hay transacciones disponibles para este producto.", 2);
                this.inicial();
            }
            if(tipoProducto.equals("Tarj. Visa")||tipoProducto.equals("Tarj. American"))
                this.tarjetas();   
        }

    }

    //Métodos locales 
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

    private void traetodo()
    {
        this.productos=this.gesPro.getTodos();
        this.clientes=this.gesCli.getTodos();
    }
    private void inicial()
    {
        this.lbl_retiro.setDisable(true);
        this.btn_retiro.setDisable(true);
        this.lbl_avance.setDisable(true);
        this.btn_avance.setDisable(true);
        this.lbl_saldos.setDisable(true);
        this.btn_saldos.setDisable(true);
        this.lbl_pagos.setDisable(true);
        this.btn_pagos.setDisable(true);
        this.lbl_deposito.setDisable(true);
        this.btn_deposito.setDisable(true);
        this.lbl_compra.setDisable(true);
        this.btn_compra.setDisable(true);
    }
    private void cuentas()
    {
        this.inicial();
        this.lbl_retiro.setDisable(false);
        this.btn_retiro.setDisable(false);
        this.lbl_deposito.setDisable(false);
        this.btn_deposito.setDisable(false);
        this.lbl_saldos.setDisable(false);
        this.btn_saldos.setDisable(false);
    }
    private void tarjetas()
    {
        this.inicial();
        this.lbl_avance.setDisable(false);
        this.btn_avance.setDisable(false);
        this.lbl_compra.setDisable(false);
        this.btn_compra.setDisable(false);
        this.lbl_saldos.setDisable(false);
        this.btn_saldos.setDisable(false);
        this.lbl_pagos.setDisable(false);
        this.btn_pagos.setDisable(false);

    }
    private void llenaCombos()
    {
        this.productos = this.gesPro.getTodos();
        
        for(Producto producto: this.productos)
        {
            if(this.cliente.getIdentificacion().equals(producto.getIdCliente()))
                this.cbx_productos.getItems().add(producto.getProducto());
        }
    }
    private void buscaProducto() 
    {
        this.tipoProducto = this.cbx_productos.getSelectionModel().getSelectedItem();
        String clave = this.psw_clave.getText();
        this.traetodo();

        for (Cliente cliente : this.clientes) 
        {
            if (cliente.getClave().equals(clave)) 
            {
                for (Producto producto : this.productos) 
                {
                    if (cliente.getIdentificacion().equals(producto.getIdCliente())&& producto.getProducto().equals(this.tipoProducto)) 
                    {
                        this.producto = producto;
                    }
                }
            }
        }
    }
    private boolean validarEntrada(String entrada) 
    {
    for (char c : entrada.toCharArray()) {
        if (!(Character.isDigit(c) || c == '.' || c == java.awt.event.KeyEvent.VK_BACK_SPACE || c == java.awt.event.KeyEvent.VK_DELETE || c == java.awt.event.KeyEvent.VK_ENTER)) {
            return false;
        }
    }
    return true;
}

}