/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gestion;

import Negocio.Producto;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class GestionProductos 
{
    private String rutaProductos;
    
    public GestionProductos() 
    { 
        this.rutaProductos = "./archivos/misProductos.txt"; 
        this.verificarArchivo();     
    }
    private void verificarArchivo() 
    {
        try 
        {
            File filex = new File(this.rutaProductos); // Objeto de la clase File para verificar que el archivo exista
            if (!filex.exists()) 
            {
                filex.createNewFile(); // Crea el archivo
            }
        }
        catch (IOException ex) 
        {
            JOptionPane.showMessageDialog(null, "Fallo buscando archivo...");
            ex.printStackTrace();
            Logger.getLogger(GestionProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public boolean guardaProducto(Producto producto)     
    {
        boolean ok=false;
        File file= new File (this.rutaProductos); // cada que se usen archivos se usa File, para metro rutaClientes
        try 
        {
            FileWriter fr= new FileWriter(file,true);
            PrintWriter ps= new PrintWriter(fr);
            ps.println(producto);
            ps.close();
        ok=true;
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(GestionProductos.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog( null, "Fallo guardando el producto");
        } 
        return ok;    
    }
    public Producto buscarProducto (String id)//devuelve un obejto tipo cliente y recibe el id
    {
        Producto producto=null;//ahi se carga el cliente
        FileReader file;
        String registro;
        try 
        {
            file=new FileReader(rutaProductos);
            Scanner lector=new Scanner(file);
            while(lector.hasNextLine()) //se usa while porque no se sabe cuantas clientes hay, y lo otro es que escriba hasta donde hay null
            {
                registro=lector.nextLine(); 
                String[] campos= registro.split(","); // se rompe el string cada coma que hay
                 if(campos[2].equals(id))
                {
                    producto=new Producto(campos[0],campos[1],campos[2],campos[3],Float.parseFloat(campos[4]),Integer.parseInt(campos[5]));// se crea el objeto producto con la info ya rota
                    break;
                }
            }
            lector.close();
        } 
        catch (FileNotFoundException ex) 
        {
            JOptionPane.showMessageDialog(null, "Fallo buscando el cleinte en el archivo");
            Logger.getLogger(GestionClientes.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return producto; //retorna al contenedor
    }
    
    public ArrayList <Producto> getTodos()//construye un array con las clientes que hay
    {
        ArrayList <Producto> productos= new ArrayList();
        FileReader file;
        String registro;
        try 
        {
            
            file=new FileReader(rutaProductos); //objrto file reader
            Scanner lector=new Scanner(file); // objeto tipo scanner lector y como paramentr5o tiene el archivo
            
            while(lector.hasNextLine()) //se usa while porque no se sabe cuantas clientes hay, mientras tenga lineas lealas
            {
                registro=lector.nextLine(); //carga la linea en registro
                String[] campos= registro.split(","); // se rompe el string cada coma que hay
                Producto producto=new Producto(campos[0],campos[1],campos[2],campos[3],Float.parseFloat(campos[4]),Integer.parseInt(campos[5]));
                productos.add(producto);// se añade el objeto al arraylist
            }
        } 
        catch (FileNotFoundException ex) 
        {
            JOptionPane.showMessageDialog(null, "Fallo cargando los productos del archivo");
            Logger.getLogger(GestionProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
    return productos; //retorna al contenedor
    }
    
    public boolean pruebaExistencia (String idProducto)//devuelve un obejto tipo producto y recibe el id
    {
        boolean existe=false;//ahi se carga el cliente
        FileReader file;
        String registro;
        try 
        {
            file=new FileReader(rutaProductos);
            Scanner lector=new Scanner(file);
            while(lector.hasNextLine()) //se usa while porque no se sabe cuantas clientes hay, y lo otro es que escriba hasta donde hay null
            {
                registro=lector.nextLine();
                String[] campos= registro.split(","); // se rompe el string cada coma que hay
                 if(campos[0].equals(idProducto))
                {
                existe=true; 
                break;
                }
            }
        } 
        catch (FileNotFoundException ex) 
        {
            JOptionPane.showMessageDialog(null, "Fallo buscando el producto en el archivo");
            Logger.getLogger(GestionClientes.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return existe; 
        }
    
        public void reemplazaArchivo(ArrayList <Producto> productos)
    {
        try
        {
            File file = new File(rutaProductos);
            FileWriter fr= new FileWriter (file,false);//true es para agregar al final, false es para borrar todo
            PrintWriter ps= new PrintWriter(fr);
            for(Producto cliente:productos)
                ps.println(cliente);//escribe la cliente en el archivo
            ps.close();
        }
        catch (IOException ioe)
        {
            System.out.println("Fallo cargando el cliente en el archivo");
            System.exit(1);
        }
    }
    public void deleteProducto(String idProducto) 
    {
       
        ArrayList<Producto> productos =this.getTodos();
       
        for (Producto producto : productos) 
        {
            if (producto.getIdProducto().equals(idProducto)) 
            {
                productos.remove(producto);
            }
        }
        this.reemplazaArchivo(productos);
        JOptionPane.showMessageDialog(null, "Eliminacion existosa");  
    }    
}