package com.example.pmdm_tema3_trabajofinal;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pmdm_tema3_trabajofinal.adapters.CartAdapter;
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
    private ConstraintLayout products,cart;
    int contador = 0;
    private Double total = 0.0;
    private ArrayList<Producto> carritoList = new ArrayList<>();
    private RecyclerView cartRecyclerView;
    private EditText txtBuscador;
    private ProductAdapter productAdapter;
    private ProductRepository productRepository;


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
        productRepository = new ProductRepository(this);
        productAdapter = new ProductAdapter(productRepository.getProductosList());
        productAdapter.setOnProductSelectedListener(this);


        //Cogemos el recycler
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(productAdapter);

        //Cogemos el recycler
        //View cartLayout = getLayoutInflater().inflate(R.layout.cart, null);

        cart = (ConstraintLayout) getLayoutInflater().inflate(R.layout.cart, null);
         cartRecyclerView = cart.findViewById(R.id.recyclerViewCart);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartRecyclerView.setAdapter(new CartAdapter(carritoList));

        //layouts
        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_shippment);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);

        products = (ConstraintLayout) getLayoutInflater().inflate(R.layout.product, null);
        //Cogemos el switch y otros elementos
        txtProductsContador = findViewById(R.id.txtContadorProducts);
        txtResumeOrder = findViewById(R.id.txtResumeOrder);
        txtBuscador = findViewById(R.id.txtBuscador);

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
                    .setTitleText("Compra realizada por: "+total.toString()+"€")
                    .show();
            Toast.makeText(this, "Compra realizada por " + txtResumeOrder.getText().toString() + "", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
        btnDialogCancelar.setOnClickListener(v -> {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Compra cancelada")
                    .show();
            Toast.makeText(this, "Compra cancelada", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
        //Añade al mensaje los productos que hay en el carrito
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
                txtBuscador.setHintTextColor(Color.WHITE);
                txtBuscador.setTextColor(Color.WHITE);


            } else {
                mainLayout.setBackgroundColor(Color.WHITE);
               // linearLayout.setBackgroundColor(Color.WHITE);
                productAdapter.setDarkMode(false);
                btnBuy.setBackgroundColor(Color.WHITE);
                btnCartShop.setBackgroundColor(Color.WHITE);
                txtProductsContador.setTextColor(Color.BLACK);
                sw.setButtonDrawable(productRepository.getDrawableByName("luna"));
                txtBuscador.setHintTextColor(Color.BLACK);
                txtBuscador.setTextColor(Color.BLACK);

            }

        });
        //Listener para el buscador
        txtBuscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }
            //Este metodo se utiliza cuando el texto cambia
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                //Cogemos el texto
                String query = charSequence.toString().toLowerCase().trim();
                filterProducts(query);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        //Listener para el carrito
        btnCartShop.setOnClickListener(v -> {
            showCartDialog();
        });
        //Listener para borrar el carrito con una pulsacion de 2 segundos
        btnCartShop.setOnLongClickListener(v -> {
            vaciarCarrito();
            return true;
        });


    }
    //Filtramos los productos segun lo que introducamos en el buscador
    private void filterProducts(String query) {
        ArrayList<Producto> filteredList = new ArrayList<>();
        //Recorre todos los productos
        for (Producto producto : productRepository.getProductosList()) {
            if (producto.getTitulo().toLowerCase().contains(query)) {
                filteredList.add(producto);
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(MainActivity.this, "No se encontraron productos", Toast.LENGTH_SHORT).show();
        }
        productAdapter.updateProductList(filteredList);
    }

    //Metodo que ensena un dialog del carrito
    private void showCartDialog() {
        Dialog cartDialog = new Dialog(MainActivity.this);
        cartDialog.setContentView(R.layout.cart);
        cartDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        cartDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cartDialog.setCancelable(true);

        //configuramos el recyclea

        RecyclerView recyclerViewCartDialog = cartDialog.findViewById(R.id.recyclerViewCart);
        CartAdapter cartAdapter = new CartAdapter(carritoList);
        cartAdapter.notifyDataSetChanged();
        recyclerViewCartDialog.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCartDialog.setAdapter(cartAdapter);

        Button btnCerrar = cartDialog.findViewById(R.id.btnCerrar);
        btnCerrar.setOnClickListener(v -> cartDialog.dismiss());

        for (Producto producto : carritoList) {
            Log.d("MainActivity", "Producto Main: " + producto.getTitulo() + " - " + producto.getPrecio());
        }

        //Verificamos si el carrito esta vacio
        if (carritoList.isEmpty()) {
           Toast.makeText(this, "El carrito está vacío", Toast.LENGTH_SHORT).show();
            return; // Salir de la función si el carrito está vacío
        }
       // Toast.makeText(this, "El carrito no está vacío"+carritoList.get(0), Toast.LENGTH_SHORT).show();
        //Mostramos el dialog
        cartDialog.show();
    }

    //este metodo se llama cuando se selecciona un producto y hace la suma de los precios
    @Override
    public void onProductSelected(Producto producto) {
        //Coge el precio del producto seleccionado y lo suma al total y parsea los datos de nuevo para
        //Que no hayan errores de conversion
        double precio = producto.getPrecio();
        if (txtResumeOrder.getText().toString().isEmpty()) {
            txtResumeOrder.setText("TOTAL: 0.0€");
        }
        String precioOrderStr = txtResumeOrder.getText().toString().replace("TOTAL: ","").replace("€","");
        double precioOrder = Double.parseDouble(precioOrderStr); //Pasar de String a double
         this.total = precioOrder + precio;
         //Redondeamos a dos decimales con el Round
        this.total = Math.round(total * 100.0) / 100.0;
        String resumeOrder = String.valueOf(this.total);
        txtResumeOrder.setText("TOTAL: "+resumeOrder+"€");
        contador++;
        txtProductsContador.setText(String.valueOf(contador));
        carritoList.add(producto);

    }
    //Metodo que restablece el carrito
    private void vaciarCarrito() {
        carritoList.clear();
        txtResumeOrder.setText("TOTAL: 0.0€");
        contador = 0;
        txtProductsContador.setText(String.valueOf(contador));
        //Modificamos el adaptador tambien
        cartRecyclerView.getAdapter().notifyDataSetChanged();
        //Toas informativo de que se ha vaciado el carrito
        Toast.makeText(this, "Carrito vaciado", Toast.LENGTH_SHORT).show();
    }
//Es el metodo que pasa a texto los nombres de los productos en el carrito
    public StringBuilder getCarrito() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < carritoList.size(); i++) {
            builder.append("\n"+"-"+carritoList.get(i).getTitulo()+": "+carritoList.get(i).getPrecio()+"€");

        }
        return builder;
    }
}