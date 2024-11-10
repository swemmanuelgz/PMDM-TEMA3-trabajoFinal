package com.example.pmdm_tema3_trabajofinal.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pmdm_tema3_trabajofinal.R;
import com.example.pmdm_tema3_trabajofinal.model.Producto;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    private List<Producto> productoList;

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

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Producto producto = productoList.get(position);
        holder.imgFoto.setImageResource(producto.getFotoId());
        holder.txtId.setText("ID: "+String.valueOf(producto.getId()));
        holder.txtTitulo.setText(producto.getTitulo());
        holder.txtDescripcion.setText(producto.getDescripcion());
        holder.txtPrecio.setText(String.valueOf(producto.getPrecio()));

    }

    @Override
    public int getItemCount() {
        return productoList.size();
    }
}
