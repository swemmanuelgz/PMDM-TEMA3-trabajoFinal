package com.example.pmdm_tema3_trabajofinal.model;

public class Producto  implements Comparable<Producto> {

    @Override
    public int compareTo(Producto producto) {
        //Compara los productos por su titulo
        return this.getTitulo().compareTo(producto.getTitulo());
    }

    private int id;
    private int fotoId;
    private String titulo;
    private String descripcion;
    private double precio;
    private double precioOferta;
    private int cantidad;



    public Producto() {

    }

    public Producto(int fotoId,int id,  String titulo, String descripcion, double precio) {
        this.fotoId = fotoId;
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.precio = precio;
    }
    public double getPrecioOferta() {
        return precioOferta;
    }

    public void setPrecioOferta(double precioOferta) {
        this.precioOferta = precioOferta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFotoId() {
        return fotoId;
    }

    public void setFotoId(int fotoId) {
        this.fotoId = fotoId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", fotoId=" + fotoId +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                '}';
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
