
package Negocio;

public class Cliente 
{
    private String identificacion;
    private String nombre;
    private String clave;
    private String foto;
    private char genero;
    private boolean productos[]; //Cta de ahorros, Cta Corriente, CDT, tarjeta Visa, tarjeta American

    public Cliente(String identificacion, String nombre, String clave, String foto, char genero, boolean[] productos) 
    {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.clave = clave;
        this.foto = foto;
        this.genero = genero;
        this.productos = productos;
    }

    public String getIdentificacion() 
    {
        return identificacion;
    }
    

    public String getNombre() 
    {
        return nombre;
    }

    public String getFoto() 
    {
        return foto;
    }

    public String getClave() 
    {
        return clave;
    }

    public void setClave(String clave) 
    {
        this.clave = clave;
    }

    public char getGenero() 
    {
        return genero;
    }

    public boolean[] getProductos() 
    {
        return productos;
    }

    public void setProductos(boolean[] productos) 
    {
        this.productos = productos;
    }
    

    @Override
    public String toString() 
    {
        String produs="";
        for(int i=0;i<this.productos.length;i++)
        {
            if(i<this.productos.length-1)
                produs+=this.productos[i]+",";
            else
                produs+=this.productos[i];
        }
        return this.identificacion + "," + this.nombre + "," + this.clave + ","+ this.foto + "," + this.genero+","+produs;
    }
}
