package com.example.pmdm_tema3_trabajofinal.model;

import java.util.Random;

public class Ofertas {

    //metodo para colocar ofertas aleatorias
    public Producto randomOferta(Producto producto){
        Random random = new Random();
        double precioProducto = producto.getPrecio();
        double precioOferta;
        //genera un numero aleatorio hasta 5
        int oferta = random.nextInt(5) + 1;
        //Si el numero es 5 tiene oferta y el porcentaje de descuento va dependiendo del precio
        if (oferta == 5){
            //Redondeamos a dos decimales con el Round
            if (producto.getPrecio()>=1000){
                precioOferta = precioProducto * 0.9;
               // producto.setPrecio(precioOferta);
                producto.setPrecioOferta(Math.round(precioOferta * 100.0) / 100.0);
            } else if (producto.getPrecio()<=1000 && producto.getPrecio()>=500) {
                precioOferta = precioProducto * 0.85;
               // producto.setPrecio(precioOferta);
                producto.setPrecioOferta(Math.round(precioOferta * 100.0) / 100.0);

            } else if (producto.getPrecio()<=500) {
                precioOferta = precioProducto * 0.8;
               // producto.setPrecio(precioOferta);
                producto.setPrecioOferta(Math.round(precioOferta * 100.0) / 100.0);
            } else  {
                precioOferta = precioProducto * 0.7;
                //producto.setPrecio(precioOferta);
                producto.setPrecioOferta(Math.round(precioOferta * 100.0) / 100.0);
            }
        }else {
            producto.setPrecioOferta(0);
        }
        return producto;
    }
}
