package com.example.pmdm_tema3_trabajofinal.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pmdm_tema3_trabajofinal.R;
import com.example.pmdm_tema3_trabajofinal.model.Producto;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    private List<Producto> productoList;
    private  boolean darkMode;

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

    public void setDarkMode(boolean darkMode) {
        this.darkMode = darkMode;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Producto producto = productoList.get(position);
        holder.imgFoto.setImageResource(producto.getFotoId());
        holder.txtId.setText("ID: "+String.valueOf(producto.getId()));
        holder.txtTitulo.setText(producto.getTitulo());
        holder.txtDescripcion.setText(producto.getDescripcion());
        holder.txtPrecio.setText(String.valueOf(producto.getPrecio()+"€"));

        if (darkMode) {
            holder.txtTitulo.setTextColor(Color.WHITE);
            holder.txtDescripcion.setTextColor(Color.WHITE);
            holder.txtPrecio.setTextColor(Color.WHITE);
            holder.txtId.setTextColor(Color.WHITE);
            holder.btnCart.setBackgroundColor(Color.BLACK);

        }else {
            holder.txtTitulo.setTextColor(Color.BLACK);
            holder.txtDescripcion.setTextColor(Color.BLACK);
            holder.txtPrecio.setTextColor(Color.BLACK);
            holder.txtId.setTextColor(Color.BLACK);
            holder.btnCart.setBackgroundColor(Color.WHITE);
        }

    }

    @Override
    public int getItemCount() {
        return productoList.size();
    }
}
