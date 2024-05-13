/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gestion;

import Negocio.Cliente;
import java.io.BufferedReader;
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


public class GestionClientes 
{
    private String rutaClientes;

    public GestionClientes() 
    { //contructor, se va desde el contructor de entrada hasta este constructor, Cuando se corre el programa se crea el archivo
        this.rutaClientes = "./archivos/misClientes.txt"; // "./" significa la carpeta donde el usuario decida poner el proyecto
        this.verificarArchivo();     
    }
    private void verificarArchivo() 
    {
        try 
        {
            File filex = new File(this.rutaClientes); // Objeto de la clase File para verificar que el archivo exista
            if (!filex.exists()) { // Si no existe
                filex.createNewFile(); // Crea el archivo
            }
        } catch (IOException ex) 
        {
            JOptionPane.showMessageDialog(null, "Fallo, buscando archivo..."); // Mensaje para el usuario
            ex.printStackTrace(); // Imprime el problema en la consola
            Logger.getLogger(GestionClientes.class.getName()).log(Level.SEVERE, null, ex); // Se registra el error en un archivo
        }
    }

    public boolean guardaCliente(Cliente cliente)
           
    {
        boolean ok=false;
        File file= new File (this.rutaClientes); // cada que se usen archivos se usa File, para metro rutaClientes
        try 
        {
            FileWriter fr= new FileWriter(file,true); //Objeto de la clase file writer, se manda el obejto file, y un boleano, si es true se escribe de ultimas, su parametro es el objeto anterior y un booleano
            PrintWriter ps= new PrintWriter(fr);// ese si escribe en el archivo  su parametro es el objeto anterior
            ps.println(cliente); //objeto creado en el metodo de arriba
            ps.close();//siempre se tiene que cerrar
        ok=true;
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(GestionClientes.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog( null, "Fallo guardando el cliente");
        } 
        return ok;    
    } 
    
    public Cliente buscarCliente (String identificacion)//devuelve un objeto tipo cliente y recibe el id
    {
        Cliente cliente=null;//ahi se carga el cliente
        FileReader file;
        String registro;
        try 
        {
            file=new FileReader(rutaClientes);
            Scanner lector=new Scanner(file);
            while(lector.hasNextLine()) //se usa while porque no se sabe cuantas clientes hay, y lo otro es que escriba hasta donde hay null
            {
                registro=lector.nextLine();
                String[] campos= registro.split(","); // se rompe el string cada coma que hay
                 if(campos[0].equals(identificacion))
                {
                    boolean produs []= {Boolean.parseBoolean(campos[5]),Boolean.parseBoolean(campos[6]),Boolean.parseBoolean(campos[7]),Boolean.parseBoolean(campos[8]),Boolean.parseBoolean(campos[9])};
                    cliente=new Cliente(campos[0],campos[1],campos[2],campos[3],campos[4].charAt(0),produs);// se crea el objeto cliente con la info ya rota
                    break;
                }
            }
        } 
        catch (FileNotFoundException ex) 
        {
            JOptionPane.showMessageDialog(null, "Fallo buscando el cliente en el archivo");
            Logger.getLogger(GestionClientes.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return cliente; //retorna al contenedor
    }
    public Cliente buscarClave(String clave)
    {
        Cliente cliente=null;
        FileReader file;
        String registro;
        try 
        {
            file=new FileReader(rutaClientes);
            Scanner lector=new Scanner(file);
            while(lector.hasNextLine()) //se usa while porque no se sabe cuantas clientes hay, y lo otro es que escriba hasta donde hay null
            {
                registro=lector.nextLine();
                String[] campos= registro.split(","); // se rompe el string cada coma que hay
                 if(campos[2].equals(clave))
                {
                    boolean produs []= {Boolean.parseBoolean(campos[5]),Boolean.parseBoolean(campos[6]),Boolean.parseBoolean(campos[7]),Boolean.parseBoolean(campos[8]),Boolean.parseBoolean(campos[9])};
                    cliente=new Cliente(campos[0],campos[1],campos[2],campos[3],campos[4].charAt(0),produs);// se crea el objeto cliente con la info ya rota
                    break;
                }
            }
        }
        catch (FileNotFoundException ex) 
        {
           JOptionPane.showMessageDialog(null, "Fallo buscando el cliente en el archivo");
            Logger.getLogger(GestionClientes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cliente ;
        
    }
    
    public ArrayList <Cliente> getTodos()//construye un array con las clientes que hay
    {
        ArrayList <Cliente> clientes= new ArrayList();
        FileReader file;
        String registro;
        try 
        {
            
            file=new FileReader(rutaClientes); //objrto file reader
            Scanner lector=new Scanner(file); // objeto tipo scanner lector y como paramentr5o tiene el archivo
            
            while(lector.hasNextLine()) //se usa while porque no se sabe cuantas clientes hay, mientras tenga lineas lealas
            {
                registro=lector.nextLine(); //carga la linea en registro
                String[] campos= registro.split(","); // se rompe el string cada coma que hay
                boolean produs []= {Boolean.parseBoolean(campos[5]),Boolean.parseBoolean(campos[6]),Boolean.parseBoolean(campos[7]),Boolean.parseBoolean(campos[8]),Boolean.parseBoolean(campos[9])};
                Cliente cliente=new Cliente(campos[0],campos[1],campos[2],campos[3],campos[4].charAt(0),produs);// se crea el objeto cliente con la info ya rota
                clientes.add(cliente);// se añade el objeto al arraylist
            }
        } 
        catch (FileNotFoundException ex) 
        {
            JOptionPane.showMessageDialog(null, "Fallo cargando los clientes del archivo");
            Logger.getLogger(GestionClientes.class.getName()).log(Level.SEVERE, null, ex);
        }
    return clientes; //retorna al contenedor
    }
    
    public boolean pruebaExistencia (String identificacion)//devuelve un objeto tipo cliente y recibe el id
    {
        boolean existe=false;//ahi se carga el cliente
        FileReader file;
        String registro;
        try 
        {
            file=new FileReader(rutaClientes);
            Scanner lector=new Scanner(file);
            while(lector.hasNextLine()) //se usa while porque no se sabe cuantas clientes hay, y lo otro es que escriba hasta donde hay null
            {
                registro=lector.nextLine();
                String[] campos= registro.split(","); // se rompe el string cada coma que hay
                 if(campos[0].equals(identificacion))
                {
                existe=true; 
                break;
                }
            }
        } 
        catch (FileNotFoundException ex) 
        {
            JOptionPane.showMessageDialog(null, "Fallo buscando el cliente en el archivo");
            Logger.getLogger(GestionClientes.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return existe; 
        }
    public boolean pruebaExistenciaClave (String clave)//devuelve un objeto tipo cliente y recibe el id
    {
        boolean existe=false;//ahi se carga el cliente
        FileReader file;
        String registro;
        try 
        {
            file=new FileReader(rutaClientes);
            Scanner lector=new Scanner(file);
            while(lector.hasNextLine()) //se usa while porque no se sabe cuantas clientes hay, y lo otro es que escriba hasta donde hay null
            {
                registro=lector.nextLine();
                String[] campos= registro.split(","); // se rompe el string cada coma que hay
                 if(campos[2].equals(clave))
                {
                existe=true; 
                break;
                }
            }
        } 
        catch (FileNotFoundException ex) 
        {
            JOptionPane.showMessageDialog(null, "Fallo buscando el cliente en el archivo");
            Logger.getLogger(GestionClientes.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return existe; 
        }
    
        public void reemplazaArchivo(ArrayList <Cliente> clientes)
    {
        try
        {
            File file = new File(rutaClientes);
            FileWriter fr= new FileWriter (file,false);//true es para agregar al final, false es para borrar todo
            PrintWriter ps= new PrintWriter(fr);
            for(Cliente cliente:clientes)
                ps.println(cliente);//escribe la cliente en el archivo
            ps.close();
        }
        catch (IOException ioe)
        {
            System.out.println("Fallo cargando el cleinte en el archivo");
            System.exit(1);
        }
    }
    public void deleteCliente(String identificacion) 
    {
       
        ArrayList<Cliente> clientes =this.getTodos();
       
        for (Cliente cliente : clientes) 
        {
            if (cliente.getIdentificacion().equals(identificacion)) 
            {
                clientes.remove(cliente);
            }
        }
        this.reemplazaArchivo(clientes);
        JOptionPane.showMessageDialog(null, "Eliminacion existosa");  
    }
}
