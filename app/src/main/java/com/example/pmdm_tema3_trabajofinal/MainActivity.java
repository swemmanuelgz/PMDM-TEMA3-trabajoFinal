package com.example.pmdm_tema3_trabajofinal;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pmdm_tema3_trabajofinal.adapters.ProductAdapter;
import com.example.pmdm_tema3_trabajofinal.repository.ProductRepository;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //Cogemos el repositorio usando el constructor por contexto
        ProductRepository productRepository = new ProductRepository(this);
        ProductAdapter productAdapter = new ProductAdapter(productRepository.getProductosList());

        //Cogemos el recycler
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(productAdapter);

        //Cogemos el switch y otros elementos
        Switch sw = findViewById(R.id.sw);
        ImageButton btnCartShop = findViewById(R.id.btnCartShop);
        ImageButton btnBuy = findViewById(R.id.btnBuy);
        ImageButton btnCart = findViewById(R.id.btnCart);
        ConstraintLayout mainLayout= findViewById(R.id.main);





        //Le ponemos un listener para poner el modo oscuro
        sw.setButtonDrawable(productRepository.getDrawableByName("luna"));
        sw.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mainLayout.setBackgroundColor(Color.BLACK);
               // linearLayout.setBackgroundColor(Color.BLACK);
                productAdapter.setDarkMode(true);
                btnBuy.setBackgroundColor(Color.BLACK);
                btnCartShop.setBackgroundColor(Color.BLACK);
                sw.setButtonDrawable(productRepository.getDrawableByName("sol"));


            } else {
                mainLayout.setBackgroundColor(Color.WHITE);
               // linearLayout.setBackgroundColor(Color.WHITE);
                productAdapter.setDarkMode(false);
                btnBuy.setBackgroundColor(Color.WHITE);
                btnCartShop.setBackgroundColor(Color.WHITE);
                sw.setButtonDrawable(productRepository.getDrawableByName("luna"));

            }

        });


        //productoList.add(new Producto(productRepository.getDrawableByName("iphone16"),1, "Iphone", "16 pro", 10.0));


    }
}