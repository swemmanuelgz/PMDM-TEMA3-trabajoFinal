package com.example.pmdm_tema3_trabajofinal.adapters;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pmdm_tema3_trabajofinal.MainActivity;
import com.example.pmdm_tema3_trabajofinal.R;
import com.example.pmdm_tema3_trabajofinal.model.Ofertas;
import com.example.pmdm_tema3_trabajofinal.model.Producto;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {
    //lista de productos
    private List<Producto> productoList;
    private  boolean darkMode;
    private OnProductSelectedListener onProductSelectedListener;

    public ProductAdapter(List<Producto> productoList) {
        this.productoList = productoList;
    }



    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product, parent, false);

        return new ProductViewHolder(view);

    }
//metodo para activar el modo oscuro
    public void setDarkMode(boolean darkMode) {
        this.darkMode = darkMode;
        notifyDataSetChanged();
    }
    //Listener que detecta que producto es seleccionado
    public void setOnProductSelectedListener(OnProductSelectedListener listener) {
        this.onProductSelectedListener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Producto producto = productoList.get(position);
        Ofertas oferta = new Ofertas();
        double precioOferta = oferta.randomOferta(producto).getPrecioOferta();
        //Restablecemos el estado inicial
        holder.txtPrecio.setPaintFlags(0);
        holder.txtPrecio.setTextColor(Color.BLACK);
        holder.txtOferta.setText("");

        //configuramos datos del producto
        holder.imgFoto.setImageResource(producto.getFotoId());
        holder.txtId.setText("ID: "+String.valueOf(producto.getId()));
        holder.txtTitulo.setText(producto.getTitulo());
        holder.txtDescripcion.setText(producto.getDescripcion());
        holder.txtPrecio.setText(String.valueOf(producto.getPrecio()+"€"));

        if (precioOferta != 0) {
            //Esto es para poner la linea tachada del precio SI HAY OFERTA
            holder.txtPrecio.setPaintFlags(holder.txtPrecio.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.txtPrecio.setTextColor(Color.RED);
            holder.txtOferta.setText(String.valueOf(precioOferta+"€"));
        }

        if (darkMode) {
            holder.txtTitulo.setTextColor(Color.WHITE);
            holder.txtDescripcion.setTextColor(Color.WHITE);
            holder.txtPrecio.setTextColor(Color.WHITE);
            holder.txtOferta.setTextColor(Color.WHITE);
            holder.txtId.setTextColor(Color.WHITE);
            holder.btnCart.setBackgroundColor(Color.BLACK);


        }else {
            holder.txtTitulo.setTextColor(Color.BLACK);
            holder.txtDescripcion.setTextColor(Color.BLACK);
            holder.txtPrecio.setTextColor(Color.BLACK);
            holder.txtOferta.setTextColor(Color.BLACK);
            holder.txtId.setTextColor(Color.BLACK);
            holder.btnCart.setBackgroundColor(Color.WHITE);

        }

        //listener que detecta que producto es seleccionado
        holder.btnCart.setOnClickListener(v ->{
            if (onProductSelectedListener != null) {
                onProductSelectedListener.showCartStorage(producto);

            }
        });

    }

    @Override
    public int getItemCount() {
        return productoList.size();
    }

    public void updateProductList(ArrayList<Producto> filteredList) {
        this.productoList = filteredList;
        notifyDataSetChanged();
    }

    public interface OnProductSelectedListener {
        void onProductSelected(Producto producto);
        Boolean showCartStorage(Producto producto);

    }

}
