
package Negocio;

public class Producto 
{
    private String idProducto;    
    private String tipoProducto;
    private String idCliente;
    private String fecha;
    private String producto;
    private float valorUno;
    private int valorDos;
    private boolean activo;

    public Producto(String idProducto, String tipoProducto, String idCliente, String fecha, float valorUno, int valorDos) 
    {
        this.idProducto = idProducto;
        this.tipoProducto = tipoProducto;
        this.idCliente = idCliente;
        this.fecha = fecha;
        this.valorUno = valorUno;
        this.valorDos = valorDos;
    }

    public String getIdProducto() 
    {
        return idProducto;
    }

    public String getTipoProducto() 
    {
        return tipoProducto;
    }

    public void setIdCliente(String idCliente) 
    {
        this.idCliente = idCliente;
    }

    public String getIdCliente() 
    {
        return idCliente;
    }

    public String getFecha() 
    {
        return fecha;
    }

    public float getValorUno() 
    {
        return valorUno;
    }

    public int getValorDos() 
    {
        return valorDos;
    }
    
    private void ProductoNombre() 
    {
        switch (tipoProducto) 
        {
            case "1":
                producto = "Cta. Ahorros";
                break;
            case "2":
                producto = "Cta. Corriente";
                break;
            case "3":
                producto = "CDT";
                break;
            case "4":
                producto = "Tarj. Visa";
                break;
            case "5":
                producto = "Tarj. American";
                break;
            default:
                producto = "Tipo de producto no reconocido";
        }
    }
    public String getProducto()
    {
        this.ProductoNombre();
        return producto;
    }

    public void setValorUno(float valorUno) 
    {
        this.valorUno = valorUno;
    }
    
    @Override
    public String toString() 
    {
        return this. idProducto + "," + this.tipoProducto + "," + this.idCliente + "," + this.fecha + "," + this.valorUno + "," + this.valorDos;
    }
}
