package com.tup.programacion3;

import com.tup.programacion3.entities.Categoria;
import com.tup.programacion3.entities.Producto;
import com.tup.programacion3.entities.Usuario;
import com.tup.programacion3.enums.FormaPago;
import com.tup.programacion3.enums.Rol;
import com.tup.programacion3.entities.Pedido;

public class Main {
    public static void main(String[] args) {

        Usuario usuario1 = new Usuario("Martin", "Perez", "usuario1@example.com", "123456789", "15151515", Rol.USUARIO);
        Usuario usuario2 = new Usuario("Juan", "Gomez", "usuario2@example.com", "987654321", "15151516", Rol.ADMIN);

        Pedido pedido1 = new Pedido(FormaPago.EFECTIVO, usuario1);
        Pedido pedido2 = new Pedido(FormaPago.TRANSFERENCIA, usuario2);
        Pedido pedido3 = new Pedido(FormaPago.TARJETA, usuario1);

        Categoria categoria1 = new Categoria("Electrónica", "Dispositivos y gadgets electrónicos");
        Categoria categoria2 = new Categoria("Ropa", "Prendas de vestir y accesorios");
        Categoria categoria3 = new Categoria("Hogar", "Artículos y muebles para el hogar");

        Producto producto1 = new Producto("Smartphone", 1000.00, "", "", 10, true, categoria1);
        Producto producto2 = new Producto("Laptop", 1500.00, "", "", 5, true, categoria1);
        Producto producto3 = new Producto("Tablet", 600.00, "", "", 8, true, categoria1);
        Producto producto4 = new Producto("Cama Doble", 200.00, "", "", 15, true, categoria3);
        Producto producto5 = new Producto("Headphones", 100.00, "", "", 20, true, categoria1);
        Producto producto6 = new Producto("Camera", 800.00, "", "", 7, true, categoria1);
        Producto producto7 = new Producto("Printer", 300.00, "", "", 4, true, categoria1);
        Producto producto8 = new Producto("Campera", 250.00, "", "", 6, true, categoria2);
        Producto producto9 = new Producto("Keyboard", 50.00, "", "", 12, true, categoria1);
        Producto producto10 = new Producto("Mouse", 30.00, "", "", 18, true, categoria1);

        pedido1.addDetallePedido(2, producto1);
        pedido1.addDetallePedido(1, producto2);
        pedido2.addDetallePedido(3, producto3);
        pedido3.addDetallePedido(1, producto4);
        pedido3.addDetallePedido(2, producto5);
        pedido3.addDetallePedido(1, producto6);
        pedido2.addDetallePedido(1, producto7);
        pedido3.addDetallePedido(1, producto8);
        pedido3.addDetallePedido(1, producto9);
        pedido3.addDetallePedido(1, producto10);

        // Mostrar producto
        System.out.println("------------Mostrando producto individual------------\n");
        System.out.println(producto1);

        // Mostrar todos los productos de la categoría Electrónica
        System.out.println("\n------------Mostrando todos los productos ------------\n");

        for (Producto producto : Producto.getProductos()) {
            System.out.println(producto);
        }

        // Mostrar el usuario con mayor cantidad de pedidos y uss pedidos

        System.out.println("\n------------Usuario con más pedidos------------\n");
        Usuario usuarioConMasPedidos = null;
        int maxPedidos = -1;
        for (Usuario usuario : Usuario.getUsuarios()) {
            if (usuario.getPedidos().size() > maxPedidos) {
                maxPedidos = usuario.getPedidos().size();
                usuarioConMasPedidos = usuario;
            }
        }

        if (usuarioConMasPedidos != null) {
            System.out.println("Usuario con más pedidos: " + usuarioConMasPedidos.getNombre() + " "
                    + usuarioConMasPedidos.getApellido());
            System.out.println("Cantidad de pedidos: " + maxPedidos);
            for (Pedido pedido : usuarioConMasPedidos.getPedidos()) {
                System.out.println(pedido);
            }
        }

        // Crear un producto nuevo con el mismo campo utilizado por equals (id)
        Producto productoComparacion = new Producto();
        productoComparacion.setId(producto1.getId());

        System.out.println("\n------------Comparación mediante equals------------\n");
        System.out.println("ID del producto nuevo: " + productoComparacion.getId());

        for (Producto producto : Producto.getProductos()) {
            boolean sonIguales = productoComparacion.equals(producto);
            System.out.printf("Producto nuevo vs. %s (id=%d): %s%n",
                    producto.getNombre(),
                    producto.getId(),
                    sonIguales ? "SON IGUALES" : "NO SON IGUALES");
        }
    }
}
