package com.example.pmdm_tema3_trabajofinal;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Layout;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pmdm_tema3_trabajofinal.adapters.ProductAdapter;
import com.example.pmdm_tema3_trabajofinal.model.Producto;
import com.example.pmdm_tema3_trabajofinal.repository.ProductRepository;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity implements ProductAdapter.OnProductSelectedListener {
    //atributos
    private TextView txtResumeOrder ;
    private TextView txtProductsContador,txtMessage;
    private Dialog dialog;
    private Button btnDialogCancelar, btnDialogConfirmar;
    private ImageButton btnCartShop, btnBuy, btnCart;
    private ConstraintLayout products;
    int contador = 0;
    private Double total = 0.0;
    private ArrayList<Producto> carritoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.txtProductsCount), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //Cogemos el repositorio usando el constructor por contexto
        ProductRepository productRepository = new ProductRepository(this);
        ProductAdapter productAdapter = new ProductAdapter(productRepository.getProductosList());
        productAdapter.setOnProductSelectedListener(this);

        //Cogemos el recycler
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(productAdapter);

        //layouts
        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_shippment);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);

        products = new ConstraintLayout(MainActivity.this);
        //Cogemos el switch y otros elementos
        txtProductsContador = findViewById(R.id.txtContadorProducts);
        txtResumeOrder = findViewById(R.id.txtResumeOrder);

        Switch sw = findViewById(R.id.sw);
         btnCartShop = findViewById(R.id.btnCartShop);
         btnBuy = findViewById(R.id.btnBuy);
         btnCart = products.findViewById(R.id.btnCart);
        ConstraintLayout mainLayout= findViewById(R.id.txtProductsCount);
        btnDialogConfirmar = dialog.findViewById(R.id.btnConfirm);
        btnDialogCancelar = dialog.findViewById(R.id.btnCancel);

      //Métodos para la compra
        btnDialogConfirmar.setOnClickListener(v -> {
            new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Compra realizada")
                    .show();
            Toast.makeText(this, "Compra realizada por " + txtResumeOrder.getText().toString() + "", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
        btnDialogCancelar.setOnClickListener(v -> {
            Toast.makeText(this, "Compra cancelada", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        btnBuy.setOnClickListener(v -> {
            txtMessage = dialog.findViewById(R.id.txtMessage);
            txtMessage.append("\n" + getCarrito());
            dialog.show();
        });



        //Le ponemos un listener para poner el modo oscuro
        sw.setButtonDrawable(productRepository.getDrawableByName("luna"));
        sw.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mainLayout.setBackgroundColor(Color.BLACK);
               // linearLayout.setBackgroundColor(Color.BLACK);
                productAdapter.setDarkMode(true);
                btnBuy.setBackgroundColor(Color.BLACK);
                btnCartShop.setBackgroundColor(Color.BLACK);
                txtProductsContador.setTextColor(Color.WHITE);
                sw.setButtonDrawable(productRepository.getDrawableByName("sol"));


            } else {
                mainLayout.setBackgroundColor(Color.WHITE);
               // linearLayout.setBackgroundColor(Color.WHITE);
                productAdapter.setDarkMode(false);
                btnBuy.setBackgroundColor(Color.WHITE);
                btnCartShop.setBackgroundColor(Color.WHITE);
                txtProductsContador.setTextColor(Color.BLACK);
                sw.setButtonDrawable(productRepository.getDrawableByName("luna"));

            }

        });


    }

    //este metodo se llama cuando se selecciona un producto y hace la suma de los precios
    @Override
    public void onProductSelected(Producto producto) {

        double precio = producto.getPrecio();
        if (txtResumeOrder.getText().toString().isEmpty()) {
            txtResumeOrder.setText("TOTAL: 0.0€");
        }
        String precioOrderStr = txtResumeOrder.getText().toString().replace("TOTAL: ","").replace("€","");
        double precioOrder = Double.parseDouble(precioOrderStr); //Pasar de String a double
         this.total = precioOrder + precio;
        String resumeOrder = String.valueOf(total);
        txtResumeOrder.setText("TOTAL: "+resumeOrder+"€");
        contador++;
        txtProductsContador.setText(String.valueOf(contador));
        carritoList.add(producto);

    }

    public StringBuilder getCarrito() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < carritoList.size(); i++) {
            builder.append("\n"+"-"+carritoList.get(i).getTitulo()+": "+carritoList.get(i).getPrecio()+"€");

        }
        return builder;
    }
}