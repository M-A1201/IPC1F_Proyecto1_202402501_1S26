package InventarioTiendaRopa.controllers;

import InventarioTiendaRopa.models.InventarioTiendaRopaModel;

/**
 *
 * @author Manuel
 */
public class InventarioTiendaRopaController {
    // vector de productos

    private InventarioTiendaRopaModel[] inventario = new InventarioTiendaRopaModel[100];

    private int contador = 0;

    // metodo para agregar producto
    public boolean agregarProducto(InventarioTiendaRopaModel producto) {

        // verificar si el id ya existe
        if (buscarPorId(producto.getId()) != null) {
            return false;
        }

        inventario[contador] = producto;
        contador++;

        return true;
    }

    // buscar producto por id
    public InventarioTiendaRopaModel buscarPorId(int id) {

        for (int i = 0; i < contador; i++) {

            if (inventario[i].getId() == id) {
                return inventario[i];
            }

        }

        return null;
    }

    // --- NUEVO MÉTODO: registrar venta ---
   public boolean registrarVenta(int id, int cantidadVendida) {

    InventarioTiendaRopaModel producto = buscarPorId(id);

    if (producto != null && producto.getCantidad() >= cantidadVendida) {

        // restar stock
        producto.setCantidad(producto.getCantidad() - cantidadVendida);

        // calcular total
        double total = producto.getPrecio() * cantidadVendida;

        try {

            java.io.FileWriter fw = new java.io.FileWriter("ventas.txt", true);
            java.io.PrintWriter pw = new java.io.PrintWriter(fw);

            java.text.SimpleDateFormat formato =
                    new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            String fecha = formato.format(new java.util.Date());

            pw.println("Producto: " + producto.getNombre());
            pw.println("ID: " + id);
            pw.println("Cantidad: " + cantidadVendida);
            pw.println("Total: " + total);
            pw.println("Fecha: " + fecha);
            pw.println("---------------------------");

            pw.close();

        } catch (Exception e) {

            System.out.println("Error al guardar la venta");

        }

        return true;
    }

    return false;

   }
   //metodo para eliminar producto
   public boolean eliminarProducto(int id) {

    for (int i = 0; i < contador; i++) {

        if (inventario[i].getId() == id) {

            // mover los elementos una posición atrás
            for (int j = i; j < contador - 1; j++) {

                inventario[j] = inventario[j + 1];

            }

            inventario[contador - 1] = null;
            contador--;

            return true;
        }

    }

    return false;
}
   
   
   
    // metodo para obtener todos los productos
    public InventarioTiendaRopaModel[] obtenerInventario() {
        return inventario;
    }

    public int getContador() {
        return contador;
    }
    // metodo para generar reporte HTML
    public void generarReporteHTML() {

        try {

        java.text.SimpleDateFormat formatoNombre = new java.text.SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");

String nombreArchivo = formatoNombre.format(new java.util.Date()) + "_Stock.html";

java.io.PrintWriter writer = new java.io.PrintWriter(nombreArchivo, "UTF-8");
            writer.println("<html>");
            writer.println("<head>");
            writer.println("<title>Reporte de Inventario</title>");
            writer.println("</head>");
            writer.println("<body>");

            writer.println("<h1>Inventario de Tienda de Ropa</h1>");

            writer.println("<table border='1'>");
            writer.println("<tr>");
            writer.println("<th>ID</th>");
            writer.println("<th>Nombre</th>");
            writer.println("<th>Categoria</th>");
            writer.println("<th>Cantidad</th>");
            writer.println("<th>Precio</th>");
            writer.println("</tr>");

            for (int i = 0; i < contador; i++) {

                writer.println("<tr>");
                writer.println("<td>" + inventario[i].getId() + "</td>");
                writer.println("<td>" + inventario[i].getNombre() + "</td>");
                writer.println("<td>" + inventario[i].getCategoria() + "</td>");
                writer.println("<td>" + inventario[i].getCantidad() + "</td>");
                writer.println("<td>" + inventario[i].getPrecio() + "</td>");
                writer.println("</tr>");

            }

            writer.println("</table>");
            writer.println("</body>");
            writer.println("</html>");

            writer.close();

            System.out.println("Reporte generado correctamente");

        } catch (Exception e) {

            System.out.println("Error al generar reporte");

        }
    }
    
     //metodo para generar reporte de venta en HTML 
 public void generarReporteVentasHTML() {
    try {

        java.text.SimpleDateFormat formatoNombre =
                new java.text.SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");

        String nombreArchivo = formatoNombre.format(new java.util.Date()) + "_Venta.html";

        java.io.BufferedReader reader =new java.io.BufferedReader(new java.io.FileReader("ventas.txt"));

        java.io.PrintWriter writer = new java.io.PrintWriter(nombreArchivo, "UTF-8");

        writer.println("<html>");
        writer.println("<head>");
        writer.println("<title>Reporte de Ventas</title>");
        writer.println("</head>");
        writer.println("<body>");

        writer.println("<h1>Historial de Ventas</h1>");
        writer.println("<pre>");

        String linea;

        while ((linea = reader.readLine()) != null) {

            writer.println(linea);

        }

        writer.println("</pre>");
        writer.println("</body>");
        writer.println("</html>");

        reader.close();
        writer.close();

        System.out.println("Reporte de ventas generado");

    } catch (Exception e) {

        System.out.println("Error al generar reporte de ventas");

    }


    }

}
