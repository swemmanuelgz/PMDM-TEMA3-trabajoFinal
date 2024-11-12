package com.example.pmdm_tema3_trabajofinal.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pmdm_tema3_trabajofinal.R;
import com.example.pmdm_tema3_trabajofinal.model.Producto;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {
    //Lista de productos
    private ArrayList<Producto> carritoList = new ArrayList<>();
    private boolean darkMode;
    private onProductSelectedListener onProductSelectedListener;

    public CartAdapter(ArrayList<Producto> carritoList) {
        this.carritoList = carritoList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart, parent, false);
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

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        //Elementos
        Producto producto = carritoList.get(position);
        holder.skbCantidad.setMax(10);
        holder.skbCantidad.setProgress(Integer.parseInt(holder.txtCantidad.getText().toString()));
        holder.imgFoto.setImageResource(producto.getFotoId());
        holder.txtPrecio.setText(String.valueOf(producto.getPrecio() + "€"));
        holder.txtProductName.setText(producto.getTitulo());
        holder.txtCantidad.setText(String.valueOf(holder.skbCantidad.getProgress()));

        //listener para actualizar el textView de la cantidad
        holder.skbCantidad.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                holder.txtCantidad.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return carritoList.size();
    }
    public interface onProductSelectedListener {
        void onProductSelected(Producto producto);
    }
}
