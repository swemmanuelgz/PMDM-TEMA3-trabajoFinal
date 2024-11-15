package com.example.pmdm_tema3_trabajofinal.adapters;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pmdm_tema3_trabajofinal.R;

public class CartViewHolder extends RecyclerView.ViewHolder{
    ImageView imgFoto;
    TextView txtProductName,txtCantidad,txtPrecio;
    Button btnDelete;
    SeekBar skbCantidad;
    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        imgFoto = itemView.findViewById(R.id.imgProduct);
        txtProductName = itemView.findViewById(R.id.txtProductName);
        txtCantidad = itemView.findViewById(R.id.txtCantidad);
        txtPrecio = itemView.findViewById(R.id.txtPrecioCart);
        btnDelete = itemView.findViewById(R.id.btnDelete);
        skbCantidad = itemView.findViewById(R.id.skbCantidad);
    }
}
