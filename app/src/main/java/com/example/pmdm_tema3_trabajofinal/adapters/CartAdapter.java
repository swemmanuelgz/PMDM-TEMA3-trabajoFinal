package com.example.pmdm_tema3_trabajofinal.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pmdm_tema3_trabajofinal.R;
import com.example.pmdm_tema3_trabajofinal.model.Producto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {
    //Lista de productos
    private ArrayList<Producto> carritoList = new ArrayList<>();
    private ArrayList<Producto> uniqueProducts = new ArrayList<>();
    private ArrayList<Integer> cantidades = new ArrayList<>();
    private boolean darkMode;
    private onProductSelectedListener onProductSelectedListener;
    private OnProductDeleteListener deleteListener;

    public CartAdapter(ArrayList<Producto> carritoList) {

        procesarCarrito(carritoList);
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_product, parent, false);
        return new CartViewHolder(view);
    }

    //metodo para activar el modo oscuro
    public void setDarkMode(boolean darkMode) {
        this.darkMode = darkMode;
        notifyDataSetChanged();
    }

    //metodo para pasar la informacion del producto al listener
    public void setOnProductSelectedListener(onProductSelectedListener listener) {
        this.onProductSelectedListener = listener;
    }
    //metodo para procesar el carrito
    public void procesarCarrito(ArrayList<Producto> carritoList) {
        HashMap<Integer, Integer> contadorProductos = new HashMap<>();
        HashMap<Integer, Producto> mapProductos = new HashMap<>();

        //Contador de cantidad por producto y que no se repitan
        for (Producto producto : carritoList) {
            if (contadorProductos.containsKey(producto.getId())) {
                contadorProductos.put(producto.getId(), contadorProductos.get(producto.getId()) + 1);
            } else {
                contadorProductos.put(producto.getId(), 1);
                mapProductos.put(producto.getId(), producto);
            }
        }
        //Conversión de HashMap a ArrayList
        for (Map.Entry<Integer, Producto> entry : mapProductos.entrySet()) {
            uniqueProducts.add(entry.getValue());
            cantidades.add(contadorProductos.get(entry.getKey()));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {

        //Elementos
        if (carritoList.isEmpty()) {
            Log.d("CartAdapter", "El carrito esta vacio");
        }

        Producto producto = uniqueProducts.get(position);
        int cantidad = cantidades.get(position);

        for (int i = 0; i < carritoList.size(); i++) {
            Log.d("CartAdapter", "Tamañom del carritoList: " + carritoList.size());
            Log.d("CartAdapter", "Tamaño de cantidades: " + cantidades.size());
        }
        holder.skbCantidad.setMax(10);
        holder.skbCantidad.setProgress(cantidad);
        holder.imgFoto.setImageResource(producto.getFotoId());
        holder.txtPrecio.setText(producto.getPrecio() + "€");
        holder.txtProductName.setText(producto.getTitulo());
        holder.txtCantidad.setText(String.valueOf(holder.skbCantidad.getProgress()));
        //listener para eliminar el producto
        holder.btnDelete.setOnClickListener(v -> {

            try {
                int position2 = holder.getAdapterPosition();
                if (position2 != RecyclerView.NO_POSITION) {
                    Producto productoBorrar = uniqueProducts.get(position2);
                    //eliminar el producto y su cantidad
                    uniqueProducts.remove(position2);
                    cantidades.remove(position2);
                    //cantidades.remove(holder.skbCantidad.getProgress());
                    if (deleteListener != null) {
                        deleteListener.onProductDeleted(productoBorrar);
                    }
                    //Notificamos el cambio
                    notifyItemRemoved(holder.getAdapterPosition());
                    Log.d("CartAdapter", "Producto eliminado: " + producto.toString());
                }

            } catch (Exception e) {
                throw new RuntimeException("Error al eliminar el producto", e);
            }

        });
        //listener para actualizar el textView de la cantidad
        holder.skbCantidad.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    holder.txtCantidad.setText(String.valueOf(progress));
                    int adapterPosition = holder.getAdapterPosition();
                    if (adapterPosition != RecyclerView.NO_POSITION && adapterPosition < carritoList.size()) {
                        cantidades.set(adapterPosition, progress);
                        Log.d("CartAdapter", "Cantidad Actualizada: " + progress);
                    }else {
                        Log.d("CartAdapter", "Error al actualizar la cantidad");
                    }


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            //metodo para actualizar la cantidad cuando se suelta el seekbar
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int adapterPosition = holder.getAdapterPosition();

                if (adapterPosition != RecyclerView.NO_POSITION) {
                    notifyItemChanged(adapterPosition);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return uniqueProducts.size();
    }
    public interface onProductSelectedListener {
        void onProductSelected(Producto producto);
    }
    // Añade un listener para comunicar la eliminación
    public interface OnProductDeleteListener {
        void onProductDeleted(Producto producto);
    }


    // Método para establecer el listener
    public void setOnProductDeleteListener(OnProductDeleteListener listener) {
        this.deleteListener = listener;
    }
}
