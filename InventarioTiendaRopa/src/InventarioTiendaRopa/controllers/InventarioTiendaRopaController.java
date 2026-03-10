
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
    
    
}
