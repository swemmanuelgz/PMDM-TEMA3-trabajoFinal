package com.example.pmdm_tema3_trabajofinal.adapters;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pmdm_tema3_trabajofinal.R;

public class ProductViewHolder extends RecyclerView.ViewHolder {
    ImageView imgFoto;
    TextView txtId, txtTitulo, txtDescripcion, txtPrecio,txtMessage;
    ImageButton btnCart;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        imgFoto = itemView.findViewById(R.id.imgFoto);
        txtId = itemView.findViewById(R.id.txtId);
        txtTitulo = itemView.findViewById(R.id.txtTitulo);
        txtDescripcion = itemView.findViewById(R.id.txtDescripcion);
        txtPrecio = itemView.findViewById(R.id.txtPrecio);
        btnCart = itemView.findViewById(R.id.btnCart);
        txtMessage = itemView.findViewById(R.id.txtMessage);
    }
}
