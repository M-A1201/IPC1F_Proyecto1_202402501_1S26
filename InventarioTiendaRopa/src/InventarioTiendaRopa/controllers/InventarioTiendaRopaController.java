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
            producto.setCantidad(producto.getCantidad() - cantidadVendida);
            // Aquí luego podrías guardar la venta en un archivo si quieres
            return true;
        }

        return false; // No existe o stock insuficiente
    }

    // AGREGAMOS EL NUEVO MÉTODO PARA ELIMINAR PRODUCTO
    // --------------------------
    public boolean eliminarProducto(int id) {
        for (int i = 0; i < contador; i++) {
            if (inventario[i].getId() == id) {
                // Desplazar los elementos para "eliminar" el producto
                for (int j = i; j < contador - 1; j++) {
                    inventario[j] = inventario[j + 1];
                }
                inventario[contador - 1] = null; // limpiar el último espacio
                contador--;
                return true; // producto eliminado
            }
        }
        return false; // producto no encontrado
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

            java.io.PrintWriter writer = new java.io.PrintWriter("reporteInventario.html", "UTF-8");

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

}
