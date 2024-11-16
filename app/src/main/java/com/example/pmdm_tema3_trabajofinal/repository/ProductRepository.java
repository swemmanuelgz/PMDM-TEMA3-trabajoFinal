package com.example.pmdm_tema3_trabajofinal.repository;



import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.example.pmdm_tema3_trabajofinal.MainActivity;
import com.example.pmdm_tema3_trabajofinal.model.Producto;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class ProductRepository {
    //la lista de productos del repositorio
    private ArrayList<Producto> productosList = new ArrayList<>();
    private Context context;

    public ProductRepository() {
    }

    public ProductRepository(Context context) {
        this.context = context;
        getProducts();
    }

    public ArrayList<Producto> getProductosList() {
        Collections.sort(productosList);
        return productosList;
    }
    //Cogemos una imagen por su nombre
    public int getDrawableByName(String name) {
        int resId = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
        if (resId == 0) {
            Log.d("ProductRepository", "No se encontro la imagen: " + name);
        }
        return resId;
    }
    public void getProducts() {
        AssetManager assetManager = context.getAssets();
        //File file = new File("productos.txt");

        //Cogemos los productos del fichero
        try( InputStream is = assetManager.open("productos.txt");
                BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            Log.d("ProductRepository", "LA RUTA ES " + is);
            String linea;
            //construye los productos a partir de las lineas
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("/");
                String fotoName = partes[0];
                int id = Integer.parseInt(partes[1]);
                String titulo = partes[2];
                String descripcion = partes[3];
                double precio = Double.parseDouble(partes[4]);
                Producto producto = new Producto(getDrawableByName(fotoName), id, titulo, descripcion, precio);
                producto.setFotoId(getDrawableByName(fotoName));
                Log.d("ProductRepository", "Producto: " + producto.getTitulo());
                productosList.add(producto);
            }
            Log.d("ProductRepository", "TAMANIO "+productosList.size());
        } catch (Exception e) {
            Log.e("ProductRepository", "ERROR "+e.getMessage());
        }
    }


}
